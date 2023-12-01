
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;


public class ProductoErrorHandler implements ErrorHandler {
	
	private static ProductoErrorHandler instance;
	
	public synchronized static ProductoErrorHandler getInstance() {
		if (instance == null)
			instance = new ProductoErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADREPETIDAACTIVA:
			return new Message("Ya existe un producto activo con el nombre introducido", "Producto existente");
		case ENTIDADINEXISTENTE: 
			return new Message("No existe ningún producto con el ID introducido", "Producto inexistente");
		case ENTIDADINACTIVA:
			return new Message("El producto con el ID introducido está inactivo", "Producto inactivo");
		case ENTIDADREPETIDA:
			return new Message("Ya existe un producto con el nombre introducido", "Producto existente");
		case PROVEEDORINEXISTENTE:
			return new Message("No existe ningún proveedor con el ID introducido", "Proveedor inexistente");
		case PROVEEDORINACTIVO:
			return new Message("El proveedor con el ID introducido está inactivo", "Proveedor inactivo");
		case VENTAINEXISTENTE:
			return new Message("No existe ninguna venta con el ID introducido", "Venta inexistente");
		case ASOCIACIONEXISTENTE:
			return new Message("El proveedor ya estaba asociado al producto", "Asociación existente");
		case ASOCIACIONINEXISTENTE:
			return new Message("El producto y el proveedor no estaban asociados", "Asociación inexistente");
		case STOCKPRODUCTOVENDIDO:
			return new Message("No se puede modificar el stock de un producto incluido en alguna venta", "Producto vendido");
		case TIPOMODIFICADO:
			return new Message("No se puede modificar el tipo de producto", "Tipo modificado");
		case SINTAXIS:
			return new Message("Los datos introducidos son sintacticamente erroneos", "Error");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos del producto.", "Error desconocido");
		}
	}
	
}