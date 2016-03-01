package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import entities.Book;
import entities.Order;
import entities.User;
import forms.LibrarianTable;
import forms.LibrarianForm;
import forms.LoginForm;
import forms.UserForm;
import forms.UserTable;

public class Client {

	private static LoginForm loginForm;
	private static LibrarianForm librarianForm;
	private static UserForm userForm;
	private static PrintWriter pw;
	private static ObjectInputStream ois;
	private static Socket socket;

	private static int userId;
	private static String userRole;
	private static ArrayList<Book> books;
	private static ArrayList<User> users;
	private static ArrayList<Book> orders;
	private static ArrayList<Order> allOrders;

	public static void main(String[] args) {
		try {
			socket = new Socket(args[0], Integer.parseInt(args[1]));
			pw = new PrintWriter(socket.getOutputStream(), true);
			ois = new ObjectInputStream(socket.getInputStream());

			loginForm = new LoginForm();
			loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			loginForm.setVisible(true);
			loginForm.getButton().addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(ActionEvent e) {
					String login = loginForm.getLoginField().getText();
					@SuppressWarnings("deprecation")
					String password = loginForm.getPasswordField().getText();
					pw.println("getUsers");

					try {
						users = (ArrayList<User>) ois.readObject();
						userId = getUserId(users, login, password);
						userRole = getUserRole(users, login, password);
						if (userRole == null)
							JOptionPane.showMessageDialog(null,
									"Invalid username or password", "",
									JOptionPane.INFORMATION_MESSAGE);
						else if (userRole.equals("User")
								&& loginForm.getRole().getSelectedItem()
										.equals("User")) {
							loginForm.setVisible(false);
							userForm = new UserForm(login);
							userForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							userForm.setVisible(true);

							pw.println("getBooks");
							Client.books = (ArrayList<Book>) ois.readObject();
							userForm.getAllBooksTable().setModel(
									new UserTable(Client.books));
							pw.println("getUserOrders" + userId);
							Client.orders = (ArrayList<Book>) ois.readObject();
							userForm.getOrderedBooksTable().setModel(
									new UserTable(Client.orders));
							userForm.getMakeOrderButton().addActionListener(
									new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent e) {
											int[] rows = userForm
													.getAllBooksTable()
													.getSelectedRows();

											if (rows.length > 0) {
												String str = "insert" + userId;
												for (int i = 0; i < rows.length; i++) {
													str += " "
															+ Client.books
																	.get(rows[i])
																	.getBookId();
												}
												pw.println(str);
											}

											try {
												Client.orders = (ArrayList<Book>) ois
														.readObject();
												userForm.getOrderedBooksTable()
														.setModel(
																new UserTable(
																		Client.orders));
											} catch (ClassNotFoundException e1) {
												e1.printStackTrace();
											} catch (IOException e1) {
												e1.printStackTrace();
											}
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
							pw.println("getOrders");
							allOrders = (ArrayList<Order>) ois.readObject();
							librarianForm.getTable().setModel(
									new LibrarianTable(allOrders));
							librarianForm.getDeleteButton().addActionListener(
									new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent e) {
											int[] rows = librarianForm
													.getTable()
													.getSelectedRows();

											if (rows.length > 0) {
												String str = "delete";
												for (int i = 0; i < rows.length; i++) {
													str += allOrders.get(
															rows[i])
															.getOrderId()
															+ " ";
												}

												pw.println(str);
												try {
													allOrders = (ArrayList<Order>) ois
															.readObject();
													librarianForm
															.getTable()
															.setModel(
																	new LibrarianTable(
																			allOrders));
												} catch (ClassNotFoundException e1) {
													e1.printStackTrace();
												} catch (IOException e1) {
													e1.printStackTrace();
												}
											}
										}
									});
						} else {
							JOptionPane.showMessageDialog(null,
									"Invalid username or password", "",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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