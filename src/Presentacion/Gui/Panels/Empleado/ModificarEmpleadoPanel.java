
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
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


public class ModificarEmpleadoPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static ModificarEmpleadoPanel instance;	
	private Panel_Main_Empleado parent;	
	private JButton ok, mod;	
	private JTextField idF, nomF, idDptoF, dniF, telF;	
	private JSpinner eurosPMS, horasES, horasP, eurosS;	
	private JPanel Completo, Parcial;	
	private Boolean buscado;
	private static Boolean selected = false;
	
	private ModificarEmpleadoPanel(){
		buscado = false;
		initGUI();
	}

	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		// CABECERA
		JLabel modificarEmpleado = new JLabel("MODIFICAR EMPLEADO");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		modificarEmpleado.setFont(f);
		modificarEmpleado.setHorizontalAlignment(SwingConstants.CENTER);
		modificarEmpleado.setForeground(Color.DARK_GRAY);
		modificarEmpleado.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(modificarEmpleado, BorderLayout.PAGE_START);

		// ______ DATOS (CENTRE) _______
		JPanel datos = new JPanel(new GridLayout(1, 2));

		// _______ DATOS COMUNES ________
		JPanel datosC = new JPanel(null);

		JPanel idPanel = new JPanel(new FlowLayout());
		idPanel.setBackground(Color.LIGHT_GRAY);
		idPanel.setBounds(100, 35, 160, 30);
		JLabel idLabel = new JLabel("ID:");
		this.idF = new JTextField(10);
		idPanel.add(idLabel);
		idPanel.add(idF);
		
		datosC.add(idPanel);
		
		// ACEPTAR ID
		ok = new JButton("Buscar");
		ok.setBounds(270, 35, 78, 30);
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					ModificarEmpleadoPanel.selected = true;
					Context request = new Context(ContextEnum.MOSTRAREMPLEADO, Integer.parseInt(idF.getText()));
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		
		datosC.add(ok);
		
		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(2, 110, 400, 30);
		JLabel nomL = new JLabel("Nombre:");
		nomF = new JTextField(10);
		nomF.setEditable(false);
		nombre.add(nomL);
		nombre.add(nomF);
		
		datosC.add(nombre);
		
		// DNI
		JPanel dni = new JPanel(new FlowLayout());
		dni.setBounds(14, 150, 400, 30);
		JLabel dniL = new JLabel("DNI:");
		dniF = new JTextField(10);
		dniF.setEditable(false);
		dni.add(dniL);
		dni.add(dniF);
		
		datosC.add(dni);
		
		// ID DEPARTAMENTO
		JPanel idDept = new JPanel(new FlowLayout());
		idDept.setBounds(4, 190, 400, 30);
		JLabel idDeptLabel = new JLabel("ID Dpto:");
		idDptoF = new JTextField(10);
		idDptoF.setEditable(false);
		idDept.add(idDeptLabel);
		idDept.add(idDptoF);
		
		datosC.add(idDept);
		
		// TELEFONO
		JPanel telefono = new JPanel(new FlowLayout());
		telefono.setBounds(0, 230, 400, 30);
		JLabel telfL = new JLabel("Telefono:");
		telF = new JTextField(10);
		telF.setEditable(false);
		telefono.add(telfL);
		telefono.add(telF);
		
		datosC.add(telefono);
		
		
		// DATOS ESPECIFICOS
		JPanel datosE = new JPanel(null);
		
		// EMP. COMPLETO
		Completo = new JPanel();
		Completo.setBounds(7, 110, 300, 40);
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
		
		datosE.add(Completo);
		
		// EMP. PARCIAL
		Parcial = new JPanel();
		Parcial.setBounds(7, 110, 200, 40);
		Parcial.setVisible(false);
		
		// HORAS EMPLEADO PARCIAL
		JPanel horas = new JPanel(new FlowLayout());
		JLabel horasL = new JLabel("Horas:");
		SpinnerModel model3 = new SpinnerNumberModel(1, 1, 7, 1);
		horasP = new JSpinner(model3);
		horasP.setPreferredSize(new Dimension(36, 25));
		horas.add(horasL);
		horas.add(horasP);
				
		Parcial.add(horas);

		// EUROS/HORA EMPLEADO PARCIAL
				
		JPanel euros = new JPanel(new FlowLayout());
		JLabel eurosL = new JLabel("Euros/hora:");
		SpinnerModel model4 = new SpinnerNumberModel(1, 1, 10, 1);
		eurosS = new JSpinner(model4);
		eurosS.setPreferredSize(new Dimension(36, 25));
		horas.add(eurosL);
		horas.add(eurosS);
				
		Parcial.add(euros);
		
		datosE.add(Parcial);
		
		mod = new JButton("Modificar");
		mod.setEnabled(false);
		mod.setBounds(180, 295, 100, 30);
		mod.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (validation()) {
					Context request;
					if(Completo.isVisible()){
						TEmpleadoCompleto completo = new TEmpleadoCompleto();
						completo.setID(Integer.parseInt(idF.getText()));
						completo.setNombre(nomF.getText());
						completo.setDNI(dniF.getText());
						completo.setIDDpto(Integer.parseInt(idDptoF.getText()));
						completo.setTelefono(Integer.parseInt(telF.getText()));
						completo.setEurosPM((Integer) eurosPMS.getValue());
						completo.setHorasExtra((Integer) horasES.getValue());
						
						request = new Context(ContextEnum.MODIFICAREMPLEADO, completo);
						
					}else{
						TEmpleadoParcial parcial = new TEmpleadoParcial();
						parcial.setID(Integer.parseInt(idF.getText()));
						parcial.setNombre(nomF.getText());
						parcial.setDNI(dniF.getText());
						parcial.setIDDpto(Integer.parseInt(idDptoF.getText()));
						parcial.setTelefono(Integer.parseInt(telF.getText()));
						parcial.setHoras((Integer) horasP.getValue());
						parcial.setEurosPH((Integer) eurosS.getValue());
						
						request = new Context(ContextEnum.MODIFICAREMPLEADO, parcial);
					}
					ApplicationController.getInstance().manageRequest(request);
				} else {
					JOptionPane.showMessageDialog(parent, "Los datos introduccidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		datosC.add(mod);
		
		
		datos.add(datosC);
		datos.add(datosE);
		
		this.add(datos, BorderLayout.CENTER);
	}

	
	public static ModificarEmpleadoPanel getInstance() {
		if(instance == null){
			instance = new ModificarEmpleadoPanel();
		}
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
		}
		else {
			if (buscado) {
				JOptionPane.showMessageDialog(parent, "El empleado se ha modificado con exito.");
				clear();
			}
			else {
				nomF.setText(((TEmpleado) o).getNombre());
				idDptoF.setText(((TEmpleado)o).getIDDpto().toString());
				dniF.setText(((TEmpleado)o).getDNI());
				telF.setText(((TEmpleado)o).getTelefono().toString());
				
				if (o instanceof TEmpleadoParcial) {
					Parcial.setVisible(true);
					horasP.setValue(((TEmpleadoParcial)o).getHoras()); 
					eurosS.setValue(((TEmpleadoParcial)o).getEurosPH());
					
					horasP.setEnabled(true);
					eurosS.setEnabled(true);
				} else {
					Completo.setVisible(true);
					eurosPMS.setValue(((TEmpleadoCompleto)o).getEurosPM()); 
					horasES.setValue(((TEmpleadoCompleto)o).getHorasExtra());
					
					eurosPMS.setEnabled(true);
					horasES.setEnabled(true);
				}

				idF.setEditable(false);
				nomF.setEditable(true);
				idDptoF.setEditable(true);
				dniF.setEditable(true);
				telF.setEditable(true);
				
				mod.setEnabled(true);
				ok.setEnabled(false);
				
				buscado = true;
			}
		}
	}

	
	public void clear() {
		idF.setText("");
		nomF.setText("");
		idDptoF.setText("");
		dniF.setText("");
		telF.setText("");
		
		eurosPMS.setValue(100); 
		horasES.setValue(0);
		
		horasP.setValue(1); 
		eurosS.setValue(1);
		
		idF.setEditable(true);
		ok.setEnabled(true);
		
		nomF.setEditable(false);
		idDptoF.setEditable(false);
		dniF.setEditable(false);
		telF.setEditable(false);
		mod.setEnabled(false);
		eurosPMS.setEnabled(false);
		horasES.setEnabled(false);
		horasP.setEnabled(false);
		eurosS.setEnabled(false);
		
		Parcial.setVisible(false);
		Completo.setVisible(false);
		buscado = false;
		ModificarEmpleadoPanel.selected = false;
	}

	@Override
	public boolean validation() {
		try {
			if (!buscado) {
				if (idF.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idF.getText()) <= 0) {
					throw new Exception();
				}
				return true;
			} else {
				Pattern pat = Pattern.compile("[0-9]{8}[A-Za-z]");
				Matcher mat;
				if (nomF.getText().equals("")) 
					throw new Exception();
				mat = pat.matcher(dniF.getText());
				if(dniF.getText().equals("") || !mat.matches()) 
					throw new Exception();
				if (telF.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(telF.getText()) < 100000000 || Integer.parseInt(telF.getText()) > 999999999)
					throw new Exception();
				if (idDptoF.getText().equals(""))
					throw new Exception();
				if (Integer.parseInt(idDptoF.getText()) < 0)
					throw new Exception();
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean isSelected() {
		return ModificarEmpleadoPanel.selected;
	}
	
}