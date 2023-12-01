
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Departamento;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Presentacion.Gui.Panels.GeneralMainPanel;
import Presentacion.Gui.Panels.GeneralPanel;

public class Panel_Main_Departamento extends GeneralMainPanel {

	private static final long serialVersionUID = 1L;

	private JButton alta;
	private JButton baja;
	private JButton modificar;
	private JButton listar;
	private JButton mostrar;

	private JPanel contentPanel;

	private AltaDepartamentoPanel altaPanel;
	private BajaDepartamentoPanel bajaPanel;
	private ModificarDepartamentoPanel modificarPanel;
	private ListarDepartamentosPanel listarPanel;
	private MostrarDepartamentoPanel mostrarPanel;

	public Panel_Main_Departamento() {
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		initPanels();

		// __________ CASOS DE USO BUTTONS (WEST)_____________

		JPanel buttonsPanel = new JPanel(new GridLayout(5, 1));

		// ALTA
		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(altaPanel);
			}

		});
		buttonsPanel.add(alta);

		// BAJA
		baja = new JButton("Baja");
		baja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(bajaPanel);
			}

		});
		buttonsPanel.add(baja);

		// MODIFICAR
		modificar = new JButton("Modificar");
		modificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(modificarPanel);
			}

		});
		buttonsPanel.add(modificar);

		// MOSTRAR
		mostrar = new JButton("Mostrar");
		mostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(mostrarPanel);
			}

		});
		buttonsPanel.add(mostrar);

		// LISTAR
		listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPanel);
			}

		});
		buttonsPanel.add(listar);

		this.add(buttonsPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
	}

	@Override
	public void initPanels() {
		contentPanel = new JPanel(new BorderLayout());

		altaPanel = AltaDepartamentoPanel.getInstance();
		altaPanel.setParent(this);

		bajaPanel = BajaDepartamentoPanel.getInstance();
		bajaPanel.setParent(this);

		modificarPanel = ModificarDepartamentoPanel.getInstance();
		modificarPanel.setParent(this);

		listarPanel = ListarDepartamentosPanel.getInstance();
		listarPanel.setParent(this);

		mostrarPanel = MostrarDepartamentoPanel.getInstance();
		mostrarPanel.setParent(this);
	}

	@Override
	public void hidePanels() {
		altaPanel.setVisible(false);
		altaPanel.clear();
		bajaPanel.setVisible(false);
		bajaPanel.clear();
		mostrarPanel.setVisible(false);
		mostrarPanel.clear();
		modificarPanel.setVisible(false);
		modificarPanel.clear();
	listarPanel.setVisible(false);
		listarPanel.clear();
	}

	@Override
	public void showPanel(GeneralPanel panel) {
		hidePanels();
		contentPanel.add(panel);
		panel.setVisible(true);
	}
	
}