package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Operador {
	
	public enum TipoOperador
	{
		HOTEL , HOSTAL, PERSONA_NATURAL, PROFESOR, EMPLEADO, EGRESADO, ESTUDIANTE, PADRE, EMPRESA_VIVIENDA
	}
	
	@JsonProperty(value="id")
	private Integer id;
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="correo")
	private String correo;
	@JsonProperty(value="identificacion")
	private Integer identificacion;
	@JsonProperty(value="tipo")
	private Integer tipo;
	
	public Operador(@JsonProperty(value="id") Integer id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="correo")String correo, @JsonProperty(value="identificacion") Integer identificacion, @JsonProperty(value="tipo") Integer tipo)
	{
		this.id=id;
		this.correo=correo;
		this.identificacion=identificacion;
		this.nombre=nombre;
		this.tipo=tipo;
	}

	
	
	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(Integer identificacion) {
		this.identificacion = identificacion;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	
}
