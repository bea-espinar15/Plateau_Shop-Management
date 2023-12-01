
package Presentacion.Gui.Panels.Empleado;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ListarEmpleadosPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static ListarEmpleadosPanel instance;	
	private Panel_Main_Empleado parent;	
	private JTable empleadosTable;	
	private ArrayList<TEmpleado> empleados;	
	private ListarEmpleadosTableModel listarEmpleadosTableModel;
	
	private ListarEmpleadosPanel(){
		initGUI();
		empleados = new ArrayList<>();
	}
	
	private void initGUI() {
	
		setLayout(new BorderLayout());

		JLabel listarEmpleados = new JLabel("LISTAR EMPLEADOS");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarEmpleados.setFont(f);
		listarEmpleados.setHorizontalAlignment(SwingConstants.CENTER);
		listarEmpleados.setForeground(Color.DARK_GRAY);
		listarEmpleados.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarEmpleados, BorderLayout.PAGE_START);

		JPanel botonListar = new JPanel();
		botonListar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTAREMPLEADOS, null);
				ApplicationController.getInstance().manageRequest(request);
			}

		});
		botonListar.add(listar);
		this.add(botonListar, BorderLayout.SOUTH);

		// CENTERPANEL		
		this.add(crearTabla(this.empleados), BorderLayout.CENTER);
	}
		
	public static ListarEmpleadosPanel getInstance() {
		if (instance == null)
			instance = new ListarEmpleadosPanel();
		return instance;
	}

	public void setParent(Panel_Main_Empleado parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TEmpleado> aux) {
		listarEmpleadosTableModel = new ListarEmpleadosTableModel(aux);
		this.empleadosTable = new JTable(listarEmpleadosTableModel);
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
			this.empleados = (ArrayList<TEmpleado>) o;
			listarEmpleadosTableModel.updateList(empleados);
		}
		
	}
	
	public void clear() {
		listarEmpleadosTableModel.updateList(new ArrayList<TEmpleado>());
	}

	@Override
	public boolean validation() {
		return true;
	}
	
}