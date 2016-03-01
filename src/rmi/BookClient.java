package rmi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.naming.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import forms.LibrarianTable;
import forms.LibrarianForm;
import forms.LoginForm;
import forms.UserForm;
import forms.UserTable;
import entities.Book;
import entities.Order;
import entities.User;

import java.util.ArrayList;

public class BookClient {

	private static UserForm userForm;
	private static LibrarianForm librarianForm;
	private static LoginForm loginForm;
	private static int userId;
	private static String userRole;
	private static ArrayList<Book> books;
	private static ArrayList<Book> orders;
	private static ArrayList<User> users;
	private static ArrayList<Order> allOrders;

	public static final String SERVER_NAME = "Server";

	public static void main(String[] args) throws RemoteException,
			NamingException, MalformedURLException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost", 8888);
		final BookService bookServ = (BookService) registry.lookup(SERVER_NAME);
		bookServ.createConnection();

		loginForm = new LoginForm();
		loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginForm.setVisible(true);
		loginForm.getButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String login = loginForm.getLoginField().getText();
				@SuppressWarnings("deprecation")
				String password = loginForm.getPasswordField().getText();
				try {
					BookClient.users = bookServ.getUsers();
				} catch (RemoteException e3) {
					e3.printStackTrace();
				}
				userId = getUserId(users, login, password);
				userRole = getUserRole(users, login, password);
				if (userRole == null)
					JOptionPane.showMessageDialog(null,
							"Incorrect log³n or password", "",
							JOptionPane.INFORMATION_MESSAGE);
				else if (userRole.equals("User")
						&& loginForm.getRole().getSelectedItem().equals("User")) {
					loginForm.setVisible(false);
					userForm = new UserForm(login);
					userForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					userForm.setVisible(true);

					try {
						BookClient.books = bookServ.getBooks();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					userForm.getAllBooksTable().setModel(
							new UserTable(BookClient.books));

					try {
						BookClient.orders = bookServ.getBooksOfUser(userId);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					userForm.getOrderedBooksTable().setModel(
							new UserTable(BookClient.orders));
					userForm.getMakeOrderButton().addActionListener(
							new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									int[] rows = userForm.getAllBooksTable()
											.getSelectedRows();
									if (rows.length > 0) {
										String str = "" + userId;
										for (int i = 0; i < rows.length; i++) {
											str += " "
													+ BookClient.books.get(
															rows[i])
															.getBookId();
										}
										try {
											bookServ.makeOrder(str.split(" "));
										} catch (RemoteException e1) {
											e1.printStackTrace();
										}
									}

									try {
										BookClient.orders = bookServ
												.getBooksOfUser(userId);
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
									userForm.getOrderedBooksTable().setModel(
											new UserTable(BookClient.orders));
								}
							});
				} else if (userRole.equals("Librarian")
						&& loginForm.getRole().getSelectedItem()
								.equals("Librarian")) {
					loginForm.setVisible(false);
					librarianForm = new LibrarianForm();
					librarianForm
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					librarianForm.setVisible(true);

					try {
						BookClient.allOrders = bookServ.getOrders();
					} catch (RemoteException e2) {
						e2.printStackTrace();
					}
					librarianForm.getTable().setModel(
							new LibrarianTable(allOrders));
					librarianForm.getDeleteButton().addActionListener(
							new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									int[] rows = librarianForm.getTable()
											.getSelectedRows();
									if (rows.length > 0) {
										String str = "";
										for (int i = 0; i < rows.length; i++) {
											str += allOrders.get(rows[i])
													.getOrderId() + " ";
										}
										try {
											BookClient.allOrders = bookServ
													.deleteOrders(str
															.split(" "));
										} catch (RemoteException e1) {
											e1.printStackTrace();
										}
										librarianForm.getTable().setModel(
												new LibrarianTable(allOrders));
									}
								}
							});
				} else {
					JOptionPane.showMessageDialog(null,
							"Incorrect log³n or password", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private static int getUserId(ArrayList<User> users, String login,
			String password) {
		for (User u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getUserId();
		}
		return -1;
	}

	private static String getUserRole(ArrayList<User> users, String login,
			String password) {
		for (User u : users) {
			if (u.getName().equals(login) && u.getPassword().equals(password))
				return u.getRole();
		}
		return null;
	}
}