
package Presentacion.Gui.Panels.Proveedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Negocio.Proveedor.TProveedor;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class AltaProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static AltaProveedorPanel instance;
	private Panel_Main_Proveedor parent;
	private JButton aceptarButton;
	private JTextField nombreField;
	private JTextField cifField;
	private JTextField tlfField;

	private AltaProveedorPanel() {
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// ____ CABECERA _____
		JLabel altaProveedor = new JLabel("ALTA PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		altaProveedor.setFont(f);
		altaProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		altaProveedor.setForeground(Color.DARK_GRAY);
		altaProveedor.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		this.add(altaProveedor, BorderLayout.PAGE_START);

		// DATOS (CENTRO)
		JPanel datos = new JPanel(null);

		// nombre
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(80, 15, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nombreField = new JTextField(10);
		nombre.add(nom);
		nombre.add(nombreField);

		// cif
		JPanel cif = new JPanel(new FlowLayout());
		cif.setBounds(92, 60, 250, 30);
		JLabel cifLbl = new JLabel("CIF: ");
		this.cifField = new JTextField(10);
		cif.add(cifLbl);
		cif.add(cifField);

		// telefono
		JPanel tlfPanel = new JPanel(new FlowLayout());
		tlfPanel.setBounds(77, 100, 250, 30);
		JLabel tlfLbl = new JLabel("Telefono: ");
		this.tlfField = new JTextField(10);
		tlfPanel.add(tlfLbl);
		tlfPanel.add(tlfField);

		aceptarButton = new JButton("Aceptar");
		aceptarButton.setBounds(190, 160, 100, 30);
		aceptarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					TProveedor proveedor = new TProveedor();
					proveedor.setTelefono(Integer.parseInt(tlfField.getText()));
					proveedor.setNombre(nombreField.getText());
					proveedor.setCIF(cifField.getText());
					Context request = new Context(ContextEnum.ALTAPROVEEDOR, proveedor);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		datos.add(nombre);
		datos.add(cif);
		datos.add(tlfPanel);
		datos.add(aceptarButton);

		this.add(datos, BorderLayout.CENTER);
	}

	public static AltaProveedorPanel getInstance() {
		if (instance == null) {
			instance = new AltaProveedorPanel();
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
			JOptionPane.showMessageDialog(parent, "El proveedor se ha creado con exito. ID: " + ((Integer)o).toString());
			clear();
		}
	}

	public void clear() {
		nombreField.setText("");
		tlfField.setText("");
		cifField.setText("");
	}

	public boolean validation() {
		try {
			if (nombreField.getText().equals("") || tlfField.getText().equals("") || cifField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(tlfField.getText()) < 100000000 || Integer.parseInt(tlfField.getText()) > 999999999)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}