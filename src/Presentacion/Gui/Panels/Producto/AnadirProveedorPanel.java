
package Presentacion.Gui.Panels.Producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import utilities.Pair;

public class AnadirProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static AnadirProveedorPanel instance;
	private Panel_Main_Producto parent;
	JTextField idProductoField, idProveedorField;

	private AnadirProveedorPanel() {
		initGUI();
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());

		// __________ CABECERA _________
		JLabel titleLbl = new JLabel("ANADIR PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		titleLbl.setFont(f);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setForeground(Color.DARK_GRAY);
		titleLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(titleLbl, BorderLayout.PAGE_START);

		// ___________ CENTER ______________
		JPanel centerPanel = new JPanel(null);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// ___________ CENTER - ID FIELDS ______________
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
		idPanel.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));
		idPanel.setMinimumSize(new Dimension(600, 100));
		idPanel.setMaximumSize(new Dimension(600, 100));
		idPanel.setPreferredSize(new Dimension(600, 100));

		// ID PRODUCTO
		JPanel productoPanel = new JPanel(new FlowLayout());
		JLabel idProductoLbl = new JLabel("ID Producto: ");
		idProductoField = new JTextField(10);
		productoPanel.add(idProductoLbl);
		productoPanel.add(idProductoField);

		idPanel.add(productoPanel);
		idPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		// ID PROVEEDOR
		JPanel proveedorPanel = new JPanel(new FlowLayout());
		JLabel idProveedorLbl = new JLabel("ID Proveedor: ");
		idProveedorField = new JTextField(10);
		proveedorPanel.add(idProveedorLbl);
		proveedorPanel.add(idProveedorField);

		idPanel.add(proveedorPanel);

		centerPanel.add(idPanel);

		JButton annadirBtn = new JButton("Aceptar");
		annadirBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					Context request = new Context(ContextEnum.ANADIRPROVEEDOR, new Pair(
							Integer.parseInt(idProductoField.getText()), Integer.parseInt(idProveedorField.getText())));
					ApplicationController.getInstance().manageRequest(request);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		annadirBtn.setAlignmentX(CENTER_ALIGNMENT);

		centerPanel.add(annadirBtn);

		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static AnadirProveedorPanel getInstance() {
		if (instance == null) {
			instance = new AnadirProveedorPanel();
		}
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
			JOptionPane.showMessageDialog(parent, "Proveedor asociado al producto con exito");
			clear();
		}
	}

	public void clear() {
		idProveedorField.setText("");
		idProductoField.setText("");
	}

	@Override
	public boolean validation() {
		try {
			if (idProductoField.getText().equals("") || idProveedorField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idProductoField.getText()) < 0 || Integer.parseInt(idProveedorField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}