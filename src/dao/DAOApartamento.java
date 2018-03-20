package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Apartamento;

public class DAOApartamento {

public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOApartamento()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Apartamento> getApartamentos() throws SQLException, Exception {
		ArrayList<Apartamento> Apartamentos = new ArrayList<Apartamento>();

		String sql = String.format("SELECT * FROM %1$s.APARTEMENTO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Apartamentos.add(convertResultSetToApartamento(rs));
		}
		return Apartamentos;
	}
	
	public Apartamento findApartamentoById(Integer id) throws SQLException, Exception 
	{
		Apartamento Apartamento = null;

		String sql = String.format("SELECT * FROM %1$s.APARTAMENTO WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Apartamento = convertResultSetToApartamento(rs);
		}

		return Apartamento;
	}
	
	public void addApartamento(Apartamento Apartamento) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.APARTAMENTO (ID, DIRECCION, CAPACIDAD, DESCRIPCION,TIPO) VALUES (%2$s, %3$s, '%4$s', '%5$s',%6$s)", 
									USUARIO, 
									Apartamento.getId(), 
									Apartamento.getDireccion(),
									Apartamento.getCacpacidad(),
									Apartamento.getDescripcion(), 
									Apartamento.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateApartamento(Apartamento Apartamento) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.APARTAMENTO ", USUARIO));
		sql.append (String.format (
				"SET DIRECCION= %1$s, CAPACIDAD = '%2$s', DESCRIPCION = '%3$s', TIPO=%4$s ",
				Apartamento.getDireccion(), Apartamento.getCacpacidad(),
				Apartamento.getDescripcion(), Apartamento.getTipo()));
		sql.append ("WHERE ID = " + Apartamento.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteApartamento(Apartamento Apartamento) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.APARTAMENTO WHERE ID = %2$d", USUARIO, Apartamento.getId());

		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
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
	
	public Apartamento convertResultSetToApartamento(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		String direccion=resultSet.getString("DIRECCION"); 
		Integer capacidad=resultSet.getInt("CAPACIDAD");
		String descripcion=resultSet.getString("DESCRIPCION");
		Integer tipo=resultSet.getInt("TIPO");
		Apartamento Apartamento=new Apartamento(id, direccion, capacidad,descripcion,tipo);
		return Apartamento;
	}
	
}
