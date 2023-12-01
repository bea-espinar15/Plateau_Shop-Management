
package Presentacion.Command.Empleado;

import Negocio.ASFactory.ASFactory;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleado;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class ModificarEmpleadoCommand implements Command {
	
	public Context execute(Object input) {
		ASEmpleado asEmpleado = ASFactory.getInstance().GetASEmpleado();
		Object output = asEmpleado.modificar((TEmpleado) input);
		return new Context(ContextEnum.MODIFICAREMPLEADO, event(output));
	}

	private Object event(Object output) {
		switch ((Integer)output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		case -5:
			return EventEnum.ENTIDADINACTIVA;
		case -6: 
			return EventEnum.ENTIDADREPETIDA;
		case -7:
			return EventEnum.DEPARTAMENTOINEXISTENTE;
		case -8:
			return EventEnum.DEPARTAMENTOINACTIVO;
		default: // no error
			return output;
		}
	}
	
}