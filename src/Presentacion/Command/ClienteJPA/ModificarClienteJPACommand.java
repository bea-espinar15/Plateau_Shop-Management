
package Presentacion.Command.ClienteJPA;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class ModificarClienteJPACommand implements Command {
	
	public Context execute(Object input) {
		ASClienteJPA asClienteJPA = ASFactory.getInstance().GetASClienteJPA();
		Object output = asClienteJPA.modificar((TClienteJPA) input);
		return new Context(ContextEnum.MODIFICARCLIENTEJPA, event(output));
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
		default: // no error
			return output;
		}
	}
	
}