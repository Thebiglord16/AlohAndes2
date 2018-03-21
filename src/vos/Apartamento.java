package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Apartamento {

	public enum Tipo 
	{
		
	}
	
	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="direccion")
	private String direccion;
	
	@JsonProperty(value="capacidad")
	private Integer cacpacidad;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="tipo")
	private Integer tipo;
	
	@JsonProperty(value="vecesSolicitada")
	private Integer vecesSolicitada;
	
	@JsonProperty(value="ofertada")
	private Boolean ofertada;
	
	public Apartamento(@JsonProperty(value="id") Integer id, @JsonProperty(value="direccion") String direccion, @JsonProperty(value="capacidad") Integer capacidad,@JsonProperty(value="descripcion") String descripcion, @JsonProperty(value="tipo") Integer tipo,@JsonProperty(value="vecesSolicitada")Integer vecesSolicitada, @JsonProperty(value="ofertada")Boolean ofertada)
	{
		this.id=id;
		this.direccion=direccion;
		this.cacpacidad=capacidad;
		this.descripcion=descripcion;
		this.direccion=direccion;
		this.tipo=tipo;
		this.vecesSolicitada=vecesSolicitada;
		this.ofertada=ofertada;
	}

	public Integer getVecesSolicitada() {
		return vecesSolicitada;
	}

	public void setVecesSolicitada(Integer vecesSolicitada) {
		this.vecesSolicitada = vecesSolicitada;
	}

	public Boolean isOfertada() {
		return ofertada;
	}

	public void setOfertada(Boolean ofertada) {
		this.ofertada = ofertada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getCacpacidad() {
		return cacpacidad;
	}

	public void setCacpacidad(Integer cacpacidad) {
		this.cacpacidad = cacpacidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	
	
}
