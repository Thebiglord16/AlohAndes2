package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ContratoApartamento;
import vos.Apartamento;
import vos.Contrato;


public class DAOContratoApartamento {

	public final static String USUARIO="ISIS2304A541810";

	private ArrayList<Object> recursos;

	private Connection conn;
	
	private DAOContrato DaoCon;
	
	private DAOApartamento daoApto;

	public DAOContratoApartamento()
	{
		this.recursos=new ArrayList<>();
		this.daoApto=new DAOApartamento();
		this.DaoCon =new DAOContrato();
	}

	public ArrayList<ContratoApartamento> getContratoApartamentos() throws SQLException, Exception {
		ArrayList<ContratoApartamento> ContratoApartamentos = new ArrayList<ContratoApartamento>();

		String sql = String.format("SELECT * FROM %1$s.CONTRATO_APARTAMENTO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			ContratoApartamentos.add(convertResultSetToContratoApartamento(rs));
		}
		return convertResultSetToContratoApto(ContratoApartamentos);
	}

	public ContratoApartamento findContratoApartamentoById(Integer id) throws SQLException, Exception 
	{
		ArrayList<ContratoApartamento> ContratoApartamentos = new ArrayList<>();
		
		String sql = String.format("SELECT * FROM %1$s.CONTRATO_APARTAMENTO WHERE ID_APARTAMENTO = %2$d", USUARIO, id); 


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		if(rs.next()) {
			ContratoApartamentos.add( convertResultSetToContratoApartamento(rs));
		}

		return ContratoApartamentos.get(0);
	}

	public void addContratoApartamento(ContratoApartamento ContratoApartamento) throws SQLException, Exception {
		for(Contrato x:ContratoApartamento.getContratos())
		{
			String sql = String.format("INSERT INTO %1$s.CONTRATO_APARTAMENTO (ID_CONTRATO, ID_APARTAMENTO ) VALUES (%3$s, %2$s)", 
					USUARIO, 
					ContratoApartamento.getApartamento().getId(), x.getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

	}

	public void updateContratoApartamento(ContratoApartamento ContratoApartamento) throws SQLException, Exception {
		for(Contrato x: ContratoApartamento.getContratos()){
			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.CONTRATO_APARTAMENTO ", USUARIO));
			sql.append (String.format (
					"SET ID_CONTRATO= %1$s, ID_APARTAMENTO = %2$s ",
					x.getId(), ContratoApartamento.getApartamento().getId()));
			sql.append ("WHERE ID_CONTRATO = " + x.getId() +" AND ID_APARTAMENTO="+ContratoApartamento.getApartamento().getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void deleteContratoApartamento(ContratoApartamento ContratoApartamento) throws SQLException, Exception {

		for(Contrato x:ContratoApartamento.getContratos()){
			String sql = String.format("DELETE FROM %1$s.CONTRATO_APARTAMENTO WHERE ID_CONTRATO = %2$d AND ID_APARTAMENTO=%3$d ", USUARIO, x.getId(), ContratoApartamento.getApartamento().getId());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void setConn(Connection connection){
		this.conn = connection;
		this.DaoCon.setConn(connection);
		this.daoApto.setConn(connection);
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
	public ContratoApartamento convertResultSetToContratoApartamento(ResultSet resultSet) throws Exception {

		Integer id_apartamento=resultSet.getInt("ID_APARTAMENTO");
		Integer id_contrato=resultSet.getInt("ID_CONTRATO");
		ArrayList<Contrato> cons=new ArrayList<>();
		cons.add(DaoCon.findContratoById(id_contrato));
		Apartamento apto=daoApto.findApartamentoById(id_apartamento);
		ContratoApartamento ContratoApartamento=new ContratoApartamento(apto,cons);
		return ContratoApartamento;
	}
	
	public ArrayList<ContratoApartamento> convertResultSetToContratoApto(ArrayList<ContratoApartamento> cAptos) throws SQLException, Exception
	{
		ArrayList<ContratoApartamento> respuesta=new ArrayList<>();
		List<Contrato> cons= new ArrayList<>();
		int idTemp=cAptos.get(0).getApartamento().getId();
		for(ContratoApartamento x : cAptos)
		{
			if(x.getApartamento().getId()!=idTemp)
			{
				ContratoApartamento ca=buscar(respuesta,idTemp);
				if(ca!=null)
				{					
					respuesta.remove(ca);
					for(Contrato y:ca.getContratos())
						cons.add(y);
					ca.setContratos(cons);
					respuesta.add(ca);
					idTemp=x.getApartamento().getId();
					cons=new ArrayList<>();
				}
				else 
				{
					respuesta.add(new ContratoApartamento(daoApto.findApartamentoById(idTemp), cons));
					idTemp=x.getApartamento().getId();
					cons=new ArrayList<>();
				}
			}
			for(Contrato z: x.getContratos())
				cons.add(z);
		}
		ContratoApartamento ca=buscar(respuesta,idTemp);
		if(ca!=null)
		{					
			respuesta.remove(ca);
			for(Contrato y:ca.getContratos())
				cons.add(y);
			ca.setContratos(cons);
			respuesta.add(ca);
		}
		else 
		{
			respuesta.add(new ContratoApartamento(daoApto.findApartamentoById(idTemp), cons));
		}
		return respuesta;	
		
	} 
	
	public ContratoApartamento buscar(ArrayList<ContratoApartamento> lista, Integer id)
	{
		for(ContratoApartamento x : lista)
		{
			if(x.getApartamento().getId()==id)
				return x;
		}
		
		return null;
	}

}
