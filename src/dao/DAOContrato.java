package dao;

import java.sql.Connection;
import java.sql.Date;
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
	
	public ArrayList<Contrato> getContratosDescripcion(String descripcion) throws SQLException
	{
		ArrayList<Contrato> cons=new ArrayList<>();
		String sql=String.format("SELECT * FROM %1s.CONTRATO  WHERE DESCRIPCION='%2s'", USUARIO,descripcion);
		PreparedStatement stmnt=conn.prepareStatement(sql);
		recursos.add(stmnt);
		ResultSet rs=stmnt.executeQuery();
		while(rs.next())
			cons.add(convertResultSetToContrato(rs));
		return cons;
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

		String sql = String.format("INSERT INTO %1$s.Contrato (ID,  FECHA_INICIO, FECHA_FIN,DESCRIPCION,ESTADO, COSTO) VALUES (%2$s, '%3$s', '%4$s', '%5$s',%6$s,%7$s)", 
									USUARIO, 
									Contrato.getId(), 
									Contrato.getFechaInicio(),
									Contrato.getFechaFin(),
									Contrato.getDescripcion(),
									Contrato.getEstado(),
									Contrato.getCosto());
		System.out.println(sql);
	
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateContrato(Contrato Contrato) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.Contrato ", USUARIO));
		String[] arInicio=Contrato.getFechaInicio().split("-");
		String fechaInicio=arInicio[2];
		for(int i=1;i>=0;i--)
		{
			fechaInicio=fechaInicio+"/"+arInicio[i];
		}
		String[] arFin=Contrato.getFechaInicio().split("-");
		String fechaFin=arFin[2];
		for(int i=1;i>=0;i--)
		{
			fechaFin=fechaFin+"/"+arFin[i];
		}
		if(arInicio.length>1) {
		sql.append (String.format (
				"SET FECHA_INICIO= '%1$s', FECHA_FIN = '%2$s', DESCRIPCION = '%3$s', ESTADO=%4$s, COSTO=%5$S ",
				fechaInicio, fechaFin,
				Contrato.getDescripcion(), Contrato.getEstado(), Contrato.getCosto()));
		sql.append ("WHERE ID = " + Contrato.getId ());
		}
		else {
			sql.append (String.format (
				"SET FECHA_INICIO= '%1$s', FECHA_FIN = '%2$s', DESCRIPCION = '%3$s', ESTADO=%4$s, COSTO=%5$S ",
				Contrato.getFechaInicio(), Contrato.getFechaFin(),
				Contrato.getDescripcion(), Contrato.getEstado(), Contrato.getCosto()));
		sql.append ("WHERE ID = " + Contrato.getId ());
		}
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
		String fechaInicio=resultSet.getDate("FECHA_INICIO").toString();
		String fechaFin=resultSet.getDate("FECHA_FIN").toString();
		String descripcion=resultSet.getString("DESCRIPCION");
		Integer estado=resultSet.getInt("ESTADO");
		Double costo=resultSet.getDouble("COSTO");
		Contrato Contrato=new Contrato(id,fechaInicio, fechaFin, descripcion,estado,costo);
		return Contrato;
	}

	
}
