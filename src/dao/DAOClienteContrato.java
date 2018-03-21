package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ApartamentoServicio;
import vos.ClienteContrato;
import vos.Contrato;


public class DAOClienteContrato {
public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	private DAOCliente daoCliente;
	private DAOContrato daoContrato;
	
	public DAOClienteContrato()
	{
		this.recursos=new ArrayList<>();
		daoCliente=new DAOCliente();
		daoContrato=new DAOContrato();
	}
	
	public ArrayList<ClienteContrato> getClienteContratos() throws SQLException, Exception {
		ArrayList<ClienteContrato> clContratos = new ArrayList<ClienteContrato>();
		String sql = String.format("SELECT * FROM %1$s.CLIENTE_CONTRATO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clContratos.add(convertResultSetToClienteContrato(rs));
		}
		
		return convertResultSetToClienteContratoCompleto(clContratos);
	}
	
	public ClienteContrato findClienteContratoById(Integer id) throws SQLException, Exception 
	{
		ArrayList<ClienteContrato> clContratos = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.CLIENTE_CONTRATO WHERE CLIENTE_ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clContratos.add(convertResultSetToClienteContrato(rs));
		}

		return convertResultSetToClienteContratoCompleto(clContratos).get(0);
	}
	
	public void addClienteContrato(ClienteContrato clContrato) throws SQLException, Exception {
		for(Contrato x: clContrato.getContratos()){
			String sql = String.format("INSERT INTO %1$s.CLIENTE_CONTRATO (CLIENTE_ID, CONTRATO_ID) VALUES (%2$s, %3$s)", 
									USUARIO, 
									clContrato.getCliente().getId(), x.getId()); 
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		}
	}
	
	public void updateClienteContrato (ClienteContrato clContratos) throws SQLException, Exception {
		
		StringBuilder sql = new StringBuilder();
		for(Contrato x: clContratos.getContratos()){
		sql.append (String.format ("UPDATE %s.CLIENTE_CONTRATO", USUARIO));
		sql.append (String.format (
				"SET CLIENTE_ID= %1$s, CONTRATO_ID = %2$s",
				clContratos.getCliente().getId(), x.getId()));
		sql.append ("WHERE CLIENTE_ID = " + clContratos.getCliente().getId()+"WHERE CONTRATO_ID ="+x.getId());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	}
	
	public void deleteClienteContrato(ClienteContrato clContratos) throws SQLException, Exception {
		for(Contrato x: clContratos.getContratos()){
		String sql = String.format("DELETE FROM %1$s.CLIENTE_CONTRATO WHERE CLIENTE_ID = %2$s AND CONTRATO_ID = %2$s", USUARIO, clContratos.getCliente().getId(), x.getId());

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
	
	public ClienteContrato convertResultSetToClienteContrato(ResultSet resultSet) throws Exception {
	
		Integer id_cliente=resultSet.getInt("CLIENTE_ID");
		Integer id_contrato=resultSet.getInt("CONTRATO_ID");
		ArrayList<Contrato> contratos = new ArrayList<>();
		contratos.add(daoContrato.findContratoById(id_contrato));
		ClienteContrato clContrato = new ClienteContrato(daoCliente.findClienteById(id_cliente), contratos);
		return clContrato;
	}
	
	public ArrayList<ClienteContrato> convertResultSetToClienteContratoCompleto(ArrayList<ClienteContrato> clContratos) throws SQLException {
		
		
		ArrayList<ClienteContrato> clContratos2 = new ArrayList<>();
		for(ClienteContrato x: clContratos)
		{
			ArrayList<Contrato> contratos = new ArrayList<>();
			for(Contrato a: x.getContratos())
			{
				contratos.add(a);
			}
			clContratos2.add(new ClienteContrato(x.getCliente(), contratos));
		}
		return clContratos2;
	}

}
