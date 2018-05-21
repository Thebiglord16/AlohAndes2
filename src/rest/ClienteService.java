package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tm.AlohAndesTM;
import vos.Cliente;

@Path("/clientes")
public class ClienteService {
	
	@Context
	private ServletContext context;
	
	private String getPath()
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientes() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Cliente> clientes;
			clientes = tm.getAllClientes();
			return Response.status(200).entity(clientes).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path("/buenos" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBuenosClientes() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			
			return Response.status(200).entity(tm.buenosClientes()).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCliente( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Cliente cliente;
			cliente = tm.getClienteById(id);
			return Response.status(200).entity(cliente).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addCliente(Cliente cliente ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addCliente(cliente);
			return Response.status(200).entity(cliente).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateCliente(Cliente cliente ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateCliente(cliente);
			return Response.status(200).entity(cliente).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteCliente(Cliente cliente ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteCliente(cliente);
			return Response.status(200).entity(cliente).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
