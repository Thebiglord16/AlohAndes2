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
import vos.Contrato;

@Path("contratos")
public class ContratoService {

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
	public Response getContratos() {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			List<Contrato> Contratos;
			//Por simplicidad, solamente se obtienen los primeros 50 resultados de la consulta
			Contratos = tm.getAllContratos();
			return Response.status(200).entity(Contratos).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@GET
	@Path( "{id: \\d+}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getContrato( @PathParam( "id" )Integer id) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());
			
			Contrato Contrato;
			Contrato = tm.getContratoById(id);
			return Response.status(200).entity(Contrato).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addContrato(Contrato Contrato ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.addContrato(Contrato);
			return Response.status(200).entity(Contrato).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateContrato(Contrato Contrato ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.updateContrato(Contrato);
			return Response.status(200).entity(Contrato).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteContrato(Contrato Contrato ) {
		
		try {
			AlohAndesTM tm = new AlohAndesTM(getPath());			
			tm.deleteContrato(Contrato);
			return Response.status(200).entity(Contrato).build();
		} 
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
}
