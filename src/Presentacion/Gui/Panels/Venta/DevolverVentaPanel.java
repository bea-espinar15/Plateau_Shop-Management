
package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;


public class DevolverVentaPanel extends GeneralPanel {
	
	
	private static final long serialVersionUID = 1L;
	private static DevolverVentaPanel instance;
	private Panel_Main_Venta parent;
	private JTextField idVentaField, idProductoField;
	private JSpinner udsS;
	private JButton ok;
	
	private DevolverVentaPanel(){
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		// _______ CABECERA (PAGE_START) ________
		JLabel devolverVenta = new JLabel("DEVOLVER VENTA");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		devolverVenta.setFont(f);
		devolverVenta.setHorizontalAlignment(SwingConstants.CENTER);
		devolverVenta.setForeground(Color.DARK_GRAY);
		devolverVenta.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		this.add(devolverVenta, BorderLayout.PAGE_START);
		
		//______ DATOS _______
		JPanel datos = new JPanel(null);
				
		//_______ CAMPOS ________				
		JPanel idVenta = new JPanel(new FlowLayout());
		idVenta.setBounds(100, 45, 200, 30);
		JLabel idVentaLabel = new JLabel("ID Venta:");
		idVentaField = new JTextField(10);
		idVenta.add(idVentaLabel);
		idVenta.add(idVentaField);
		
		JPanel idProducto = new JPanel(new FlowLayout());
		idProducto.setBounds(90, 87, 200, 30);
		JLabel idProductoLabel = new JLabel("ID Producto:");
		idProductoField = new JTextField(10);
		idProducto.add(idProductoLabel);
		idProducto.add(idProductoField);
		
		JPanel uds = new JPanel(new FlowLayout());
		uds.setBounds(20, 130, 280, 50);
		JLabel udsLabel = new JLabel("Unidades:");
		SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
		udsS = new JSpinner(model);
		udsS.setPreferredSize(new Dimension(38, 22));
		uds.add(udsLabel);
		uds.add(udsS);
		
		ok = new JButton("Devolver");
		ok.setBounds(390, 50, 90, 25);
		ok.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					ArrayList<Integer> devolucion = new ArrayList<Integer>();
					devolucion.add(Integer.parseInt(idVentaField.getText()));
					devolucion.add(Integer.parseInt(idProductoField.getText()));
					devolucion.add((Integer)udsS.getValue());
					Context request = new Context(ContextEnum.DEVOLVERVENTA, devolucion);
					ApplicationController.getInstance().manageRequest(request);
				}
				else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		datos.add(idVenta);
		datos.add(idProducto);
		datos.add(uds);
		datos.add(ok);
		
		this.add(datos, BorderLayout.CENTER);
	}

	public static DevolverVentaPanel getInstance() {
		if (instance == null)
			instance = new DevolverVentaPanel();
		return instance;
	}

	public void setParent(Panel_Main_Venta parent) {
		this.parent=parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.VENTA, (EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		}
		else {
			JOptionPane.showMessageDialog(parent, "La venta ha sido devuelta con exito");
			clear();
		}
	}

	public void clear() {
		idVentaField.setText("");
		idProductoField.setText("");
		udsS.setValue(1);
	}

	@Override
	public boolean validation() {
		try {
			if (idVentaField.getText().equals("") || idProductoField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idVentaField.getText()) < 0 || Integer.parseInt(idProductoField.getText()) < 0)
				throw new Exception();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
}