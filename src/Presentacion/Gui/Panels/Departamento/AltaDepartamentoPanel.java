
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
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class AltaDepartamentoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static AltaDepartamentoPanel instance;
	private Panel_Main_Departamento parent;
	private JTextField nombreField;

	private AltaDepartamentoPanel() {
		initGUI();
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());

		// _______ CABECERA (PAGE_START) ________
		JLabel altaDepartamento = new JLabel("ALTA DEPARTAMENTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		altaDepartamento.setFont(f);
		altaDepartamento.setHorizontalAlignment(SwingConstants.CENTER);
		altaDepartamento.setForeground(Color.DARK_GRAY);
		altaDepartamento.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(altaDepartamento, BorderLayout.PAGE_START);

		// ______ CENTER _______
		JPanel centerPanel = new JPanel(null);

		// nombre
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(57, 40, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nombreField = new JTextField(10);
		nombre.add(nom);
		nombre.add(nombreField);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.setBounds(155, 100, 100, 30);
		aceptarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					TDepartamento departamento = new TDepartamento();
					departamento.setNombre(nombreField.getText());

					Context request = new Context(ContextEnum.ALTADEPARTAMENTO, departamento);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		centerPanel.add(nombre);
		centerPanel.add(aceptarButton);

		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static AltaDepartamentoPanel getInstance() {
		if (instance == null)
			instance = new AltaDepartamentoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Departamento parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.DEPARTAMENTO, (EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			JOptionPane.showMessageDialog(parent, "El departamento se ha creado con exito. ID: " + ((Integer)o).toString());
			clear();
		}
	}

	public boolean validation() {
		try {
			if (nombreField.getText().equals(""))
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clear() {
		nombreField.setText("");
	}

}