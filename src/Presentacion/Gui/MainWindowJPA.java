
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Presentacion.Gui.Panels.ClienteJPA.Panel_Main_ClienteJPA;
import Presentacion.Gui.Panels.Departamento.Panel_Main_Departamento;
import Presentacion.Gui.Panels.Empleado.Panel_Main_Empleado;
import Presentacion.Gui.Panels.Producto.Panel_Main_Producto;
import Presentacion.Gui.Panels.Proveedor.Panel_Main_Proveedor;
import Presentacion.Gui.Panels.Venta.Panel_Main_Venta;

public class MainWindowJPA extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Color boton1 = new Color(255, 203, 114);
	private static final Color boton2 = new Color(255, 218, 154);
	private static final Color pulsado = new Color(255, 240, 215);

	private JButton gClienteJPA;
	private JButton gDepartamento;
	private JButton gEmpleado;
	private JButton gProducto;
	private JButton gProveedor;
	private JButton gVenta;

	private JPanel contentPanel, mainPanel;
	private Panel_Main_ClienteJPA ClientesPanel;
	private Panel_Main_Departamento DptoPanel;
	private Panel_Main_Empleado empleadoPanel;
	private Panel_Main_Producto productoPanel;
	private Panel_Main_Proveedor proveedorPanel;
	private Panel_Main_Venta ventaPanel;

	private MainWindowJPA() {
		super("Plateau - Gestion de Tienda");
		initGUI();
		this.setLocationRelativeTo(null);
	}

	private void initGUI() {
		this.setMinimumSize(new Dimension(900, 850));
		mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.setPreferredSize(new Dimension(900, 900));
		mainPanel.setMinimumSize(new Dimension(600, 900));
		mainPanel.setMaximumSize(new Dimension(600, 900));
		initContainer();

		//____________ CABECERA ____________
		JPanel header = new JPanel();
		header.setBackground(new Color(41, 51, 72));
		header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

		JPanel logoPanel = new JPanel(new BorderLayout());
		logoPanel.setBackground(new Color(41, 51, 72));
		JLabel logoLabel = new JLabel();
		Image aux = new ImageIcon("images/logo.png").getImage();
		ImageIcon logo = new ImageIcon(aux.getScaledInstance(290, 150, Image.SCALE_SMOOTH));
		logoLabel.setIcon(logo);
		logoLabel.setHorizontalAlignment(JLabel.CENTER);
		logoPanel.add(logoLabel, BorderLayout.CENTER);

		header.add(logoPanel);

		//- - - - - - Botones Panel - - - - - - -

		JPanel buttons = new JPanel();
		buttons.setBackground(Color.black);
		buttons.setLayout(new GridLayout(1, 7));

		// G. CLIENTES
		gClienteJPA = new JButton("Clientes");
		gClienteJPA.setBackground(boton1);
		gClienteJPA.setBounds(100, 450, 70, 40);
		gClienteJPA.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gClienteJPA.setEnabled(false);
				resetColors();
				gClienteJPA.setBackground(pulsado);

				hidePanels();
				contentPanel.add(ClientesPanel, BorderLayout.CENTER);
				ClientesPanel.setVisible(true);
				ClientesPanel.hidePanels();
			}

		});
		
		buttons.add(gClienteJPA);

		// G. DEPARTAMENTOS
		gDepartamento = new JButton("Departamentos");
		gDepartamento.setBackground(boton2);
		gDepartamento.setBounds(100, 450, 70, 40);
		
		gDepartamento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gDepartamento.setBackground(new Color(255, 240, 215));
				gDepartamento.setEnabled(false);
				resetColors();
				gDepartamento.setBackground(pulsado);

				hidePanels();
				contentPanel.add(DptoPanel, BorderLayout.CENTER);
				DptoPanel.setVisible(true);
				DptoPanel.hidePanels();
			}

		});
		
		buttons.add(gDepartamento);

		//G. EMPLEADOS
		gEmpleado = new JButton("Empleados");
		gEmpleado.setBackground(boton1);
		gEmpleado.setBounds(100, 450, 70, 40);
		
		gEmpleado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gEmpleado.setEnabled(false);
				resetColors();
				gEmpleado.setBackground(pulsado);

				hidePanels();
				contentPanel.add(empleadoPanel, BorderLayout.CENTER);
				empleadoPanel.setVisible(true);
				empleadoPanel.hidePanels();
			}

		});
		
		buttons.add(gEmpleado);
		
		//G. PRODUCTOS

		gProducto = new JButton("Productos");
		gProducto.setBackground(boton2);
		gProducto.setBounds(100, 450, 70, 40);
		gProducto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gProducto.setEnabled(false);
				resetColors();
				gProducto.setBackground(pulsado);

				hidePanels();
				contentPanel.add(productoPanel, BorderLayout.CENTER);
				productoPanel.setVisible(true);
				productoPanel.hidePanels();
			}

		});
		buttons.add(gProducto);

		gProveedor = new JButton("Proveedores");
		gProveedor.setBackground(boton1);
		gProveedor.setBounds(100, 450, 70, 40);
		
		gProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gProveedor.setEnabled(false);
				resetColors();
				gProveedor.setBackground(pulsado);

				hidePanels();
				contentPanel.add(proveedorPanel, BorderLayout.CENTER);
				proveedorPanel.setVisible(true);
				proveedorPanel.hidePanels();
			}

		});
		
		buttons.add(gProveedor);
		
		gVenta = new JButton("Ventas");
		gVenta.setBackground(boton2);
		gVenta.setBounds(100, 450, 70, 40);
		
		gVenta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				activateButtons();
				gVenta.setEnabled(false);
				resetColors();
				gVenta.setBackground(pulsado);

				hidePanels();
				contentPanel.add(ventaPanel, BorderLayout.CENTER);
				ventaPanel.setVisible(true);
				ventaPanel.hidePanels();
			}

		});
		
		buttons.add(gVenta);

		header.add(buttons);
		mainPanel.add(header, BorderLayout.NORTH);
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private void initContainer() {
		contentPanel = new JPanel(new BorderLayout());
		ClientesPanel = new Panel_Main_ClienteJPA();
		DptoPanel = new Panel_Main_Departamento();
		empleadoPanel = new Panel_Main_Empleado();
		productoPanel = new Panel_Main_Producto();
		proveedorPanel = new  Panel_Main_Proveedor();
		ventaPanel = new Panel_Main_Venta();

	}

	private void hidePanels() {
		ClientesPanel.setVisible(false);
		DptoPanel.setVisible(false);
		empleadoPanel.setVisible(false);
		productoPanel.setVisible(false);
		proveedorPanel.setVisible(false);
		ventaPanel.setVisible(false);
	}

	private void activateButtons() {
		gClienteJPA.setEnabled(true);
		gDepartamento.setEnabled(true);
		gEmpleado.setEnabled(true);
		gProducto.setEnabled(true);
		gProveedor.setEnabled(true);
		gVenta.setEnabled(true);
	}

	private void resetColors() {
		gClienteJPA.setBackground(boton1);
		gDepartamento.setBackground(boton2);
		gEmpleado.setBackground(boton1);
		gProducto.setBackground(boton2);
		gProveedor.setBackground(boton1);
		gVenta.setBackground(boton2);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindowJPA();
			}
		});
	}
}