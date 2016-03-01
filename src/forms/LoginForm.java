package forms;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginForm extends JFrame {

	JPanel panel;
	JTextField loginField;
	JPasswordField passwordField;
	JComboBox role;
	JButton sign;
	final String[] roles = { "Librarian", "User" };

	public LoginForm() {
		this.setSize(400, 250);
		this.setLocation(500, 300);
		setTitle("Authentication");

		panel = new JPanel(new GridLayout(4, 2));
		loginField = new JTextField(10);
		passwordField = new JPasswordField(10);
		role = new JComboBox(roles);
		sign = new JButton("Sign in");

		panel.add(new JLabel("Enter your name: "));
		panel.add(loginField);
		panel.add(new JLabel("Enter password: "));
		panel.add(passwordField);

		panel.add(new JLabel("Choose your role: "));
		panel.add(role);
		this.add(panel);
		this.add(sign, BorderLayout.PAGE_END);
	}

	public JButton getButton() {
		return this.sign;
	}

	public JPasswordField getPasswordField() {
		return this.passwordField;
	}

	public JTextField getLoginField() {
		return this.loginField;
	}

	public JComboBox getRole() {
		return this.role;
	}
}