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
import vos.SuperReserva;

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


	@PUT
	@Path("/deshabilitar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deshabilitarApartamento(Apartamento apto) 
	{
		try 
		{

			AlohAndesTM tm=new AlohAndesTM(getPath());
			tm.deshabilitarOfertaAlojamiento(apto);
			return Response.status(200).entity(apto).build();
		}	
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@PUT
	@Path("/habilitar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response habilitarApartamento(Apartamento apto) 
	{
		try 
		{
			AlohAndesTM tm=new AlohAndesTM(getPath());
			tm.habilitarOfertaAlojamiento(apto);
			return Response.status(200).entity(apto).build();
		}	
		catch(Exception e) 
		{
			e.printStackTrace();
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}


	//RFC1

	@GET
	@Path("/dineroProveedor")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getDineroProveedor(Apartamento apartamento, @PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.dineroProveedor(apartamento);
				return Response.status(200).entity(tm.dineroProveedor(apartamento)).build();

			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	//RFC 2
	
	@GET
	@Path("/ofertasMasPopulares")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOfertasMasPopulares(@PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.ofertasMasPopulares();
				return Response.status(200).entity(tm.ofertasMasPopulares()).build();

			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	//RFC3
	
	@GET
	@Path("/indiceOcupamiento")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getIndiceOcupamiento(@PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){	
				return Response.status(200).entity(tm.indiceOcupamiento()).build();
			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	//RFC 9
	
	@GET
	@Path("/menosDemanda")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenosDemanda(@PathParam("operadorId") Integer operadorId) {

		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.ofertasMasPopulares();
				return Response.status(200).entity(tm.menosDemanda()).build();

			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	
	@GET
	@Path("/consumo/{fechaInicio:\\d+}/{fechaFin:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumo(@PathParam("operadorId") Integer operadorId, Apartamento apartamento, @PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.ofertasMasPopulares();
				return Response.status(200).entity(tm.consumoEnAlohandes(apartamento, fechaInicio, fechaFin)).build();

			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@GET
	@Path("/consumo/{fechaInicio:\\d+}/{fechaFin:\\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsumoV2(@PathParam("operadorId") Integer operadorId, Apartamento apartamento, @PathParam("fechaInicio") String fechaInicio, @PathParam("fechaFin") String fechaFin) {
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			Operador op=tm.getOperadorById(operadorId);
			if(op!=null){			
				tm.ofertasMasPopulares();
				return Response.status(200).entity(tm.consumoEnAlohandesV2(apartamento, fechaInicio, fechaFin)).build();

			}
			else
				return Response.status(404).entity("No existe el operador").build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
