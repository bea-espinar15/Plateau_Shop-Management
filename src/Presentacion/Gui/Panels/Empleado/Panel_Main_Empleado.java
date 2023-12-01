
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Empleado;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Presentacion.Gui.Panels.GeneralMainPanel;
import Presentacion.Gui.Panels.GeneralPanel;
import Presentacion.Gui.Panels.Empleado.AltaEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.BajaEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosCompletoPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosParcialPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosPanel;
import Presentacion.Gui.Panels.Empleado.ModificarEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.MostrarEmpleadoPanel;

public class Panel_Main_Empleado extends GeneralMainPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JButton alta;
	private JButton baja;
	private JButton mostrar;
	private JButton modificar;
	private JButton listar;
	private JButton listarCompleto;
	private JButton listarParcial;
	private JButton listarPorDepartamento;
	
	private JPanel contentPanel;
	
	private AltaEmpleadoPanel altaPanel;
	private BajaEmpleadoPanel bajaPanel;
	private MostrarEmpleadoPanel mostrarPanel;
	private ModificarEmpleadoPanel modificarPanel;
	private ListarEmpleadosPanel listarPanel;
	private ListarEmpleadosCompletoPanel listarEmpleadoCompletoPanel;
	private ListarEmpleadosParcialPanel listarEmpleadoParcialPanel;
	private ListarPorDepartamentoPanel listarPorDepartamentoPanel;
	
	
	public Panel_Main_Empleado() {
		initGUI();
	}

	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		initPanels();
		
		// __________ CASOS DE USO BUTTONS (WEST)_____________
		
		JPanel buttonsPanel = new JPanel(new GridLayout(8, 1));
		
		// ALTA
		alta = new JButton("Alta");
		alta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(altaPanel);
			}

		});
		buttonsPanel.add(alta);
		
		//BAJA
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
		
		// LISTAR EMPLEADO PARCIAL
		listarParcial = new JButton("Listar Parcial");
		listarParcial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarEmpleadoParcialPanel);
			}

		});
		buttonsPanel.add(listarParcial);

		// LISTAR EMPLEADO COMPLETO
		listarCompleto = new JButton("Listar Completo");
		listarCompleto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarEmpleadoCompletoPanel);
			}

		});
		buttonsPanel.add(listarCompleto);
		
		// LISTAR EMPLEADO POR DEPARTAMENTO
		listarPorDepartamento = new JButton("Listar por Departamento");
		listarPorDepartamento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showPanel(listarPorDepartamentoPanel);
			}

		});
		buttonsPanel.add(listarPorDepartamento);

		this.add(buttonsPanel, BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
	}


	public void initPanels() {
		
		contentPanel = new JPanel(new BorderLayout());
		
		altaPanel = AltaEmpleadoPanel.getInstance();
		altaPanel.setParent(this);
		
		bajaPanel = BajaEmpleadoPanel.getInstance();
		bajaPanel.setParent(this);
		
		mostrarPanel = MostrarEmpleadoPanel.getInstance();
		mostrarPanel.setParent(this);
		
		modificarPanel = ModificarEmpleadoPanel.getInstance();
		modificarPanel.setParent(this);
		
		listarPanel = ListarEmpleadosPanel.getInstance();
		listarPanel.setParent(this);
		
		listarEmpleadoCompletoPanel = ListarEmpleadosCompletoPanel.getInstance();
		listarEmpleadoCompletoPanel.setParent(this);
		
		listarEmpleadoParcialPanel = ListarEmpleadosParcialPanel.getInstance();
		listarEmpleadoParcialPanel.setParent(this);
		
		listarPorDepartamentoPanel = ListarPorDepartamentoPanel.getInstance();
		listarPorDepartamentoPanel.setParent(this);
	}


	public void hidePanels() {
		
		altaPanel.setVisible(false);
		altaPanel.clear();
		
		bajaPanel.setVisible(false);
		bajaPanel.clear();
		
		mostrarPanel.setVisible(false);
		mostrarPanel.clear();
		
		listarPanel.setVisible(false);
		listarPanel.clear();
		
		modificarPanel.setVisible(false);
		modificarPanel.clear();
		
		listarEmpleadoCompletoPanel.setVisible(false);
		listarEmpleadoCompletoPanel.clear();
		
		listarEmpleadoParcialPanel.setVisible(false);
		listarEmpleadoParcialPanel.clear();
		
		listarPorDepartamentoPanel.setVisible(false);
		listarPorDepartamentoPanel.clear();
	}
	

	public void showPanel(GeneralPanel panel) {
	
		hidePanels();
		contentPanel.add(panel);
		panel.setVisible(true);
		
	}
}