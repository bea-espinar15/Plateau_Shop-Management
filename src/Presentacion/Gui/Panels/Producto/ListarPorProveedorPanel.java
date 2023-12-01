
package Presentacion.Gui.Panels.Producto;

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

import Negocio.Producto.TProducto;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarPorProveedorPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ListarPorProveedorPanel instance;
	private Panel_Main_Producto parent;
	private JTable productosTable;
	private ArrayList<TProducto> productos;
	private ListarPorProveedorTableModel TableModel;
	private JTextField idProveedorField;
	private Integer id;

	private ListarPorProveedorPanel() {
		initGUI();
		productos = new ArrayList<>();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA
		JLabel listarProductos = new JLabel("LISTAR POR PROVEEDOR");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarProductos.setFont(f);
		listarProductos.setHorizontalAlignment(SwingConstants.CENTER);
		listarProductos.setForeground(Color.DARK_GRAY);
		listarProductos.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarProductos, BorderLayout.PAGE_START);

		// CENTERPANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		// ID Panel
		JPanel idPanel = new JPanel();
		JLabel idProveedorLbl = new JLabel("ID Proveedor:");
		this.idProveedorField = new JTextField(10);
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					id = Integer.parseInt(idProveedorField.getText());
					Context request = new Context(ContextEnum.LISTARPRODUCTOSPORPROVEEDOR, id);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		idPanel.add(idProveedorLbl);
		idPanel.add(idProveedorField);
		idPanel.add(listar);
		idPanel.setMaximumSize(new Dimension(800, 200));
		idPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
		
		centerPanel.add(idPanel);

		// Table model
		centerPanel.add(crearTabla(productos));

		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static ListarPorProveedorPanel getInstance() {
		if (instance == null)
			instance = new ListarPorProveedorPanel();
		return instance;
	}

	public Component crearTabla(ArrayList<TProducto> aux) {
		TableModel = new ListarPorProveedorTableModel(aux);
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
			parent.showPanel(this);
			idProveedorField.setText(id.toString());
			productos = (ArrayList<TProducto>) o;
			TableModel.updateList(productos);
		}
	}

	public void clear() {
		idProveedorField.setText("");
		TableModel.updateList(new ArrayList<TProducto>());
	}

	public boolean validation() {
		try {
			if (idProveedorField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idProveedorField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}