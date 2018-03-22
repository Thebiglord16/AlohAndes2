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
import vos.Cliente;
import vos.ClienteContrato;
import vos.Contrato;
import vos.ContratoApartamento;


@Path("/{owner}/{id:\\d+}/contratos")
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
	public Response getContratos(@PathParam("owner")String owner, @PathParam("id") Integer idOwner) {

		try {
			if(owner.equals("apartamentos")){
				AlohAndesTM tm = new AlohAndesTM(getPath());
				Apartamento apto=tm.getApartamentoById(idOwner);
				if(apto!=null){
					List<Contrato> Contratos;
					Contratos = tm.getContratoApartamentoById(idOwner).getContratos();
					return Response.status(200).entity(Contratos).build();
				}
				else
					return Response.status(404).entity("No existe el operador , por lo tanto no existen Contratos de el").build();
			}
			else if(owner.equals("clientes")){
				AlohAndesTM tm = new AlohAndesTM(getPath());
				Cliente cliente=tm.getClienteById(idOwner);
				if(cliente!=null){
					List<Contrato> Contratos;
					Contratos = tm.getClienteContratoById(idOwner).getContratos();
					return Response.status(200).entity(Contratos).build();
				}
				else
					return Response.status(404).entity("No existe el operador , por lo tanto no existen Contratos de el").build();
			}
			else
				return Response.status(404).entity("No existe tal entidad capaz de firmar un contrato").build();
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
	public Response addContrato(Contrato Contrato, @PathParam("owner")String  owner, @PathParam("id") Integer idOwner ) {

		try {
			if(owner.equals("apartamentos")){
				AlohAndesTM tm = new AlohAndesTM(getPath());
				Apartamento apto=tm.getApartamentoById(idOwner);
				if(apto!=null){
					tm.addContrato(Contrato);
					ArrayList<Contrato> Contratos= new ArrayList<>();
					Contratos.add(Contrato);
					tm.addContratoApartamento(new ContratoApartamento(apto, Contratos));
					return Response.status(200).entity(Contrato).build();
				}
				else
					return Response.status(404).entity("No existe el operador , por lo tanto no existen Contratos de el").build();
			}
			else if(owner.equals("clientes")){
				AlohAndesTM tm = new AlohAndesTM(getPath());
				Cliente cliente=tm.getClienteById(idOwner);
				if(cliente!=null){
					tm.addContrato(Contrato);
					ArrayList<Contrato> Contratos= new ArrayList<>();
					Contratos.add(Contrato);
					tm.addClienteContrato(new ClienteContrato(cliente, Contratos));
					return Response.status(200).entity(Contrato).build();
				}
				else
					return Response.status(404).entity("No existe el operador , por lo tanto no existen Contratos de el").build();
			}
			else
				return Response.status(404).entity("No existe tal entidad capaz de firmar un contrato").build();
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
