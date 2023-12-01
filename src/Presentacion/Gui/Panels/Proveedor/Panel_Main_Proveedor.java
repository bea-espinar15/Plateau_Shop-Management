
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Proveedor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Presentacion.Gui.Panels.GeneralMainPanel;
import Presentacion.Gui.Panels.GeneralPanel;

public class Panel_Main_Proveedor extends GeneralMainPanel {

	private static final long serialVersionUID = 1L;

	private JButton alta, baja, modificar, mostrar, listar, listarPorProducto;

	private AltaProveedorPanel altaPanel;
	private BajaProveedorPanel bajaPanel;
	private ModificarProveedorPanel modificarPanel;
	private MostrarProveedorPanel mostrarPanel;
	private ListarProveedoresPanel listarPanel;
	private ListarProveedoresPorProductoPanel listarPorProductoPanel;

	private JPanel contentPanel;

	public Panel_Main_Proveedor() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		initPanels();

		// __________ CASOS DE USO BUTTONS (WEST)_____________

		JPanel buttonsPanel = new JPanel(new GridLayout(6, 1));

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
		
		mostrar = new JButton("Mostrar");
		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(mostrarPanel);
			}

		});
		buttonsPanel.add(mostrar);

		modificar = new JButton("Modificar");
		modificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(modificarPanel);
			}

		});
		buttonsPanel.add(modificar);

		listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPanel);
			}

		});
		buttonsPanel.add(listar);

		listarPorProducto = new JButton("Listar Por Producto");
		listarPorProducto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPorProductoPanel);
			}

		});
		buttonsPanel.add(listarPorProducto);

		this.add(buttonsPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
	}

	public void initPanels() {
		contentPanel = new JPanel(new BorderLayout());
		altaPanel = AltaProveedorPanel.getInstance();
		altaPanel.setParent(this);
		bajaPanel = BajaProveedorPanel.getInstance();
		bajaPanel.setParent(this);
		modificarPanel = ModificarProveedorPanel.getInstance();
		modificarPanel.setParent(this);
		mostrarPanel = MostrarProveedorPanel.getInstance();
		mostrarPanel.setParent(this);
		listarPanel = ListarProveedoresPanel.getInstance();
		listarPanel.setParent(this);
		listarPorProductoPanel = ListarProveedoresPorProductoPanel.getInstance();
		listarPorProductoPanel.setParent(this);
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
		listarPanel.setVisible(false);
		listarPanel.clear();
		listarPorProductoPanel.setVisible(false);
		listarPorProductoPanel.clear();
	}

	public void showPanel(GeneralPanel panel) {
		hidePanels();
		contentPanel.add(panel);
		panel.setVisible(true);
	}
}