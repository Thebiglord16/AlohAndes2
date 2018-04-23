package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.Cliente;
import vos.SuperReserva;

@Path("superReservas")
public class SuperReservaService 
{
	@Context
	private ServletContext context;
	
	private String getPath()
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response crearSuperReserva(SuperReserva sr) 
	{
		try 
		{
			AlohAndesTM tm=new AlohAndesTM(getPath());
			if(tm.getAllAptosDisponibles(sr.getFechaInicio()).size()>sr.getCantidad())
			{
				return Response.status(200).entity(sr).build();
			}
			else
				return Response.status(412).entity("En este momento no existe la disponibilidad para tal reserva").build();
		}	
		catch(Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
}
