package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ContratoHabitacion {

	@JsonProperty(value="habitacion")
	private Habitacion habitacion;
	
	@JsonProperty(value="contratos")
	private List<Contrato>contratos;
	
	public ContratoHabitacion(@JsonProperty(value="habitacion") Habitacion habitacion, @JsonProperty(value="contratos")List<Contrato>contratos)
	{
		this.habitacion = habitacion;
		this.contratos=contratos;
	}
	
	public Habitacion getHabitacion()
	{
		return habitacion;
	}
	
	public void setHabitacion(Habitacion habitacion)
	{
		this.habitacion = habitacion;
	}
	
	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
}
