
package Presentacion.Gui.Panels.Departamento;

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

import Negocio.Departamento.TDepartamento;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class ModificarDepartamentoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ModificarDepartamentoPanel instance;
	private Panel_Main_Departamento parent;
	private JButton ok, mod;
	private JTextField idField, nomField;
	private boolean buscado;
	private static Boolean selected = false;

	private ModificarDepartamentoPanel() {
		buscado = false;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// CABECERA
		JLabel modificarDepartamento = new JLabel("MODIFICAR DEPARTAMENTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		modificarDepartamento.setFont(f);
		modificarDepartamento.setHorizontalAlignment(SwingConstants.CENTER);
		modificarDepartamento.setForeground(Color.DARK_GRAY);
		modificarDepartamento.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(modificarDepartamento, BorderLayout.PAGE_START);

		// ______ DATOS (CENTRE) _______
		JPanel datos = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(100, 35, 160, 30);
		JLabel idLabel = new JLabel("ID:");
		this.idField = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idField);

		// ACEPTAR ID
		ok = new JButton("Buscar");
		ok.setBounds(270, 35, 78, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					ModificarDepartamentoPanel.selected = true;
					Context request = new Context(ContextEnum.MOSTRARDEPARTAMENTO, Integer.parseInt(idField.getText()));
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
		nombre.setBounds(0, 130, 400, 30);
		JLabel nom = new JLabel("Nombre:");
		nomField = new JTextField(10);
		nomField.setEditable(false);
		nombre.add(nom);
		nombre.add(nomField);
		datos.add(nombre);

		mod = new JButton("Modificar");
		mod.setEnabled(false);
		mod.setBounds(183, 195, 100, 30);
		mod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					TDepartamento d = new TDepartamento();
					d.setID(Integer.parseInt(idField.getText()));
					d.setNombre(nomField.getText());
					Context request = new Context(ContextEnum.MODIFICARDEPARTAMENTO, d);
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

	public static ModificarDepartamentoPanel getInstance() {
		if (instance == null)
			instance = new ModificarDepartamentoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Departamento parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.DEPARTAMENTO, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		}
		else {
			if (buscado) {
				JOptionPane.showMessageDialog(parent, "El departamento se ha modificado con exito.");
				clear();
			}
			else {
				nomField.setText(((TDepartamento) o).getNombre());
				idField.setEditable(false);
				nomField.setEditable(true);
				mod.setEnabled(true);
				ok.setEnabled(false);
				buscado = true;
			}
		}
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
			} else {
				if (nomField.getText().equals(""))
					throw new Exception();
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public void clear() {
		idField.setText("");
		nomField.setText("");

		nomField.setEditable(false);
		idField.setEditable(true);
		
		ok.setEnabled(true);
		mod.setEnabled(false);

		buscado = false;
		ModificarDepartamentoPanel.selected = false;
	}
	
	public Boolean isSelected() {
		return ModificarDepartamentoPanel.selected;
	}
	
}