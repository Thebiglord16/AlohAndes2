package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class HabitacionServicio {

	@JsonProperty(value="habitacion")
	private Habitacion habitacion;
	
	@JsonProperty(value="servicios")
	private List<Servicio> servicios;
	
	public HabitacionServicio(@JsonProperty(value="habitacion") Habitacion habitacion, @JsonProperty(value="servicios")List<Servicio>servicios)
	{
		this.habitacion = habitacion;
		this.servicios = servicios;
	}
	
	public Habitacion getHabitacion()
	{
		return habitacion;
	}
	
	public void setHabitacion(Habitacion habitacion)
	{
		this.habitacion = habitacion;
	}
	
	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}
}
