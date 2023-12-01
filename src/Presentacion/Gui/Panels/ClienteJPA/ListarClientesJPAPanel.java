
package Presentacion.Gui.Panels.ClienteJPA;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Negocio.ClienteJPA.TClienteJPA;
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


public class ListarClientesJPAPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;
	
	private static ListarClientesJPAPanel instance;
	private Panel_Main_ClienteJPA parent;
	private JTable clientesJPATable;
	private ArrayList<TClienteJPA> clientesJPA;
	private ListarClientesJPATableModel tableModel;
	
	private ListarClientesJPAPanel(){
		this.clientesJPA = new ArrayList<TClienteJPA>();
		initGUI();
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());

		//CABECERA

		JLabel listarClientesJPA = new JLabel("LISTAR CLIENTES");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		listarClientesJPA.setFont(f);
		listarClientesJPA.setHorizontalAlignment(SwingConstants.CENTER);
		listarClientesJPA.setForeground(Color.DARK_GRAY);
		listarClientesJPA.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(listarClientesJPA, BorderLayout.PAGE_START);

		JPanel botonListar = new JPanel();
		botonListar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Context request = new Context(ContextEnum.LISTARCLIENTESJPA, null);
				ApplicationController.getInstance().manageRequest(request);
				tableModel.updateList(clientesJPA);
			}

		});
		botonListar.add(listar);

		this.add(botonListar, BorderLayout.SOUTH);

		// CENTERPANEL		
		this.add(crearTabla(clientesJPA), BorderLayout.CENTER);
	}

	public static ListarClientesJPAPanel getInstance() {
		if (instance == null)
			instance = new ListarClientesJPAPanel();
		return instance;
	}

	public void setParent(Panel_Main_ClienteJPA parent) {
		this.parent = parent;
	}

	public Component crearTabla(ArrayList<TClienteJPA> aux) {
		tableModel = new ListarClientesJPATableModel(aux);
		clientesJPATable = new JTable(tableModel);
		JScrollPane scrollpane = new JScrollPane(clientesJPATable);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
		return scrollpane;
	}

	@SuppressWarnings("unchecked")
	public void update(Object o) {
		if (hasError(o)) {			
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.CLIENTEJPA,(EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			parent.showPanel(this);
			clear();
		} else {
			this.parent.showPanel(this);
			clientesJPA = (ArrayList<TClienteJPA>) o;
			tableModel.updateList(clientesJPA);
		}
	}

	public void clear() {
		tableModel.updateList(new ArrayList<TClienteJPA>());
	}

	@Override
	public boolean validation() {
		return true;
	}
	
}