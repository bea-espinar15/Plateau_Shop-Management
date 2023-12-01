
package Presentacion.Gui.Panels.Producto;

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

public class BajaProductoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static BajaProductoPanel instance;
	private Panel_Main_Producto parent;
	private JTextField field;

	private BajaProductoPanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA

		JLabel bajaProducto = new JLabel("BAJA PRODUCTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		bajaProducto.setFont(f);
		bajaProducto.setHorizontalAlignment(SwingConstants.CENTER);
		bajaProducto.setForeground(Color.DARK_GRAY);
		bajaProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(bajaProducto, BorderLayout.PAGE_START);

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
					Context request = new Context(ContextEnum.BAJAPRODUCTO, Integer.parseInt(field.getText()));
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

	public static BajaProductoPanel getInstance() {
		if (instance == null)
			instance = new BajaProductoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Producto parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.PRODUCTO,((EventEnum) o));
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			JOptionPane.showMessageDialog(parent, "El producto se ha dado de baja con exito");
			clear();
		}
	}

	public void clear() {
		field.setText("");
	}

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