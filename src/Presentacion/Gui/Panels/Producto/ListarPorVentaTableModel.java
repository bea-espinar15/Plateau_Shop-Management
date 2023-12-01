
package Presentacion.Gui.Panels.Producto;

import javax.swing.table.AbstractTableModel;

import Negocio.Producto.TProducto;
import Negocio.Producto.TProductoBebida;

import java.util.ArrayList;
import java.util.List;

public class ListarPorVentaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "ID", "Activo", "Nombre", "Precio actual", "Stock", "Tipo" };
	private List<TProducto> productos;

	public ListarPorVentaTableModel(ArrayList<TProducto> productos) {
		this.productos = productos;
	}

	public int getRowCount() {
		return productos.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public void updateList(ArrayList<TProducto> productos) {
		this.productos = productos;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return productos.get(rowIndex).getID();
		case 1:
			if (productos.get(rowIndex).getActivo())
				return "SI";
			else
				return "NO";
		case 2:
			return productos.get(rowIndex).getNombre();
		case 3:
			return productos.get(rowIndex).getPrecioActual();
		case 4:
			return productos.get(rowIndex).getStock();
		case 5:
			if (productos.get(rowIndex) instanceof TProductoBebida)
				return "BEBIDA";
			else
				return "COMIDA";
		}
		return null;
	}
	
}