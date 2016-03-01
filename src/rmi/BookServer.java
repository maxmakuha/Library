package rmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BookServer {

	private static final String SERVER_NAME = "Server";
	private String port;

	public BookServer(String port) {
		this.port = port;
	}

	public void runServer() throws IllegalArgumentException, NotBoundException,
			RemoteException {
		BookServiceImpl serviceImpl = new BookServiceImpl();
		Registry registry = LocateRegistry.createRegistry(Integer.parseInt(
				port, 10));
		registry.rebind(SERVER_NAME, serviceImpl);
	}

	public static void main(String[] args) throws RemoteException,
			IllegalArgumentException, NotBoundException {
		BookServer server = new BookServer("8888");
		server.runServer();
		System.out.println("Server has started");
	}
}