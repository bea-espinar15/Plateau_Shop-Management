
package Presentacion.Gui.Panels.ClienteJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class ModificarClienteJPAPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ModificarClienteJPAPanel instance;
	private Panel_Main_ClienteJPA parent;
	private JTextField idField, nomF, dniF;
	private JButton modificar, ok;
	private Boolean buscado;
	private static Boolean selected = false;

	private ModificarClienteJPAPanel() {
		buscado = false;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// __________ CABECERA _________
		JLabel titleLbl = new JLabel("MODIFICAR CLIENTE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		titleLbl.setFont(f);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setForeground(Color.DARK_GRAY);
		titleLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(titleLbl, BorderLayout.PAGE_START);

		// ___________ CENTER ______________
		JPanel centerPanel = new JPanel(null);

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
					ModificarClienteJPAPanel.selected = true;
					Context request = new Context(ContextEnum.MOSTRARCLIENTEJPA, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		centerPanel.add(idPanel);
		centerPanel.add(ok);

		// ___________ CENTER - OTHER FIELDS ______________

		// NOMBRE
		JPanel nombrePanel = new JPanel(new FlowLayout());
		nombrePanel.setBounds(48, 110, 250, 30);
		JLabel nomLbl = new JLabel("Nombre: ");
		this.nomF = new JTextField(10);
		nomF.setEditable(false);
		nombrePanel.add(nomLbl);
		nombrePanel.add(nomF);

		centerPanel.add(nombrePanel);

		// DNI
		JPanel dniPanel = new JPanel(new FlowLayout());
		dniPanel.setBounds(63, 155, 250, 30);
		JLabel dniLbl = new JLabel("DNI: ");
		this.dniF = new JTextField(10);
		dniF.setEditable(false);
		dniPanel.add(dniLbl);
		dniPanel.add(dniF);

		centerPanel.add(dniPanel);

		modificar = new JButton("Modificar");
		modificar.setBounds(160, 235, 100, 30);
		modificar.setEnabled(false);
		modificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request;
				if (validation()) {
					TClienteJPA clienteJPA = new TClienteJPA();
					clienteJPA.setID(Integer.parseInt(idField.getText()));
					clienteJPA.setNombre(nomF.getText());
					clienteJPA.setDNI(dniF.getText().toUpperCase());
					request = new Context(ContextEnum.MODIFICARCLIENTEJPA, clienteJPA);
					ApplicationController.getInstance().manageRequest(request);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
			}

		});

		centerPanel.add(modificar);

		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static ModificarClienteJPAPanel getInstance() {
		if (instance == null) {
			instance = new ModificarClienteJPAPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_ClienteJPA parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.CLIENTEJPA, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			if (buscado) {
				JOptionPane.showMessageDialog(parent, "El cliente se ha modificado con exito.");
				clear();
			} else {
				nomF.setText(((TClienteJPA) o).getNombre());
				dniF.setText(((TClienteJPA) o).getDNI());
				idField.setEditable(false);
				nomF.setEditable(true);
				dniF.setEditable(true);
				modificar.setEnabled(true);
				ok.setEnabled(false);
				buscado = true;
			}
		}
	}

	public void clear() {
		idField.setText("");
		nomF.setText("");
		dniF.setText("");

		nomF.setEditable(false);
		dniF.setEditable(false);
		idField.setEditable(true);
		ok.setEnabled(true);
		modificar.setEnabled(false);
		buscado = false;
		
		ModificarClienteJPAPanel.selected = false;
	}

	@Override
	public boolean validation() {
		try {
			if (!buscado) {
				if (idField.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idField.getText()) < 0) {
					throw new Exception();
				}
				return true;
			} else {
				Pattern pat = Pattern.compile("[0-9]{8}[A-Za-z]");
				Matcher mat;
				if (nomF.getText().equals(""))
					throw new Exception();
				mat = pat.matcher(dniF.getText());
				if (dniF.getText().equals("") || !mat.matches())
					throw new Exception();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isSelected() {
		return ModificarClienteJPAPanel.selected;
	}
	
}