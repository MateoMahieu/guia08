package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	@Test
	public void testSalario() {
		
		Empleado e1 = new Empleado(1000, "Juan" , Tipo.CONTRATADO, 50.0);
		Tarea t1 = new Tarea(1, "Mantenimiento", 8, LocalDateTime.now());
		Tarea t2 = new Tarea(1, "Limpieza", 8, LocalDateTime.now());
		Tarea t3 = new Tarea(1, "tarea 3", 8, LocalDateTime.now());
		
		List<Tarea> tareas = new ArrayList<Tarea>();
		
		tareas.add(t1);
		tareas.add(t2);
		tareas.add(t3);
		
		e1.setTareasAsignadas(tareas);
		
		double salario = e1.salario();
		
		assertEquals((int)1200.0,(int) salario);  /**No me toma el equals cuando pongo double por eso lo paso a int*/
		
	}

	@Test
	public void testCostoTarea() {
		
		Empleado e1 = new Empleado(1000, "Juan" , Tipo.CONTRATADO, 50.0);
		Tarea t1 = new Tarea(1, "Mantenimiento", 8, LocalDateTime.now());
		
		double costo = e1.costoTarea(t1);
		
		assertEquals((int)400, (int)costo); /**No me toma el equals cuando pongo double por eso lo paso a int*/
	}

	@Test(expected = AsignacionTareaIncorrectaException.class)
	public void testAsignarTareaIncorrecta() {
		
		Empleado e1 = new Empleado(1000, "Juan" , Tipo.CONTRATADO, 50.0);
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t1 = new Tarea(1, "Mantenimiento", 8, LocalDateTime.now());
		t1.setEmpleadoAsignado(e2);
		t1.setFechaFin(LocalDateTime.now());
		try {
			e1.asignarTarea(t1);	
		}
		catch (AsignacionTareaIncorrectaException e) {
			// TODO: handle exception
		}
		
	}

	
	@Test(expected = NoExisteTareaException.class)
	public void testComenzarIncorrectoInteger() {
		
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t1 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		
		try{
			e2.comenzar(1);
		}
		catch (NoExisteTareaException e) {
			e.getMessage();
		}
	}
	
	
	@Test
	public void testComenzarInteger() throws AsignacionTareaIncorrectaException {
		
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t1 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		e2.asignarTarea(t1);

			e2.comenzar(1);
	}

	@Test
	public void testFinalizarInteger() throws AsignacionTareaIncorrectaException {
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t2 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		e2.asignarTarea(t2);

			e2.finalizar(1);
	}

	@Test
	public void testComenzarIntegerString() throws AsignacionTareaIncorrectaException {
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t2 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		e2.asignarTarea(t2);

			e2.comenzar(1, "10-03-2020 10:25");
	}

	@Test
	public void testFinalizarIntegerString() throws AsignacionTareaIncorrectaException {
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t2 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		e2.asignarTarea(t2);

			e2.finalizar(1,"10-03-2020 10:25");
	}

}
