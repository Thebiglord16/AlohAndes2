package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import dao.DAOApartamento;
import dao.DAOCliente;
import dao.DAOContrato;
import dao.DAOContratoApartamento;
import dao.DAOHabitacion;
import dao.DAOOperador;
import dao.DAOOperadorApartamento;
import dao.DAOOperadorHabitacion;
import dao.DAOServicio;
import vos.Apartamento;
import vos.Cliente;
import vos.Contrato;
import vos.ContratoApartamento;
import vos.Habitacion;
import vos.Operador;
import vos.OperadorApartamento;
import vos.OperadorHabitacion;
import vos.Servicio;


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
	
	//TODO METODOS RECURSO ||APARTAMENTO||
	
	public List<Apartamento> getAllApartamentos() throws Exception
	{
		DAOApartamento dao= new DAOApartamento();
		List<Apartamento> Apartamentos;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Apartamentos=dao.getApartamentos();
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
		return Apartamentos;
	}

	public Apartamento getApartamentoById(Integer id) throws Exception
	{
		DAOApartamento dao=new DAOApartamento();
		Apartamento Apartamento=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Apartamento=dao.findApartamentoById(id);
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
		return Apartamento;
	}
	
	public void addApartamento(Apartamento Apartamento) throws Exception
	{
		DAOApartamento dao= new DAOApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addApartamento(Apartamento);
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
	
	public void updateApartamento(Apartamento Apartamento) throws Exception
	{
		DAOApartamento dao= new DAOApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateApartamento(Apartamento);
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
	
	public void deleteApartamento(Apartamento Apartamento) throws Exception
	{
		DAOApartamento dao= new DAOApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Apartamento borrar=dao.findApartamentoById(Apartamento.getId());
			if(borrar!=null)
				dao.deleteApartamento(Apartamento);
			else
				throw new Exception("ese tal Apartamento no existe!");
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
	
	//TODO METODOS RECURSO ||HABITACION||
	
	public List<Habitacion> getAllHabitacions() throws Exception
	{
		DAOHabitacion dao= new DAOHabitacion();
		List<Habitacion> Habitacions;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Habitacions=dao.getHabitacions();
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
		return Habitacions;
	}

	public Habitacion getHabitacionById(Integer id) throws Exception
	{
		DAOHabitacion dao=new DAOHabitacion();
		Habitacion Habitacion=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Habitacion=dao.findHabitacionById(id);
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
		return Habitacion;
	}
	
	public void addHabitacion(Habitacion Habitacion) throws Exception
	{
		DAOHabitacion dao= new DAOHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addHabitacion(Habitacion);
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
	
	public void updateHabitacion(Habitacion Habitacion) throws Exception
	{
		DAOHabitacion dao= new DAOHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateHabitacion(Habitacion);
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
	
	public void deleteHabitacion(Habitacion Habitacion) throws Exception
	{
		DAOHabitacion dao= new DAOHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Habitacion borrar=dao.findHabitacionById(Habitacion.getId());
			if(borrar!=null)
				dao.deleteHabitacion(Habitacion);
			else
				throw new Exception("ese tal Habitacion no existe!");
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
	
	//TODO METODOS RECURSO ||SERVICIO||
	public List<Servicio> getAllServicios() throws Exception
	{
		DAOServicio dao= new DAOServicio();
		List<Servicio> Servicios;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Servicios=dao.getServicios();
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
		return Servicios;
	}

	public Servicio getServicioById(Integer id) throws Exception
	{
		DAOServicio dao=new DAOServicio();
		Servicio Servicio=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Servicio=dao.findServicioById(id);
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
		return Servicio;
	}
	
	public void addServicio(Servicio Servicio) throws Exception
	{
		DAOServicio dao= new DAOServicio();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addServicio(Servicio);
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
	
	public void updateServicio(Servicio Servicio) throws Exception
	{
		DAOServicio dao= new DAOServicio();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateServicio(Servicio);
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
	
	public void deleteServicio(Servicio Servicio) throws Exception
	{
		DAOServicio dao= new DAOServicio();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Servicio borrar=dao.findServicioById(Servicio.getId());
			if(borrar!=null)
				dao.deleteServicio(Servicio);
			else
				throw new Exception("ese tal Servicio no existe!");
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
	
	//TODO METODOS RELACION ||CONTRATO_APARTAMENTO||
	
	public List<ContratoApartamento> getAllContratoApartamentos() throws Exception
	{
		DAOContratoApartamento dao= new DAOContratoApartamento();
		List<ContratoApartamento> ContratoApartamentos;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoApartamentos=dao.getContratoApartamentos();
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
		return ContratoApartamentos;
	}

	public ContratoApartamento getContratoApartamentoById(Integer id) throws Exception
	{
		DAOContratoApartamento dao=new DAOContratoApartamento();
		ContratoApartamento ContratoApartamento=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoApartamento=dao.findContratoApartamentoById(id);
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
		return ContratoApartamento;
	}
	
	public void addContratoApartamento(ContratoApartamento ContratoApartamento) throws Exception
	{
		DAOContratoApartamento dao= new DAOContratoApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addContratoApartamento(ContratoApartamento);
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
	
	public void updateContratoApartamento(ContratoApartamento ContratoApartamento) throws Exception
	{
		DAOContratoApartamento dao= new DAOContratoApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateContratoApartamento(ContratoApartamento);
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
	
	public void deleteContratoApartamento(ContratoApartamento ContratoApartamento) throws Exception
	{
		DAOContratoApartamento dao= new DAOContratoApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoApartamento borrar=dao.findContratoApartamentoById(ContratoApartamento.getApartamento().getId());
			if(borrar!=null)
				dao.deleteContratoApartamento(ContratoApartamento);
			else
				throw new Exception("ese tal ContratoApartamento no existe!");
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
	
	//TODO METODOS RELACION ||OPERADOR_HABITACION||
	
	public List<OperadorHabitacion> getAllOperadorHabitacions() throws Exception
	{
		DAOOperadorHabitacion dao= new DAOOperadorHabitacion();
		List<OperadorHabitacion> OperadorHabitacions;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorHabitacions=dao.getOperadorHabitacions();
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
		return OperadorHabitacions;
	}

	public OperadorHabitacion getOperadorHabitacionById(Integer id) throws Exception
	{
		DAOOperadorHabitacion dao=new DAOOperadorHabitacion();
		OperadorHabitacion OperadorHabitacion=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorHabitacion=dao.findOperadorHabitacionById(id);
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
		return OperadorHabitacion;
	}
	
	public void addOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws Exception
	{
		DAOOperadorHabitacion dao= new DAOOperadorHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addOperadorHabitacion(OperadorHabitacion);
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
	
	public void updateOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws Exception
	{
		DAOOperadorHabitacion dao= new DAOOperadorHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateOperadorHabitacion(OperadorHabitacion);
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
	
	public void deleteOperadorHabitacion(OperadorHabitacion OperadorHabitacion) throws Exception
	{
		DAOOperadorHabitacion dao= new DAOOperadorHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorHabitacion borrar=dao.findOperadorHabitacionById(OperadorHabitacion.getOperador().getId());
			if(borrar!=null)
				dao.deleteOperadorHabitacion(OperadorHabitacion);
			else
				throw new Exception("ese tal OperadorHabitacion no existe!");
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
	
	//TODO METODOS RELACION ||OPERADOR_APARTAMENTO||
	
	public List<OperadorApartamento> getAllOperadorApartamentos() throws Exception
	{
		DAOOperadorApartamento dao= new DAOOperadorApartamento();
		List<OperadorApartamento> OperadorApartamentos;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorApartamentos=dao.getOperadorApartamentos();
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
		return OperadorApartamentos;
	}

	public OperadorApartamento getOperadorApartamentoById(Integer id) throws Exception
	{
		DAOOperadorApartamento dao=new DAOOperadorApartamento();
		OperadorApartamento OperadorApartamento=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorApartamento=dao.findOperadorApartamentoById(id);
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
		return OperadorApartamento;
	}
	
	public void addOperadorApartamento(OperadorApartamento OperadorApartamento) throws Exception
	{
		DAOOperadorApartamento dao= new DAOOperadorApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addOperadorApartamento(OperadorApartamento);
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
	
	public void updateOperadorApartamento(OperadorApartamento OperadorApartamento) throws Exception
	{
		DAOOperadorApartamento dao= new DAOOperadorApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateOperadorApartamento(OperadorApartamento);
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
	
	public void deleteOperadorApartamento(OperadorApartamento OperadorApartamento) throws Exception
	{
		DAOOperadorApartamento dao= new DAOOperadorApartamento();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			OperadorApartamento borrar=dao.findOperadorApartamentoById(OperadorApartamento.getOperador().getId());
			if(borrar!=null)
				dao.deleteOperadorApartamento(OperadorApartamento);
			else
				throw new Exception("ese tal OperadorApartamento no existe!");
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
