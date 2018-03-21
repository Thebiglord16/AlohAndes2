package rest;

import java.util.ArrayList;
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
import vos.Operador;
import vos.OperadorHabitacion;
@Path("operadores/{operadorId:\\d+}/habitaciones")
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
	public Response getHabitacions(@PathParam("operadorId") Integer operadorId) {

		try {

			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
				List<Habitacion> Habitacions;
				Habitacions = tm.getOperadorHabitacionById(operadorId).getHabitaciones();
				return Response.status(200).entity(Habitacions).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen habitaciones de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getHabitacion(@PathParam("operadorId") Integer operadorId, @PathParam( "id" )Integer id) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
				Habitacion Habitacion;
				Habitacion = tm.getHabitacionById(id);
				return Response.status(200).entity(Habitacion).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen habitaciones de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addHabitacion(Habitacion Habitacion , @PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
				
				//TODO Crea la relacion
				ArrayList<Habitacion> habs=new ArrayList<>();
				habs.add(Habitacion);
				OperadorHabitacion oh=new OperadorHabitacion(op, habs);
				tm.addHabitacion(Habitacion);
				tm.addOperadorHabitacion(oh);
				return Response.status(200).entity(Habitacion).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen habitaciones de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateHabitacion(Habitacion Habitacion, @PathParam("operadorId") Integer operadorId ) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.updateHabitacion(Habitacion);
				return Response.status(200).entity(Habitacion).build();

			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen habitaciones de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteHabitacion(Habitacion Habitacion, @PathParam("operadorId") Integer operadorId  ) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
			ArrayList<Habitacion> habs=new ArrayList<>();
			habs.add(Habitacion);
			OperadorHabitacion oh=new OperadorHabitacion(op, habs);	
			tm.deleteHabitacion(Habitacion);
			tm.deleteOperadorHabitacion(oh);
			//TODO borrar de la relacion
			return Response.status(200).entity(Habitacion).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen habitaciones de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

}
