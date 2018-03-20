package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Servicio;

public class DAOServicio {
public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOServicio()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Servicio> getServicios() throws SQLException, Exception {
		ArrayList<Servicio> Servicios = new ArrayList<Servicio>();

		String sql = String.format("SELECT * FROM %1$s.SERVICIO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Servicios.add(convertResultSetToServicio(rs));
		}
		return Servicios;
	}
	
	public Servicio findServicioById(Integer id) throws SQLException, Exception 
	{
		Servicio Servicio = null;

		String sql = String.format("SELECT * FROM %1$s.SERVICIO WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Servicio = convertResultSetToServicio(rs);
		}

		return Servicio;
	}
	
	public void addServicio(Servicio Servicio) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.SERVICIO (ID, COSTO, NOMBRE, DESCRIPCION) VALUES (%2$s, %3$s, '%4$s', '%5$s')", 
									USUARIO, 
									Servicio.getId(), 
									Servicio.getCosto(),
									Servicio.getNombre(),
									Servicio.getDescripcion());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateServicio(Servicio Servicio) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.SERVICIO ", USUARIO));
		sql.append (String.format (
				"SET COSTO= %1$s, NOMBRE = '%2$s', DESCRIPCION = '%3$s'",
				Servicio.getCosto(), Servicio.getNombre(),
				Servicio.getDescripcion()));
		sql.append ("WHERE ID = " + Servicio.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteServicio(Servicio Servicio) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.SERVICIO WHERE ID = %2$d", USUARIO, Servicio.getId());

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
	
	public Servicio convertResultSetToServicio(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		Double costo=resultSet.getDouble("COSTO"); 
		String nombre=resultSet.getString("NOMBRE");
		String descripcion= resultSet.getString("DESCRIPCION");
		Servicio Servicio=new Servicio(id, costo, nombre,descripcion);
		return Servicio;
	}
	
}
