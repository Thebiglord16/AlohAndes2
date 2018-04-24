package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.AlohAndesTM;
import vos.Apartamento;
import vos.Cliente;
import vos.Habitacion;
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
			if(sr.getTipoAcomodamiento().equals("apartamentos")) {
				List<Apartamento> aptos=tm.getAllAptosDisponibles(sr.getFechaInicio());
				if(aptos.size()>sr.getCantidad())				
				{
					tm.superReservar(sr,aptos);
					return Response.status(200).entity(sr).build();
				}
				else
					return Response.status(412).entity(doErrorMessage(new Exception("No es posible ofrecer tal cantidad de aptos"))).build();
			}
			else {
				List<Habitacion> habs=tm.getAllHabsDisponibles(sr.getFechaInicio());
				if(habs.size()>sr.getCantidad())				
				{
					tm.superReservarHab(sr,habs);
					return Response.status(200).entity(sr).build();
				}
				else
					return Response.status(412).entity(doErrorMessage(new Exception("No es posible ofrecer tal cantidad de aptos"))).build();
			}
		}	
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response cancelarSuperReserva(SuperReserva sr) 
	{
		try 
		{
			AlohAndesTM tm=new AlohAndesTM(getPath());
			tm.cancelarSuperReserva(sr);
			return Response.status(200).entity(sr).build();
		}	
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
