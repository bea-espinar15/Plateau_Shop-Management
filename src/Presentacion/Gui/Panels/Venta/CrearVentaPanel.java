
// IMPORTANTE: Las vistas no se redireccionan entre ellas a través del controlador porque son JPanels

package Presentacion.Gui.Panels.Venta;

import java.awt.BorderLayout;
import java.util.HashMap;

import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Presentacion.Gui.Panels.GeneralPanel;


public class CrearVentaPanel extends GeneralPanel {
	
	private static final long serialVersionUID = 1L;

	
	private static CrearVentaPanel instance;	
	private Panel_Main_Venta parent;	
	private CerrarVentaPanel cerrarVentaPanel;	
	private AbrirVentaPanel abrirVentaPanel;
	
	private CrearVentaPanel(){
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		initPanels();
		showAbrirPanel();
	}

	public void showCerrarPanel(TVenta venta, HashMap<Integer,TLineaVenta>carrito){
		abrirVentaPanel.setVisible(false);
		cerrarVentaPanel.setTVenta(venta);
		cerrarVentaPanel.setCarrito(carrito);
		this.add(cerrarVentaPanel, BorderLayout.CENTER);
		cerrarVentaPanel.setVisible(true);
		cerrarVentaPanel.clear();
	}
	
	public void showAbrirPanel(){
		cerrarVentaPanel.setVisible(false);
		this.add(abrirVentaPanel, BorderLayout.CENTER);
		abrirVentaPanel.setVisible(true);
		abrirVentaPanel.clear();
	}
	
	private void initPanels(){
		abrirVentaPanel = AbrirVentaPanel.getInstance();
		abrirVentaPanel.setParent(this);
		cerrarVentaPanel = CerrarVentaPanel.getInstance();
		cerrarVentaPanel.setParent(this);
	}
	
	public static CrearVentaPanel getInstance() {
		if (instance == null)
			instance = new CrearVentaPanel();
		return instance;
	}
	
	public void setParent(Panel_Main_Venta parent) {
		this.parent=parent;
	}

	public void update(Object o) {}
	
	public void clear() {
		abrirVentaPanel.setVisible(true);
		abrirVentaPanel.clear();
		cerrarVentaPanel.setVisible(false);
		cerrarVentaPanel.clear();
	}

	@Override
	public boolean validation() {
		return true;
	}
	
}