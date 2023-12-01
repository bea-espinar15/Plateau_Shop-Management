
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public class ErrorHandlerManagerJPA {

	private static ErrorHandlerManagerJPA instance;

	public synchronized static ErrorHandlerManagerJPA getInstance() {
		if (instance == null)
			instance = new ErrorHandlerManagerJPA();
		return instance;
	}

	public Message getMessage(EntityEnumJPA entity, EventEnum event) {

		switch (entity) {
		case CLIENTEJPA:
			return ClienteJPAErrorHandler.getInstance().getMessage(event);
		case DEPARTAMENTO:
			return DepartamentoErrorHandler.getInstance().getMessage(event);
		case EMPLEADO:
			return EmpleadoErrorHandler.getInstance().getMessage(event);
		case PRODUCTO:
			return ProductoErrorHandler.getInstance().getMessage(event);
		case PROVEEDOR:
			return ProveedorErrorHandler.getInstance().getMessage(event);
		case VENTA:
			return VentaErrorHandler.getInstance().getMessage(event);
		default:
				return new Message("Se ha producido un error desconocido en el tratamiento de los datos", "Error desconocido");
		}
		
	}

}