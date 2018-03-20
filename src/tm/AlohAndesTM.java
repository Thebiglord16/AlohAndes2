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
import dao.DAOContrato;
import dao.DAOOperador;
import vos.Cliente;
import vos.Contrato;
import vos.Operador;


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
	
	//TODO METODOS RECURSO ||OPERADOR||
	public List<Operador> getAllOperadores() throws Exception
	{
		DAOOperador dao= new DAOOperador();
		List<Operador> Operadores;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Operadores=dao.getOperadores();
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
		return Operadores;
	}

	public Operador getOperadorById(Integer id) throws Exception
	{
		DAOOperador dao=new DAOOperador();
		Operador Operador=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Operador=dao.findOperadorById(id);
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
		return Operador;
	}
	
	public void addOperador(Operador Operador) throws Exception
	{
		DAOOperador dao= new DAOOperador();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addOperador(Operador);
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
	
	public void updateOperador(Operador Operador) throws Exception
	{
		DAOOperador dao= new DAOOperador();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateOperador(Operador);
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
	
	public void deleteOperador(Operador Operador) throws Exception
	{
		DAOOperador dao= new DAOOperador();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Operador borrar=dao.findOperadorById(Operador.getId());
			if(borrar!=null)
				dao.deleteOperador(Operador);
			else
				throw new Exception("ese tal Operador no existe!");
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
	
	
	//TODO Operaciones del recurso ||CONTRATO||
	public List<Contrato> getAllContratos() throws Exception
	{
		DAOContrato dao= new DAOContrato();
		List<Contrato> Contratos;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Contratos=dao.getContratos();
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
		return Contratos;
	}

	public Contrato getContratoById(Integer id) throws Exception
	{
		DAOContrato dao=new DAOContrato();
		Contrato Contrato=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Contrato=dao.findContratoById(id);
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
		return Contrato;
	}
	
	public void addContrato(Contrato Contrato) throws Exception
	{
		DAOContrato dao= new DAOContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addContrato(Contrato);
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
	
	public void updateContrato(Contrato Contrato) throws Exception
	{
		DAOContrato dao= new DAOContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateContrato(Contrato);
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
	
	public void deleteContrato(Contrato Contrato) throws Exception
	{
		DAOContrato dao= new DAOContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Contrato borrar=dao.findContratoById(Contrato.getId());
			if(borrar!=null)
				dao.deleteContrato(Contrato);
			else
				throw new Exception("ese tal Contrato no existe!");
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
