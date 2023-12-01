
package Presentacion.Gui.Panels.Producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import Negocio.Producto.TProductoBebida;
import Negocio.Producto.TProductoComida;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class AltaProductoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private JButton ok;
	private JComboBox<String> typeProducto, tamanoC;
	private static AltaProductoPanel instance;
	private Panel_Main_Producto parent;
	private JTextField nomF;
	private JSpinner precioS, stockS, pesoS;
	private JPanel comida, bebida;

	private AltaProductoPanel() {
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// ____ CABECERA _____
		JLabel altaProducto = new JLabel("ALTA PRODUCTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		altaProducto.setFont(f);
		altaProducto.setHorizontalAlignment(SwingConstants.CENTER);
		altaProducto.setForeground(Color.DARK_GRAY);
		altaProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(altaProducto, BorderLayout.PAGE_START);

		// DATOS (CENTRO)
		JPanel datos = new JPanel(new GridLayout(1, 2));

		// DATOS COMUNES
		JPanel datosC = new JPanel(null);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(10, 35, 400, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomF = new JTextField(10);
		nombre.add(nom);
		nombre.add(nomF);

		// PRECIO
		JPanel precio = new JPanel(new FlowLayout());
		precio.setBounds(12, 75, 400, 30);
		JLabel Lprecio = new JLabel("Precio:");
		SpinnerModel modelp = new SpinnerNumberModel(1, 1, 99, 1);
		precioS = new JSpinner(modelp);
		precioS.setPreferredSize(new Dimension(113, 20));
		precio.add(Lprecio);
		precio.add(precioS);

		// STOCK
		JPanel stock = new JPanel(new FlowLayout());
		stock.setBounds(13, 115, 400, 30);
		JLabel Lstock = new JLabel("Stock:");
		SpinnerModel models = new SpinnerNumberModel(1, 1, 99, 1);
		stockS = new JSpinner(models);
		stockS.setPreferredSize(new Dimension(113, 20));
		stock.add(Lstock);
		stock.add(stockS);

		ok = new JButton("Aceptar");
		ok.setBounds(190, 190, 100, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					if (typeProducto.getSelectedItem() == "BEBIDA") {
						TProductoBebida bebida = new TProductoBebida();
						bebida.setNombre(nomF.getText());
						bebida.setPrecioActual((Integer) precioS.getValue());
						bebida.setStock((Integer) stockS.getValue());
						bebida.setTamano((String)tamanoC.getSelectedItem());
						Context request = new Context(ContextEnum.ALTAPRODUCTO, bebida);
						ApplicationController.getInstance().manageRequest(request);
					} else if (typeProducto.getSelectedItem() == "COMIDA") {
						TProductoComida comida = new TProductoComida();
						comida.setNombre(nomF.getText());
						comida.setPrecioActual((Integer) precioS.getValue());
						comida.setStock((Integer) stockS.getValue());
						comida.setPeso((Integer) pesoS.getValue());
						Context request = new Context(ContextEnum.ALTAPRODUCTO, comida);
						ApplicationController.getInstance().manageRequest(request);
					} else {
						JOptionPane.showMessageDialog(parent, "No se ha introducido el tipo del producto", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		datosC.add(nombre);
		datosC.add(precio);
		datosC.add(stock);
		datosC.add(ok);

		datos.add(datosC);

		// DATOS ESPECIFICOS
		JPanel datosE = new JPanel(null);

		// COMIDA
		comida = new JPanel();
		comida.setBounds(7, 110, 200, 40);
		comida.setVisible(false);

		// PESO
		JPanel peso = new JPanel(new FlowLayout());
		JLabel Lpeso = new JLabel("Peso:");
		SpinnerModel modelw = new SpinnerNumberModel(1, 1, 99, 1);
		pesoS = new JSpinner(modelw);
		pesoS.setPreferredSize(new Dimension(72, 25));
		peso.add(Lpeso);
		peso.add(pesoS);

		comida.add(peso);

		// BEBIDA
		bebida = new JPanel();
		bebida.setBounds(7, 110, 200, 40);
		bebida.setVisible(false);

		// TAMANO
		JPanel tamano = new JPanel(new FlowLayout());
		JLabel Ltamano = new JLabel("Tamano:");
		tamanoC = new JComboBox<String>();
		tamanoC.addItem("S");
		tamanoC.addItem("M");
		tamanoC.addItem("L");
		tamanoC.addItem("XL");
		tamanoC.setSelectedItem(null);
		tamano.add(Ltamano);
		tamano.add(tamanoC);

		bebida.add(tamano);

		// COMBO TIPOS
		JPanel tipos = new JPanel(new FlowLayout());
		tipos.setBounds(10, 35, 200, 50);
		JLabel tipo = new JLabel("Tipo:");
		typeProducto = new JComboBox<String>();
		typeProducto.addItem("COMIDA");
		typeProducto.addItem("BEBIDA");		
		typeProducto.setSelectedItem(null);
		typeProducto.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (typeProducto.getSelectedItem() == null || typeProducto.getSelectedItem().equals("BEBIDA")) {
					comida.setVisible(false);
					bebida.setVisible(true);
				} else {
					comida.setVisible(true);
					bebida.setVisible(false);
				}
			}

		});
		tipos.add(tipo);
		tipos.add(typeProducto);
		datosE.add(tipos);
		datosE.add(bebida);
		datosE.add(comida);
		datos.add(datosE);

		this.add(datos, BorderLayout.CENTER);
	}

	public static AltaProductoPanel getInstance() {
		if (instance == null)
			instance = new AltaProductoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Producto parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.PRODUCTO,((EventEnum) o));
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			JOptionPane.showMessageDialog(parent, "El producto se ha creado con exito. ID: " + ((Integer)o).toString());
			clear();
		}
	}

	public boolean validation() {
		try {
			if (nomF.getText().equals(""))
				throw new Exception();
			if (typeProducto.getSelectedItem() != null && typeProducto.getSelectedItem().equals("BEBIDA") && tamanoC.getSelectedItem() == null)
				throw new Exception();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clear() {
		nomF.setText("");
		precioS.setValue(1);
		stockS.setValue(1);
		pesoS.setValue(1);
		tamanoC.setSelectedItem(null);
		typeProducto.setSelectedItem(null);
		comida.setVisible(false);
		bebida.setVisible(false);
	}

}