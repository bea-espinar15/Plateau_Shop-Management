
package Presentacion.Gui.Panels.Producto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

public class MostrarProductoPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;

	private static MostrarProductoPanel instance;
	private Panel_Main_Producto parent;
	private String type;
	private JTextField idF, actF, nomF, precioF, stockF, tamanoF, pesoF, noTypeF, bebF, comF;
	private JPanel typeEmpty, bebida, comida, tamano, peso;

	private MostrarProductoPanel() {
		type = "";
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA

		JLabel mostrarProducto = new JLabel("MOSTRAR PRODUCTO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarProducto.setFont(f);
		mostrarProducto.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarProducto.setForeground(Color.DARK_GRAY);
		mostrarProducto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarProducto, BorderLayout.PAGE_START);

		// CENTER

		JPanel centerPanel = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(228, 35, 200, 30);
		JLabel id = new JLabel("ID:");
		this.idF = new JTextField(10);
		idPanel.add(id);
		idPanel.add(idF);

		centerPanel.add(idPanel);

		JButton mostrar = new JButton("Mostrar");
		mostrar.setBounds(438, 35, 100, 30);
		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					Context request = new Context(ContextEnum.MOSTRARPRODUCTO, Integer.parseInt(idF.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		centerPanel.add(mostrar);

		// ACTIVO
		JPanel activo = new JPanel(new FlowLayout());
		activo.setBounds(44, 110, 250, 30);
		JLabel act = new JLabel("Activo:");
		this.actF = new JTextField(10);
		actF.setEditable(false);
		activo.add(act);
		activo.add(actF);

		centerPanel.add(activo);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(40, 150, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomF = new JTextField(10);
		nomF.setEditable(false);
		nombre.add(nom);
		nombre.add(nomF);

		centerPanel.add(nombre);

		// Precio
		JPanel precio = new JPanel(new FlowLayout());
		precio.setBounds(44, 190, 250, 30);
		JLabel precioLabel = new JLabel("Precio:");
		this.precioF = new JTextField(10);
		precioF.setEditable(false);
		precio.add(precioLabel);
		precio.add(precioF);

		centerPanel.add(precio);

		// Sotck
		JPanel stock = new JPanel(new FlowLayout());
		stock.setBounds(45, 230, 250, 30);
		JLabel stockLabel = new JLabel("Stock:");
		this.stockF = new JTextField(10);
		stockF.setEditable(false);
		stock.add(stockLabel);
		stock.add(stockF);

		centerPanel.add(stock);

		// SIN TIPO
		typeEmpty = new JPanel(new FlowLayout());
		typeEmpty.setBounds(350, 110, 250, 30);
		JLabel noType = new JLabel("Tipo:");
		noTypeF = new JTextField(10);
		noTypeF.setEditable(false);
		typeEmpty.add(noType);
		typeEmpty.add(noTypeF);

		centerPanel.add(typeEmpty);

		// TIPO BEBIDA
		bebida = new JPanel(new FlowLayout());
		bebida.setBounds(350, 110, 250, 30);
		JLabel bebid = new JLabel("Tipo:");
		bebF = new JTextField("BEBIDA", 10);
		bebF.setEditable(false);
		bebida.add(bebid);
		bebida.add(bebF);

		centerPanel.add(bebida);

		// Tamano
		tamano = new JPanel(new FlowLayout());
		tamano.setBounds(338, 150, 250, 30);
		JLabel tam = new JLabel("Tamano:");
		this.tamanoF = new JTextField(10);
		tamanoF.setEditable(false);
		tamano.add(tam);
		tamano.add(tamanoF);

		centerPanel.add(tamano);

		// TIPO COMIDA
		comida = new JPanel(new FlowLayout());
		comida.setBounds(350, 110, 250, 30);
		JLabel comidaLabel = new JLabel("Tipo:");
		comF = new JTextField("COMIDA", 10);
		comF.setEditable(false);
		comida.add(comidaLabel);
		comida.add(comF);

		centerPanel.add(comida);

		// Peso
		peso = new JPanel(new FlowLayout());
		peso.setBounds(348, 150, 250, 30);
		JLabel pes = new JLabel("Peso:");
		this.pesoF = new JTextField(10);
		pesoF.setEditable(false);
		peso.add(pes);
		peso.add(pesoF);

		centerPanel.add(peso);

		updatePanel(type);

		this.add(centerPanel, BorderLayout.CENTER);

	}

	public static MostrarProductoPanel getInstance() {
		if (instance == null)
			instance = new MostrarProductoPanel();
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
			//parent.showPanel(this);
			if (o instanceof TProductoBebida) {
				this.type = "BEBIDA";
				updatePanel(type);
				this.tamanoF.setText(((TProductoBebida) o).getTamano().toString());
				this.bebF.setText("BEBIDA");
			} else {
				this.type = "COMIDA";
				updatePanel(type);
				this.pesoF.setText(((TProductoComida) o).getPeso().toString());
				this.comF.setText("COMIDA");
			}
			this.idF.setText(((TProducto) o).getID().toString());
			this.precioF.setText(((TProducto) o).getPrecioActual().toString());
			this.nomF.setText(((TProducto) o).getNombre());
			this.stockF.setText(((TProducto) o).getStock().toString());
			Boolean act = ((TProducto) o).getActivo();
			if (act)
				actF.setText("SI");
			else
				actF.setText("NO");
		}
	}

	public void clear() {
		idF.setText("");
		actF.setText("");
		nomF.setText("");
		precioF.setText("");
		stockF.setText("");
		pesoF.setText("");
		tamanoF.setText("");
		comF.setText("");
		bebF.setText("");
		updatePanel("");
	}

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

	private void updatePanel(String type) {
		if (type == "") {
			comida.setVisible(false);
			tamano.setVisible(false);
			peso.setVisible(false);
			bebida.setVisible(false);
			typeEmpty.setVisible(true);
		} else if (type == "BEBIDA") {
			typeEmpty.setVisible(false);
			comida.setVisible(false);
			peso.setVisible(false);
			bebida.setVisible(true);
			tamano.setVisible(true);
		} else {
			typeEmpty.setVisible(false);
			bebida.setVisible(false);
			tamano.setVisible(false);
			comida.setVisible(true);
			peso.setVisible(true);
		}
	}
	
}