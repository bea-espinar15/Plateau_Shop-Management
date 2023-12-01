
package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Negocio.Venta.TVentaEnDetalle;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class MostrarVentaEnDetallePanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static MostrarVentaEnDetallePanel instance;
	private Panel_Main_Venta parent;
	private JTextField idF, precioF, fechaF, idClienteF, dniClienteF, nombreClienteF, idEmpleadoF, dniEmpleadoF,
			nombreEmpleadoF, sueldoF, telefonoF, mtField;
	private JPanel precio, fecha, idCliente, dniCliente, nombreCliente, idEmpleado, dniEmpleado, nombreEmpleado, sueldo,
			telefono, metodoPago;
	private MostrarVentaEnDetalleTableModel mostrarVentaEnDetalleTableModel;
	private ArrayList<TLineaVenta> lineasVenta;
	private ArrayList<TProducto> productos;
	private JTable table;

	private MostrarVentaEnDetallePanel() {
		lineasVenta = new ArrayList<>();
		productos = new ArrayList<>();
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		JLabel mostrarVentaEnDetalle = new JLabel("MOSTRAR VENTA EN DETALLE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarVentaEnDetalle.setFont(f);
		mostrarVentaEnDetalle.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarVentaEnDetalle.setForeground(Color.DARK_GRAY);
		mostrarVentaEnDetalle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

		this.add(mostrarVentaEnDetalle, BorderLayout.PAGE_START);

		JPanel centerPanel = new JPanel(new BorderLayout());

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setMinimumSize(new Dimension(200, 50));
		idPanel.setMaximumSize(new Dimension(200, 50));
		idPanel.setPreferredSize(new Dimension(200, 50));
		JLabel id = new JLabel("ID:");
		this.idF = new JTextField(10);
		JButton mostrar = new JButton("Mostrar");
		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.MOSTRARVENTAENDETALLE, Integer.parseInt(idF.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		idPanel.add(id);
		idPanel.add(idF);
		idPanel.add(mostrar);

		centerPanel.add(idPanel, BorderLayout.PAGE_START);

		// DATOS VENTA, CLIENTE Y EMPLEADO
		JPanel data = new JPanel(new GridLayout(1, 3));
		data.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		Font f2 = new Font("Monospaced", Font.ITALIC, 25);

		// VENTA
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));

		// TITULO
		JPanel tituloVenta = new JPanel(new FlowLayout());
		JLabel titV = new JLabel("VENTA");
		titV.setFont(f2);
		tituloVenta.add(titV);
		west.add(tituloVenta);

		// PRECIO TOTAL
		precio = new JPanel(new FlowLayout());
		JLabel pr = new JLabel("Precio total:");
		this.precioF = new JTextField(10);
		precioF.setEditable(false);
		precio.add(pr);
		precio.add(precioF);
		west.add(precio);

		// FECHA
		fecha = new JPanel(new FlowLayout());
		JLabel fe = new JLabel("Fecha:");
		this.fechaF = new JTextField(10);
		fechaF.setEditable(false);
		fecha.add(fe);
		fecha.add(fechaF);

		west.add(fecha);

		// METODO PAGO
		metodoPago = new JPanel(new FlowLayout());
		JLabel mtLabel = new JLabel("Metodo Pago:");
		this.mtField = new JTextField(10);
		mtField.setEditable(false);
		metodoPago.add(mtLabel);
		metodoPago.add(mtField);
		west.add(metodoPago);

		data.add(west);

		// CLIENTE
		JPanel middle = new JPanel();
		middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));

		// TITULO
		JPanel tituloCliente = new JPanel(new FlowLayout());
		JLabel titC = new JLabel("CLIENTE");
		titC.setFont(f2);
		tituloCliente.add(titC);
		middle.add(tituloCliente);

		// ID
		idCliente = new JPanel(new FlowLayout());
		JLabel idClienteLbl = new JLabel("ID:");
		this.idClienteF = new JTextField(10);
		idClienteF.setEditable(false);
		idCliente.add(idClienteLbl);
		idCliente.add(idClienteF);
		middle.add(idCliente);

		// DNI CLIENTE
		dniCliente = new JPanel(new FlowLayout());
		JLabel dniClienteLabel = new JLabel("DNI:");
		this.dniClienteF = new JTextField(10);
		dniClienteF.setEditable(false);
		dniCliente.add(dniClienteLabel);
		dniCliente.add(dniClienteF);
		middle.add(dniCliente);

		// NOMBRE
		nombreCliente = new JPanel(new FlowLayout());
		JLabel nombreClienteLbl = new JLabel("Nombre:");
		nombreClienteF = new JTextField(10);
		nombreClienteF.setEditable(false);
		nombreCliente.add(nombreClienteLbl);
		nombreCliente.add(nombreClienteF);
		middle.add(nombreCliente);

		data.add(middle);

		// EMPLEADO
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));

		// TITULO
		JPanel tituloEmpleado = new JPanel(new FlowLayout());
		JLabel titE = new JLabel("EMPLEADO");
		titE.setFont(f2);
		tituloEmpleado.add(titE);
		east.add(tituloEmpleado);

		// ID EMPLEADO
		idEmpleado = new JPanel(new FlowLayout());
		JLabel idEmpleadoLbl = new JLabel("ID:");
		idEmpleadoF = new JTextField(10);
		idEmpleadoF.setEditable(false);
		idEmpleado.add(idEmpleadoLbl);
		idEmpleado.add(idEmpleadoF);
		east.add(idEmpleado);

		// DNI EMPLEADO
		dniEmpleado = new JPanel(new FlowLayout());
		dniEmpleado.setBounds(328, 150, 250, 30);
		JLabel dniEmpleadoLbl = new JLabel("DNI:");
		this.dniEmpleadoF = new JTextField(10);
		dniEmpleadoF.setEditable(false);
		dniEmpleado.add(dniEmpleadoLbl);
		dniEmpleado.add(dniEmpleadoF);
		east.add(dniEmpleado);

		// NOMBRE EMPLEADO
		nombreEmpleado = new JPanel(new FlowLayout());
		nombreEmpleado.setBounds(328, 150, 250, 30);
		JLabel nombreEmpleadoLbl = new JLabel("Nombre:");
		this.nombreEmpleadoF = new JTextField(10);
		nombreEmpleadoF.setEditable(false);
		nombreEmpleado.add(nombreEmpleadoLbl);
		nombreEmpleado.add(nombreEmpleadoF);
		east.add(nombreEmpleado);

		// SUELDO
		sueldo = new JPanel(new FlowLayout());
		sueldo.setBounds(350, 110, 250, 30);
		JLabel sueldoLbl = new JLabel("Sueldo:");
		this.sueldoF = new JTextField(10);
		sueldoF.setEditable(false);
		sueldo.add(sueldoLbl);
		sueldo.add(sueldoF);
		east.add(sueldo);

		// TELEFONO
		telefono = new JPanel(new FlowLayout());
		telefono.setBounds(331, 150, 250, 30);
		JLabel telefonoLbl = new JLabel("Telefono:");
		this.telefonoF = new JTextField(10);
		telefonoF.setEditable(false);
		telefono.add(telefonoLbl);
		telefono.add(telefonoF);
		east.add(telefono);

		data.add(east);

		centerPanel.add(data, BorderLayout.CENTER);
		
		// PRODUCTOS
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
		JLabel titP = new JLabel("PRODUCTOS");
		titP.setFont(f2);
		south.add(titP);
		south.add(crearTabla(lineasVenta, productos));
		south.setMinimumSize(new Dimension(500, 360));
		south.setMaximumSize(new Dimension(500, 360));
		south.setPreferredSize(new Dimension(500, 360));

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);

	}

	public static MostrarVentaEnDetallePanel getInstance() {
		if (instance == null)
			instance = new MostrarVentaEnDetallePanel();
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
			parent.showPanel(this);
			clear();
		} else {
			parent.showPanel(this);
			this.idF.setText(((TVentaEnDetalle) o).getTVenta().getID().toString());
			this.precioF.setText(((TVentaEnDetalle) o).getTVenta().getPrecioTotal().toString());
			this.fechaF.setText(new SimpleDateFormat("dd-MM-yyyy").format(((TVentaEnDetalle) o).getTVenta().getFecha()));
			this.mtField.setText(((TVentaEnDetalle) o).getTVenta().getMetodoPago());
			this.idClienteF.setText(((TVentaEnDetalle) o).getTClienteJPA().getID().toString());
			this.dniClienteF.setText(((TVentaEnDetalle) o).getTClienteJPA().getDNI());
			this.nombreClienteF.setText(((TVentaEnDetalle) o).getTClienteJPA().getNombre());
			this.idEmpleadoF.setText(((TVentaEnDetalle) o).getTEmpleado().getID().toString());
			this.dniEmpleadoF.setText(((TVentaEnDetalle) o).getTEmpleado().getDNI());
			this.nombreEmpleadoF.setText(((TVentaEnDetalle) o).getTEmpleado().getNombre());
			this.sueldoF.setText(((TVentaEnDetalle) o).getTEmpleado().getSueldo().toString());
			this.telefonoF.setText(((TVentaEnDetalle) o).getTEmpleado().getTelefono().toString());
			lineasVenta = ((TVentaEnDetalle) o).getTLineasVenta();
			productos = ((TVentaEnDetalle) o).getTProductos();
			mostrarVentaEnDetalleTableModel.updateList(lineasVenta, productos);
		}
	}

	public void clear() {
		precioF.setText("");
		fechaF.setText("");
		mtField.setText("");
		idClienteF.setText("");
		dniClienteF.setText("");
		nombreClienteF.setText("");
		idEmpleadoF.setText("");
		dniEmpleadoF.setText("");
		nombreEmpleadoF.setText("");
		sueldoF.setText("");
		telefonoF.setText("");
		idF.setText("");
		mostrarVentaEnDetalleTableModel.updateList(new ArrayList<TLineaVenta>(), new ArrayList<TProducto>());
	}

	public Component crearTabla(ArrayList<TLineaVenta> lineasVenta, ArrayList<TProducto> productos) {
		mostrarVentaEnDetalleTableModel = new MostrarVentaEnDetalleTableModel(lineasVenta, productos);
		table = new JTable(mostrarVentaEnDetalleTableModel);
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		return scrollpane;
	}

	@Override
	public boolean validation() {
		try {
			if (idF.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idF.getText()) < 0)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}