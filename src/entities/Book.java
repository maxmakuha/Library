package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Book implements Serializable {

	private int bookId;
	private String title;
	private String author;
	private String description;

	@SuppressWarnings("unused")
	private Book() {
	};

	public Book(int id, String title, String author, String description) {
		this.bookId = id;
		this.title = title;
		this.author = author;
		this.description = description;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Book [title = " + title + ", author = " + author
				+ ", description = " + description + "]";
	}
}