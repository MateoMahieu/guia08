package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;

import frsf.isi.died.guia08.problema01.modelo.AsignacionTareaIncorrectaException;
import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.NoExisteEmpleadoException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class AppRRHHTest {

	@Test
	public void asignarTareaCorrectaTest() throws NoExisteEmpleadoException {
		
		AppRRHH a1 = new AppRRHH();
		
		a1.agregarEmpleadoContratado(1000, "Juan", 50.0);
		a1.agregarEmpleadoEfectivo(2000, "Maria", 50.0);
		
		a1.asignarTarea(2000, 5, "Nueva tarea", 10);
		
	}
	
	@Test(expected = NoExisteEmpleadoException.class)
	public void asignarTareaIncorrectaTest() throws NoExisteEmpleadoException {
		
		AppRRHH a1 = new AppRRHH();
		
		a1.agregarEmpleadoContratado(1000, "Juan", 50.0);
		a1.agregarEmpleadoEfectivo(2000, "Maria", 50.0);
		
		a1.asignarTarea(5000, 5, "Nueva tarea", 10);
		
	}
	
	@Test
	public void cargarTareasTest() throws FileNotFoundException, IOException, AsignacionTareaIncorrectaException, NoExisteEmpleadoException{
		
		AppRRHH a1 = new AppRRHH();
		
		
		a1.cargarEmpleadosEfectivosCSV("C:\\Users\\Mateo\\eclipse-workspace\\guia08\\EmpleadosEfectivos.csv");
		
		a1.cargarTareasCSV("C:\\Users\\Mateo\\eclipse-workspace\\guia08\\Tareas.csv");
		
		Empleado e1 = null;
		
		for(Empleado e: a1.getEmpleados()) {
			
			if(e.getCuil()==2) {
				e1 = e;
			}
		}
		
		int resultado = e1.getTareasAsignadas().size();  /**revisa si se le asignarion al empleado las 2 tareas leidas en el csv*/
		
		assertEquals(2, resultado);
		
	}
	
	
	
	@Test
	public void cargarEmpleadosEfectivosTest() throws FileNotFoundException, IOException{
		
		AppRRHH a1 = new AppRRHH();
		
		
		a1.cargarEmpleadosEfectivosCSV("C:\\Users\\Mateo\\eclipse-workspace\\guia08\\EmpleadosEfectivos.csv");
		
		assertEquals(4, a1.getEmpleados().size());
		
	}
	
	
	@Test
	public void cargarEmpleadosContratadosTest() throws FileNotFoundException, IOException{
		
		AppRRHH a1 = new AppRRHH();
		
		
		a1.cargarEmpleadosEfectivosCSV("C:\\Users\\Mateo\\eclipse-workspace\\guia08\\EmpleadosContratados.csv");
		
		assertEquals(4, a1.getEmpleados().size());
	}
	
	
	
	

}
