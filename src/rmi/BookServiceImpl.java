package rmi;

import java.util.ArrayList;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.Book;
import entities.Order;
import entities.User;

@SuppressWarnings("serial")
public class BookServiceImpl extends UnicastRemoteObject implements BookService {

	private static ArrayList<Book> books;
	private static ArrayList<User> users;
	private static ArrayList<Order> orders;

	public void createConnection() throws RemoteException {
		System.setProperty("jdbc.drivers", derbyDriver);
	}

	public BookServiceImpl() throws RemoteException {
	}

	@Override
	public ArrayList<Book> getBooks() throws RemoteException {
		books = new ArrayList<Book>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				statement = conn.prepareStatement("SELECT * from app.books");
				rs = statement.executeQuery();
				while (rs.next()) {
					books.add(new Book(rs.getInt("bookId"), rs
							.getString("title"), rs.getString("author"), rs
							.getString("description")));
				}
			} catch (SQLException se) {
				System.out.println("SQL Error: " + se);
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
		return books;
	}

	@Override
	public ArrayList<Book> getBooksOfUser(int userId) throws RemoteException {
		books = new ArrayList<Book>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {

			PreparedStatement statement = null;
			ResultSet rs = null;

			try {
				statement = conn
						.prepareStatement("SELECT * from app.books,app.orders WHERE app.orders.bookId=app.books.bookId AND app.orders.userId =?");
				statement.setInt(1, userId);
				rs = statement.executeQuery();
				while (rs.next()) {
					books.add(new Book(rs.getInt("bookId"), rs
							.getString("title"), rs.getString("author"), rs
							.getString("description")));
				}
			} catch (SQLException se) {
				System.out.println("SQL Error: " + se);
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}

		return books;
	}

	@Override
	public ArrayList<User> getUsers() throws RemoteException {
		users = new ArrayList<User>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				statement = conn.prepareStatement("SELECT * from app.users");
				rs = statement.executeQuery();
				while (rs.next()) {
					users.add(new User(rs.getInt("userId"), rs
							.getString("role"), rs.getString("name"), rs
							.getString("password")));
				}
			} catch (SQLException se) {
				System.out.println("SQL Error: " + se);
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
		return users;
	}

	@Override
	public void makeOrder(String[] index) throws RemoteException {
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			for (int i = 1; i < index.length; i++) {
				try {
					statement = conn
							.prepareStatement("INSERT INTO  app.orders (orderId, bookId, userId)"
									+ " VALUES (?, ?, ?)");
					statement.setInt(1, (getNextOrderId()));
					statement.setString(2, index[i]);
					statement.setString(3, index[0]);
					statement.executeUpdate();
				} catch (SQLException se) {
					System.out.println("SQL Error: " + se);
				}
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
	}

	@Override
	public int getNextOrderId() throws RemoteException {
		int x = 0;
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			try {
				Statement xs = conn.createStatement();
				if (xs.execute("SELECT max(app.orders.orderId) from app.orders")) {
					ResultSet xrs = xs.getResultSet();
					if (xrs.next()) {
						x = xrs.getInt(1);
					}
				}
			} catch (SQLException se) {
				System.out.println("SQL Error: " + se);
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
		return x + 1;
	}

	@Override
	public ArrayList<Order> getOrders() throws RemoteException {
		orders = new ArrayList<Order>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				statement = conn
						.prepareStatement("SELECT * from app.orders left join app.books on app.books.bookId = app.orders.bookId left join app.users on app.orders.userId = app.users.userId");
				rs = statement.executeQuery();
				while (rs.next()) {
					orders.add(new Order(Integer.parseInt(rs.getString(1)), rs
							.getString(10), rs.getString(5), rs.getString(6)));
				}
			} catch (SQLException se) {
				System.out.println("SQL Error: " + se);
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
		return orders;
	}

	@Override
	public ArrayList<Order> deleteOrders(String[] words) throws RemoteException {
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			for (int i = 0; i < words.length; i++) {
				try {
					statement = conn
							.prepareStatement("DELETE FROM app.ORDERS WHERE app.orders.orderId = ?");
					statement.setString(1, words[i]);
					statement.executeUpdate();
				} catch (SQLException se) {
					System.out.println("SQL Error: " + se);
				}
			}
		} catch (SQLException se) {
			System.out.println("Connection failed: " + se);
		}
		return getOrders();
	}
}