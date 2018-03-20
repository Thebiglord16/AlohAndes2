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
import vos.Apartamento;

@Path("apartamentos")
public class ApartamentoService {
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
	public Response getApartamentos() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Apartamento> Apartamentos;
			Apartamentos = tm.getAllApartamentos();
			return Response.status(200).entity(Apartamentos).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getApartamento( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Apartamento Apartamento;
			Apartamento = tm.getApartamentoById(id);
			return Response.status(200).entity(Apartamento).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addApartamento(Apartamento Apartamento ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addApartamento(Apartamento);
			return Response.status(200).entity(Apartamento).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateApartamento(Apartamento Apartamento ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateApartamento(Apartamento);
			return Response.status(200).entity(Apartamento).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteApartamento(Apartamento Apartamento ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteApartamento(Apartamento);
			return Response.status(200).entity(Apartamento).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
