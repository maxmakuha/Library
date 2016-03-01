package forms;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class UserForm extends JFrame {

	private JPanel allBooksPanel;
	private JPanel orderedBooksPanel;
	private JTable allBooksTable;
	private JTable orderedBooksTable;
	private JScrollPane allBooksPane;
	private JScrollPane orderedBooksPane;
	private JButton makeOrder;
	private JTabbedPane tabbedPane;

	public UserForm(String user) {
		this.setSize(950, 300);
		this.setTitle("User page: " + user);

		allBooks();
		orderedBooks();

		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(800, 300);
		tabbedPane.addTab("All books", allBooksPanel);
		tabbedPane.addTab("My orders", orderedBooksPanel);
		this.add(tabbedPane);
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				allBooksTable.clearSelection();
				orderedBooksTable.clearSelection();
			}
		});
	}

	private void orderedBooks() {
		allBooksTable = new JTable();
		allBooksTable
				.setPreferredScrollableViewportSize(new Dimension(700, 100));
		allBooksTable.setSize(800, 300);

		allBooksPane = new JScrollPane(allBooksTable);
		allBooksPane.setPreferredSize(new Dimension(700, 100));
		allBooksPane.setSize(800, 300);

		makeOrder = new JButton("Make order");

		allBooksPanel = new JPanel();
		allBooksPanel.setSize(800, 400);
		allBooksPanel.add(allBooksPane);
		allBooksPanel.add(makeOrder);
	}

	private void allBooks() {

		orderedBooksTable = new JTable();
		orderedBooksTable.setPreferredScrollableViewportSize(new Dimension(700,
				100));
		orderedBooksTable.setSize(800, 300);
		orderedBooksPane = new JScrollPane(orderedBooksTable);
		orderedBooksPane.setPreferredSize(new Dimension(700, 100));
		orderedBooksPane.setSize(800, 300);
		orderedBooksPanel = new JPanel();
		orderedBooksPanel.add(orderedBooksPane);
	}

	public JTable getAllBooksTable() {
		return this.allBooksTable;
	}

	public JTable getOrderedBooksTable() {
		return this.orderedBooksTable;
	}

	public JButton getMakeOrderButton() {
		return this.makeOrder;
	}
}