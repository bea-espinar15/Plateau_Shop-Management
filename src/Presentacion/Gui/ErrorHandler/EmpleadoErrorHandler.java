package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class EmpleadoErrorHandler implements ErrorHandler {
	
	private static EmpleadoErrorHandler instance;
	
	public synchronized static EmpleadoErrorHandler getInstance() {
		if (instance == null)
			instance = new EmpleadoErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADREPETIDAACTIVA:
			return new Message("Ya existe un empleado activo con el DNI introducido", "Empleado existente");
		case ENTIDADINEXISTENTE: 
			return new Message("No existe ningún empleado con el ID introducido", "Empleado inexistente");
		case ENTIDADINACTIVA:
			return new Message("El empleado con el ID introducido está inactivo", "Empleado inactivo");
		case ENTIDADREPETIDA:
			return new Message("Ya existe un empleado con el DNI introducido", "Empleado existente");
		case DEPARTAMENTOINEXISTENTE:
			return new Message("No existe ningún departamento con el ID introducido", "Departamento inexistente");
		case DEPARTAMENTOINACTIVO:
			return new Message("El departamento con el ID introducido está inactivo", "Departamento inactivo");
		case TIPOMODIFICADO:
			return new Message("No se puede modificar el tipo de empleado", "Tipo modificado");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos del empleado.", "Error desconocido");
		}
	}
	
}