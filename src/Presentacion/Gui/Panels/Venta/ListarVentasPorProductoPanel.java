package Presentacion.Gui.Panels.Venta;

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

import Negocio.Venta.TVenta;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarVentasPorProductoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ListarVentasPorProductoPanel instance;
	private Panel_Main_Venta parent;
	private JTable ventasTable;
	private JTextField idField;
	private ArrayList<TVenta> ventas;
	private Integer id;
	private ListarVentasPorProductoTableModel listarVentasPorProductoTableModel;

	private ListarVentasPorProductoPanel() {
		ventas = new ArrayList<>();
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// _______ CABECERA (PAGE_START) ________
		JLabel listarPorProducto = new JLabel("LISTAR POR PRODUCTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarPorProducto.setFont(f);
		listarPorProducto.setHorizontalAlignment(SwingConstants.CENTER);
		listarPorProducto.setForeground(Color.DARK_GRAY);
		listarPorProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarPorProducto, BorderLayout.PAGE_START);

		// CENTERPANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		// _______ CAMPOS ENTRADA _______
		JPanel idProducto = new JPanel(new FlowLayout());
		idProducto.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JLabel idProductoLabel = new JLabel("ID Producto:");
		idField = new JTextField(10);
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					id = Integer.parseInt(idField.getText());
					Context request = new Context(ContextEnum.LISTARVENTASPORPRODUCTO, id);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		idProducto.add(idProductoLabel);
		idProducto.add(idField);
		idProducto.add(listar);
		idProducto.setMaximumSize(new Dimension(800, 200));
		idProducto.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		centerPanel.add(idProducto);

		// Table model
		centerPanel.add(crearTabla(ventas));

		// ______ DATOS (CENTER) _______
		this.add(centerPanel, BorderLayout.CENTER);
		
	}

	public static ListarVentasPorProductoPanel getInstance() {
		if (instance == null)
			instance = new ListarVentasPorProductoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Venta parent) {
		this.parent = parent;
	}

	public Component crearTabla(List<TVenta> aux) {
		listarVentasPorProductoTableModel = new ListarVentasPorProductoTableModel(aux);
		ventasTable = new JTable(listarVentasPorProductoTableModel);
		JScrollPane scrollpane = new JScrollPane(ventasTable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.VENTA, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			parent.showPanel(this);
			clear();
		} else {
			parent.showPanel(this);
			idField.setText(id.toString());
			ventas = (ArrayList<TVenta>) o;
			listarVentasPorProductoTableModel.updateList(ventas);
		}
	}

	public void clear() {
		idField.setText("");
		listarVentasPorProductoTableModel.updateList(new ArrayList<TVenta>());
	}

	@Override
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
	
}