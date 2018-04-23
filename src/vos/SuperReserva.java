package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SuperReserva {
	
	@JsonProperty("tipoAcomodamiento")
	String tipoAcomodamiento;
	
	@JsonProperty("fechaInicio")
	String fechaInicio;
	
	@JsonProperty("fechaFin")
	String fechaFin;
	
	@JsonProperty("cantidad")
	int cantidad;
	
	@JsonProperty("clientes")
	List<Cliente> clientes;
	
	public SuperReserva(@JsonProperty("tipoAcomodamiento")String tipoAcomodamiento, @JsonProperty("fechaInicio") String fechaInicio,	@JsonProperty("fechaFin") String fechaFin,@JsonProperty("cantidad") int cantidad, @JsonProperty("clientes")List<Cliente> clientes)
	{
		this.cantidad=cantidad;
		this.clientes=clientes;
		this.tipoAcomodamiento=tipoAcomodamiento;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getTipoAcomodamiento() {
		return tipoAcomodamiento;
	}

	public void setTipoAcomodamiento(String tipoAcomodamiento) {
		this.tipoAcomodamiento = tipoAcomodamiento;
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
