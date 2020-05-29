package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class TareaTest {

	
	@Test
	public void asignarEmpleadoCorrectoTest() throws AsignacionIncorrectaEmpleadoATareaException {
		
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Tarea t1 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		
		t1.asignarEmpleado(e2);
		
	}
	
	@Test (expected = AsignacionIncorrectaEmpleadoATareaException.class)
	public void asignarEmpleadoIncorrectoTest() throws AsignacionIncorrectaEmpleadoATareaException {
		
		Empleado e2 = new Empleado(2000, "Maria" , Tipo.EFECTIVO, 50.0);
		Empleado e3 = new Empleado(3000, "Juana" , Tipo.CONTRATADO, 50.0);
		Tarea t1 = new Tarea(1, "Proyecto 1", 8, LocalDateTime.now());
		t1.setEmpleadoAsignado(e3);
		
		t1.asignarEmpleado(e2);
	}
	

}
