package vos;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

public class Contrato {
	
	public enum Estado
	{
		
	}
	
	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="fechaInicio")
	private String fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private String fechaFin;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="estado")
	private Integer estado;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	public Contrato(@JsonProperty(value="id")Integer id, @JsonProperty(value="fechaInicio") String fechaInicio,@JsonProperty(value="fechaFin") String fechaFin, @JsonProperty(value="descripcion") String descripcion, @JsonProperty(value="estado") Integer estado, @JsonProperty(value="costo")Double costo)
	{
		this.id=id;
		this.fechaInicio=fechaInicio;
		this.fechaFin=fechaFin;
		this.descripcion=descripcion;
		this.estado=estado;
		this.costo=costo;
	}
	
	
	
	public Double getCosto() {
		return costo;
	}



	public void setCosto(Double costo) {
		this.costo = costo;
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

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
