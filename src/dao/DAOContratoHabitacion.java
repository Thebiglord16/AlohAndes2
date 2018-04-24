package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Contrato;
import vos.ContratoHabitacion;

public class DAOContratoHabitacion {
	public final static String USUARIO="ISIS2304A541810";

	private ArrayList<Object> recursos;

	private Connection conn;
	
	private DAOContrato DaoCon;
	
	private DAOHabitacion daoApto;

	public DAOContratoHabitacion()
	{
		this.recursos=new ArrayList<>();
		this.daoApto=new DAOHabitacion();
		this.DaoCon =new DAOContrato();
	}

	public ArrayList<ContratoHabitacion> getContratoHabitacions() throws SQLException, Exception {
		ArrayList<ContratoHabitacion> ContratoHabitacions = new ArrayList<ContratoHabitacion>();
		
		String sql = String.format("SELECT * FROM %1$s.CONTRATO_Habitacion WHERE ROWNUM <= 50", USUARIO);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			ContratoHabitacions.add(convertResultSetToContratoHabitacion(rs));
		}
		if(ContratoHabitacions.size()>0) {
		return convertResultSetToContratoApto(ContratoHabitacions);
		}
		else
			return ContratoHabitacions;
	}

	public ContratoHabitacion findContratoHabitacionById(Integer id) throws SQLException, Exception 
	{
		ArrayList<ContratoHabitacion> ContratoHabitacions = new ArrayList<>();
		
		String sql = String.format("SELECT * FROM %1$s.CONTRATO_Habitacion WHERE CONTRATO_ID = %2$d", USUARIO, id); 


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		if(rs.next()) {
			ContratoHabitacions.add( convertResultSetToContratoHabitacion(rs));
		}

		return ContratoHabitacions.get(0);
	}

	public void addContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws SQLException, Exception {
		for(Contrato x:ContratoHabitacion.getContratos())
		{
			String sql = String.format("INSERT INTO %1$s.CONTRATO_Habitacion (CONTRATO_ID, HABITACION_ID ) VALUES (%2$s, %3$s)", 
					USUARIO, 
					 x.getId(),ContratoHabitacion.getHabitacion().getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

	}

	public void updateContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws SQLException, Exception {
		for(Contrato x: ContratoHabitacion.getContratos()){
			StringBuilder sql = new StringBuilder();
			sql.append (String.format ("UPDATE %s.CONTRATO_Habitacion ", USUARIO));
			sql.append (String.format (
					"SET CONTRATO_ID= %1$s, HABITACION_ID = %2$s ",
					x.getId(), ContratoHabitacion.getHabitacion().getId()));
			sql.append ("WHERE ID_CONTRATO = " + x.getId() +" AND ID_Habitacion="+ContratoHabitacion.getHabitacion().getId());
			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void deleteContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws SQLException, Exception {

		for(Contrato x:ContratoHabitacion.getContratos()){
			String sql = String.format("DELETE FROM %1$s.CONTRATO_Habitacion WHERE CONTRATO_ID = %2$d AND Habitacion_ID=%3$d", USUARIO, x.getId(), ContratoHabitacion.getHabitacion().getId());

			System.out.println(sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}

	public void setConn(Connection connection){
		this.conn = connection;
		this.DaoCon.setConn(connection);
		this.daoApto.setConn(connection);
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
	public ContratoHabitacion convertResultSetToContratoHabitacion(ResultSet resultSet) throws Exception {

		Integer id_Habitacion=resultSet.getInt("HABITACION_ID");
		Integer id_contrato=resultSet.getInt("CONTRATO_ID");
		ArrayList<Contrato> cons=new ArrayList<>();
		cons.add(DaoCon.findContratoById(id_contrato));
		ContratoHabitacion ContratoHabitacion=new ContratoHabitacion(daoApto.findHabitacionById(id_Habitacion),cons);
		return ContratoHabitacion;
	}
	
	public ArrayList<ContratoHabitacion> convertResultSetToContratoApto(ArrayList<ContratoHabitacion> cAptos) throws SQLException, Exception
	{
		ArrayList<ContratoHabitacion> respuesta=new ArrayList<>();
		List<Contrato> cons= new ArrayList<>();
		int idTemp=cAptos.get(0).getHabitacion().getId();
		for(ContratoHabitacion x : cAptos)
		{
			if(x.getHabitacion().getId()!=idTemp)
			{
				ContratoHabitacion ca=buscar(respuesta,idTemp);
				System.out.println(ca);
				if(ca!=null)
				{					
					respuesta.remove(ca);
					for(Contrato y:ca.getContratos())
						cons.add(y);
					ca.setContratos(cons);
					respuesta.add(ca);
					idTemp=x.getHabitacion().getId();
					cons=new ArrayList<>();
				}
				else 
				{
					respuesta.add(new ContratoHabitacion(daoApto.findHabitacionById(idTemp), cons));
					idTemp=x.getHabitacion().getId();
					cons=new ArrayList<>();
				}
			}
			for(Contrato z: x.getContratos())
				cons.add(z);
		}
		ContratoHabitacion ca=buscar(respuesta,idTemp);
		if(ca!=null)
		{					
			respuesta.remove(ca);
			for(Contrato y:ca.getContratos())
				cons.add(y);
			ca.setContratos(cons);
			respuesta.add(ca);
		}
		else 
		{
			respuesta.add(new ContratoHabitacion(daoApto.findHabitacionById(idTemp), cons));
		}
		return respuesta;	
		
	} 
	
	public ContratoHabitacion buscar(ArrayList<ContratoHabitacion> lista, Integer id)
	{
		for(ContratoHabitacion x : lista)
		{
			if(x.getHabitacion().getId()==id)
				return x;
		}
		
		return null;
	}
}
