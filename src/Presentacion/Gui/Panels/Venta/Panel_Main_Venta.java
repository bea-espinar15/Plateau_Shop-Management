
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Presentacion.Gui.Panels.GeneralMainPanel;
import Presentacion.Gui.Panels.GeneralPanel;


public class Panel_Main_Venta extends GeneralMainPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton devolverVenta;	
	private JButton mostrarVenta;
	private JButton mostrarVentaEnDetalle;
	private JButton listarVentas;
	private JButton listarVentasPorProducto;
	private JButton listarVentasPorCliente;
	private JButton listarVentasPorEmpleado;
	private JButton crearVenta;
	private CrearVentaPanel crearVentaPanel;
	private DevolverVentaPanel devolverVentaPanel;	
	private MostrarVentaPanel mostrarVentaPanel;	
	private MostrarVentaEnDetallePanel mostrarVentaEnDetallePanel;	
	private ListarVentasPanel listarVentasPanel;	
	private ListarVentasPorProductoPanel listarVentasPorProductoPanel;	
	private ListarVentasPorClientePanel listarVentasPorClientePanel;	
	private ListarVentasPorEmpleadoPanel listarVentasPorEmpleadoPanel;
	
	private JPanel contentPanel;

	public Panel_Main_Venta() {
		initGUI();
	}
	private void initGUI() {
		setLayout(new BorderLayout());
		initPanels();
		
		JPanel buttons =new JPanel(new GridLayout( 8,1));
		
		crearVenta=new JButton("Crear");
		crearVenta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(crearVentaPanel);
				crearVentaPanel.setVisible(true);
			}
		});
		buttons.add(crearVenta);
		
		devolverVenta=new JButton("Devolver");
		devolverVenta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(devolverVentaPanel);
				devolverVentaPanel.setVisible(true);
			}
		});
		buttons.add(devolverVenta);
		
		mostrarVenta=new JButton("Mostrar");
		mostrarVenta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(mostrarVentaPanel);
				mostrarVentaPanel.setVisible(true);
			}
		});
		buttons.add(mostrarVenta);
		
		mostrarVentaEnDetalle=new JButton("Mostrar en detalle");
		mostrarVentaEnDetalle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(mostrarVentaEnDetallePanel);
				mostrarVentaEnDetallePanel.setVisible(true);
			}
		});
		buttons.add(mostrarVentaEnDetalle);
		
		listarVentas=new JButton("Listar");
		listarVentas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(listarVentasPanel);
				listarVentasPanel.setVisible(true);
			}
		});
		buttons.add(listarVentas);
		
		listarVentasPorProducto=new JButton("Listar por producto");
		listarVentasPorProducto.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(listarVentasPorProductoPanel);
				listarVentasPorProductoPanel.setVisible(true);
			}
		});
		buttons.add(listarVentasPorProducto);
		
		listarVentasPorCliente=new JButton("Listar por cliente");
		listarVentasPorCliente.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(listarVentasPorClientePanel);
				listarVentasPorClientePanel.setVisible(true);
			}
		});
		buttons.add(listarVentasPorCliente);
		
		listarVentasPorEmpleado=new JButton("Listar por empleado");
		listarVentasPorEmpleado.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hidePanels();
				contentPanel.add(listarVentasPorEmpleadoPanel);
				listarVentasPorEmpleadoPanel.setVisible(true);
			}
		});
		buttons.add(listarVentasPorEmpleado);		
		
		this.add(buttons,BorderLayout.WEST);
		this.add(contentPanel,BorderLayout.CENTER);

	}
	
	public void initPanels() {
		contentPanel=new JPanel(new BorderLayout());		
		crearVentaPanel=CrearVentaPanel.getInstance();
		crearVentaPanel.setParent(this);
		devolverVentaPanel=DevolverVentaPanel.getInstance();
		devolverVentaPanel.setParent(this);
		mostrarVentaPanel=MostrarVentaPanel.getInstance();
		mostrarVentaPanel.setParent(this);
		mostrarVentaEnDetallePanel=MostrarVentaEnDetallePanel.getInstance();
		mostrarVentaEnDetallePanel.setParent(this);
		listarVentasPanel=ListarVentasPanel.getInstance();
		listarVentasPanel.setParent(this);
		listarVentasPorProductoPanel=ListarVentasPorProductoPanel.getInstance();
		listarVentasPorProductoPanel.setParent(this);
		listarVentasPorClientePanel=ListarVentasPorClientePanel.getInstance();
		listarVentasPorClientePanel.setParent(this);
		listarVentasPorEmpleadoPanel=ListarVentasPorEmpleadoPanel.getInstance();
		listarVentasPorEmpleadoPanel.setParent(this);	
	}
	
	public void hidePanels() {
		crearVentaPanel.setVisible(false);
		crearVentaPanel.clear();
		devolverVentaPanel.setVisible(false);
		devolverVentaPanel.clear();
		mostrarVentaPanel.setVisible(false);
		mostrarVentaPanel.clear();
		mostrarVentaEnDetallePanel.setVisible(false);
		mostrarVentaEnDetallePanel.clear();
		listarVentasPanel.setVisible(false);
		listarVentasPanel.clear();
		listarVentasPorProductoPanel.setVisible(false);
		listarVentasPorProductoPanel.clear();
		listarVentasPorClientePanel.setVisible(false);
		listarVentasPorClientePanel.clear();
		listarVentasPorEmpleadoPanel.setVisible(false);
		listarVentasPorEmpleadoPanel.clear();	
	}

	public void showPanel(GeneralPanel panel) {
		hidePanels();
		contentPanel.add(panel);
		panel.setVisible(true);
	}
}