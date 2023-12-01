
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarVentasPorClientePanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;
	
	private static ListarVentasPorClientePanel instance;
	private Panel_Main_Venta parent;
	private JTable ventasTable;
	private JTextField idClienteField;
	private ArrayList<TVenta> ventas;
	private Integer id;
	private ListarVentasPorClienteTableModel listarVentasPorClienteTableModel;

	private ListarVentasPorClientePanel() {
		ventas = new ArrayList<TVenta>();
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JLabel listarVentas = new JLabel("LISTAR POR CLIENTE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarVentas.setFont(f);
		listarVentas.setHorizontalAlignment(SwingConstants.CENTER);
		listarVentas.setForeground(Color.DARK_GRAY);
		listarVentas.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarVentas, BorderLayout.PAGE_START);

		// CENTERPANEL
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));

		JPanel idPanel = new JPanel();
		idPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JLabel idClienteLbl = new JLabel("ID Cliente");
		this.idClienteField = new JTextField(10);
		JButton listar = new JButton("LIstar");

		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					id = Integer.parseInt(idClienteField.getText());
					Context request = new Context(ContextEnum.LISTARVENTASPORCLIENTE, id);
					ApplicationController.getInstance().manageRequest(request);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
			}

		});
		idPanel.add(idClienteLbl);
		idPanel.add(idClienteField);
		idPanel.add(listar);
		idPanel.setMaximumSize(new Dimension(800, 200));
		idPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

		centerPanel.add(idPanel);

		// Table model
		centerPanel.add(crearTabla(ventas));

		// ______ DATOS (CENTER) _______
		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static ListarVentasPorClientePanel getInstance() {
		if (instance == null)
			instance = new ListarVentasPorClientePanel();
		return instance;
	}

	public void setParent(Panel_Main_Venta parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TVenta> aux) {
		listarVentasPorClienteTableModel = new ListarVentasPorClienteTableModel(aux);
		this.ventasTable = new JTable(listarVentasPorClienteTableModel);
		JScrollPane scroll = new JScrollPane(this.ventasTable);
		scroll.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scroll;
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
			idClienteField.setText(id.toString());
			this.ventas = (ArrayList<TVenta>) o;
			listarVentasPorClienteTableModel.updateList(ventas);
		}
	}

	public void clear() {
		this.idClienteField.setText("");
		listarVentasPorClienteTableModel.updateList(new ArrayList<TVenta>());
	}

	@Override
	public boolean validation() {
		try {
			if (idClienteField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idClienteField.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}