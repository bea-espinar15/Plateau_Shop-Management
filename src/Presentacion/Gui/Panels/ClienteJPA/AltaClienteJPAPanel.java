
package Presentacion.Gui.Panels.ClienteJPA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class AltaClienteJPAPanel extends GeneralPanel {

	private static final long serialVersionUID = 1L;
	private static AltaClienteJPAPanel instance;
	private Panel_Main_ClienteJPA parent;
	private JTextField nomF, dniF;
	private Integer idClienteJPA;
	
	private AltaClienteJPAPanel(){
		initGUI();
	}
	
	private void initGUI() {
		
		this.setLayout(new BorderLayout());

		// __________ CABECERA _________
		JLabel titleLbl = new JLabel("ALTA CLIENTE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		titleLbl.setFont(f);
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setForeground(Color.DARK_GRAY);
		titleLbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(titleLbl, BorderLayout.PAGE_START);

		// ___________ CENTER ______________
		JPanel centerPanel = new JPanel(null);

		// ___________ CENTER - ALL FIELDS ______________
		JPanel nombrePanel = new JPanel(new FlowLayout());
		nombrePanel.setBounds(57, 55, 250, 30);
		JLabel nombreLbl = new JLabel("Nombre: ");
		this.nomF = new JTextField(10);
		nombrePanel.add(nombreLbl);
		nombrePanel.add(nomF);

		centerPanel.add(nombrePanel);

		JPanel dniPanel = new JPanel(new FlowLayout());
		dniPanel.setBounds(70, 95, 250, 30);
		JLabel dniLbl = new JLabel("DNI: ");
		this.dniF = new JTextField(10);
		dniPanel.add(dniLbl);
		dniPanel.add(dniF);

		centerPanel.add(dniPanel);
		
		JButton aceptarBtn = new JButton("Aceptar");
		aceptarBtn.setBounds(160, 170, 100, 30);
		aceptarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validation()) {
					TClienteJPA cliente = new TClienteJPA();
					cliente.setDNI(dniF.getText().toUpperCase());
					cliente.setNombre(nomF.getText());
					Context request = new Context(ContextEnum.ALTACLIENTEJPA, cliente);
					ApplicationController.getInstance().manageRequest(request);

				} else
					JOptionPane.showMessageDialog(parent, "Los datos introducidos son sintacticamente erroneos",
							"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		centerPanel.add(aceptarBtn);

		this.add(centerPanel, BorderLayout.CENTER);
	}

	public void setParent(Panel_Main_ClienteJPA parent) {
		this.parent = parent;
	}

	public static AltaClienteJPAPanel getInstance() {
		if (instance == null)instance = new AltaClienteJPAPanel();
		return instance;
	}

	public void update(Object o) {
		if (hasError(o)) {
			ErrorHandlerManagerJPA ehm = ErrorHandlerManagerJPA.getInstance();
			Message msg = ehm.getMessage(EntityEnumJPA.CLIENTEJPA,(EventEnum)o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			idClienteJPA = (Integer) o;
			String mensaje = "El cliente se ha dado de alta con exito con ID: " + idClienteJPA.toString();
			JOptionPane.showMessageDialog(parent, mensaje);
			clear();
		}
	}

	public void clear() {
		nomF.setText("");
		dniF.setText("");
	}

	@Override
	public boolean validation() {
		try {
			Pattern pat = Pattern.compile("[0-9]{8}[A-Za-z]");
			Matcher mat;
			if (nomF.getText().equals("")) 
				throw new Exception();
			mat = pat.matcher(dniF.getText());
			if(dniF.getText().equals("") || !mat.matches()) 
				throw new Exception();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
}