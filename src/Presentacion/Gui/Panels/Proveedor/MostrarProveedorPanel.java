
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

import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;

import Negocio.Proveedor.TProveedor;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.Panels.GeneralPanel;

public class MostrarProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static MostrarProveedorPanel instance;
	private Panel_Main_Proveedor parent;
	private JTextField idField, tlfField, nomField, cifField, activoField;

	private MostrarProveedorPanel() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA

		JLabel mostrarProveedor = new JLabel("MOSTRAR PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarProveedor.setFont(f);
		mostrarProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarProveedor.setForeground(Color.DARK_GRAY);
		mostrarProveedor.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarProveedor, BorderLayout.PAGE_START);

		// CENTER

		JPanel centerPanel = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(228, 35, 200, 30);
		JLabel id = new JLabel("ID:");
		this.idField = new JTextField(10);
		idPanel.add(id);
		idPanel.add(idField);

		centerPanel.add(idPanel);
		JButton mostrar = new JButton("Mostrar");
		mostrar.setBounds(438, 35, 100, 30);

		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.MOSTRARPROVEEDOR, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		centerPanel.add(mostrar);

		// ACTIVO
		JPanel activo = new JPanel(new FlowLayout());
		activo.setBounds(44, 100, 250, 30);
		JLabel act = new JLabel("Activo:");
		this.activoField = new JTextField(10);
		activoField.setEditable(false);
		activo.add(act);
		activo.add(activoField);
		centerPanel.add(activo);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(40, 140, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomField = new JTextField(10);
		nomField.setEditable(false);
		nombre.add(nom);
		nombre.add(nomField);
		centerPanel.add(nombre);

		// CIF
		JPanel cifPanel = new JPanel(new FlowLayout());
		cifPanel.setBounds(55, 180, 250, 30);
		JLabel cif = new JLabel("CIF:");
		this.cifField = new JTextField(10);
		cifField.setEditable(false);
		cifPanel.add(cif);
		cifPanel.add(cifField);
		centerPanel.add(cifPanel);

		// TELEFONO
		JPanel telefono = new JPanel(new FlowLayout());
		telefono.setBounds(39, 220, 250, 30);
		JLabel tlf = new JLabel("Telefono:");
		this.tlfField = new JTextField(10);
		tlfField.setEditable(false);
		telefono.add(tlf);
		telefono.add(tlfField);
		centerPanel.add(telefono);

		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static MostrarProveedorPanel getInstance() {
		if (instance == null) {
			instance = new MostrarProveedorPanel();
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
			idField.setText(((TProveedor) o).getID().toString());
			tlfField.setText(((TProveedor) o).getTelefono().toString());
			nomField.setText(((TProveedor) o).getNombre());
			cifField.setText(((TProveedor) o).getCIF());
			Boolean act = ((TProveedor) o).getActivo();
			if (act)
				activoField.setText("SI");
			else
				activoField.setText("NO");
		}
	}

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

	public void clear() {
		idField.setText("");
		activoField.setText("");
		tlfField.setText("");
		nomField.setText("");
		cifField.setText("");
	}

}