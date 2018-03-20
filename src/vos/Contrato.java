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
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="estado")
	private Integer estado;
	
	public Contrato(@JsonProperty(value="id")Integer id, @JsonProperty(value="fechaInicio") Timestamp fechaInicio,@JsonProperty(value="fechaFin") Timestamp fechaFin, @JsonProperty(value="descripcion") String descripcion, @JsonProperty(value="estado") Integer estado)
	{
		this.id=id;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.descripcion=descripcion;
		this.estado=estado;
	}
	
	
	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
