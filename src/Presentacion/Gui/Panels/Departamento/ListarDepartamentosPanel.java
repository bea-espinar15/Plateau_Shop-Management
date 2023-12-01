
package Presentacion.Gui.Panels.Departamento;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ListarDepartamentosPanel extends GeneralPanel {
	
	
	private static final long serialVersionUID = 1L;

	private static ListarDepartamentosPanel instance;	
	private Panel_Main_Departamento parent;		
	private ListarDepartamentosTableModel DepartamentosTableModel;	
	private JTable departamentosTable;	
	private ArrayList<TDepartamento> departamentos;

	private ListarDepartamentosPanel() {
		initGUI();
		departamentos = new ArrayList<>();
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		
		// CABECERA
		JLabel listarDepartamentos = new JLabel("LISTAR DEPARTAMENTOS");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarDepartamentos.setFont(f);
		listarDepartamentos.setHorizontalAlignment(SwingConstants.CENTER);
		listarDepartamentos.setForeground(Color.DARK_GRAY);
		listarDepartamentos.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarDepartamentos, BorderLayout.PAGE_START);

		JPanel botonListar = new JPanel();
		botonListar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTARDEPARTAMENTOS, null);
				ApplicationController.getInstance().manageRequest(request);
			}

		});
		botonListar.add(listar);

		this.add(botonListar, BorderLayout.SOUTH);

		// CENTERPANEL
		this.add(crearTabla(departamentos), BorderLayout.CENTER);

	}

	public static ListarDepartamentosPanel getInstance() {
		if (instance == null)
			instance = new ListarDepartamentosPanel();
		return instance;
	}

	public void setParent(Panel_Main_Departamento parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TDepartamento> aux) {
		DepartamentosTableModel = new ListarDepartamentosTableModel(aux);
		departamentosTable = new JTable(DepartamentosTableModel);
		JScrollPane scrollpane = new JScrollPane(departamentosTable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.DEPARTAMENTO, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			parent.showPanel(this);
			this.departamentos = (ArrayList<TDepartamento>) o;
			DepartamentosTableModel.updateList(departamentos);
		}
	}

	@Override
	public boolean validation() {
		return true;
	}

	public void clear() {
		DepartamentosTableModel.updateList(new ArrayList<TDepartamento>());
	}
	
}