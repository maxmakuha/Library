package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private int userId;
	private String role;
	private String name;
	private String password;

	@SuppressWarnings("unused")
	private User() {
	};

	public User(int userId, String role, String name, String password) {
		this.userId = userId;
		this.role = role;
		this.name = name;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [role = " + role + ", name = " + name + ", password = "
				+ password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;

		return this.name.equals(u.getName())
				&& this.password.equals(u.getPassword())
				&& this.role.equals(u.getRole());
	}
}