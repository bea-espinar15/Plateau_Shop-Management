
package Presentacion.Gui.Panels.Venta;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Negocio.Producto.TProducto;
import Negocio.Producto.TProductoComida;
import Negocio.Venta.TLineaVenta;

public class MostrarVentaEnDetalleTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"ID Producto", "Nombre", "Stock", "Precio actual", "Tipo", "Uds", "Precio"};
	private ArrayList<TProducto> productos;
	private ArrayList<TLineaVenta> lineasVentas;

	public MostrarVentaEnDetalleTableModel(ArrayList<TLineaVenta> lineasVenta,ArrayList<TProducto> productos) {
		this.lineasVentas = lineasVenta;
		this.productos = productos;
	}

	@Override
	public int getRowCount() {
		return lineasVentas.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	public String getColumnName(int name) {
		return this.columnNames[name];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return productos.get(rowIndex).getID();
		case 1:
			return productos.get(rowIndex).getNombre();
		case 2:
			return productos.get(rowIndex).getStock();
		case 3:
			return productos.get(rowIndex).getPrecioActual(); 
		case 4:
			if (productos.get(rowIndex) instanceof TProductoComida) return "COMIDA";
			else return "BEBIDA";
		case 5:
			return lineasVentas.get(rowIndex).getUds();
		case 6:
			return lineasVentas.get(rowIndex).getPrecio();
		}
		return null;
	}

	public void updateList(ArrayList<TLineaVenta> lineasVenta,ArrayList<TProducto> productos) {
		this.lineasVentas = lineasVenta;
		this.productos = productos;
	}
	
}
