package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import frsf.isi.died.guia08.problema01.modelo.AsignacionTareaIncorrectaException;
import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.NoExisteEmpleadoException;
import frsf.isi.died.guia08.problema01.modelo.NoExisteTareaException;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public AppRRHH(List<Empleado> empleados) {
		super();
		this.empleados = empleados;
	}
	


	public AppRRHH() {
		super();
		this.empleados = new ArrayList<Empleado>();
	}

	
	

	public List<Empleado> getEmpleados() {
		return empleados;
	}



	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}



	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		
		Empleado empleado = new Empleado(cuil, nombre, Tipo.CONTRATADO, costoHora);
		
		empleados.add(empleado);
		
		// crear un empleado
		// agregarlo a la lista
	}
	
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		
		Empleado empleado = new Empleado(cuil, nombre, Tipo.EFECTIVO, costoHora);
		
		empleados.add(empleado);
		
		// crear un empleado
		// agregarlo a la lista		
	}
	
	
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws NoExisteEmpleadoException {
		
		
		Optional<Empleado> empleado = this.buscarEmpleado(e -> e.getCuil().equals(cuil));
		
		if(empleado.isPresent()) {
			
			Tarea tarea = new Tarea(idTarea,descripcion,duracionEstimada);
			
			try {
				empleado.get().asignarTarea(tarea);
			}
			catch (AsignacionTareaIncorrectaException e) {
				e.getMessage();
				e.printStackTrace();
			}
			
		}
		else {
			throw new NoExisteEmpleadoException("no se ha encontrado el empleado buscado");
		}
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
	}
	
	
	
	public void empezarTarea(Integer cuil,Integer idTarea) throws NoExisteEmpleadoException {
		
		Optional<Empleado> empleado = this.buscarEmpleado(e -> e.getCuil().equals(cuil));
		
		if(empleado.isPresent()) {
			
			try{
				empleado.get().comenzar(idTarea);
			}
			catch (NoExisteTareaException e) {
				e.getMessage();
			}
			
		}
		else {
			
			throw new NoExisteEmpleadoException("El empleado buscado no existe");
			
		}
		
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
	}
	
	
	
	public void terminarTarea(Integer cuil,Integer idTarea) throws NoExisteEmpleadoException {
		
	Optional<Empleado> empleado = this.buscarEmpleado(e -> e.getCuil().equals(cuil));
		
		if(empleado.isPresent()) {
			
			try{
				empleado.get().comenzar(idTarea);
			}
			catch (NoExisteTareaException e) {
				e.getMessage();
				e.printStackTrace();
			}
			
		}
		else {
			
			throw new NoExisteEmpleadoException("El empleado buscado no existe");
			
		}
	}
	
	

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			
			try(BufferedReader in = new BufferedReader(fileReader)){
				
				String linea = null;
				
				while((linea = in.readLine())!=null) {
					
					String[] fila = linea.split(";");
						
					
					 	this.agregarEmpleadoContratado(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));

						
				}
			}
		}
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
	}
	
	

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) throws FileNotFoundException, IOException { 
		
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			
			try(BufferedReader in = new BufferedReader(fileReader)){
				
				String linea = null;
				
				while((linea = in.readLine())!=null) {
					
					String[] fila = linea.split(";");
						
					
						this.agregarEmpleadoEfectivo(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));


				}
			}
		}
		
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoEfectivo		
	}
	
	
	

	public void cargarTareasCSV(String nombreArchivo) throws FileNotFoundException, IOException, AsignacionTareaIncorrectaException, NoExisteEmpleadoException{
		
		try(Reader fileReader = new FileReader(nombreArchivo)) {
			
			try(BufferedReader in = new BufferedReader(fileReader)){
				
				String linea = null; 
				
				while((linea = in.readLine())!=null) {
					
					String[] fila = linea.split(";");
						
						Tarea t = new Tarea();
						
						t.setId(Integer.valueOf(fila[0]));
						t.setDescripcion(fila[1]);
						t.setDuracionEstimada(Integer.valueOf(fila[2]));
						Integer cuilEmpleado = (Integer.valueOf(fila[3]));
						
						Optional<Empleado> empleado = this.buscarEmpleado(e -> e.getCuil().equals(cuilEmpleado));
						
						if(empleado.isPresent()) {
							
							empleado.get().asignarTarea(t);
							
						}	
						
						else {
							throw new NoExisteEmpleadoException("no se ha encontrado el empleado buscado");
						}
						
						}
					}
				}
		
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
	}
	
	
	
	private void guardarTareasTerminadasCSV() throws IOException {
		
	for(Empleado e: empleados) {
			
			List<Tarea> tareas = e.getTareasAsignadas();
			
			for(Tarea t: tareas){
				
				if(t.getFechaFin()!= null && t.getFacturada() == false){
					
					try(Writer fileWriter= new FileWriter("tareasTerminadas.csv",true)) {
						
						try(BufferedWriter out = new BufferedWriter(fileWriter)){
							
						out.write(t.asCsv()+ System.getProperty("line.separator"));
						
						}
						}
				}

			}
		}
			
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
	}


	
	
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}
	

	public Double facturar() throws IOException {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
