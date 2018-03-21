package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;
import vos.OperadorApartamento;

public class DAOOperadorApartamento {

	public final static String USUARIO="ISIS2304A541810";

	private ArrayList<Object> recursos;

	private Connection conn;
	
	private DAOOperador DaoOp;
	
	private DAOApartamento daoApto;

	public DAOOperadorApartamento()
	{
		this.recursos=new ArrayList<>();
		this.DaoOp=new DAOOperador();
		this.daoApto =new DAOApartamento();
	}

	public ArrayList<OperadorApartamento> getOperadorApartamentos() throws SQLException, Exception {
		ArrayList<OperadorApartamento> OperadorApartamentos = new ArrayList<OperadorApartamento>();

		String sql = String.format("SELECT * FROM %1$s.OPERADOR_APARTAMENTO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			OperadorApartamentos.add(convertResultSetToOperadorApartamento(rs));
		}
		return convertResultSetToContratoApto(OperadorApartamentos);
	}

	public OperadorApartamento findOperadorApartamentoById(Integer id) throws SQLException, Exception 
	{
		ArrayList<OperadorApartamento> OperadorApartamentos = new ArrayList<>();
		
		String sql = String.format("SELECT * FROM %1$s.OPERADOR_APARTAMENTO WHERE ID_OPERADOR = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			OperadorApartamentos.add( convertResultSetToOperadorApartamento(rs));
		}

		return OperadorApartamentos.get(0);
	}

	public void addOperadorApartamento(OperadorApartamento OperadorApartamento) throws SQLException, Exception {
		for(Apartamento x:OperadorApartamento.getApartamentos())
		{
			String sql = String.format("INSERT INTO %1$s.OPERADOR_APARTAMENTO (ID_OPERADOR, ID_APARTAMENTO) VALUES (%2$d, %3$d)", 
					USUARIO, 
					OperadorApartamento.getOperador().getId(), x.getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

	}

	public void updateOperadorApartamento(OperadorApartamento OperadorApartamento) throws SQLException, Exception {
		for(Apartamento x: OperadorApartamento.getApartamentos()){
			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.OPERADOR_APARTAMENTO ", USUARIO));
			sql.append (String.format (
					"SET ID_APARTAMENTO= %1$d, ID_OPERADOR = %2$d ",
					x.getId(), OperadorApartamento.getOperador().getId()));
			sql.append ("WHERE ID_APARTAMENTO= " + x.getId() +" AND ID_OPERADOR="+OperadorApartamento.getOperador().getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void deleteOperadorApartamento(OperadorApartamento OperadorApartamento) throws SQLException, Exception {

		for(Apartamento x:OperadorApartamento.getApartamentos()){
			String sql = String.format("DELETE FROM %1$s.OPERADOR_HABITACION WHERE ID_APARTAMENTO= %2$d AND ID_OPERADOR=%3$d", USUARIO, x.getId(), OperadorApartamento.getOperador().getId());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void setConn(Connection connection){
		this.conn = connection;
		daoApto.setConn(connection);
		DaoOp.setConn(conn);
	}

	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	public OperadorApartamento convertResultSetToOperadorApartamento(ResultSet resultSet) throws Exception {

		Integer id_apartamento=resultSet.getInt("ID_APARTAMENTO");
		Integer id_operador=resultSet.getInt("ID_OPERADOR");
		ArrayList<Apartamento> aptos=new ArrayList<>();
		aptos.add(daoApto.findApartamentoById(id_apartamento));
		OperadorApartamento OperadorApartamento=new OperadorApartamento(DaoOp.findOperadorById(id_operador),aptos);
		return OperadorApartamento;
	}
	
	public ArrayList<OperadorApartamento> convertResultSetToContratoApto(ArrayList<OperadorApartamento> oAptos) throws SQLException, Exception
	{
		ArrayList<OperadorApartamento> respuesta=new ArrayList<>();
		ArrayList<Apartamento> aptos= new ArrayList<>(); 
		int idTemp=oAptos.get(0).getOperador().getId();
		for(OperadorApartamento x: oAptos){
			if(x.getOperador().getId()!=idTemp)
			{
				respuesta.add(new OperadorApartamento(DaoOp.findOperadorById(idTemp), aptos));
				aptos=new ArrayList<>();
				idTemp=x.getOperador().getId();
			}
			for(Apartamento a: x.getApartamentos())
			{
				aptos.add(a);
			}
		}
		return respuesta;	
		
	} 
	
}
