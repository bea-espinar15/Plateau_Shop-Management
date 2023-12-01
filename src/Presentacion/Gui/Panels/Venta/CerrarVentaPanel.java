
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import utilities.Pair;

import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;

import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;


public class CerrarVentaPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;

	private static CerrarVentaPanel instance;
	
	private CrearVentaPanel parent;
	private JLabel idProductoLabel,udsLabel;
	private JSpinner uds;
	private JTextField idProductoField;
	private JButton anadir, quitar, cerrar;
	private Boolean quitarVenta, anadirVenta;
	private HashMap<Integer, TLineaVenta> carrito;
	private TVenta TVenta;
	
	private CerrarVentaPanel(){
		anadirVenta = false;
		quitarVenta = false;
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JLabel cerrarVenta = new JLabel("CERRAR VENTA");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		cerrarVenta.setFont(f);
		cerrarVenta.setHorizontalAlignment(SwingConstants.CENTER);
		cerrarVenta.setForeground(Color.DARK_GRAY);
		cerrarVenta.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(cerrarVenta, BorderLayout.PAGE_START);
		
		JPanel data = new JPanel();
		data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));

		JPanel inputsPanel = new JPanel();
		inputsPanel.setLayout(new BoxLayout(inputsPanel, BoxLayout.X_AXIS));
		inputsPanel.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));
		inputsPanel.setMinimumSize(new Dimension(600, 100));
		inputsPanel.setMaximumSize(new Dimension(600, 100));
		inputsPanel.setPreferredSize(new Dimension(600, 100));

		// ID PRODUCTO
		JPanel idPPanel = new JPanel(new FlowLayout());
		idProductoLabel = new JLabel("ID Producto:");
		idProductoField = new JTextField(10);
		idPPanel.add(idProductoLabel);
		idPPanel.add(idProductoField);

		inputsPanel.add(idPPanel);
		inputsPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		// UNIDADES
		JPanel udsPanel = new JPanel(new FlowLayout());
		udsLabel = new JLabel("Unidades:");
		SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
		uds = new JSpinner(model);
		uds.setPreferredSize(new Dimension(38, 22));
		udsPanel.add(udsLabel);
		udsPanel.add(uds);

		inputsPanel.add(udsPanel);
		inputsPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		data.add(inputsPanel);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setPreferredSize(new Dimension(350, 100));
		buttonsPanel.setMaximumSize(new Dimension(350, 100));
		buttonsPanel.setMinimumSize(new Dimension(350, 100));

		// ANADIR
		anadir = new JButton("Anadir");
		anadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				anadirVenta = true;
				if (validation()) {
					if (carrito.containsKey(Integer.parseInt(idProductoField.getText()))) {
						Integer stock = carrito.get(Integer.parseInt(idProductoField.getText())).getUds()
								+ (Integer) uds.getValue();
						carrito.get(Integer.parseInt(idProductoField.getText())).setUds(stock);
					} else {
						TLineaVenta lineaVenta = new TLineaVenta();
						lineaVenta.setIDProducto((Integer.parseInt(idProductoField.getText())));
						lineaVenta.setUds(((Integer) uds.getValue()));
						carrito.put(Integer.parseInt(idProductoField.getText()), lineaVenta);
					}
				} else
					JOptionPane.showMessageDialog(parent, "Los datos son sintacticamente erroneos", "Error",
							JOptionPane.ERROR_MESSAGE);
				anadirVenta = false;
				clear();
			}

		});

		buttonsPanel.add(anadir);

		buttonsPanel.add(Box.createHorizontalGlue());

		quitar = new JButton("Quitar");
		quitar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quitarVenta = true;
				if (validation()) {
					if (carrito.containsKey(Integer.parseInt(idProductoField.getText()))) {
						Integer unidadesAct = carrito.get(Integer.parseInt(idProductoField.getText())).getUds()
								- (Integer) uds.getValue();
						if (unidadesAct <= 0)
							carrito.remove(Integer.parseInt(idProductoField.getText()));
						else
							carrito.get(Integer.parseInt(idProductoField.getText())).setUds(unidadesAct);
					} else {
						JOptionPane.showMessageDialog(parent, "El producto no estaba anadido al carrito", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(parent, "Los datos son sintacticamente erroneos", "Error",
							JOptionPane.ERROR_MESSAGE);
				clear();
				quitarVenta = false;
			}

		});

		buttonsPanel.add(quitar);

		buttonsPanel.add(Box.createHorizontalGlue());

		cerrar = new JButton("Cerrar");
		cerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					if (!carrito.isEmpty()) {
						Context request = new Context(ContextEnum.CERRARVENTA, new Pair<TVenta, HashMap<Integer, TLineaVenta>>(TVenta, carrito));
						ApplicationController.getInstance().manageRequest(request);
					}
					else
						JOptionPane.showMessageDialog(parent, "El carrito de la venta está vacío", "Venta vacía",
								JOptionPane.ERROR_MESSAGE);
				} else
					JOptionPane.showMessageDialog(parent, "Los datos son sintacticamente erroneos", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});

		buttonsPanel.add(cerrar);

		data.add(buttonsPanel);

		this.add(data, BorderLayout.CENTER);
	}

	public static CerrarVentaPanel getInstance() {
		if (instance == null)
			instance = new CerrarVentaPanel();
		return instance;
	}
	
	public void setParent(CrearVentaPanel parent) {
		this.parent = parent;
	}
	
	public void setTVenta(TVenta TVenta) {
		this.TVenta = TVenta;
	}

	public void setCarrito(HashMap<Integer, TLineaVenta> carrito) {
		this.carrito = carrito;
	}
	
	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.VENTA, (EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
			parent.showAbrirPanel();
		} else {
			JOptionPane.showMessageDialog(parent, "La venta se ha realizado con exito, ID: " + ((Integer)o).toString());
			clear();
			parent.showAbrirPanel();
		}
	}

	@Override
	public boolean validation() {
		try {
			if (quitarVenta || anadirVenta) {
				if (idProductoField.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idProductoField.getText()) < 0)
					throw new Exception();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clear() {
		idProductoField.setText("");
		uds.setValue(1);
	}
	
}
	