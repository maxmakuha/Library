package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO {

	final static String derbyProtocol = "jdbc:derby://localhost:1527/";
	final static String dbName = "sample";
	final static String jdbcURL = derbyProtocol + dbName;
	final static String derbyDriver = "org.apache.derby.jdbc.ClientDriver";

	private static ArrayList<Book> books;
	private static ArrayList<User> users;
	private static ArrayList<Order> orders;

	public static void createConnection() {
		System.setProperty("jdbc.drivers", derbyDriver);
	}

	public static ArrayList<Book> getBooks() {
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

	public static ArrayList<Book> getBooksOfUser(int userId) {
		books = new ArrayList<Book>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, "app",
				"root")) {
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				statement = conn
						.prepareStatement("SELECT * from app.books, app.orders WHERE app.orders.bookId = app.books.bookId AND app.orders.userId = ?");
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

	public static ArrayList<User> getUsers() {
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

	public static void makeOrder(String[] index) {
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

	private static int getNextOrderId() {
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

	public static ArrayList<Order> getOrders() {
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

	public static ArrayList<Order> deleteOrders(String[] words) {
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
		return DAO.getOrders();
	}
}