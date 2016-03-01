package rmi;

import java.util.ArrayList;
import java.rmi.*;

import entities.Book;
import entities.Order;
import entities.User;

public interface BookService extends Remote {

	final static String derbyProtocol = "jdbc:derby://localhost:1527/";
	final static String dbName = "sample";
	final static String jdbcURL = derbyProtocol + dbName;
	final static String derbyDriver = "D:\\Program Files\\Eclipse\\JDK\\db\\lib\\derby.jar";

	public void createConnection() throws RemoteException;

	public ArrayList<Book> getBooks() throws RemoteException;

	public ArrayList<Book> getBooksOfUser(int userId) throws RemoteException;

	public ArrayList<User> getUsers() throws RemoteException;

	public int getNextOrderId() throws RemoteException;

	public void makeOrder(String[] index) throws RemoteException;

	public ArrayList<Order> getOrders() throws RemoteException;

	public ArrayList<Order> deleteOrders(String[] words) throws RemoteException;
}
