
package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class MostrarVentaPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static MostrarVentaPanel instance;
	private Panel_Main_Venta parent;
	private JTextField idField, precioTotalField, fechaField, idClienteField, idEmpleadoField, metodoPagoField;
	private JButton ok;

	private MostrarVentaPanel() {
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// _______ CABECERA (PAGE_START) ________
		JLabel mostrarVenta = new JLabel("MOSTRAR VENTA");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarVenta.setFont(f);
		mostrarVenta.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarVenta.setForeground(Color.DARK_GRAY);
		mostrarVenta.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarVenta, BorderLayout.PAGE_START);

		// ______ DATOS _______
		JPanel datos = new JPanel(null);

		// _______ CAMPOS ENTRADA ________
		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(228, 35, 200, 30);
		JLabel id = new JLabel("ID:");
		this.idField = new JTextField(10);
		idPanel.add(id);
		idPanel.add(idField);

		ok = new JButton("Mostrar");
		ok.setBounds(438, 35, 100, 30);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.MOSTRARVENTA, Integer.parseInt(idField.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// ______ CAMPOS SALIDA _______
		JPanel precioTotal = new JPanel(new FlowLayout());
		precioTotal.setBounds(100, 125, 200, 30);
		JLabel precioTotalLabel = new JLabel("Precio total:");
		precioTotalField = new JTextField(10);
		precioTotalField.setEditable(false);
		precioTotal.add(precioTotalLabel);
		precioTotal.add(precioTotalField);

		JPanel fecha = new JPanel(new FlowLayout());
		fecha.setBounds(75, 165, 280, 30);
		JLabel fechaLabel = new JLabel("Fecha:");
		fechaField = new JTextField(10);
		fechaField.setEditable(false);
		fecha.add(fechaLabel);
		fecha.add(fechaField);
		
		JPanel metodoPago = new JPanel(new FlowLayout());
		metodoPago.setBounds(46, 205, 300, 30);
		JLabel mpLabel = new JLabel("Metodo pago:");
		metodoPagoField = new JTextField(10);
		metodoPagoField.setEditable(false);
		metodoPago.add(mpLabel);
		metodoPago.add(metodoPagoField);

		JPanel idCliente = new JPanel(new FlowLayout());
		idCliente.setBounds(55, 245, 300, 30);
		JLabel idClienteLabel = new JLabel("ID Cliente:");
		idClienteField = new JTextField(10);
		idClienteField.setEditable(false);
		idCliente.add(idClienteLabel);
		idCliente.add(idClienteField);

		JPanel idEmpleado = new JPanel(new FlowLayout());
		idEmpleado.setBounds(46, 285, 300, 30);
		JLabel idEmpleadoLabel = new JLabel("ID Empleado:");
		idEmpleadoField = new JTextField(10);
		idEmpleadoField.setEditable(false);
		idEmpleado.add(idEmpleadoLabel);
		idEmpleado.add(idEmpleadoField);

		datos.add(idPanel);
		datos.add(precioTotal);
		datos.add(fecha);
		datos.add(metodoPago);
		datos.add(idCliente);
		datos.add(idEmpleado);
		datos.add(ok);

		this.add(datos, BorderLayout.CENTER);
	}

	public static MostrarVentaPanel getInstance() {
		if (instance == null)
			instance = new MostrarVentaPanel();
		return instance;
	}

	public void setParent(Panel_Main_Venta parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.VENTA, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			idField.setText(((TVenta) o).getID().toString());
			precioTotalField.setText(((TVenta) o).getPrecioTotal().toString());
			fechaField.setText(new SimpleDateFormat("dd-MM-yyyy").format(((TVenta) o).getFecha()));
			idClienteField.setText(((TVenta) o).getIDClienteJPA().toString());
			idEmpleadoField.setText(((TVenta) o).getIDEmpleado().toString());
			metodoPagoField.setText(((TVenta) o).getMetodoPago());
		}
	}

	public void clear() {
		idField.setText("");
		precioTotalField.setText(""); 
		fechaField.setText(""); 
		idClienteField.setText("");
		idEmpleadoField.setText("");
		metodoPagoField.setText("");
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