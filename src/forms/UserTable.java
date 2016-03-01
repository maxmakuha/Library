package forms;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import entities.Book;

@SuppressWarnings("serial")
public class UserTable extends AbstractTableModel {

	private ArrayList<Book> allBooks;
	private String[] columnNames = { "Title", "Author", "Description" };

	public UserTable(ArrayList<Book> book) {
		allBooks = book;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return allBooks.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
		case 0:
			return allBooks.get(arg0).getTitle();
		case 1:
			return allBooks.get(arg0).getAuthor();
		case 2:
			return allBooks.get(arg0).getDescription();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}