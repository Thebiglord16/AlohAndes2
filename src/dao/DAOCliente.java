package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Cliente;
import vos.VOMejorCliente;

public class DAOCliente {
	
	public final static String USUARIO="ISIS2304A541810";
	
	private ArrayList<Object> recursos;
	
	private Connection conn;
	
	public DAOCliente()
	{
		this.recursos=new ArrayList<>();
	}
	
	public ArrayList<Cliente> getClientes() throws SQLException, Exception {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		String sql = String.format("SELECT * FROM %1$s.CLIENTE WHERE ROWNUM <= 50", USUARIO);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			clientes.add(convertResultSetToCliente(rs));
		}
		return clientes;
	}
	
	public Cliente findClienteById(Integer id) throws SQLException, Exception 
	{
		Cliente cliente = null;

		String sql = String.format("SELECT * FROM %1$s.CLIENTE WHERE ID = %2$d", USUARIO, id); 

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			cliente = convertResultSetToCliente(rs);
		}

		return cliente;
	}
	
	public void addCliente(Cliente cliente) throws SQLException, Exception {

		String sql = String.format("INSERT INTO %1$s.CLIENTE (ID, IDENTIFICACION, NOMBRE, CORREO,TIPO) VALUES (%2$s, %3$s, '%4$s', '%5$s',%6$s)", 
									USUARIO, 
									cliente.getId(), 
									cliente.getIdentificacion(),
									cliente.getNombre(),
									cliente.getCorreo(), 
									cliente.getTipo());
		System.out.println(sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void updateCliente(Cliente cliente) throws SQLException, Exception {

		StringBuilder sql = new StringBuilder();
		sql.append (String.format ("UPDATE %s.CLIENTE ", USUARIO));
		sql.append (String.format (
				"SET IDENTIFICACION= %1$s, NOMBRE = '%2$s', CORREO = '%3$s', TIPO=%4$s ",
				cliente.getIdentificacion(), cliente.getNombre(),
				cliente.getCorreo(), cliente.getTipo()));
		sql.append ("WHERE ID = " + cliente.getId ());
		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql.toString());
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteCliente(Cliente cliente) throws SQLException, Exception {

		String sql = String.format("DELETE FROM %1$s.CLIENTE WHERE ID = %2$d", USUARIO, cliente.getId());

		System.out.println(sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<VOMejorCliente> mejoresClientes() throws SQLException
	{
		ArrayList<VOMejorCliente> res=new ArrayList<>();
		String sql=String.format("select nombre, correo, identificacion,habitacion_id, costo, tipo from %1$s.contrato_habitacion join (select * from %1$s.contrato join (select * from %1$s.cliente join %1$s.cliente_contrato on %1$s.cliente.id=%1$s.cliente_contrato.cliente_id)A on %1$s.contrato.id=A.contrato_id)B on B.contrato_id=%1$s.contrato_habitacion.CONTRATO_ID  where costo>=150 or tipo=0 andfecha_inicio='01-01-19';",USUARIO);
		System.out.println(sql);
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next())
		{
			Integer id=rs.getInt("CLIENTE_ID");
			String nombre=rs.getString("NOMBRE");
			String correo=rs.getString("CORREO");
			Integer tipo=rs.getInt("TIPO");
			Integer identificacion=rs.getInt("IDENTIFICACION");
			Integer habitacion_id=rs.getInt("HABITACION_ID");
			Double costo=rs.getDouble("COSTO");
			res.add(new VOMejorCliente(id, identificacion, nombre, correo, tipo, habitacion_id, costo));
		}
		return res;
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
	
	public Cliente convertResultSetToCliente(ResultSet resultSet) throws SQLException {

		Integer id=resultSet.getInt("ID");
		Integer identificacion=resultSet.getInt("IDENTIFICACION");
		String nombre=resultSet.getString("NOMBRE");
		String correo=resultSet.getString("CORREO");
		Integer tipo=resultSet.getInt("TIPO");
		String fechaIngreso=resultSet.getString("FECHA_INGRESO");
		Cliente cliente=new Cliente(id, identificacion,correo,tipo,nombre, fechaIngreso);
		return cliente;
	}
}
