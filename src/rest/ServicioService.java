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
import vos.Servicio;

@Path("servicios")
public class ServicioService {

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
	public Response getServicios() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Servicio> Servicios;
			Servicios = tm.getAllServicios();
			return Response.status(200).entity(Servicios).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getServicio( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Servicio Servicio;
			Servicio = tm.getServicioById(id);
			return Response.status(200).entity(Servicio).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addServicio(Servicio Servicio ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addServicio(Servicio);
			return Response.status(200).entity(Servicio).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateServicio(Servicio Servicio ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateServicio(Servicio);
			return Response.status(200).entity(Servicio).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteServicio(Servicio Servicio ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteServicio(Servicio);
			return Response.status(200).entity(Servicio).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
