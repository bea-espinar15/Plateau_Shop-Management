
package Presentacion.Gui.Panels.ClienteJPA;

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

import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;


public class BajaClienteJPAPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static BajaClienteJPAPanel instance;
	private Panel_Main_ClienteJPA parent;
	private JTextField idField;

	private BajaClienteJPAPanel(){
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());

		//__________ CABECERA _________
		JLabel titleLbl = new JLabel("BAJA CLIENTE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		titleLbl.setFont(f);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setForeground(Color.DARK_GRAY);
		titleLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(titleLbl, BorderLayout.PAGE_START);

		//___________ CENTER ______________
		JPanel centerPanel = new JPanel();

		//___________ CENTER - ID FIELD + BUTTON ______________
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
		idPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
		JLabel idLabel = new JLabel("ID:");
		idField = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		idPanel.add(idField);

		idPanel.add(Box.createRigidArea(new Dimension(15, 0)));

		JButton aceptarButton = new JButton("Aceptar");
		idPanel.add(aceptarButton);
		aceptarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.BAJACLIENTEJPA, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		centerPanel.add(idPanel);

		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static BajaClienteJPAPanel getInstance() {
		if (instance == null)
			instance = new BajaClienteJPAPanel();
		return instance;
	}

	public void setParent(Panel_Main_ClienteJPA parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.CLIENTEJPA, ((EventEnum)o));
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			String mensaje = "El cliente se ha dado de baja correctamente";
			JOptionPane.showMessageDialog(parent, mensaje);
			clear();
		}
	}

	public void clear() {
		idField.setText("");
	}
	
	@Override
	public boolean validation() {
		try {
			if (idField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}