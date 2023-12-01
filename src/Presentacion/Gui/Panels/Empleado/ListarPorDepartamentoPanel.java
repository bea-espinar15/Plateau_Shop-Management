
package Presentacion.Gui.Panels.Empleado;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Negocio.Empleado.TEmpleado;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarPorDepartamentoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ListarPorDepartamentoPanel instance;
	private Panel_Main_Empleado parent;
	private JTable empleadosTable;
	private ArrayList<TEmpleado> empleados;
	private ListarPorDepartamentoTableModel listarPorDepartamentoTableModel;
	private JTextField idField;
	private Integer id;

	private ListarPorDepartamentoPanel() {
		empleados = new ArrayList<TEmpleado>();
		initGUI();
	}

	private void initGUI() {

		setLayout(new BorderLayout());

		// _______ CABECERA (PAGE_START) ________
		JLabel listarPorDept = new JLabel("LISTAR POR DEPARTAMENTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarPorDept.setFont(f);
		listarPorDept.setHorizontalAlignment(SwingConstants.CENTER);
		listarPorDept.setForeground(Color.DARK_GRAY);
		listarPorDept.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarPorDept, BorderLayout.PAGE_START);

		// CENTERPANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		// _______ CAMPOS ENTRADA _______
		JPanel idDept = new JPanel(new FlowLayout());
		idDept.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		JLabel idDeptLabel = new JLabel("ID Departamento:");
		idField = new JTextField(10);
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					id = Integer.parseInt(idField.getText());
					Context request = new Context(ContextEnum.LISTAREMPLEADOSPORDEPARTAMENTO,
							Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);

			}

		});
		idDept.add(idDeptLabel);
		idDept.add(idField);
		idDept.add(listar);
		idDept.setMaximumSize(new Dimension(800, 200));
		idDept.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		centerPanel.add(idDept);
		centerPanel.add(crearTabla(empleados));
		
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static ListarPorDepartamentoPanel getInstance() {
		if (instance == null) {
			instance = new ListarPorDepartamentoPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Empleado parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TEmpleado> aux) {
		listarPorDepartamentoTableModel = new ListarPorDepartamentoTableModel(aux);
		this.empleadosTable = new JTable(listarPorDepartamentoTableModel);
		JScrollPane scrollpane = new JScrollPane(this.empleadosTable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.EMPLEADO, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			parent.showPanel(this);
			clear();
		} else {
			parent.showPanel(this);
			idField.setText(id.toString());
			this.empleados = (ArrayList<TEmpleado>) o;
			listarPorDepartamentoTableModel.updateList(empleados);
		}
	}

	public void clear() {
		idField.setText("");
		listarPorDepartamentoTableModel.updateList(new ArrayList<TEmpleado>());
	}

	@Override
	public boolean validation() {
		try {
			if (idField.getText() == "")
				throw new Exception();
			if (Integer.parseInt(idField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}