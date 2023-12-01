
package Presentacion.Command.ClienteJPA;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class ListarClientesJPACommand implements Command {
	
	public Context execute(Object input) {
		ASClienteJPA asClienteJPA = ASFactory.getInstance().GetASClienteJPA();
		Object output = asClienteJPA.listar();
		return new Context(ContextEnum.LISTARCLIENTESJPA, event(output));
	}

	@SuppressWarnings("unchecked")
	private Object event(Object output) {
		if (((ArrayList<TClienteJPA>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TClienteJPA>) output).get(0).getID()) {
			case -1:
				return EventEnum.BASEDEDATOS;
			case -2:
				return EventEnum.CONCURRENCIA;
			default: // no error
				return output;
			}
		}
	}
	
}