
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Presentacion.Gui.Panels.GeneralPanel;


public class AbrirVentaPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JButton ok;
	private static AbrirVentaPanel instance;	
	private CrearVentaPanel parent;
	private JTextField idClienteField;
	private JTextField idEmpleadoField;
	private JTextField metodoDePagoField;

	private AbrirVentaPanel(){
		initGUI();
	}
	
	private void initGUI() {
		
		this.setLayout(new BorderLayout());
		
		JLabel abrirVenta = new JLabel("ABRIR VENTA");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		abrirVenta.setFont(f);
		abrirVenta.setHorizontalAlignment(SwingConstants.CENTER);
		abrirVenta.setForeground(Color.DARK_GRAY);
		abrirVenta.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(abrirVenta, BorderLayout.PAGE_START);

		// CENTER
		JPanel datosC = new JPanel(new GridLayout(1,2));

		// ENTRADA
		JPanel entrada = new JPanel(null);
		
		// ID CLIENTE
		JPanel clientePanel = new JPanel(new FlowLayout());
		clientePanel.setBounds(28, 40, 400, 30);
		JLabel cliente = new JLabel("ID Cliente:");
		this.idClienteField = new JTextField(10);
		clientePanel.add(cliente);
		clientePanel.add(idClienteField);
			
		// ID EMPLEADO
		JPanel empleadoPanel = new JPanel(new FlowLayout());
		empleadoPanel.setBounds(18, 80, 400, 30);
		JLabel empleado = new JLabel("ID Empleado:");
		this.idEmpleadoField = new JTextField(10);
		empleadoPanel.add(empleado);
		empleadoPanel.add(idEmpleadoField);
		
		// METODO PAGO
		JPanel pagoPanel = new JPanel(new FlowLayout());
		pagoPanel.setBounds(8, 120, 400, 30);
		JLabel pago = new JLabel("Metodo de pago:");
		this.metodoDePagoField = new JTextField(10);
		pagoPanel.add(pago);
		pagoPanel.add(metodoDePagoField);
		
		entrada.add(clientePanel);
		entrada.add(empleadoPanel);
		entrada.add(pagoPanel);
		datosC.add(entrada);
		
		// BOTON
		JPanel aceptarPanel = new JPanel(null);
	
		ok = new JButton("Aceptar");
		ok.setBounds(50, 80, 100, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (validation()) {
						TVenta venta = new TVenta();
						venta.setIDClienteJPA(Integer.parseInt(idClienteField.getText()));
						venta.setIDEmpleado(Integer.parseInt(idEmpleadoField.getText()));
						venta.setMetodoPago(metodoDePagoField.getText());
						
						HashMap<Integer, TLineaVenta> carrito = new HashMap<Integer, TLineaVenta>();
						parent.showCerrarPanel(venta, carrito);					
					
					} else {
						JOptionPane.showMessageDialog(parent,  "Los datos introducidos son sintacticamente erroneos", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
			}

		});
		aceptarPanel.add(ok);
		datosC.add(aceptarPanel);
		
		this.add(datosC, BorderLayout.CENTER);
		
	}

	public static AbrirVentaPanel getInstance() {
		if (instance == null)
			instance = new AbrirVentaPanel();
		return instance;
	}

	public void setParent(CrearVentaPanel parent) {
		this.parent=parent;
	}

	public void update(Object o) {}

	public void clear() {
		idClienteField.setText("");
		idEmpleadoField.setText("");
		metodoDePagoField.setText("");
	}

	@Override
	public boolean validation() {
		try {
			if (idClienteField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idClienteField.getText()) < 0)
				throw new Exception();
			if (idEmpleadoField.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idEmpleadoField.getText()) < 0)
				throw new Exception();
			if (metodoDePagoField.getText().equals(""))
				throw new Exception();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
}