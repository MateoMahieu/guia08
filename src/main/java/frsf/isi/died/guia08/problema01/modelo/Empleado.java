package frsf.isi.died.guia08.problema01.modelo;

import java.time.Duration;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO};  

	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;
	
	private Predicate<Tarea> puedeAsignarTarea = t-> t.getEmpleadoAsignado() == null || t.getFechaFin() == null;
	
	
	//constructores y getters and setters
	
	public Empleado(Integer cuil, String nombre, Tipo tipo, Double costoHora) {
		super();
		this.cuil = cuil;
		this.nombre = nombre;
		this.tipo = tipo;
		this.costoHora = costoHora;
		this.tareasAsignadas =  new ArrayList<Tarea>();
	}
	
	public Empleado(String nombre, Tipo tipo) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.tareasAsignadas = new ArrayList<Tarea>();
	}
	
	public List<Tarea> getTareasAsignadas() {
		return tareasAsignadas;
	}

	public void setTareasAsignadas(List<Tarea> tareasAsignadas) {
		this.tareasAsignadas = tareasAsignadas;
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Integer getCuil() {
		return cuil;
	}

	public void setCuil(Integer cuil) {
		this.cuil = cuil;
	}

	public Empleado() {
		super();
	}

	
	
	public Double salario() { 
															   
			Double totalSuma = this.tareasAsignadas.stream().filter(t -> t.getFacturada() == false).mapToDouble(t -> this.costoTarea(t)).sum();
			
			this.tareasAsignadas.stream().filter(t -> t.getFacturada() == false).forEach(t -> t.setFacturada(true));
			
			return totalSuma;
			
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
	}
	
	
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	
	public Double costoTarea(Tarea t) {
		
		double resultado = 0.0;
		
		if(t.getFechaFin() != null) { /**caso en que la tarea fue finalizada*/
			
			 
			if(this.terminoTareaAntes(t)) {
			
					switch (tipo) {
					
					case CONTRATADO:
						
						resultado = ((this.costoHora) * 1.3)* t.getDuracionEstimada();
						
		
					case EFECTIVO:
						
						resultado = ((this.costoHora) * 1.2)* t.getDuracionEstimada();
						
					}
				}
			
			else {
				
				switch (tipo) {
				
				case CONTRATADO:
					
					if(this.cantidadDeDiasTareaAtrasada(t) > 2) {
						
						resultado = ((this.costoHora) * 0.75) * t.getDuracionEstimada();
						
					}
					else {
						resultado = (this.costoHora) * t.getDuracionEstimada();
					}

				case EFECTIVO:
					
					resultado = (this.costoHora) * t.getDuracionEstimada();
					
				}
			}
			}
		
		else { /**caso en q la tarea no haya sido terminada, verifico para el contratado si esta atrasada mas de 2 dias
		 			sino cobra en base a lo estimado, el efectivo por lo q entiendo cobra lo estimado igual, aunque no haya terminado
		 			ni este atrasado*/
			
			switch (tipo) {
			
			case CONTRATADO:
				
				if(this.cantidadDeDiasTareaAtrasada(t) > 2) {
					
					resultado = ((this.costoHora) * 0.75) * t.getDuracionEstimada();
					
				}
				else {
					resultado = (this.costoHora) * t.getDuracionEstimada();
				}

			case EFECTIVO:
				
				resultado = (this.costoHora) * t.getDuracionEstimada();
				
			}
			
		}
		
		return resultado;
			
	}
	
	

	private long cantidadDeDiasTareaAtrasada(Tarea t) {
		
		long resultado;
		
		long diasDuracionDetarea = (t.getDuracionEstimada() / 4);
		
		if(t.getFechaFin()!=null) {
			
		Duration duracion = Duration.between(t.getFechaInicio(),t.getFechaFin());
		resultado = duracion.toDays() - diasDuracionDetarea;
		
		}
		
		else {
			
			Duration duracion = Duration.between(t.getFechaInicio(),LocalDateTime.now());
			resultado = duracion.toDays() - diasDuracionDetarea;
			
		}
		
		return resultado;

	}
	
	
	
	private boolean terminoTareaAntes(Tarea t) {
		
		Duration duracion = Duration.between(t.getFechaInicio(),t.getFechaFin());
		if((duracion.toDays() * 4) < t.getDuracionEstimada()) {
			return true;
		}
		
		else return false;
	}
		
	
	
	public Boolean asignarTarea(Tarea t) throws AsignacionTareaIncorrectaException {
		
		
		if (puedeAsignarTarea.test(t)) {
			
			switch (tipo) {
			
			case CONTRATADO:
				
				if(this.cantTareasPendientes(tareasAsignadas) <= 5) {
					tareasAsignadas.add(t);
				}
				else 
					return false;
				
				break;

			case EFECTIVO:
			
				if(this.cantidadHorasTareasPendientes(tareasAsignadas) <= 15) {
					tareasAsignadas.add(t);
				}
				else 
					
					return false;
				
				break;
			}
			
		}
		
		else {	
			
		throw new AsignacionTareaIncorrectaException("La tarea que se quiere asignar es incorrecta, seleccione otra tarea.");
		
		}
		return true;
		
		}
	


	private int cantidadHorasTareasPendientes (List<Tarea> tareas) {
		int cantHoras = 0;
		for(Tarea t: tareas) {
			
			if(t.getFechaFin() == null)
				cantHoras += t.getDuracionEstimada(); 
		}
		
		return cantHoras;
		
	}
	
	
	private int cantTareasPendientes(List<Tarea> tareas) {
		
		int cantidad = 0;
		for(Tarea t: tareas) {
			
			if (t.getFechaFin() == null) cantidad++; 
			
		}
		
		return cantidad;
		
	}
	
	
	
	public void comenzar(Integer idTarea) {
		
		 tareasAsignadas.stream()
						.filter(t -> t.getId().equals(idTarea))
						.forEach(t -> {if (t != null) {
					
							t.setFechaInicio(LocalDateTime.now());
							
						}
						
						else {
							
							throw new NoExisteTareaException("La tarea que desea comenzar no existe.");
						}
						
						});

		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
	}
	
	
	
	public void finalizar(Integer idTarea){
		
		tareasAsignadas.stream()
		.filter(t -> t.getId().equals(idTarea))
		.forEach(t -> {if (t != null) {
	
			t.setFechaFin(LocalDateTime.now());
			
		}
		
		else {
			
			throw new NoExisteTareaException("La tarea que desea comenzar no existe.");
		}
		
		});
		
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
	}

	
	
	public void comenzar(Integer idTarea,String fecha){
		
		LocalDateTime fechaformateada = LocalDateTime.parse(fecha,DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		
		tareasAsignadas.stream()
		.filter(t -> t.getId().equals(idTarea))
		.forEach(t -> {if (t != null) {
	
			t.setFechaInicio(fechaformateada);
			
		}
		
		else {
			
			throw new NoExisteTareaException("La tarea que desea comenzar no existe.");
		}
		
		});
		
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
	}
	
	
	
	public void finalizar(Integer idTarea,String fecha) throws NoExisteTareaException {
		
		
		LocalDateTime fechaformateada = LocalDateTime.parse(fecha,DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
		
		tareasAsignadas.stream()
		.filter(t -> t.getId().equals(idTarea))
		.forEach(t -> {if (t != null) {
	
			t.setFechaFin(fechaformateada);
			
		}
		
		else {
			throw new NoExisteTareaException("La tarea que desea comenzar no existe.");
		}
		
		});
		
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
	}
	
	
	
	
	
}
