
package Presentacion.Gui.Panels.Empleado;

import javax.swing.table.AbstractTableModel;

import Negocio.Empleado.TEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;

import java.util.ArrayList;

public class ListarPorDepartamentoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames = { "ID", "Nombre", "DNI", "Telefono", "Sueldo", "Tipo" };
	private ArrayList<TEmpleado> empleados;
	
	public ListarPorDepartamentoTableModel(ArrayList<TEmpleado> empleados){	
		this.empleados = empleados;
	}
	
	public int getRowCount() {
		return empleados.size();
	}

	public int getColumnCount() {
		return this.columnNames.length;
	}
	
	public String getColumnName(int col) {
		return this.columnNames[col];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return empleados.get(rowIndex).getID();
		case 1:
			return empleados.get(rowIndex).getNombre();
		case 2:
			return empleados.get(rowIndex).getDNI();
		case 3:
			return empleados.get(rowIndex).getTelefono();
		case 4:
			return empleados.get(rowIndex).getSueldo();
		case 5:
			if (empleados.get(rowIndex) instanceof TEmpleadoCompleto) return "COMPLETO";
			else return "PARCIAL";
		}
		return null;
	}
	
	public void updateList(ArrayList<TEmpleado> empleados) {
		this.empleados = empleados;
	}
	
}