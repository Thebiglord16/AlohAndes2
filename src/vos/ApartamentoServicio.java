package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ApartamentoServicio {
	
	@JsonProperty(value="apartamento")
	private Apartamento apartamento;
	
	@JsonProperty(value="servicios")
	private List<Servicio> servicios;
	
	public ApartamentoServicio(@JsonProperty(value="apartamento") Apartamento apartamento, @JsonProperty(value="servicios")List<Servicio>servicios)
	{
		this.apartamento = apartamento;
		this.servicios = servicios;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

}
