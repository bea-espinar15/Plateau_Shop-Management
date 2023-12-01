
package Presentacion.Gui.Panels.Empleado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Empleado.TEmpleadoParcial;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class AltaEmpleadoPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static AltaEmpleadoPanel instance;
	private Panel_Main_Empleado parent;
	
	private JTextField nomF, dniF, tlfF, idDptoF;
	private JComboBox<String> tipoEmpleado;
	private JButton aceptar;
	private JSpinner eurosPMS,horasES, horasP, eurosS;
	private JPanel Completo, Parcial;
	
	private AltaEmpleadoPanel(){
		initGUI();
	}
	
	private void initGUI() {
	
		this.setLayout(new BorderLayout());

			
		JLabel altaEmpleado = new JLabel("ALTA EMPLEADO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		altaEmpleado.setFont(f);
		altaEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
		altaEmpleado.setForeground(Color.DARK_GRAY);
		altaEmpleado.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(altaEmpleado, BorderLayout.PAGE_START);
		
		// DATOS (CENTRO)
		JPanel datos = new JPanel(new GridLayout(1,2));
		
		// DATOS COMUNES
		JPanel datosC = new JPanel(null);
		
		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(25, 40, 400, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomF = new JTextField(10);
		nombre.add(nom);
		nombre.add(nomF);
		
		// DNI
		JPanel dni = new JPanel(new FlowLayout());
		dni.setBounds(40, 80, 400, 30);
		JLabel dniL = new JLabel("DNI:");
		this.dniF = new JTextField(10);
		dni.add(dniL);
		dni.add(dniF);
		
		// TELEFONO
		JPanel tlf = new JPanel(new FlowLayout());
		tlf.setBounds(24, 120, 400, 30);
		JLabel tlfL = new JLabel("Telefono:");
		this.tlfF = new JTextField(10);
		tlf.add(tlfL);
		tlf.add(tlfF);		
		
		// ID DEPARTAMENTO
		JPanel idDept = new JPanel(new FlowLayout());
		idDept.setBounds(0, 160, 400, 30);
		JLabel idDeptLabel = new JLabel("ID Departamento:");
		this.idDptoF = new JTextField(10);
		idDept.add(idDeptLabel);
		idDept.add(idDptoF);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBounds(190, 255, 100, 30);
		aceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					if (tipoEmpleado.getSelectedItem() == "PARCIAL") {
						TEmpleadoParcial empleado = new TEmpleadoParcial();
						empleado.setNombre(nomF.getText());
						empleado.setDNI(dniF.getText().toUpperCase());
						empleado.setTelefono(Integer.parseInt(tlfF.getText()));
						empleado.setIDDpto(Integer.parseInt(idDptoF.getText()));
						empleado.setEurosPH((Integer) eurosS.getValue());
						empleado.setHoras((Integer) horasP.getValue());
						
						Context request = new Context(ContextEnum.ALTAEMPLEADO, empleado);
						ApplicationController.getInstance().manageRequest(request);
						
					} else if (tipoEmpleado.getSelectedItem() == "COMPLETO") {
						TEmpleadoCompleto empleado = new TEmpleadoCompleto();
						empleado.setNombre(nomF.getText());
						empleado.setDNI(dniF.getText().toUpperCase());
						empleado.setTelefono(Integer.parseInt(tlfF.getText()));
						empleado.setIDDpto(Integer.parseInt(idDptoF.getText()));
						empleado.setEurosPM((Integer) eurosPMS.getValue());
						empleado.setHorasExtra((Integer) horasES.getValue());

						Context request = new Context(ContextEnum.ALTAEMPLEADO, empleado);
						ApplicationController.getInstance().manageRequest(request);
						
					} else {
						JOptionPane.showMessageDialog(parent, "No se ha introducido el tipo del empleado", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introduccidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		datosC.add(nombre);
		datosC.add(dni);
		datosC.add(tlf);
		datosC.add(idDept);
		datosC.add(aceptar);

		datos.add(datosC);
		
		// DATOS ESPECIFICOS
		JPanel datosE = new JPanel(null);
		
		Completo = new JPanel();
		Completo.setBounds(7, 90, 325, 40);
		Completo.setVisible(false);
		
		// EUROS/MES EMPLEADO COMPLETO		
		JPanel eurosPM = new JPanel(new FlowLayout());
		JLabel eurosPML = new JLabel("Euros/mes:");
		SpinnerModel model = new SpinnerNumberModel(100, 100, 5000, 100);
		eurosPMS = new JSpinner(model);
		eurosPMS.setPreferredSize(new Dimension(45, 25));
		eurosPM.add(eurosPML);
		eurosPM.add(eurosPMS);

		Completo.add(eurosPM);
		
		//HORAS EXTRA EMPLEADO COMPLETO		
		JPanel horasE = new JPanel(new FlowLayout());
		JLabel horasExtraL = new JLabel("Horas Extra:");
		SpinnerModel model2 = new SpinnerNumberModel(0, 0, 50, 1);
		horasES = new JSpinner(model2);
		horasES.setPreferredSize(new Dimension(36, 25));
		horasE.add(horasExtraL);
		horasE.add(horasES);
		
		Completo.add(horasE);
		
		Parcial = new JPanel();
		Parcial.setBounds(7, 90, 200, 40);
		//Parcial.setBackground(Color.BLUE);
		Parcial.setVisible(false);
		
		//HORAS EMPLEADO PARCIAL
		JPanel horas = new JPanel(new FlowLayout());
		JLabel horasL = new JLabel("Horas:");
		SpinnerModel model3 = new SpinnerNumberModel(1, 1, 7, 1);
		horasP = new JSpinner(model3);
		horasP.setPreferredSize(new Dimension(36, 25));
		horas.add(horasL);
		horas.add(horasP);
		
		Parcial.add(horas);

		//EUROS/HORA 
		
		JPanel euros = new JPanel(new FlowLayout());
		JLabel eurosL = new JLabel("Euros/hora:");
		SpinnerModel model4 = new SpinnerNumberModel(1, 1, 10, 1);
		eurosS = new JSpinner(model4);
		eurosS.setPreferredSize(new Dimension(36, 25));
		horas.add(eurosL);
		horas.add(eurosS);
		
		Parcial.add(euros);
		
		//SELECCION DEL TIPO DE EMPLEADO
		
		JPanel tipos = new JPanel(new FlowLayout());
		tipos.setBounds(10, 35, 200, 50);
		JLabel tipo = new JLabel("Tipo:");
		tipoEmpleado = new JComboBox<String>();
		tipoEmpleado.addItem("COMPLETO");
		tipoEmpleado.addItem("PARCIAL");
		tipoEmpleado.setSelectedItem(null);
		tipoEmpleado.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tipoEmpleado.getSelectedItem() == null || tipoEmpleado.getSelectedItem().equals("PARCIAL")){
					Completo.setVisible(false);
					Parcial.setVisible(true);
				}
				else{
					Parcial.setVisible(false);
					Completo.setVisible(true);
				}
					
			}

		});
		tipos.add(tipo);
		tipos.add(tipoEmpleado);
		datosE.add(tipos);
		datosE.add(Completo);
		datosE.add(Parcial);
		datos.add(datosE);

		this.add(datos, BorderLayout.CENTER);
		
	}

	public static AltaEmpleadoPanel getInstance() {
		if (instance == null)
			instance = new AltaEmpleadoPanel();
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
		} else {
			JOptionPane.showMessageDialog(parent, "El empleado se ha creado con exito. ID: " + ((Integer)o).toString());
			clear();
		}
	}
	
	@Override
	public boolean validation() {
		try{
			Pattern pat = Pattern.compile("[0-9]{8}[A-Za-z]");
			Matcher mat;
			if (nomF.getText().equals("")) 
				throw new Exception();
			mat = pat.matcher(dniF.getText());
			if(dniF.getText().equals("") || !mat.matches()) 
				throw new Exception();
			if (tlfF.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(tlfF.getText()) < 100000000 || Integer.parseInt(tlfF.getText()) > 999999999)
				throw new Exception();
			if (idDptoF.getText().equals(""))
				throw new Exception();
			if (Integer.parseInt(idDptoF.getText()) < 0)
				throw new Exception();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void clear() {
		nomF.setText("");
		dniF.setText("");
		tlfF.setText("");
		idDptoF.setText("");
		eurosS.setValue(1);
		eurosPMS.setValue(100);
		horasES.setValue(0);
		horasP.setValue(1);
		tipoEmpleado.setSelectedItem(null);
		Completo.setVisible(false);
		Parcial.setVisible(false);
	}
	
}