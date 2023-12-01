
package Presentacion.Command.Departamento;

import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Presentacion.Command.Command;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;


public class AltaDepartamentoCommand implements Command {
	
	@Override
	public Context execute(Object input) {
		ASDepartamento asDepartamento = ASFactory.getInstance().GetASDepartamento();
		Object output = asDepartamento.alta((TDepartamento) input);
		return new Context(ContextEnum.ALTADEPARTAMENTO, event(output));
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