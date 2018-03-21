package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.OperadorHabitacion;
import vos.Habitacion;
public class DAOOperadorHabitacion {
	public final static String USUARIO="ISIS2304A541810";

	private ArrayList<Object> recursos;

	private Connection conn;
	
	private DAOOperador DaoOp;
	
	private DAOHabitacion daoHab;

	public DAOOperadorHabitacion()
	{
		this.recursos=new ArrayList<>();
		this.DaoOp=new DAOOperador();
		this.daoHab =new DAOHabitacion();
	}

	public ArrayList<OperadorHabitacion> getOperadorHabitacions() throws SQLException, Exception {
		ArrayList<OperadorHabitacion> OperadorHabitacions = new ArrayList<OperadorHabitacion>();

		String sql = String.format("SELECT * FROM %1$s.OPERADOR_HABITACION WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			OperadorHabitacions.add(convertResultSetToOperadorHabitacion(rs));
		}
		return convertResultSetToContratoApto(OperadorHabitacions);
	}

	public OperadorHabitacion findOperadorHabitacionById(Integer id) throws SQLException, Exception 
	{
		ArrayList<OperadorHabitacion> OperadorHabitacions = new ArrayList<>();
		
		String sql = String.format("SELECT * FROM %1$s.OPERADOR_HABITACION WHERE ID_OPERADOR = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			OperadorHabitacions.add( convertResultSetToOperadorHabitacion(rs));
		}

		return OperadorHabitacions.get(0);
	}

	public void addOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws SQLException, Exception {
		for(Habitacion x:OperadorHabitacion.getHabitaciones())
		{
			String sql = String.format("INSERT INTO %1$s.OPERADOR_HABITACION (ID_OPERADOR, ID_HABITACION) VALUES (%2$d, %3$d)", 
					USUARIO, 
					OperadorHabitacion.getOperador().getId(), x.getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

	}

	public void updateOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws SQLException, Exception {
		for(Habitacion x: OperadorHabitacion.getHabitaciones()){
			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.OPERADOR_HABITACION ", USUARIO));
			sql.append (String.format (
					"SET ID_HABITACION= %1$d, ID_OPERADOR = %2$d ",
					x.getId(), OperadorHabitacion.getOperador().getId()));
			sql.append ("WHERE ID_HABITACION = " + x.getId() +" AND ID_OPERADOR="+OperadorHabitacion.getOperador().getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void deleteOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws SQLException, Exception {

		for(Habitacion x:OperadorHabitacion.getHabitaciones()){
			String sql = String.format("DELETE FROM %1$s.OPERADOR_HABITACION WHERE ID_HABITACION= %2$d AND ID_OPERADOR=%3$d", USUARIO, x.getId(), OperadorHabitacion.getOperador().getId());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void setConn(Connection connection){
		this.conn = connection;
		this.conn = connection;
		daoHab.setConn(connection);
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

	public OperadorHabitacion convertResultSetToOperadorHabitacion(ResultSet resultSet) throws Exception {

		Integer id_habitacion=resultSet.getInt("ID_HABITACION");
		Integer id_operador=resultSet.getInt("ID_OPERADOR");
		ArrayList<Habitacion> habs=new ArrayList<>();
		habs.add(daoHab.findHabitacionById(id_habitacion));
		OperadorHabitacion OperadorHabitacion=new OperadorHabitacion(DaoOp.findOperadorById(id_operador),habs);
		return OperadorHabitacion;
	}
	
	public ArrayList<OperadorHabitacion> convertResultSetToContratoApto(ArrayList<OperadorHabitacion> oHabs) throws SQLException, Exception
	{
		ArrayList<OperadorHabitacion> respuesta=new ArrayList<>();
		ArrayList<Habitacion> habs= new ArrayList<>(); 
		int idTemp=oHabs.get(0).getOperador().getId();
		for(OperadorHabitacion x: oHabs){
			if(x.getOperador().getId()!=idTemp)
			{
				respuesta.add(new OperadorHabitacion(DaoOp.findOperadorById(idTemp), habs));
				habs=new ArrayList<>();
				idTemp=x.getOperador().getId();
			}
			for(Habitacion a: x.getHabitaciones())
			{
				habs.add(a);
			}
		}
		return respuesta;	
		
	} 
}
