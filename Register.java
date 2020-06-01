import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField reEnterPasswordField;
	private JTextField emailField;
	private JTextField reEnterEmailField;

	public Register() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 440);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LoginWindow frame;
				try {
					frame = new LoginWindow();
					frame.setVisible(true);
				}catch(Exception e1) {
					System.out.println(e1);
				}
			}
		});
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(10, 11, 61, 20);
		contentPane.add(backButton);
		
		JLabel registerLabel = new JLabel("Register");
		registerLabel.setFont(new Font("Maiandra GD", Font.BOLD, 16));
		registerLabel.setBounds(197, 29, 99, 34);
		contentPane.add(registerLabel);
		
		JLabel emailLabel = new JLabel("Enter Your Email");
		emailLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		emailLabel.setBounds(174, 74, 105, 14);
		contentPane.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setBounds(167, 99, 129, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		JLabel reEnterEmailLabel = new JLabel("Re-Enter Email");
		reEnterEmailLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		reEnterEmailLabel.setBounds(174, 130, 105, 14);
		contentPane.add(reEnterEmailLabel);
		
		reEnterEmailField = new JTextField();
		reEnterEmailField.setBounds(167, 155, 129, 20);
		contentPane.add(reEnterEmailField);
		reEnterEmailField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Enter a Username");
		usernameLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		usernameLabel.setBounds(174, 186, 105, 14);
		contentPane.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(167, 211, 129, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Enter a Password");
		passwordLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		passwordLabel.setBounds(174, 242, 105, 14);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(167, 267, 129, 20);
		contentPane.add(passwordField);
		
		JLabel reEnterPasswordLabel = new JLabel("Re-Enter Password");
		reEnterPasswordLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		reEnterPasswordLabel.setBounds(174, 298, 105, 14);
		contentPane.add(reEnterPasswordLabel);
		
		reEnterPasswordField = new JPasswordField();
		reEnterPasswordField.setBounds(167, 323, 129, 20);
		contentPane.add(reEnterPasswordField);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(emailField.getText().equals(reEnterEmailField.getText())) {
					if(passwordField.getText().equals(reEnterPasswordField.getText())) {
						boolean check = checkRegisterInfo(emailField.getText(), usernameField.getText(), passwordField.getText());
						if(check == true) {
							JOptionPane.showMessageDialog(null, "Account Created Successfully");
							dispose();
							LoginWindow frame = new LoginWindow();
							frame.setVisible(true);
						}
						
					}
				}
				
			}
		});
		registerButton.setForeground(Color.BLACK);
		registerButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		registerButton.setBackground(Color.WHITE);
		registerButton.setBounds(167, 367, 129, 23);
		contentPane.add(registerButton);
		
		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		copyrightLabel.setBounds(303, 14, 171, 14);
		contentPane.add(copyrightLabel);
	}
	
	public static boolean checkRegisterInfo(String email, String username, String password) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                			"[a-zA-Z0-9_+&*-]+)*@" + 
                			"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                			"A-Z]{2,7}$"; 
		Pattern pat = Pattern.compile(emailRegex);
		boolean emailPattern = pat.matcher(email).matches();
				
		if(emailPattern == true) {			
			Connection con = null;
			
			String usernameCheck;
			String emailCheck;
			boolean accountCreated = false;
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
	                    ResultSet.CONCUR_UPDATABLE);
				String sql = "SELECT username, email FROM users";
				ResultSet rs = stmt.executeQuery(sql);
				
				rs.beforeFirst();
				while(rs.next()) {
					usernameCheck = rs.getString("username");
					emailCheck = rs.getString("email");
					if(usernameCheck.equals(username)) {
						accountCreated = false;
						JOptionPane.showMessageDialog(null, "Username Already Exists Choose Another Username");
						break;
					}
					if(password.length() < 5) {
						accountCreated = false;
						JOptionPane.showMessageDialog(null, "Password is Too Short");
					}
					if(emailCheck.equals(email)) {
						accountCreated = false;
						JOptionPane.showMessageDialog(null, "Email Already Exists");
						break;
					}
					else {
						accountCreated = true;
					}
				}
			}catch(Exception e1) {
				System.out.println(e1);
			}
			
			if(accountCreated == true) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
		                    ResultSet.CONCUR_UPDATABLE);
					String sql = "INSERT INTO users SET username = '"+username+"', password = '"+password+"',email = '"+email+"'";
					int rs = stmt.executeUpdate(sql);
					
					if(rs > 0) {
						System.out.println("Successfully Submitted Query");
						return true;
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		}else {
			emailPattern = false;
			JOptionPane.showMessageDialog(null, "Please Enter a Valid Email");
		}
		return false;
	}
}
