
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

import Negocio.Producto.TProducto;
import Negocio.Producto.TProductoBebida;
import Negocio.Producto.TProductoComida;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class ModificarProductoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static ModificarProductoPanel instance;
	private Panel_Main_Producto parent;
	private JButton ok, mod;
	private JTextField idF, nomF;
	private JSpinner precioS, stockS, pesoS;
	private JComboBox<String> tamanoC;
	private JPanel comida, bebida;
	private Boolean buscado;
	private static Boolean selected = false;

	private ModificarProductoPanel() {
		buscado = false;
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());

		// CABECERA
		JLabel modificarProducto = new JLabel("MODIFICAR PRODUCTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		modificarProducto.setFont(f);
		modificarProducto.setHorizontalAlignment(SwingConstants.CENTER);
		modificarProducto.setForeground(Color.DARK_GRAY);
		modificarProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(modificarProducto, BorderLayout.PAGE_START);

		// ______ DATOS (CENTRE) _______
		JPanel datos = new JPanel(new GridLayout(1, 2));

		// _______ DATOS COMUNES ________
		JPanel datosC = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(100, 35, 170, 30);
		JLabel idLabel = new JLabel("ID:");
		this.idF = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idF);

		// ACEPTAR ID
		ok = new JButton("Buscar");
		ok.setBounds(280, 35, 80, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					ModificarProductoPanel.selected = true;
					Context request = new Context(ContextEnum.MOSTRARPRODUCTO, Integer.parseInt(idF.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(0, 130, 400, 30);
		JLabel nom = new JLabel("Nombre:");
		nomF = new JTextField(10);
		nomF.setEditable(false);
		nombre.add(nom);
		nombre.add(nomF);

		// PRECIO
		JPanel precio = new JPanel(new FlowLayout());
		precio.setBounds(2, 170, 400, 30);
		JLabel Lprecio = new JLabel("Precio:");
		SpinnerModel modelp = new SpinnerNumberModel(1, 1, 99, 1);
		precioS = new JSpinner(modelp);
		precioS.setPreferredSize(new Dimension(113, 20));
		precioS.setEnabled(false);
		precio.add(Lprecio);
		precio.add(precioS);

		// STOCK
		JPanel stock = new JPanel(new FlowLayout());
		stock.setBounds(3, 210, 400, 30);
		JLabel Lstock = new JLabel("Stock:");
		SpinnerModel models = new SpinnerNumberModel(1, 1, 99, 1);
		stockS = new JSpinner(models);
		stockS.setPreferredSize(new Dimension(113, 20));
		stockS.setEnabled(false);
		stock.add(Lstock);
		stock.add(stockS);

		mod = new JButton("Modificar");
		mod.setEnabled(false);
		mod.setBounds(180, 285, 100, 30);
		mod.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					Context request;
					if (bebida.isVisible()) {
						TProductoBebida bebida = new TProductoBebida();
						bebida.setID(Integer.parseInt(idF.getText()));
						bebida.setNombre(nomF.getText());
						bebida.setPrecioActual((Integer) precioS.getValue());
						bebida.setStock((Integer) stockS.getValue());
						bebida.setTamano((String)tamanoC.getSelectedItem());
						request = new Context(ContextEnum.MODIFICARPRODUCTO, bebida);
					} else {
						TProductoComida comida = new TProductoComida();
						comida.setID(Integer.parseInt(idF.getText()));
						comida.setNombre(nomF.getText());
						comida.setPrecioActual((Integer) precioS.getValue());
						comida.setStock((Integer) stockS.getValue());
						comida.setPeso((Integer) pesoS.getValue());
						request = new Context(ContextEnum.MODIFICARPRODUCTO, comida);
					}
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introduccidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		datosC.add(idPanel);
		datosC.add(ok);
		datosC.add(nombre);
		datosC.add(precio);
		datosC.add(stock);
		datosC.add(mod);

		datos.add(datosC);

		// DATOS ESPECIFICOS
		JPanel datosE = new JPanel(null);

		// COMIDA
		comida = new JPanel();
		comida.setBounds(7, 125, 200, 40);
		comida.setVisible(false);

		// PESO
		JPanel peso = new JPanel(new FlowLayout());
		JLabel Lpeso = new JLabel("Peso:");
		SpinnerModel modelw = new SpinnerNumberModel(1, 1, 99, 1);
		pesoS = new JSpinner(modelw);
		pesoS.setPreferredSize(new Dimension(72, 20));
		pesoS.setEnabled(false);
		peso.add(Lpeso);
		peso.add(pesoS);

		comida.add(peso);

		// BEBIDA
		bebida = new JPanel();
		bebida.setBounds(7, 120, 200, 40);
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
		tamanoC.setEnabled(false);
		tamano.add(Ltamano);
		tamano.add(tamanoC);

		bebida.add(tamano);

		datosE.add(bebida);
		datosE.add(comida);

		datos.add(datosE);

		this.add(datos, BorderLayout.CENTER);

	}

	public static ModificarProductoPanel getInstance() {
		if (instance == null)
			instance = new ModificarProductoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Producto parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.PRODUCTO, ((EventEnum) o));
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		}
		else {
			if (buscado) {
				JOptionPane.showMessageDialog(parent, "El producto se ha modificado con exito.");
				clear();
			}
			else {
				nomF.setText(((TProducto) o).getNombre());
				precioS.setValue((Integer) ((TProducto) o).getPrecioActual());
				stockS.setValue((Integer) ((TProducto) o).getStock());
				if (o instanceof TProductoBebida) {
					bebida.setVisible(true);
					tamanoC.setSelectedItem(((TProductoBebida) o).getTamano());
					tamanoC.setEnabled(true);
					
				} else {
					comida.setVisible(true);
					pesoS.setValue((Integer) ((TProductoComida) o).getPeso());
					pesoS.setEnabled(true);
				}

				idF.setEditable(false);
				nomF.setEditable(true);
				precioS.setEnabled(true);
				stockS.setEnabled(true);
				
				mod.setEnabled(true);
				ok.setEnabled(false);
				
				buscado = true;
			}
		}
	}

	public void clear() {
		ModificarProductoPanel.selected = false;
		
		idF.setText("");
		nomF.setText("");
		precioS.setValue(1);
		stockS.setValue(1);
		pesoS.setValue(1);
		tamanoC.setSelectedItem(null);

		nomF.setEditable(false);
		precioS.setEnabled(false);
		stockS.setEnabled(false);
		pesoS.setEnabled(false);
		tamanoC.setEnabled(false);		
		idF.setEditable(true);
		ok.setEnabled(true);
		mod.setEnabled(false);
		
		comida.setVisible(false);
		bebida.setVisible(false);
		buscado = false;
	}

	public boolean validation() {
		try {
			if (!buscado) {
				if (idF.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idF.getText()) < 0) {
					throw new Exception();
				}
				return true;
			} else {
				if (nomF.getText().equals(""))
					throw new Exception();
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isSelected() {
		return ModificarProductoPanel.selected;
	}
}