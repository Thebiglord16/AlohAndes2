package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadorHabitacion {

	@JsonProperty(value="operador")
	private Operador operador;
	
	@JsonProperty(value="habitaciones")
	private List<Habitacion> habitaciones;
	
	public OperadorHabitacion(@JsonProperty(value="operador") Operador operador, @JsonProperty(value="habitaciones")List<Habitacion>habitaciones)
	{
		this.operador = operador;
		this.habitaciones = habitaciones;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	
}
