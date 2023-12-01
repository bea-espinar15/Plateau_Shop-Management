package Presentacion.Command.ClienteJPA;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class AltaClienteJPACommand implements Command {
	
	@Override
	public Context execute(Object input) {
		ASClienteJPA asClienteJPA = ASFactory.getInstance().GetASClienteJPA();
		Object output = asClienteJPA.alta((TClienteJPA) input);
		return new Context(ContextEnum.ALTACLIENTEJPA, event(output));
	}

	private Object event(Object output) {
		switch((Integer)output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -3:
			return EventEnum.ENTIDADREPETIDAACTIVA;
		default: // no error
			return output;
		}
	}
	
}