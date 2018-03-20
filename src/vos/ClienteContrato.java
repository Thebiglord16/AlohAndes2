package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteContrato {
	
	@JsonProperty(value="cliente")
	private Cliente cliente;
	
	@JsonProperty(value="contratos")
	private List<Contrato> contratos;
	
	public ClienteContrato(@JsonProperty(value="cliente") Cliente cliente, @JsonProperty(value="contratos")List<Contrato>contratos)
	{
		this.cliente=cliente;
		this.contratos=contratos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	
	
}
