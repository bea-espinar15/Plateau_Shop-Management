
package Presentacion.Gui.Panels.Proveedor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Negocio.Proveedor.TProveedor;
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

public class ListarProveedoresPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;	
	
	private static ListarProveedoresPanel instance;
	private Panel_Main_Proveedor parent;
	private JTable proveedoresTable;
	private ListarProveedoresTableModel proveedoresTableModel;
	private ArrayList<TProveedor> proveedores;
	
	//CONSTRUCTORA
	private ListarProveedoresPanel() {
		proveedores = new ArrayList<>();
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA
		JLabel listarProveedores = new JLabel("LISTAR PROVEEDORES");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarProveedores.setFont(f);
		listarProveedores.setHorizontalAlignment(SwingConstants.CENTER);
		listarProveedores.setForeground(Color.DARK_GRAY);
		listarProveedores.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarProveedores, BorderLayout.PAGE_START);

		JPanel botonListar = new JPanel();
		botonListar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTARPROVEEDORES, null);
				ApplicationController.getInstance().manageRequest(request);
			}

		});
		botonListar.add(listar);

		this.add(botonListar, BorderLayout.SOUTH);

		// CENTERPANEL
		this.add(crearTabla(proveedores), BorderLayout.CENTER);
	}

	public static ListarProveedoresPanel getInstance() {
		if (instance == null){
			instance = new ListarProveedoresPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Proveedor parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TProveedor> aux) {
		proveedoresTableModel = new ListarProveedoresTableModel(aux);
		proveedoresTable = new JTable(proveedoresTableModel);
		JScrollPane scrollpane = new JScrollPane(proveedoresTable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.PROVEEDOR, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			parent.showPanel(this);
			clear();
		} else {
			parent.showPanel(this);
			proveedores = (ArrayList<TProveedor>) o;
			proveedoresTableModel.updateList(proveedores);
		}
	}

	public void clear() {
		proveedoresTableModel.updateList(new ArrayList<TProveedor>());
	}

	@Override
	public boolean validation() {
		return true;
	}
	
}