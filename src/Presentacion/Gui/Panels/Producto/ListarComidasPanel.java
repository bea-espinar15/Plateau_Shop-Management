
package Presentacion.Gui.Panels.Producto;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Negocio.Producto.TProductoComida;
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

public class ListarComidasPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ListarComidasPanel instance;
	private Panel_Main_Producto parent;
	private JTable productosTable;
	private ArrayList<TProductoComida> productos;
	private ListarComidasTableModel TableModel;

	private ListarComidasPanel() {
		initGUI();
		productos = new ArrayList<>();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA

		JLabel listarProductos = new JLabel("LISTAR COMIDAS");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarProductos.setFont(f);
		listarProductos.setHorizontalAlignment(SwingConstants.CENTER);
		listarProductos.setForeground(Color.DARK_GRAY);
		listarProductos.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarProductos, BorderLayout.PAGE_START);

		JPanel botonListar = new JPanel();
		botonListar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTARCOMIDAS, null);
				ApplicationController.getInstance().manageRequest(request);
			}

		});
		botonListar.add(listar);

		this.add(botonListar, BorderLayout.SOUTH);

		// CENTERPANEL
		this.add(crearTabla(productos), BorderLayout.CENTER);
	}

	public static ListarComidasPanel getInstance() {
		if (instance == null)
			instance = new ListarComidasPanel();
		return instance;
	}

	public Component crearTabla(ArrayList<TProductoComida> aux) {
		TableModel = new ListarComidasTableModel(aux);
		productosTable = new JTable(TableModel);
		JScrollPane scrollpane = new JScrollPane(productosTable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	public void setParent(Panel_Main_Producto parent) {
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.PRODUCTO,((EventEnum) o));
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			parent.showPanel(this);
			clear();
		} else {
			this.parent.showPanel(this);
			productos = (ArrayList<TProductoComida>) o;
			TableModel.updateList(productos);
		}
	}

	public void clear() {
		TableModel.updateList(new ArrayList<TProductoComida>());
	}

	public boolean validation() {
		return true;
	}
	
}