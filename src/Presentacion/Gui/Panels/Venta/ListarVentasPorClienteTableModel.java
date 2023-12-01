
package Presentacion.Gui.Panels.Venta;

import javax.swing.table.AbstractTableModel;

import Negocio.Venta.TVenta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ListarVentasPorClienteTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"ID", "Fecha", "Precio total", "Metodo pago", "ID Empleado" };
	private ArrayList<TVenta> ventas;
	
	public ListarVentasPorClienteTableModel(ArrayList<TVenta> aux) {
		this.ventas = aux;
	}

	@Override
	public int getRowCount() {
		return ventas.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int name) {
		return this.columnNames[name];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ventas.get(rowIndex).getID();
		case 1:
			return new SimpleDateFormat("dd-MM-yyyy").format( ventas.get(rowIndex).getFecha());
		case 2:
			return ventas.get(rowIndex).getPrecioTotal();
		case 3:
			return ventas.get(rowIndex).getMetodoPago();
		case 4:
			return ventas.get(rowIndex).getIDEmpleado();
		}
		return null;
	}

	public void updateList(ArrayList<TVenta> ventas) {
		this.ventas = ventas;
	}

}