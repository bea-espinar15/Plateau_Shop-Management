
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class DepartamentoErrorHandler implements ErrorHandler {
	
	private static DepartamentoErrorHandler instance;
	
	public synchronized static DepartamentoErrorHandler getInstance() {
		if (instance == null)
			instance = new DepartamentoErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADREPETIDAACTIVA:
			return new Message("Ya existe un departamento activo con el nombre introducido", "Departamento existente");
		case ENTIDADINEXISTENTE: 
			return new Message("No existe ningún departamento con el ID introducido", "Departamento inexistente");
		case ENTIDADINACTIVA:
			return new Message("El departamento con el ID introducido está inactivo", "Departamento inactivo");
		case ENTIDADREPETIDA:
			return new Message("Ya existe un departamento con el nombre introducido", "Departamento existente");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos del departamento.", "Error desconocido");
		}
	}
	
}