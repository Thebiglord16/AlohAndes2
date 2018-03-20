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
import vos.Habitacion;
@Path("habitaciones")
public class HabitacionService {

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
	public Response getHabitacions() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Habitacion> Habitacions;
			Habitacions = tm.getAllHabitacions();
			return Response.status(200).entity(Habitacions).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getHabitacion( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Habitacion Habitacion;
			Habitacion = tm.getHabitacionById(id);
			return Response.status(200).entity(Habitacion).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addHabitacion(Habitacion Habitacion ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addHabitacion(Habitacion);
			return Response.status(200).entity(Habitacion).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateHabitacion(Habitacion Habitacion ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateHabitacion(Habitacion);
			return Response.status(200).entity(Habitacion).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteHabitacion(Habitacion Habitacion ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteHabitacion(Habitacion);
			return Response.status(200).entity(Habitacion).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
}
