package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ApartamentoServicio;
import vos.Servicio;
public class DAOApartamentoServicio {

public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	private DAOApartamento daoApto;
	private DAOServicio daoServ;
	
	public DAOApartamentoServicio()
	{
		this.recursos=new ArrayList<>();
		daoApto=new DAOApartamento();
		daoServ=new DAOServicio();
	}
	
	public ArrayList<ApartamentoServicio> getApartamentoServicios() throws SQLException, Exception {
		ArrayList<ApartamentoServicio> apServicios = new ArrayList<ApartamentoServicio>();
		String sql = String.format("SELECT * FROM %1$s.APARTAMENTO_SERVICIO WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			apServicios.add(convertResultSetToApartamentoServicio(rs));
		}
		
		return convertResultSetToApartamentoServicioCompleto(apServicios);
	}
	
	public ApartamentoServicio findApartamentoServicioById(Integer id) throws SQLException, Exception 
	{
		ArrayList<ApartamentoServicio> apServicios = new ArrayList<>();

		String sql = String.format("SELECT * FROM %1$s.APARTAMENTO_SERVICIO WHERE APARTAMENTO_ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			apServicios.add(convertResultSetToApartamentoServicio(rs));
		}

		return convertResultSetToApartamentoServicioCompleto(apServicios).get(0);
	}
	
	public void addApartamentoServicio(ApartamentoServicio apServicio) throws SQLException, Exception {
		for(Servicio x: apServicio.getServicios()){
			String sql = String.format("INSERT INTO %1$s.APARTAMENTO_SERVICIO (APARTAMENTO_ID, SERVICIO_ID) VALUES (%2$s, %3$s)", 
									USUARIO, 
									apServicio.getApartamento().getId(), x.getId()); 
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		}
	}
	
	public void updateApartamentoServicio (ApartamentoServicio apServicio) throws SQLException, Exception {
		
		StringBuilder sql = new StringBuilder();
		for(Servicio x: apServicio.getServicios()){
		sql.append (String.format ("UPDATE %s.APARTAMENTO_SERVICIO", USUARIO));
		sql.append (String.format (
				"SET APARTAMENTO_ID= %1$s, SERVICIO_ID = %2$s",
				apServicio.getApartamento().getId(), x.getId()));
		sql.append ("WHERE APARTAMENTO_ID = " + apServicio.getApartamento().getId()+"WHERE APARTAMENTO_ID ="+x.getId());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	}
	
	public void deleteApartamentoServicio(ApartamentoServicio apServicio) throws SQLException, Exception {
		for(Servicio x: apServicio.getServicios()){
		String sql = String.format("DELETE FROM %1$s.APARTAMENTO_SERVICIO WHERE APARTAMENTO_ID = %2$s AND SERVICIO_ID = %2$s", USUARIO, apServicio.getApartamento().getId(), x.getId());

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
	
	public ApartamentoServicio convertResultSetToApartamentoServicio(ResultSet resultSet) throws Exception {
	
		Integer id_apartamento=resultSet.getInt("APARTAMENTO_ID");
		Integer id_servicio=resultSet.getInt("SERVICIO_ID");
		ArrayList<Servicio> servicios=new ArrayList<>();
		servicios.add(daoServ.findServicioById(id_servicio));
		ApartamentoServicio apServicio=new ApartamentoServicio(daoApto.findApartamentoById(id_apartamento),servicios);
		return apServicio;
	}
	
	public ArrayList<ApartamentoServicio> convertResultSetToApartamentoServicioCompleto(ArrayList<ApartamentoServicio> apServicios) throws SQLException {
		
		
		ArrayList<ApartamentoServicio> apServicios2=new ArrayList<>();
		for(ApartamentoServicio x: apServicios)
		{
			ArrayList<Servicio> servicios=new ArrayList<>();
			for(Servicio a: x.getServicios())
			{
				servicios.add(a);
			}
			apServicios2.add(new ApartamentoServicio(x.getApartamento(), servicios));
		}
		return apServicios2;
	}
}
