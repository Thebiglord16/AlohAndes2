package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ContratoApartamento {
	
	@JsonProperty(value="apartamento")
	private Apartamento apartamento;
	
	@JsonProperty(value="contratos")
	private List<Contrato>contratos;
	
	public ContratoApartamento(@JsonProperty(value="apartamento") Apartamento apartamento, @JsonProperty(value="contratos")List<Contrato>contratos)
	{
		this.apartamento = apartamento;
		this.contratos=contratos;
	}
	
	public Apartamento getApartamento()
	{
		return apartamento;
	}
	
	public void setApartamento(Apartamento apartamento)
	{
		this.apartamento = apartamento;
	}
	
	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
}
