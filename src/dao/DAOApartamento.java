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

		String sql = String.format("SELECT * FROM %1$s.APARTAMENTO WHERE ROWNUM <= 50", USUARIO);

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
		System.out.println(id);
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
		
		Apartamento.setOfertada(true);
		Apartamento.setVecesSolicitada(0);
		Integer booleano=0;
		Integer habilitada=0;
		if(Apartamento.isOfertada())
			booleano=1;
		else
			booleano=0;
		
		if(Apartamento.isHabilitada())
			habilitada=1;
		else
			habilitada=0;
		String sql = String.format("INSERT INTO %1$s.APARTAMENTO (ID, DIRECCION, CAPACIDAD, DESCRIPCION,TIPO,VECES_SOLICITADA,OFERTADA,HABILITADA) VALUES (%2$s, '%3$s', '%4$s', '%5$s',%6$s,%7$s,%8$s,%9$s)", 
									USUARIO, 
									Apartamento.getId(), 
									Apartamento.getDireccion(),
									Apartamento.getCacpacidad(),
									Apartamento.getDescripcion(), 
									Apartamento.getTipo(),
									Apartamento.getVecesSolicitada(),
									booleano, habilitada);
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateApartamento(Apartamento Apartamento) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		Integer booleano=0;
		Integer habilitada=0;
		if(Apartamento.isOfertada())
			booleano=1;
		else
			booleano=0;
		if(Apartamento.isHabilitada())
			habilitada=1;
		else
			habilitada=0;
		sql.append (String.format ("UPDATE %s.APARTAMENTO ", USUARIO));
		sql.append (String.format (
				"SET DIRECCION= '%1$s', CAPACIDAD = %2$s, DESCRIPCION = '%3$s', TIPO=%4$s, VECES_SOLICITADA=%5$S, OFERTADA=%6$S, HABILITADA=%7$S",
				Apartamento.getDireccion(), Apartamento.getCacpacidad(),
				Apartamento.getDescripcion(), Apartamento.getTipo(),Apartamento.getVecesSolicitada(), booleano, habilitada));
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
		Integer vecesSolicitada=resultSet.getInt("VECES_SOLICITADA");
		Integer ofertada=resultSet.getInt("OFERTADA");
		Integer habilitada=resultSet.getInt("HABILITADA");
		boolean booleano;
		boolean habilitadaboo;
		if(ofertada==1)
			booleano=true;
		else
			booleano=false;
		if(habilitada==1)
			habilitadaboo=true;
		else
			habilitadaboo=false;
		Apartamento Apartamento=new Apartamento(id, direccion, capacidad,descripcion,tipo,vecesSolicitada,booleano, habilitadaboo);
		return Apartamento;
	}
	
}
