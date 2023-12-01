
package Presentacion.Gui.Panels.Departamento;

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

public class BajaDepartamentoPanel extends GeneralPanel {

	// __________ ATRIBUTOS __________
	private static final long serialVersionUID = 1L;

	private static BajaDepartamentoPanel instance;
	private Panel_Main_Departamento parent;
	private JTextField idField;

	// __________ CONSTRUCTOR __________
	private BajaDepartamentoPanel() {
		initGUI();
	}

	// __________ METODOS __________
	private void initGUI() {
		
		this.setLayout(new BorderLayout());

		// __________ CABECERA _________
		JLabel bajaDepartamento = new JLabel("BAJA DEPARTAMENTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		bajaDepartamento.setFont(f);
		bajaDepartamento.setHorizontalAlignment(SwingConstants.CENTER);
		bajaDepartamento.setForeground(Color.DARK_GRAY);
		bajaDepartamento.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(bajaDepartamento, BorderLayout.PAGE_START);

		// ______ DATOS (CENTER) _______
		JPanel centerPanel = new JPanel();

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
					Context request = new Context(ContextEnum.BAJADEPARTAMENTO, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});

		centerPanel.add(idPanel);

		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static BajaDepartamentoPanel getInstance() {
		if (instance == null)
			instance = new BajaDepartamentoPanel();
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
			JOptionPane.showMessageDialog(parent, "El departamento se ha dado de baja correctamente");
			clear();
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
	}
	
}