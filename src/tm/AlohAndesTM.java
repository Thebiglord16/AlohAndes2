package tm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonProperty;

import dao.DAOApartamento;
import dao.DAOCliente;
import dao.DAOClienteContrato;
import dao.DAOContrato;
import dao.DAOContratoApartamento;
import dao.DAOContratoHabitacion;
import dao.DAOHabitacion;
import dao.DAOOperador;
import dao.DAOOperadorApartamento;
import dao.DAOOperadorHabitacion;
import dao.DAOServicio;
import vos.Apartamento;
import vos.Cliente;
import vos.ClienteContrato;
import vos.Contrato;
import vos.ContratoApartamento;
import vos.ContratoHabitacion;
import vos.Habitacion;
import vos.Operador;
import vos.OperadorApartamento;
import vos.OperadorHabitacion;
import vos.Servicio;
import vos.SuperReserva;


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
	
	public List<Contrato> getContratosDescrpcion(String descripcion) throws Exception
	{
		DAOContrato dao= new DAOContrato();
		List<Contrato> Contratos;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			Contratos=dao.getContratosDescripcion(descripcion);
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

	public List<Apartamento> getAllAptosDisponibles(String fechaInicio) throws Exception
	{
		List<Apartamento> aptos= getAllApartamentos();
		List<ContratoApartamento> contratos= getAllContratoApartamentos();
		for(Apartamento x:aptos)
		{
			for(ContratoApartamento y:contratos)
			{
				if(y.getApartamento().getId()==x.getId())
					for(Contrato z: y.getContratos())
					{	
						System.out.println(fechaInicio+" esto es en el tm");
						String[] inicioR=fechaInicio.split("/");
						String inicioRd="20"+inicioR[2];
						for(int i=1; i>=0;i--)
							inicioRd=inicioRd +"-"+inicioR[i];

						Date fechaInicioC= Date.valueOf(z.getFechaInicio());
						Date fechaFinC=Date.valueOf(z.getFechaFin());
						Date fechaInicioR= Date.valueOf(inicioRd);
						if(fechaInicioC.before(fechaInicioR)&&fechaFinC.after(fechaInicioR))
						{
							aptos.remove(x);
							break;
						}
					}
			}
		}

		return aptos;
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

	//TODO METODOS DE LA RELACION ||CLIENTE_CONTRATO||


	public List<ClienteContrato> getAllClienteContratos() throws Exception
	{
		DAOClienteContrato dao= new DAOClienteContrato();
		List<ClienteContrato> ClienteContratos;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ClienteContratos=dao.getClienteContratos();
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
		return ClienteContratos;
	}

	public ClienteContrato getClienteContratoById(Integer id) throws Exception
	{
		DAOClienteContrato dao=new DAOClienteContrato();
		ClienteContrato ClienteContrato=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ClienteContrato=dao.findClienteContratoById(id);
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
		return ClienteContrato;
	}

	public void addClienteContrato(ClienteContrato ClienteContrato) throws Exception
	{
		DAOClienteContrato dao= new DAOClienteContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addClienteContrato(ClienteContrato);
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

	public void updateClienteContrato(ClienteContrato ClienteContrato) throws Exception
	{
		DAOClienteContrato dao= new DAOClienteContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateClienteContrato(ClienteContrato);
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

	public void deleteClienteContrato(ClienteContrato ClienteContrato) throws Exception
	{
		DAOClienteContrato dao= new DAOClienteContrato();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ClienteContrato borrar=dao.findClienteContratoById(ClienteContrato.getCliente().getId());
			if(borrar!=null)
				dao.deleteClienteContrato(ClienteContrato);
			else
				throw new Exception("ese tal ClienteContrato no existe!");
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

	//TODO METODOS RELACION ||CONTRATO_HABITACION||

	public List<ContratoHabitacion> getAllContratoHabitacions() throws Exception
	{
		DAOContratoHabitacion dao= new DAOContratoHabitacion();
		List<ContratoHabitacion> ContratoHabitacions;
		try 
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoHabitacions=dao.getContratoHabitacions();
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
		return ContratoHabitacions;
	}

	public ContratoHabitacion getContratoHabitacionById(Integer id) throws Exception
	{
		DAOContratoHabitacion dao=new DAOContratoHabitacion();
		ContratoHabitacion ContratoHabitacion=null;
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoHabitacion=dao.findContratoHabitacionById(id);
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
		return ContratoHabitacion;
	}

	public void addContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws Exception
	{
		DAOContratoHabitacion dao= new DAOContratoHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.addContratoHabitacion(ContratoHabitacion);
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

	public void updateContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws Exception
	{
		DAOContratoHabitacion dao= new DAOContratoHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			dao.updateContratoHabitacion(ContratoHabitacion);
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

	public void deleteContratoHabitacion(ContratoHabitacion ContratoHabitacion) throws Exception
	{
		DAOContratoHabitacion dao= new DAOContratoHabitacion();
		try
		{
			this.conn=darConexion();
			dao.setConn(conn);
			ContratoHabitacion borrar=dao.findContratoHabitacionById(ContratoHabitacion.getHabitacion().getId());
			if(borrar!=null)
				dao.deleteContratoHabitacion(ContratoHabitacion);
			else
				throw new Exception("ese tal ContratoHabitacion no existe!");
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

	
	//RF9

	public void deshabilitarOfertaAlojamiento(Apartamento apto) throws Exception
	{
		this.conn=darConexion();
		conn.setAutoCommit(false);

			//capacidad del apartamento que ya no estará habilitado
			int capacidad = apto.getCacpacidad();

			//contratos del apartamento
			List<ContratoApartamento> contratoApartamentos = getAllContratoApartamentos();

			//Lista de contratos del apartamento
			List<Contrato> contratos = new ArrayList<Contrato>();

			if(!contratoApartamentos.isEmpty()){
				for (ContratoApartamento contratoApartamento : contratoApartamentos) 
				{

					Apartamento apto2 = contratoApartamento.getApartamento();
					if(apto2.getId()==apto.getId())
					{
						List<Contrato> con = contratoApartamento.getContratos();
						for (Contrato contrato : con) 
						{
							contratos.add(contrato);
						}
					}
				}

				//Hasta aqui saco los contratos del apto

				for (Contrato contrato : contratos) 
				{
					//Fecha de inicio de los contratos que se deben modificar
					String inicio = contrato.getFechaInicio();

					//Apartamentos disponibles en esa fecha
					List<Apartamento> aptosDisp = getAllAptosDisponibles(inicio);
					if(!aptosDisp.isEmpty())
					{
						for (Apartamento apartamento : aptosDisp) 
						{
							if(apartamento.getCacpacidad()==capacidad)
							{
								int idContrato = contrato.getId();

								for (ContratoApartamento conn : contratoApartamentos) {
									List<Contrato> con = conn.getContratos();
									for (Contrato contrato1 : con) 
									{
										if(idContrato==contrato1.getId())
										{
											conn.setApartamento(apartamento);
											updateContratoApartamento(conn);
										}
									}
								}
							}

							else
							{
								//aquí se supone que el estado cambia a inconcluso (o una vaina asi)
								contrato.setEstado(5);
							}

						}

						//Hasta aquí reasigno el apartamento del contrato en contratoApartamento

						apto.setHabilitada(false);
						updateApartamento(apto);
					}
				}
			}
		

	}
				
	//RF10

	public void habilitarOfertaAlojamiento(Integer id) throws Exception
	{
		this.conn=darConexion();
		conn.setAutoCommit(false);
		Apartamento apto = getApartamentoById(id);
		if(apto!=null)
		{
			apto.setHabilitada(true);
			updateApartamento(apto);
		}

		else
		{
			throw new Exception("No existe el apartamento");
		}
	}

	//TODO METODOS REQ ||SUPERRESERVA||
	public void superReservar(SuperReserva sr, List<Apartamento> aptos) throws Exception
	{
		this.conn=darConexion();
		conn.setAutoCommit(false);
		try {
			for(Cliente x:sr.getClientes()) {
				if(getClienteById(x.getId())==null) {

					addCliente(x);
					this.conn=darConexion();
				}
				else
					continue;
			}
				for(int i=0;i<sr.getCantidad(); i++)
				{
					Contrato con=new Contrato(i+sr.hashCode(), sr.getFechaInicio(), sr.getFechaFin(), sr.getDescipcion(), 0 , 25000.0);
					generarRelacionContrato("apartamentos",aptos.get(i).getId(),sr.getClientes().get(i).getId(),con);
					this.conn=darConexion();

				}
				conn.commit();
				conn.setAutoCommit(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		
		
	}
	public Contrato generarRelacionContrato(String owner,Integer idOwner, Integer idUser, Contrato contrato) throws Exception
	{
		if(owner.equals("apartamentos")){
			
			Apartamento apto=getApartamentoById(idOwner);
			Cliente cliente=getClienteById(idUser);
			if(apto!=null&& cliente!=null){
				addContrato(contrato);
				ArrayList<Contrato> Contratos= new ArrayList<>();
				Contratos.add(contrato);
				addContratoApartamento(new ContratoApartamento(apto, Contratos));
				addClienteContrato(new ClienteContrato(cliente, Contratos));
				return contrato;
			}
			else
				throw new Exception("No existe el operador , por lo tanto no existen Contratos de el");
		}
		else if(owner.equals("clientes")){
			Cliente cliente=getClienteById(idOwner);
			Apartamento apto=getApartamentoById(idUser);
			if(cliente!=null && apto!=null){
				addContrato(contrato);
				ArrayList<Contrato> Contratos= new ArrayList<>();
				Contratos.add(contrato);
				addClienteContrato(new ClienteContrato(cliente, Contratos));
				addContratoApartamento(new ContratoApartamento(apto, Contratos));
				return contrato;
			}
			else
				throw new Exception("No existe el operador , por lo tanto no existen Contratos de el");
		}
		else if(owner.equals("habitaciones")){			
			Cliente cliente=getClienteById(idUser);
			Habitacion hab=getHabitacionById(idOwner);
			if(cliente!=null && hab!=null){
				addContrato(contrato);
				ArrayList<Contrato> Contratos= new ArrayList<>();
				Contratos.add(contrato);
				addClienteContrato(new ClienteContrato(cliente, Contratos));
				addContratoHabitacion(new ContratoHabitacion(hab, Contratos));

				return contrato;
			}
			else
				throw new Exception("No existe el operador , por lo tanto no existen Contratos de el");
		}

		
		
		

		else
			throw new Exception("No existe el operador , por lo tanto no existen Contratos de el");
	}
	public void cancelarSuperReserva(SuperReserva sr) throws Exception
	{
		List<Contrato> cons=getContratosDescrpcion(sr.getDescipcion());
		for(Contrato x:cons)
		{
			x.setEstado(5);
			updateContrato(x);
		}
			

	}
}
