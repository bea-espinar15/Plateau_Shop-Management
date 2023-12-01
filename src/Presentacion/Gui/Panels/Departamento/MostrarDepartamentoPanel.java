
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


public class MostrarDepartamentoPanel extends GeneralPanel {
	
	private static final long serialVersionUID=1L;
	
	private static MostrarDepartamentoPanel instance;	
	private Panel_Main_Departamento parent;	
	private JTextField idField, actField, nomField, nominaField;
	
	private MostrarDepartamentoPanel() {
		initGUI();
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		
		// CABECERA

		JLabel mostrarDepartamento = new JLabel("MOSTRAR DEPARTAMENTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarDepartamento.setFont(f);
		mostrarDepartamento.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarDepartamento.setForeground(Color.DARK_GRAY);
		mostrarDepartamento.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarDepartamento, BorderLayout.PAGE_START);
		
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
					Context request = new Context(ContextEnum.MOSTRARDEPARTAMENTO, Integer.parseInt(idField.getText()));
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
		activo.setBounds(44, 110, 250, 30);
		JLabel act = new JLabel("Activo:");
		this.actField = new JTextField(10);
		actField.setEditable(false);
		activo.add(act);
		activo.add(actField);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(38, 150, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomField = new JTextField(10);
		nomField.setEditable(false);
		nombre.add(nom);
		nombre.add(nomField);
		
		// NOMINA
		JPanel nominaP = new JPanel(new FlowLayout());
		nominaP.setBounds(38, 190, 250, 30);
		JLabel nomima = new JLabel("Nomina:");
		this.nominaField = new JTextField(10);
		nominaField.setEditable(false);
		nominaP.add(nomima);
		nominaP.add(nominaField);
		
		centerPanel.add(activo);
		centerPanel.add(nombre);
		centerPanel.add(nominaP);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
	}
	
	public static MostrarDepartamentoPanel getInstance() {
		if (instance == null)
			instance = new MostrarDepartamentoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Departamento parent) {
		this.parent = parent;
	}
	
	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.DEPARTAMENTO, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			this.idField.setText(((TDepartamento) o).getID().toString());
			this.nomField.setText(((TDepartamento) o).getNombre());
			this.nominaField.setText(((TDepartamento) o).getNomina().toString());
			boolean act = ((TDepartamento) o).getActivo();
			if (act)
				actField.setText("SI");
			else
				actField.setText("NO");
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
		actField.setText("");
		nomField.setText("");
		nominaField.setText("");
	}
	
}