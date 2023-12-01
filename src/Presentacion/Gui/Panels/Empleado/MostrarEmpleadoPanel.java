
package Presentacion.Gui.Panels.Empleado;

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

import Negocio.Empleado.TEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Empleado.TEmpleadoParcial;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class MostrarEmpleadoPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static MostrarEmpleadoPanel instance;	
	private Panel_Main_Empleado parent;	
	private String type;	
	private JTextField idF, actF, nomF, dniF, sueldoF, telF, idDptoF, typeF, completoF, parcialF, eurosPMF, horasEF, horasF, eurosHF;
	private JPanel typeEmpty, completo, parcial, eurosPM, horas_extra, horas, euros_hora;
	
	private MostrarEmpleadoPanel(){
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());

		// CABECERA

		JLabel mostrarEmpleado = new JLabel("MOSTRAR EMPLEADO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarEmpleado.setFont(f);
		mostrarEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarEmpleado.setForeground(Color.DARK_GRAY);
		mostrarEmpleado.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarEmpleado, BorderLayout.PAGE_START);

		// CENTER

		JPanel centerPanel = new JPanel(null);

		// ID
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
					Context request = new Context(ContextEnum.MOSTRAREMPLEADO, Integer.parseInt(idF.getText()));
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
		activo.setBounds(70, 110, 250, 30);
		JLabel act = new JLabel("Activo:");
		this.actF = new JTextField(10);
		actF.setEditable(false);
		activo.add(act);
		activo.add(actF);

		centerPanel.add(activo);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(65, 150, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomF = new JTextField(10);
		nomF.setEditable(false);
		nombre.add(nom);
		nombre.add(nomF);

		centerPanel.add(nombre);
		
		//DNI
		JPanel dni = new JPanel(new FlowLayout());
		dni.setBounds(80, 190, 250, 30);
		JLabel dniLabel = new JLabel("DNI:");
		this.dniF = new JTextField(10);
		dniF.setEditable(false);
		dni.add(dniLabel);
		dni.add(dniF);
		
		centerPanel.add(dni);
		
		//SUELDO
		JPanel sueldo = new JPanel(new FlowLayout());
		sueldo.setBounds(69, 230, 250, 30);
		JLabel sueldoLabel = new JLabel("Sueldo:");
		this.sueldoF = new JTextField(10);
		sueldoF.setEditable(false);
		sueldo.add(sueldoLabel);
		sueldo.add(sueldoF);

		centerPanel.add(sueldo);
		
		// TELEFONO
		JPanel telefono = new JPanel(new FlowLayout());
		telefono.setBounds(65, 270, 250, 30);
		JLabel telefonoLabel = new JLabel("Telefono:");
		this.telF = new JTextField(10);
		telF.setEditable(false);
		telefono.add(telefonoLabel);
		telefono.add(telF);

		centerPanel.add(telefono);
		
		// ID DEPARTAMENTO
		JPanel idDptoPanel = new JPanel(new FlowLayout());
		idDptoPanel.setBounds(68, 310, 250, 30);
		JLabel idDptoL = new JLabel("ID Dpto:");
		this.idDptoF = new JTextField(10);
		idDptoF.setEditable(false);
		idDptoPanel.add(idDptoL);
		idDptoPanel.add(idDptoF);

		centerPanel.add(idDptoPanel);
		
		// TIPO
		typeEmpty = new JPanel(new FlowLayout());
		typeEmpty.setBounds(349, 110, 250, 30);
		JLabel noType = new JLabel("Tipo:");
		typeF = new JTextField(10);
		typeF.setEditable(false);
		typeEmpty.add(noType);
		typeEmpty.add(typeF);

		centerPanel.add(typeEmpty);
		
		// COMPLETO
		completo = new JPanel(new FlowLayout());
		completo.setBounds(349, 110, 250, 30);
		JLabel completoL = new JLabel("Tipo:");
		completoF = new JTextField("COMPLETO", 10);
		completoF.setEditable(false);
		completo.add(completoL);
		completo.add(completoF);
		
		centerPanel.add(completo);
		
		// EUROS/MES (EMP. COMPLETO)
		eurosPM = new JPanel(new FlowLayout());
		eurosPM.setBounds(330, 150, 250, 30);
		JLabel eurosPML = new JLabel("Euros/mes:");
		this.eurosPMF = new JTextField(10);
		eurosPMF.setEditable(false);
		
		eurosPM.add(eurosPML);
		eurosPM.add(eurosPMF);
		
		centerPanel.add(eurosPM);
		
		
		// HORAS EXTRA (EMP.COMPLETO)
		horas_extra = new JPanel(new FlowLayout());
		horas_extra.setBounds(328, 190, 250, 30);
		JLabel horasEL = new JLabel("Horas Extra:");
		this.horasEF = new JTextField(10);
		horasEF.setEditable(false);
		
		horas_extra.add(horasEL);
		horas_extra.add(horasEF);
		
		centerPanel.add(horas_extra);
		
		
		// PARCIAL
		parcial = new JPanel(new FlowLayout());
		parcial.setBounds(349, 110, 250, 30);
		JLabel parcialLabel = new JLabel("Tipo:");
		parcialF = new JTextField("PARCIAL", 10);
		parcialF.setEditable(false);
		parcial.add(parcialLabel);
		parcial.add(parcialF);

		centerPanel.add(parcial);
		
		// HORAS (EMP.PARCIAL)
		horas = new JPanel(new FlowLayout());
		horas.setBounds(343, 150, 250, 30);
		JLabel horasL = new JLabel("Horas:");
		this.horasF = new JTextField(10);
		horasF.setEditable(false);
		horas.add(horasL);
		horas.add(horasF);

		centerPanel.add(horas);
		
		// EUROS/HORA (EMP.PARCIAL)
		euros_hora = new JPanel(new FlowLayout());
		euros_hora.setBounds(328, 190, 250, 30);
		JLabel eurosHL = new JLabel("Euros/hora:");
		this.eurosHF = new JTextField(10);
		eurosHF.setEditable(false);
		euros_hora.add(eurosHL);
		euros_hora.add(eurosHF);

		centerPanel.add(euros_hora);
		
		updatePanel(type);
		
		this.add(centerPanel, BorderLayout.CENTER);
	}

	public static MostrarEmpleadoPanel getInstance() {
		if (instance == null)
			instance = new MostrarEmpleadoPanel();
		return instance;
	}

	public void setParent(Panel_Main_Empleado parent) {
		this.parent = parent;
	}

	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.EMPLEADO, (EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		}else{
			parent.showPanel(this);
			if(o instanceof TEmpleadoParcial){
				this.type = "PARCIAL";
				updatePanel(type);
				this.horasF.setText(((TEmpleadoParcial)o).getHoras().toString());
				this.eurosHF.setText(((TEmpleadoParcial)o).getEurosPH().toString());
				this.parcialF.setText("PARCIAL");
			}else{
				this.type = "COMPLETO";
				updatePanel(type);
				this.eurosPMF.setText(((TEmpleadoCompleto)o).getEurosPM().toString());
				this.horasEF.setText(((TEmpleadoCompleto)o).getHorasExtra().toString());
				this.completoF.setText("COMPLETO");
			}
			
			this.idF.setText(((TEmpleado)o).getID().toString());
			this.nomF.setText(((TEmpleado)o).getNombre());
			this.dniF.setText(((TEmpleado)o).getDNI());
			this.sueldoF.setText(((TEmpleado)o).getSueldo().toString());
			this.telF.setText(((TEmpleado)o).getTelefono().toString());
			this.idDptoF.setText(((TEmpleado)o).getIDDpto().toString());
			Boolean act = ((TEmpleado)o).getActivo();
			if(act){
				actF.setText("SI");
			}else{
				actF.setText("NO");
			}
		}
	}

	public void clear() {
		idF.setText("");
		actF.setText("");
		nomF.setText("");
		dniF.setText("");
		sueldoF.setText("");
		telF.setText("");
		idDptoF.setText(""); 
		completoF.setText("");
		parcialF.setText("");
		eurosPMF.setText(""); 
		horasEF.setText(""); 
		horasF.setText(""); 
		eurosHF.setText("");
		updatePanel("");
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
	
	private void updatePanel(String type) {
		
		if (type == "") {
			completo.setVisible(false);
			eurosPM.setVisible(false);
			horas_extra.setVisible(false);
			
			parcial.setVisible(false);
			horas.setVisible(false);
			euros_hora.setVisible(false);
			
			typeEmpty.setVisible(true);
			
		} else if (type == "COMPLETO") {
			typeEmpty.setVisible(false);
			
			parcial.setVisible(false);
			horas.setVisible(false);
			euros_hora.setVisible(false);
			
			completo.setVisible(true);
			eurosPM.setVisible(true);
			horas_extra.setVisible(true);
			
		} else {
			typeEmpty.setVisible(false);
			
			completo.setVisible(false);
			eurosPM.setVisible(false);
			horas_extra.setVisible(false);
			
			parcial.setVisible(true);
			horas.setVisible(true);
			euros_hora.setVisible(true);
		}
	}
	
}