
package Presentacion.Controller;

import Presentacion.Command.Command;
import Presentacion.Command.CommandFactory;


public class ApplicationControllerImp extends ApplicationController {

	@Override
	public void manageRequest(Context context) {
		Command command = CommandFactory.getInstance().getCommand(context.getContext());
		Dispatcher.getInstance().Dispatch(command.execute(context.getData()));
		
	}

}