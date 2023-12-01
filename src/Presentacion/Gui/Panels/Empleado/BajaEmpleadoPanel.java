
package Presentacion.Gui.Panels.Empleado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;


public class BajaEmpleadoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;
	
	private static BajaEmpleadoPanel instance;	
	private Panel_Main_Empleado parent;	
	private JTextField field;
	
	private BajaEmpleadoPanel(){
		initGUI();		
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		
		JLabel bajaEmpleado = new JLabel("BAJA EMPLEADO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		bajaEmpleado.setFont(f);
		bajaEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
		bajaEmpleado.setForeground(Color.DARK_GRAY);
		bajaEmpleado.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(bajaEmpleado, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel();
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
		idPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
		JLabel id = new JLabel("ID:");
		this.field = new JTextField(10);
		idPanel.add(id);
		idPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		idPanel.add(field);

		idPanel.add(Box.createRigidArea(new Dimension(15, 0)));

		JButton ok = new JButton("Aceptar");
		idPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.BAJAEMPLEADO, Integer.parseInt(field.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		centerPanel.add(idPanel);

		this.add(centerPanel, BorderLayout.CENTER);
		
	}

	public static BajaEmpleadoPanel getInstance() {
		if (instance == null)
			instance = new BajaEmpleadoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Empleado parent) {
		this.parent = parent;
	}
	
	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.EMPLEADO, (EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			JOptionPane.showMessageDialog(parent, "El empleado se ha dado de baja con exito");
			clear();
		}
	}

	public void clear() {
		field.setText("");
	}

	@Override
	public boolean validation() {
		try {
			if (field.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(field.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}