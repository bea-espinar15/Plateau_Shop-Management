
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Producto;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Presentacion.Gui.Panels.GeneralMainPanel;
import Presentacion.Gui.Panels.GeneralPanel;

public class Panel_Main_Producto extends GeneralMainPanel {

	private static final long serialVersionUID = 1L;

	private JButton alta, baja, modificar, mostrar, listar, listarComidas, listarBebidas, listarPorProveedor,
			listarPorVenta, anadirProveedor, quitarProveedor;

	private AltaProductoPanel altaPanel;
	private BajaProductoPanel bajaPanel;
	private ModificarProductoPanel modificarPanel;
	private MostrarProductoPanel mostrarPanel;
	private ListarProductosPanel listarProductosPanel;
	private ListarComidasPanel listarComidasPanel;
	private ListarBebidasPanel listarBebidasPanel;
	private ListarPorProveedorPanel listarPorProveedorPanel;
	private ListarPorVentaPanel listarPorVentaPanel;
	private AnadirProveedorPanel anadirProveedorPanel;
	private QuitarProveedorPanel quitarProveedorPanel;

	private JPanel contentPanel;

	public Panel_Main_Producto() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		initPanels();

		// __________ CASOS DE USO BUTTONS (WEST)_____________

		JPanel buttonsPanel = new JPanel(new GridLayout(11, 1));

		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(altaPanel);
			}

		});
		buttonsPanel.add(alta);

		baja = new JButton("Baja");
		baja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(bajaPanel);
			}

		});
		buttonsPanel.add(baja);

		modificar = new JButton("Modificar");
		modificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(modificarPanel);
			}

		});
		buttonsPanel.add(modificar);

		mostrar = new JButton("Mostrar");
		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(mostrarPanel);
			}

		});
		buttonsPanel.add(mostrar);

		listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarProductosPanel);
			}

		});
		buttonsPanel.add(listar);

		listarComidas = new JButton("Listar Comidas");
		listarComidas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarComidasPanel);
			}

		});
		buttonsPanel.add(listarComidas);

		listarBebidas = new JButton("Listar Bebidas");
		listarBebidas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarBebidasPanel);
			}

		});
		buttonsPanel.add(listarBebidas);

		listarPorProveedor = new JButton("Listar por Proveedor");
		listarPorProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPorProveedorPanel);
			}

		});
		buttonsPanel.add(listarPorProveedor);

		listarPorVenta = new JButton("Listar por Venta");
		listarPorVenta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPorVentaPanel);
			}

		});
		buttonsPanel.add(listarPorVenta);

		anadirProveedor = new JButton("Anadir proveedor");
		anadirProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(anadirProveedorPanel);
			}

		});
		buttonsPanel.add(anadirProveedor);

		quitarProveedor = new JButton("Quitar proveedor");
		quitarProveedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(quitarProveedorPanel);
			}

		});
		buttonsPanel.add(quitarProveedor);

		this.add(buttonsPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
	}

	public void initPanels() {
		contentPanel = new JPanel(new BorderLayout());
		altaPanel = AltaProductoPanel.getInstance();
		altaPanel.setParent(this);
		bajaPanel = BajaProductoPanel.getInstance();
		bajaPanel.setParent(this);
		modificarPanel = ModificarProductoPanel.getInstance();
		modificarPanel.setParent(this);
		mostrarPanel = MostrarProductoPanel.getInstance();
		mostrarPanel.setParent(this);
		listarProductosPanel = ListarProductosPanel.getInstance();
		listarProductosPanel.setParent(this);
		listarComidasPanel = ListarComidasPanel.getInstance();
		listarComidasPanel.setParent(this);
		listarBebidasPanel = ListarBebidasPanel.getInstance();
		listarBebidasPanel.setParent(this);
		listarPorProveedorPanel = ListarPorProveedorPanel.getInstance();
		listarPorProveedorPanel.setParent(this);
		listarPorVentaPanel = ListarPorVentaPanel.getInstance();
		listarPorVentaPanel.setParent(this);
		anadirProveedorPanel = AnadirProveedorPanel.getInstance();
		anadirProveedorPanel.setParent(this);
		quitarProveedorPanel = QuitarProveedorPanel.getInstance();
		quitarProveedorPanel.setParent(this);
	}

	public void hidePanels() {
		altaPanel.setVisible(false);
		altaPanel.clear();
		bajaPanel.setVisible(false);
		bajaPanel.clear();
		modificarPanel.setVisible(false);
		modificarPanel.clear();
		mostrarPanel.setVisible(false);
		mostrarPanel.clear();
		listarProductosPanel.setVisible(false);
		listarProductosPanel.clear();
		listarComidasPanel.setVisible(false);
		listarComidasPanel.clear();
		listarBebidasPanel.setVisible(false);
		listarBebidasPanel.clear();
		listarPorProveedorPanel.setVisible(false);
		listarPorProveedorPanel.clear();
		listarPorVentaPanel.setVisible(false);
		listarPorVentaPanel.clear();
		anadirProveedorPanel.setVisible(false);
		anadirProveedorPanel.clear();
		quitarProveedorPanel.setVisible(false);
		quitarProveedorPanel.clear();
	}

	public void showPanel(GeneralPanel panel) {
		hidePanels();
		contentPanel.add(panel);
		panel.setVisible(true);
	}
}