package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import vos.Contrato;

public class DAOContrato {

public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOContrato()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Contrato> getContratos() throws SQLException, Exception {
		ArrayList<Contrato> Contratos = new ArrayList<Contrato>();

		String sql = String.format("SELECT * FROM %1$s.CONTRATO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Contratos.add(convertResultSetToContrato(rs));
		}
		return Contratos;
	}
	
	public Contrato findContratoById(Integer id) throws SQLException, Exception 
	{
		Contrato Contrato = null;

		String sql = String.format("SELECT * FROM %1$s.CONTRATO WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Contrato = convertResultSetToContrato(rs);
		}

		return Contrato;
	}
	
	public void addContrato(Contrato Contrato) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.Contrato (ID,  FECHA_INICIO, FECHA_FIN,DESCRIPCION,ESTADO) VALUES (%2$s, %3$s, '%4$s', '%5$s',%6$s)", 
									USUARIO, 
									Contrato.getId(), 
									Contrato.getFechaInicio(),
									Contrato.getFechaFin(),
									Contrato.getDescripcion(),
									Contrato.getEstado());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateContrato(Contrato Contrato) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.Contrato ", USUARIO));
		sql.append (String.format (
				"SET FECHA_INICIO= %1$s, FECHA_FIN = '%2$s', DESCRIPCION = '%3$s', ESTADO=%4$s ",
				Contrato.getFechaInicio(), Contrato.getFechaFin(),
				Contrato.getDescripcion(), Contrato.getEstado()));
		sql.append ("WHERE ID = " + Contrato.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteContrato(Contrato Contrato) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.Contrato WHERE ID = %2$d", USUARIO, Contrato.getId());

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
	
	public Contrato convertResultSetToContrato(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		Timestamp fechaInicio=resultSet.getTimestamp("FECHA_INICIO");
		Timestamp fechaFin=resultSet.getTimestamp("FECHA_FIN");
		String descripcion=resultSet.getString("DESCRIPCION");
		Integer estado=resultSet.getInt("ESTADO");
		Contrato Contrato=new Contrato(id,fechaInicio, fechaFin, descripcion,estado);
		return Contrato;
	}

	
}
