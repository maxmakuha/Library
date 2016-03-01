package forms;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class LibrarianForm extends JFrame {
	private JPanel mainPanel;
	private JTable allOrdersTable;
	private JScrollPane allOrdersPane;
	private JLabel label;
	private JButton delete;

	private void initPanel() {
		this.allOrdersTable = new JTable();
		this.allOrdersTable.setPreferredScrollableViewportSize(new Dimension(
				750, 100));
		this.allOrdersTable.setSize(800, 300);
		this.label = new JLabel("All orders");
		this.delete = new JButton("Delete");
		this.allOrdersPane = new JScrollPane(allOrdersTable);
		this.allOrdersPane.setPreferredSize(new Dimension(750, 200));
		this.allOrdersPane.setSize(800, 300);
		this.mainPanel = new JPanel();
		this.mainPanel.add(label);
		this.mainPanel.add(allOrdersPane);
		this.mainPanel.add(delete);
	}

	public LibrarianForm() {
		this.setSize(800, 300);
		this.setTitle("Librarian panel");
		initPanel();
		this.add(mainPanel);
	}

	public JTable getTable() {
		return this.allOrdersTable;
	}

	public JButton getDeleteButton() {
		return this.delete;
	}
}