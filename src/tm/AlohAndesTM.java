package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.DAOCliente;
import vos.Cliente;

public class AlohAndesTM {

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String CONNECTION_DATA_PATH;

	private String user;

	private String password;

	private String url;

	private String driver;

	private Connection conn;


	public AlohAndesTM(String contextPathP) {

		try {
			CONNECTION_DATA_PATH = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
			initializeConnectionData();
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initializeConnectionData() throws IOException, ClassNotFoundException {

		FileInputStream fileInputStream = new FileInputStream(new File(AlohAndesTM.CONNECTION_DATA_PATH));
		Properties properties = new Properties();
		
		properties.load(fileInputStream);
		fileInputStream.close();
		
		this.url = properties.getProperty("url");
		this.user = properties.getProperty("usuario");
		this.password = properties.getProperty("clave");
		this.driver = properties.getProperty("driver");
	}
	
	private Connection darConexion() throws SQLException {
		System.out.println("[AlohA APP] Attempting Connection to: " + url + " - By User: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	
	
	//TODO METODOS RECURSO ||CLIENTE||
	public List<Cliente> getAllClientes() throws Exception
	{
		DAOCliente dao= new DAOCliente();
		List<Cliente> clientes;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			clientes=dao.getClientes();
		}
		catch( SQLException e)
		{
			System.err.println("[Excepción!] SQLException "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(Exception e)
		{
			System.err.println("[Excepción!] Exception "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(("[Excepción!] SQLException mientras se cerraban los recursos: "+e.getMessage()));
				e.printStackTrace();
				throw e;
			}
		}
		return clientes;
	}

	public Cliente getClienteById(Integer id) throws Exception
	{
		DAOCliente dao=new DAOCliente();
		Cliente cliente=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			cliente=dao.findClienteById(id);
		}
		catch( SQLException e)
		{
			System.err.println("[Excepción!] SQLException "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(Exception e)
		{
			System.err.println("[Excepción!] Exception "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(("[Excepción!] SQLException mientras se cerraban los recursos: "+e.getMessage()));
				e.printStackTrace();
				throw e;
			}
		}
		return cliente;
	}
	
	public void addCliente(Cliente cliente) throws Exception
	{
		DAOCliente dao= new DAOCliente();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addCliente(cliente);
		}
		catch( SQLException e)
		{
			System.err.println("[Excepción!] SQLException "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(Exception e)
		{
			System.err.println("[Excepción!] Exception "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(("[Excepción!] SQLException mientras se cerraban los recursos: "+e.getMessage()));
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public void updateCliente(Cliente cliente) throws Exception
	{
		DAOCliente dao= new DAOCliente();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateCliente(cliente);
		}
		catch( SQLException e)
		{
			System.err.println("[Excepción!] SQLException "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(Exception e)
		{
			System.err.println("[Excepción!] Exception "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(("[Excepción!] SQLException mientras se cerraban los recursos: "+e.getMessage()));
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public void deleteCliente(Cliente cliente) throws Exception
	{
		DAOCliente dao= new DAOCliente();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Cliente borrar=dao.findClienteById(cliente.getId());
			if(borrar!=null)
				dao.deleteCliente(cliente);
			else
				throw new Exception("el cliente que se deseaba eliminar no existe!");
		}
		catch( SQLException e)
		{
			System.err.println("[Excepción!] SQLException "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch(Exception e)
		{
			System.err.println("[Excepción!] Exception "+ e.getMessage());
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			}
			catch(SQLException e)
			{
				System.err.println(("[Excepción!] SQLException mientras se cerraban los recursos: "+e.getMessage()));
				e.printStackTrace();
				throw e;
			}
		}
	}
	
}
