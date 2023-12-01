
package Presentacion.Gui.Panels.Proveedor;

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

import Negocio.Proveedor.TProveedor;
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

public class ListarProveedoresPorProductoPanel extends GeneralPanel {
	
	
	private static final long serialVersionUID = 1L;
	
	private static ListarProveedoresPorProductoPanel instance;	
	private Panel_Main_Proveedor parent;	
	private JTable proveedoresTable;	
	private ArrayList<TProveedor> proveedores;	
	private ListarProveedoresPorProductoTableModel TableModel;	
	private JTextField idProductoField;
	private Integer id;
	
	private ListarProveedoresPorProductoPanel() {
		initGUI();
		proveedores = new ArrayList<>();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA
		JLabel listarProductos = new JLabel("LISTAR POR PRODUCTO");
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
		JLabel idProductoLbl = new JLabel("ID Producto:");
		this.idProductoField = new JTextField(10);
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					id = Integer.parseInt(idProductoField.getText());
					Context request = new Context(ContextEnum.LISTARPROVEEDORESPORPRODUCTO, id);
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		idPanel.add(idProductoLbl);
		idPanel.add(idProductoField);
		idPanel.add(listar);
		idPanel.setMaximumSize(new Dimension(800, 200));
		idPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		centerPanel.add(idPanel);

		// Table model
		centerPanel.add(crearTabla(proveedores));

		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static ListarProveedoresPorProductoPanel getInstance() {
		if (instance == null) {
			instance = new ListarProveedoresPorProductoPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Proveedor parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TProveedor> aux) {
		TableModel = new ListarProveedoresPorProductoTableModel(aux);
		proveedoresTable = new JTable(TableModel);
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
			idProductoField.setText(id.toString());
			proveedores = (ArrayList<TProveedor>) o;
			TableModel.updateList(proveedores);
		}
	}

	public boolean validation() {
		try {
			if (idProductoField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idProductoField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clear() {
		idProductoField.setText("");
		TableModel.updateList(new ArrayList<TProveedor>());
	}
	
}