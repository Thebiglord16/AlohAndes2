package vos;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

public class Contrato {
	
	public enum Estado
	{
		
	}
	
	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="fechaInicio")
	private Timestamp fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private Timestamp fechaFin;
	
	public Contrato(@JsonProperty(value="id")Integer id, @JsonProperty(value="fechaInicio") Timestamp fechaInicio,@JsonProperty(value="fechaFin") Timestamp fechaFin)
	{
		this.id=id;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
