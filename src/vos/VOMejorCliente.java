package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VOMejorCliente {

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
	
	@JsonProperty(value="habitacionId")
	private Integer habitacionId;
	
	@JsonProperty(value="costo")
	private Double costo;
	
	
	
	public VOMejorCliente(@JsonProperty(value="id")Integer id, @JsonProperty(value="identificacion")Integer identificacion, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="correo")String correo, @JsonProperty(value="tipo")Integer tipo,
			@JsonProperty(value="habitacionId")Integer habitacionId, @JsonProperty(value="costo")Double costo) {
		this.id = id;
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.correo = correo;
		this.tipo = tipo;
		this.habitacionId = habitacionId;
		this.costo = costo;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Integer getHabitacionId() {
		return habitacionId;
	}

	public void setHabitacionId(Integer habitacionId) {
		this.habitacionId = habitacionId;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	
}
