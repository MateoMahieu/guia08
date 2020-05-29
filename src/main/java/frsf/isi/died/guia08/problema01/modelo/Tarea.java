package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada,LocalDateTime localDate) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.empleadoAsignado = null;
		this.fechaInicio = localDate;
		this.facturada = false;
	}
	
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.empleadoAsignado = null;
		this.facturada = false;
	}

	public void setEmpleadoAsignado(Empleado empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}

	public Tarea() {
		super();
	}
	

	public void asignarEmpleado(Empleado e) throws AsignacionIncorrectaEmpleadoATareaException{
		
		if(fechaFin != null || empleadoAsignado != null) { /**Comentario: lo tomo como si una tarea no puede tener mas de 1 empleado asignado
																		  ni estar en estado finalizada*/
			
			throw new AsignacionIncorrectaEmpleadoATareaException("No se puede asignar el empleado a la tarea, la misma ya tiene asignado uno o esta finalizada");
			
		}
		
		else {	
				this.setEmpleadoAsignado(e);
		}
			
		// si la tarea ya tiene un empleado asignado
		// y tiene fecha de finalizado debe lanzar una excepcion
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}


	public String asCsv() {
		return this.id+ ";\""+ this.descripcion+"\";"+this.duracionEstimada+ ";\""+ this.empleadoAsignado.getCuil() + ";\""+ this.empleadoAsignado.getNombre();
	}
	
	
}
