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
import vos.ContratoApartamento;
import vos.Operador;

@Path("operadores")
public class OperadorService {
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
	public Response getOperadores() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Operador> Operadores;
			Operadores = tm.getAllOperadores();
			return Response.status(200).entity(Operadores).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path("/contratoAptos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getContratoApartamentos() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<ContratoApartamento> CAs;
			CAs = tm.getAllContratoApartamentos();
			return Response.status(200).entity(CAs).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOperador( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Operador Operador;
			Operador = tm.getOperadorById(id);
			return Response.status(200).entity(Operador).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addOperador(Operador Operador ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addOperador(Operador);
			return Response.status(200).entity(Operador).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateOperador(Operador Operador ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateOperador(Operador);
			return Response.status(200).entity(Operador).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteOperador(Operador Operador ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteOperador(Operador);
			return Response.status(200).entity(Operador).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
