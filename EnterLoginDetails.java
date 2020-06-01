import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;

public class EnterLoginDetails extends JFrame {

	private JPanel loginPane;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public EnterLoginDetails() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 440);
		loginPane = new JPanel();
		loginPane.setBackground(UIManager.getColor("ScrollBar.background"));
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPane);
		loginPane.setLayout(null);
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("Maiandra GD", Font.BOLD, 16));
		loginLabel.setBounds(215, 30, 61, 34);
		loginPane.add(loginLabel);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		usernameLabel.setBounds(204, 96, 61, 14);
		loginPane.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		usernameField.setBounds(185, 121, 108, 20);
		loginPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		passwordLabel.setBounds(204, 171, 61, 14);
		loginPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(185, 196, 108, 20);
		loginPane.add(passwordField);
		
		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		copyrightLabel.setBounds(10, 386, 171, 14);
		loginPane.add(copyrightLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MySql con = new MySql();
					String username = usernameField.getText();
					String password = passwordField.getText();
					
					if(MySql.connect(username, password) == true) {
						dispose();
						UserInfo info = new UserInfo();
						List<String> userInfo = UserInfo.getUserInfo(username, password);
						System.out.println("userInfo = " + userInfo);
						UserInterface frame = new UserInterface(userInfo);
						frame.setVisible(true);
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		
		loginPane.getRootPane().setDefaultButton(loginButton);
		
		loginButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		loginButton.setForeground(Color.BLACK);
		loginButton.setBackground(Color.WHITE);
		loginButton.setBounds(185, 264, 108, 23);
		loginPane.add(loginButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginWindow frame = new LoginWindow();
				frame.setVisible(true);
			}
		});
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(10, 11, 61, 20);
		loginPane.add(backButton);
	}
	
	// Checks if enter key is pressed (usually used for pages with only one "submit button"
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				MySql con = new MySql();
				String password = passwordField.getText();
				String username = usernameField.getText();
				
				if(MySql.connect(username, password) == true) {
					// UserInterface
					UserInfo userInfo = new UserInfo();
					UserInfo.getUserInfo(username, password);
					new UserInterface(UserInfo.getUserInfo(username, password)).setVisible(true);
				}
			}catch(Exception ex) {System.out.println(ex);}
		}
	}
	
}
