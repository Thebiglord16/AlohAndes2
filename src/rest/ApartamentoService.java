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
import vos.Apartamento;
import vos.Operador;
import vos.OperadorApartamento;

@Path("operadores/{operadorId:\\d+}/apartamentos")
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
	public Response getApartamentos(@PathParam("operadorId") Integer operadorId) {

		try {

			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
				List<Apartamento> Apartamentos;
				Apartamentos = tm.getOperadorApartamentoById(operadorId).getApartamentos();
				return Response.status(200).entity(Apartamentos).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen Apartamentoes de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addApartamento(Apartamento Apartamento , @PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
				
				//TODO Crea la relacion
				ArrayList<Apartamento> aptos=new ArrayList<>();
				aptos.add(Apartamento);
				OperadorApartamento oh=new OperadorApartamento(op, aptos);
				tm.addApartamento(Apartamento);
				tm.addOperadorApartamento(oh);
				return Response.status(200).entity(Apartamento).build();
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
	public Response updateApartamento(Apartamento Apartamento, @PathParam("operadorId") Integer operadorId ) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.updateApartamento(Apartamento);
				return Response.status(200).entity(Apartamento).build();

			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen Apartamentoes de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteApartamento(Apartamento Apartamento, @PathParam("operadorId") Integer operadorId  ) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){
			ArrayList<Apartamento> habs=new ArrayList<>();
			habs.add(Apartamento);
			OperadorApartamento oh=new OperadorApartamento(op, habs);	
			tm.deleteApartamento(Apartamento);
			tm.deleteOperadorApartamento(oh);
			//TODO borrar de la relacion
			return Response.status(200).entity(Apartamento).build();
			}
			else
				return Response.status(404).entity("No existe el operador , por lo tanto no existen Apartamentoes de el").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
