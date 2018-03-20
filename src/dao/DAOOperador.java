package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Operador;

public class DAOOperador {
public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOOperador()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Operador> getOperadores() throws SQLException, Exception {
		ArrayList<Operador> Operadores = new ArrayList<Operador>();

		String sql = String.format("SELECT * FROM %1$s.OPERADOR WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Operadores.add(convertResultSetToOperador(rs));
		}
		return Operadores;
	}
	
	public Operador findOperadorById(Integer id) throws SQLException, Exception 
	{
		Operador Operador = null;

		String sql = String.format("SELECT * FROM %1$s.OPERADOR WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Operador = convertResultSetToOperador(rs);
		}

		return Operador;
	}
	
	public void addOperador(Operador Operador) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.OPERADOR (ID,  NOMBRE, CORREO,IDENTIFICACION,TIPO) VALUES (%2$s, %3$s, '%4$s', '%5$s',%6$s)", 
									USUARIO, 
									Operador.getId(), 
									Operador.getIdentificacion(),
									Operador.getNombre(),
									Operador.getCorreo(), 
									Operador.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateOperador(Operador Operador) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.OPERADOR ", USUARIO));
		sql.append (String.format (
				"SET IDENTIFICACION= %1$s, NOMBRE = '%2$s', CORREO = '%3$s', TIPO=%4$s ",
				Operador.getIdentificacion(), Operador.getNombre(),
				Operador.getCorreo(), Operador.getTipo()));
		sql.append ("WHERE ID = " + Operador.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteOperador(Operador Operador) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.OPERADOR WHERE ID = %2$d", USUARIO, Operador.getId());

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
	
	public Operador convertResultSetToOperador(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		Integer identificacion=resultSet.getInt("IDENTIFICACION");
		String nombre=resultSet.getString("NOMBRE");
		String correo=resultSet.getString("CORREO");
		Integer tipo=resultSet.getInt("TIPO");
		Operador Operador=new Operador(id,nombre, correo, identificacion,tipo);
		return Operador;
	}
}
