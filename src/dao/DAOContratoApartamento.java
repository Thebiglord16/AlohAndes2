package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ContratoApartamento;
import vos.Contrato;

public class DAOContratoApartamento {
	
public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	private DAOApartamento daoApto;
	private DAOContrato daoContrato;
	
	public DAOContratoApartamento()
	{
		this.recursos=new ArrayList<>();
		daoApto=new DAOApartamento();
		daoContrato=new DAOContrato();
	}
	
	public ArrayList<ContratoApartamento> getContratoApartamentos() throws SQLException, Exception {
		ArrayList<ContratoApartamento> apContratos = new ArrayList<ContratoApartamento>();
		String sql = String.format("SELECT * FROM %1$s.CONTRATO_APARTAMENTO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			apContratos.add(convertResultSetToContratoApartamento(rs));
		}
		
		return convertResultSetToContratoApartamentoCompleto(apContratos);
	}
	
	public ContratoApartamento findContratoApartamentoById(Integer id) throws SQLException, Exception 
	{
		ArrayList<ContratoApartamento> apContratos = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.CONTRATO_APARTAMENTO WHERE APARTAMENTO_ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			apContratos.add(convertResultSetToContratoApartamento(rs));
		}

		return convertResultSetToContratoApartamentoCompleto(apContratos).get(0);
	}
	
	public void addContratoApartamento(ContratoApartamento apContrato) throws SQLException, Exception {
		for(Contrato x: apContrato.getContratos()){
			String sql = String.format("INSERT INTO %1$s.CONTRATO_APARTAMENTO (APARTAMENTO_ID, CONTRATO_ID) VALUES (%2$s, %3$s)", 
									USUARIO, 
									apContrato.getApartamento().getId(), x.getId()); 
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		}
	}
	
	public void updateContratoApartamento (ContratoApartamento apContrato) throws SQLException, Exception {
		
		StringBuilder sql = new StringBuilder();
		for(Contrato x: apContrato.getContratos()){
		sql.append (String.format ("UPDATE %s.CONTRATO_APARTAMENTO", USUARIO));
		sql.append (String.format (
				"SET APARTAMENTO_ID= %1$s, CONTRATO_ID = %2$s",
				apContrato.getApartamento().getId(), x.getId()));
		sql.append ("WHERE APARTAMENTO_ID = " + apContrato.getApartamento().getId()+"WHERE APARTAMENTO_ID ="+x.getId());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	}
	
	public void deleteContratoApartamento(ContratoApartamento apContrato) throws SQLException, Exception {
		for(Contrato x: apContrato.getContratos()){
		String sql = String.format("DELETE FROM %1$s.CONTRATO_APARTAMENTO WHERE APARTAMENTO_ID = %2$s AND Contrato_ID = %2$s", USUARIO, apContrato.getApartamento().getId(), x.getId());

		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	}
	
	public void setConn(Connection connection){
		this.conn = connection;
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
	
		Integer id_apartamento=resultSet.getInt("APARTAMENTO_ID");
		Integer id_Contrato=resultSet.getInt("CONTRATO_ID");
		ArrayList<Contrato> Contratos=new ArrayList<>();
		Contratos.add(daoContrato.findContratoById(id_Contrato));
		ContratoApartamento apContrato=new ContratoApartamento(daoApto.findApartamentoById(id_apartamento),Contratos);
		return apContrato;
	}
	
	public ArrayList<ContratoApartamento> convertResultSetToContratoApartamentoCompleto(ArrayList<ContratoApartamento> apContratos) throws SQLException {
		
		
		ArrayList<ContratoApartamento> apContratos2=new ArrayList<>();
		for(ContratoApartamento x: apContratos)
		{
			ArrayList<Contrato> Contratos=new ArrayList<>();
			for(Contrato a: x.getContratos())
			{
				Contratos.add(a);
			}
			apContratos2.add(new ContratoApartamento(x.getApartamento(), Contratos));
		}
		return apContratos2;
	}

}
