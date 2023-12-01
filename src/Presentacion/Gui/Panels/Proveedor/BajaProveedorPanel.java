
package Presentacion.Gui.Panels.Proveedor;

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

import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class BajaProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static BajaProveedorPanel instance;
	private Panel_Main_Proveedor parent;
	private JTextField idField;

	// __________ CONSTRUCTOR __________
	private BajaProveedorPanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA
		JLabel bajaProveedor = new JLabel("BAJA PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		bajaProveedor.setFont(f);
		bajaProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		bajaProveedor.setForeground(Color.DARK_GRAY);
		bajaProveedor.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(bajaProveedor, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel();
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
		idPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
		JLabel id = new JLabel("ID:");
		this.idField = new JTextField(10);
		idPanel.add(id);
		idPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		idPanel.add(idField);

		idPanel.add(Box.createRigidArea(new Dimension(15, 0)));

		JButton ok = new JButton("Aceptar");
		idPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.BAJAPROVEEDOR, Integer.parseInt(idField.getText()));
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

	public static BajaProveedorPanel getInstance() {
		if (instance == null) {
			instance = new BajaProveedorPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Proveedor parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.PROVEEDOR, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			JOptionPane.showMessageDialog(parent, "El proveedor se ha dado de baja con exito");
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