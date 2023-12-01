
package Presentacion.Gui.Panels.Empleado;

import javax.swing.table.AbstractTableModel;

import Negocio.Empleado.TEmpleadoCompleto;

import java.util.ArrayList;
import java.util.List;

public class ListarEmpleadosCompletoTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"ID", "Activo", "Nombre", "DNI" ,"Telefono","Sueldo", "ID Dpto", "Euros/mes", "HorasExtra"};
	private List<TEmpleadoCompleto> empleados;

	public ListarEmpleadosCompletoTableModel(ArrayList<TEmpleadoCompleto> empleados) {
		this.empleados = empleados;		
	}
	
	public int getRowCount() {
		return empleados.size();
	}
	
	public int getColumnCount() {	
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return empleados.get(rowIndex).getID();
		case 1:
			if (empleados.get(rowIndex).getActivo())
				return "SI";
			else
				return "NO";
		case 2:
			return empleados.get(rowIndex).getNombre();
		case 3:
			return empleados.get(rowIndex).getDNI();
		case 4:
			return empleados.get(rowIndex).getTelefono();
		case 5:
			return empleados.get(rowIndex).getSueldo();
		case 6:
			return empleados.get(rowIndex).getIDDpto();
		case 7:
			return empleados.get(rowIndex).getEurosPM();
		case 8:
			return empleados.get(rowIndex).getHorasExtra();
		}
		return null;
	}
	
	public void updateList(ArrayList<TEmpleadoCompleto> empleados) {
		this.empleados = empleados;
	}
	
}