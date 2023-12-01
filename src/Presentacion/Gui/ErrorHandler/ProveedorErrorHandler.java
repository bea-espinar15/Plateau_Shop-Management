
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class ProveedorErrorHandler implements ErrorHandler {
	
	private static ProveedorErrorHandler instance;
	
	public synchronized static ProveedorErrorHandler getInstance() {
		if (instance == null)
			instance = new ProveedorErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADREPETIDAACTIVA:
			return new Message("Ya existe un proveedor activo con el CIF introducido", "Proveedor existente");
		case ENTIDADINEXISTENTE:
			return new Message("No existe ning�n proveedor con el ID introducido", "Proveedor inexistente");
		case ENTIDADINACTIVA:
			return new Message("El proveedor con el ID introducido est� inactivo", "Proveedor inactivo");
		case ENTIDADREPETIDA:
			return new Message("Ya existe un proveedor con el CIF introducido", "Proveedor existente");
		case PRODUCTOINEXISTENTE:
			return new Message("No existe ning�n producto con el ID introducido", "Producto inexistente");
		case PRODUCTOINACTIVO:
			return new Message("El producto con el ID introducido est� inactivo", "Producto inactivo");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos del proveedor.", "Error desconocido");
		}
	}
	
}