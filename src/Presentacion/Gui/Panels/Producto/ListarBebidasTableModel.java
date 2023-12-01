package Presentacion.Gui.Panels.Producto;

import javax.swing.table.AbstractTableModel;

import Negocio.Producto.TProductoBebida;

import java.util.ArrayList;
import java.util.List;

public class ListarBebidasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "ID", "Activo", "Nombre", "Precio actual", "Stock", "Tamano" };
	private List<TProductoBebida> productos;

	public ListarBebidasTableModel(ArrayList<TProductoBebida> productos) {
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

	public void updateList(ArrayList<TProductoBebida> productos) {
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
			return productos.get(rowIndex).getTamano();
		}
		return null;
	}
	
}