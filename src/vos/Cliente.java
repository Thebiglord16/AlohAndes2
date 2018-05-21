package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {
	
	enum TipoCliente
	{
		ESTUDIANTE, EGRESADO, EMPLEADO, PROFESOR, PADRE, PROFESOR_INVITADO, PERSONA_REGISTRADA
	}
	
	@JsonProperty(value="id")
	private Integer id;
	
	@JsonProperty(value="identificacion")
	private Integer identificacion;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="correo")
	private String correo;
	
	@JsonProperty(value="tipo")
	private Integer tipo;
	
	@JsonProperty(value="fecha_ingreso")
	private String fechaIngreso;
	
	public Cliente(@JsonProperty(value="id") Integer id, @JsonProperty(value="identificacion") Integer identificacion, @JsonProperty(value="correo")String correo, @JsonProperty(value="tipo")Integer tipo, @JsonProperty("nombre")String nombre,@JsonProperty(value="fecha_ingreso")String fechaIngreso)
	{
		this.id=id;
		this.identificacion=identificacion;
		this.correo=correo;
		this.tipo=tipo;
		this.nombre=nombre;
		this.fechaIngreso=fechaIngreso;
	}

	
	
	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(Integer identificacion) {
		this.identificacion = identificacion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}



	public String getFechaIngreso() {
		return fechaIngreso;
	}



	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	
	
}
