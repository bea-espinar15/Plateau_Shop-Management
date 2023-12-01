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

public class ModificarProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ModificarProveedorPanel instance;
	private Panel_Main_Proveedor parent;
	private JButton ok, mod;
	private Boolean buscado;
	private JTextField idField, tlfField, nomField, cifField;
	private static Boolean selected = false;

	private ModificarProveedorPanel() {
		buscado = false;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// CABECERA
		JLabel modificarProducto = new JLabel("MODIFICAR PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		modificarProducto.setFont(f);
		modificarProducto.setHorizontalAlignment(SwingConstants.CENTER);
		modificarProducto.setForeground(Color.DARK_GRAY);
		modificarProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(modificarProducto, BorderLayout.PAGE_START);

		// ______ DATOS (CENTRE) _______
		JPanel datos = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(100, 35, 170, 30);
		JLabel idLabel = new JLabel("ID:");
		this.idField = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idField);

		// ACEPTAR ID
		ok = new JButton("Buscar");
		ok.setBounds(280, 35, 80, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					ModificarProveedorPanel.selected = true;
					Context request = new Context(ContextEnum.MOSTRARPROVEEDOR, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		datos.add(idPanel);
		datos.add(ok);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(40, 100, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomField = new JTextField(10);
		nomField.setEditable(false);
		nombre.add(nom);
		nombre.add(nomField);
		datos.add(nombre);

		// CIF
		JPanel cifPanel = new JPanel(new FlowLayout());
		cifPanel.setBounds(55, 140, 250, 30);
		JLabel cif = new JLabel("CIF:");
		this.cifField = new JTextField(10);
		cifField.setEditable(false);
		cifPanel.add(cif);
		cifPanel.add(cifField);
		datos.add(cifPanel);

		// TELEFONO
		JPanel telefono = new JPanel(new FlowLayout());
		telefono.setBounds(39, 180, 250, 30);
		JLabel tlf = new JLabel("Telefono:");
		this.tlfField = new JTextField(10);
		tlfField.setEditable(false);
		telefono.add(tlf);
		telefono.add(tlfField);
		datos.add(telefono);

		mod = new JButton("Modificar");
		mod.setEnabled(false);
		mod.setBounds(150, 245, 100, 30);
		mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					TProveedor p = new TProveedor();
					p.setID(Integer.parseInt(idField.getText()));
					p.setTelefono(Integer.parseInt(tlfField.getText()));
					p.setNombre(nomField.getText());
					p.setCIF(cifField.getText());
					Context request = new Context(ContextEnum.MODIFICARPROVEEDOR, p);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introduccidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		datos.add(mod);

		this.add(datos, BorderLayout.CENTER);
	}

	public static ModificarProveedorPanel getInstance() {
		if (instance == null) {
			instance = new ModificarProveedorPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Proveedor parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.PROVEEDOR, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			if (buscado) {
				JOptionPane.showMessageDialog(parent, "El proveedor se ha modificado con exito.");
				clear();
			} 
			else {
				tlfField.setText(((TProveedor) o).getTelefono().toString());
				nomField.setText(((TProveedor) o).getNombre());
				cifField.setText(((TProveedor) o).getCIF());

				idField.setEditable(false);
				tlfField.setEditable(true);
				nomField.setEditable(true);
				cifField.setEditable(true);
				mod.setEnabled(true);
				ok.setEnabled(false);
				
				buscado = true;
			}
		}
	}

	public void clear() {
		idField.setText("");
		tlfField.setText("");
		nomField.setText("");
		cifField.setText("");
		
		idField.setEditable(true);
		tlfField.setEditable(false);
		nomField.setEditable(false);
		cifField.setEditable(false);
		mod.setEnabled(false);
		ok.setEnabled(true);
		
		buscado = false;
		ModificarProveedorPanel.selected = false;
	}

	public boolean validation() {
		try {
			if (!buscado) {
				if (idField.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idField.getText()) < 0) {
					throw new Exception();
				}
				return true;
			} 
			else {
				if (nomField.getText().equals("") || tlfField.getText().equals("") || cifField.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(tlfField.getText()) < 100000000 || Integer.parseInt(tlfField.getText()) > 999999999)
					throw new Exception();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isSelected() {
		return ModificarProveedorPanel.selected;
	}
	
}