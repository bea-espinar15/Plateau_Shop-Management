
package Presentacion.Gui.ErrorHandler;

import Presentacion.Command.EventEnum;

public interface ErrorHandler {
	public abstract Message getMessage(EventEnum event);
}