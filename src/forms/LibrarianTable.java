package forms;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import entities.Order;

@SuppressWarnings("serial")
public class LibrarianTable extends AbstractTableModel {

	private ArrayList<Order> allOrders;
	private String[] columnNames = { "Student", "Title", "Author" };

	public LibrarianTable(ArrayList<Order> order) {
		allOrders = order;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return allOrders.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
		case 0:
			return allOrders.get(arg0).getUser();
		case 1:
			return allOrders.get(arg0).getTitle();
		case 2:
			return allOrders.get(arg0).getAuthor();
		}
		return null;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}
}