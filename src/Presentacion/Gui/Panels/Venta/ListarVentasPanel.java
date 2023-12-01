
package Presentacion.Gui.Panels.Venta;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ListarVentasPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	private static ListarVentasPanel instance;
	private Panel_Main_Venta parent;
	private JTable ventasTable;
	private ArrayList<TVenta> ventas;
	private ListarVentasTableModel listarVentasTableModel;
	
	private ListarVentasPanel(){
		initGUI();
		ventas = new ArrayList<>();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		
		JLabel listarVentas = new JLabel("LISTAR VENTAS");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarVentas.setFont(f);
		listarVentas.setHorizontalAlignment(SwingConstants.CENTER);
		listarVentas.setForeground(Color.DARK_GRAY);
		listarVentas.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		this.add(listarVentas, BorderLayout.PAGE_START);
		
		JPanel listarBtnPanel = new JPanel();
		listarBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTARVENTAS, null);
				ApplicationController.getInstance().manageRequest(request);
			}

		});
		
		listarBtnPanel.add(listar);
		this.add(listarBtnPanel, BorderLayout.SOUTH);
		
		this.add(crearTabla(this.ventas), BorderLayout.CENTER);
	}
	
	public static ListarVentasPanel getInstance() {
		if (instance == null) {
			instance = new ListarVentasPanel();
		}
		return instance;
	}

	public void setParent(Panel_Main_Venta parent) {
		this.parent = parent;
	}
	
	public Component crearTabla(ArrayList<TVenta> aux) {
		listarVentasTableModel = new ListarVentasTableModel(aux);
		this.ventasTable = new JTable(listarVentasTableModel);
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
			clear();
		} else {
			parent.showPanel(this);
			this.ventas = (ArrayList<TVenta>) o;
			listarVentasTableModel.updateList(ventas);
		}
	}

	public void clear() {
		listarVentasTableModel.updateList(new ArrayList<TVenta>());
	}

	@Override
	public boolean validation() {
		return true;
	}
	
}