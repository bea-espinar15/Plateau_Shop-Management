
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class VentaErrorHandler implements ErrorHandler {
	
	private static VentaErrorHandler instance;
	
	public synchronized static VentaErrorHandler getInstance() {
		if (instance == null)
			instance = new VentaErrorHandler();
		return instance;
	}
	
	public Message getMessage(EventEnum event) {
		switch (event) {
		case BASEDEDATOS:
			return new Message("Se ha producido un error con la base de datos", "Base de datos");
		case CONCURRENCIA:
			return new Message("Se ha producido un error de concurrencia", "Concurrencia");
		case ENTIDADINEXISTENTE:
			return new Message("No existe ninguna venta con el ID introducido", "Venta inexistente");
		case STOCKINSUFICIENTE:
			return new Message("No hay suficiente stock del producto que se desea vender", "Stock insuficiente");
		case PRODUCTOINEXISTENTE:
			return new Message("No existe ningún producto con el ID introducido", "Producto inexistente");
		case PRODUCTOINACTIVO:
			return new Message("El producto con el ID introducido está inactivo", "Producto inactivo");
		case CLIENTEINEXISTENTE:
			return new Message("No existe ningún cliente con el ID introducido", "Cliente inexistente");
		case CLIENTEINACTIVO:
			return new Message("El cliente con el ID introducido está inactivo", "Cliente inactivo");
		case EMPLEADOINEXISTENTE:
			return new Message("No existe ningún empleado con el ID introducido", "Empleado inexistente");
		case EMPLEADOINACTIVO:
			return new Message("El empleado con el ID introducido está inactivo", "Empleado inactivo");
		case PRODUCTONOINCLUIDO:
			return new Message("El producto no está incluido en la venta", "Producto no incluido");
		default:
			return new Message("Se ha producido un error desconocido en el tratamiento de datos de la venta.", "Error desconocido");
		}
	}
	
}