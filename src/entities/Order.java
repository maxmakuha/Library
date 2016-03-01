package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {

	private int orderId;
	private String user;
	private String author;
	private String title;

	@SuppressWarnings("unused")
	private Order() {
	};

	public Order(int orderId, String user, String author, String title) {
		this.orderId = orderId;
		this.user = user;
		this.author = author;
		this.title = title;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getUser() {
		return user;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return user + " orders book \"" + title + "\" by " + author;
	}
}