
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class ClienteJPAErrorHandler implements ErrorHandler {
	
	private static ClienteJPAErrorHandler instance;
	
	public synchronized static ClienteJPAErrorHandler getInstance() {
		if (instance == null)
			instance = new ClienteJPAErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADREPETIDAACTIVA:
			return new Message("Ya existe un cliente activo con el DNI introducido", "Cliente existente");
		case ENTIDADINEXISTENTE: 
			return new Message("No existe ningún cliente con el ID introducido", "Cliente inexistente");
		case ENTIDADINACTIVA:
			return new Message("El cliente con el ID introducido está inactivo", "Cliente inactivo");
		case ENTIDADREPETIDA:
			return new Message("Ya existe un cliente con el DNI introducido", "Cliente existente");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos del departamento.", "Error desconocido");
		}
	}
	
}