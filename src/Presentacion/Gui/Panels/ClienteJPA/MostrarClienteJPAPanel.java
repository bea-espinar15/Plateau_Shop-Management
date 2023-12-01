
package Presentacion.Gui.Panels.ClienteJPA;

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

import Negocio.ClienteJPA.TClienteJPA;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Context;
import Presentacion.Gui.ErrorHandler.EntityEnumJPA;
import Presentacion.Gui.ErrorHandler.ErrorHandlerManagerJPA;
import Presentacion.Gui.ErrorHandler.Message;
import Presentacion.Gui.Panels.GeneralPanel;

public class MostrarClienteJPAPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;
	private static MostrarClienteJPAPanel instance;
	private Panel_Main_ClienteJPA parent;
	private JTextField idF, actF, nomF, dniF;
	
	private MostrarClienteJPAPanel(){
		initGUI();
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());

		// CABECERA
		JLabel mostrarClienteJPA = new JLabel("MOSTRAR CLIENTE");
		Font f = new Font("Monospaced", Font.ITALIC, 40);
		mostrarClienteJPA.setFont(f);
		mostrarClienteJPA.setHorizontalAlignment(SwingConstants.CENTER);
		mostrarClienteJPA.setForeground(Color.DARK_GRAY);
		mostrarClienteJPA.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		this.add(mostrarClienteJPA, BorderLayout.PAGE_START);

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
					Context request = new Context(ContextEnum.MOSTRARCLIENTEJPA, Integer.parseInt(idF.getText()));
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
		activo.setBounds(47, 110, 250, 30);
		JLabel act = new JLabel("Activo:");
		this.actF = new JTextField(10);
		actF.setEditable(false);
		activo.add(act);
		activo.add(actF);

		centerPanel.add(activo);

		// NOMBRE
		JPanel nombre = new JPanel(new FlowLayout());
		nombre.setBounds(42, 150, 250, 30);
		JLabel nom = new JLabel("Nombre:");
		this.nomF = new JTextField(10);
		nomF.setEditable(false);
		nombre.add(nom);
		nombre.add(nomF);

		centerPanel.add(nombre);

		// DNI
		JPanel dni = new JPanel(new FlowLayout());
		dni.setBounds(54, 190, 250, 30);
		JLabel dniLabel = new JLabel("DNI:");
		this.dniF = new JTextField(10);
		dniF.setEditable(false);
		dni.add(dniLabel);
		dni.add(dniF);

		centerPanel.add(dni);
		
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public static MostrarClienteJPAPanel getInstance() {
		if (instance == null)
			instance = new MostrarClienteJPAPanel();
		return instance;
	}
	
	public void setParent(Panel_Main_ClienteJPA parent) {
		this.parent = parent;
	}
	
	public void update(Object o) {
		if (hasError(o)) {
			Message msg = ErrorHandlerManagerJPA.getInstance().getMessage(EntityEnumJPA.CLIENTEJPA,(EventEnum) o);
			JOptionPane.showMessageDialog(parent, msg.getText(), msg.getTitle(), JOptionPane.ERROR_MESSAGE);
			clear();
		} else {
			this.idF.setText(((TClienteJPA) o).getID().toString());
			this.dniF.setText(((TClienteJPA) o).getDNI());
			this.nomF.setText(((TClienteJPA) o).getNombre());
			Boolean act = ((TClienteJPA) o).getActivo();
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
		dniF.setText("");
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
	
}