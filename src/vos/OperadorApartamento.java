package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadorApartamento {
	
	@JsonProperty(value="operador")
	private Operador operador;
	
	@JsonProperty(value="apartamentos")
	private List<Apartamento> apartamentos;
	
	public OperadorApartamento(@JsonProperty(value="operador") Operador operador, @JsonProperty(value="apartamentos")List<Apartamento>apartamentos)
	{
		this.operador = operador;
		this.apartamentos = apartamentos;
	}
	
	public Operador getOperador()
	{
		return operador;
	}
	
	public void setOperador(Operador operador)
	{
		this.operador = operador;
	}

	public List<Apartamento> getApartamentos() {
		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
	}
	
}
