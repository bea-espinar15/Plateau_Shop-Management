
package Presentacion.Gui.Panels;

import javax.swing.JPanel;
import Presentacion.Command.EventEnum;

public abstract class GeneralPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public abstract void update(Object o);
	public abstract boolean validation();
	public abstract void clear();
	
	public boolean hasError(Object response) {
		return response instanceof EventEnum;
	}
	
}