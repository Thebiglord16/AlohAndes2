package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Habitacion;

public class DAOHabitacion {
public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOHabitacion()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Habitacion> getHabitacions() throws SQLException, Exception {
		ArrayList<Habitacion> Habitacions = new ArrayList<Habitacion>();

		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Habitacions.add(convertResultSetToHabitacion(rs));
		}
		return Habitacions;
	}
	
	public Habitacion findHabitacionById(Integer id) throws SQLException, Exception 
	{
		Habitacion Habitacion = null;

		String sql = String.format("SELECT * FROM %1$s.HABITACION WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Habitacion = convertResultSetToHabitacion(rs);
		}

		return Habitacion;
	}
	
	public void addHabitacion(Habitacion Habitacion) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.HABITACION (ID, DIRECCION, CAPACIDAD, DESCRIPCION,TIPO) VALUES (%2$s, %3$s, '%4$s', '%5$s',%6$s)", 
									USUARIO, 
									Habitacion.getId(), 
									Habitacion.getDireccion(),
									Habitacion.getCacpacidad(),
									Habitacion.getDescripcion(), 
									Habitacion.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateHabitacion(Habitacion Habitacion) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.HABITACION ", USUARIO));
		sql.append (String.format (
				"SET DIRECCION= %1$s, CAPACIDAD = '%2$s', DESCRIPCION = '%3$s', TIPO=%4$s ",
				Habitacion.getDireccion(), Habitacion.getCacpacidad(),
				Habitacion.getDescripcion(), Habitacion.getTipo()));
		sql.append ("WHERE ID = " + Habitacion.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteHabitacion(Habitacion Habitacion) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.HABITACION WHERE ID = %2$d", USUARIO, Habitacion.getId());

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
	
	public Habitacion convertResultSetToHabitacion(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		String direccion=resultSet.getString("DIRECCION"); 
		Integer capacidad=resultSet.getInt("CAPACIDAD");
		String descripcion=resultSet.getString("DESCRIPCION");
		Integer tipo=resultSet.getInt("TIPO");
		Habitacion Habitacion=new Habitacion(id, direccion, capacidad,descripcion,tipo);
		return Habitacion;
	}
	
}
