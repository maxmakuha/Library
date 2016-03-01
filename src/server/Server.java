package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import thread.SocketThread;
import entities.DAO;

public class Server {
	public static void main(String[] args) {
		DAO.createConnection();
		try (ServerSocket servsocket = new ServerSocket(
				Integer.parseInt(args[0]))) {
			System.out.println("Server has started");
			while (true) {
				Socket socket = servsocket.accept();
				new Thread(new SocketThread(socket)).start();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}