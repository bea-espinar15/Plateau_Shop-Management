
package Presentacion.Command.ClienteJPA;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class MostrarClienteJPACommand implements Command {

	public Context execute(Object input) {
		ASClienteJPA asClienteJPA = ASFactory.getInstance().GetASClienteJPA();
		Object output = asClienteJPA.mostrar((Integer) input);
		return new Context(ContextEnum.MOSTRARCLIENTEJPA, event(output));
	}

	private Object event(Object output) {
		switch (((TClienteJPA)output).getID()) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		default: // no error
			return output;
		}
	}
	
}