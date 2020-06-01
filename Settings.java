import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.awt.Color;

public class Settings extends JFrame {

	private JPanel contentPane;

	public Settings(int userID, List<String> userInfo) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel settingsLabel = new JLabel("Settings");
		settingsLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 24));
		settingsLabel.setBounds(204, 11, 92, 34);
		contentPane.add(settingsLabel);

		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = userInfo.get(2);
				String passwordCheck = null;
				
				try {
					passwordCheck = JOptionPane.showInputDialog(passwordCheck, "Please Enter Your Password");
					if(password.equals(passwordCheck)) {
						dispose();
						ChangePassword frame = new ChangePassword(userID, userInfo);
						frame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Incorrect Password");
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		
		changePasswordButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		changePasswordButton.setBackground(Color.WHITE);
		changePasswordButton.setBounds(186, 199, 130, 60);
		contentPane.add(changePasswordButton);

		JButton changeEmailButton = new JButton("Change Email");
		changeEmailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = userInfo.get(2);
				String passwordCheck = null;

				try {
					passwordCheck = JOptionPane.showInputDialog(passwordCheck, "Please Enter Your Password");
					if (password.equals(passwordCheck)) {
						dispose();
						ChangeEmail frame = new ChangeEmail(userID, userInfo);
						frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect Password");
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		changeEmailButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		changeEmailButton.setBackground(Color.WHITE);
		changeEmailButton.setBounds(186, 84, 130, 60);
		contentPane.add(changeEmailButton);

		JButton deleteAccountButton = new JButton("Delete Account :(");
		deleteAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String passwordCheck = null;
				String password = userInfo.get(2);
				
				try {
					passwordCheck = JOptionPane.showInputDialog(passwordCheck, "Please Enter Your Password");
					if(passwordCheck.equals(password)) {
						int reply = JOptionPane.showConfirmDialog(null, "Are You Sure You'd Like to Delete This Account FOREVER? \"This CANNOT be Undone\"");
						if(reply == JOptionPane.YES_OPTION) {
							System.out.println("hello");
							editAccount(userID, null, null, 3);
							dispose();
							LoginWindow frame = new LoginWindow();
							frame.setVisible(true);
						}						
					}
				}catch(Exception e1) {
					System.out.println(e1);
				}
			}
		});
		deleteAccountButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		deleteAccountButton.setBackground(Color.WHITE);
		deleteAccountButton.setBounds(186, 319, 130, 60);
		contentPane.add(deleteAccountButton);

		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		copyrightLabel.setBounds(10, 446, 156, 14);
		contentPane.add(copyrightLabel);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					UserInterface frame = new UserInterface(userInfo);
					frame.setVisible(true);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(10, 11, 61, 20);
		contentPane.add(backButton);
	}

	public void editAccount(int userID, String newEmail, String newPassword, int editType) {

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");

			// Select all users from the database
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// Select all users information
			String sql = null;
			System.out.println("userID = "+userID+" newEmail = "+newEmail+" newPassword = "+newPassword+" editType = "+editType);
			if (editType == 1) { // change email
				System.out.println("hellooo");
				sql = "UPDATE users SET email = '" + newEmail + "' WHERE userID = '" + userID + "'";
			} else if (editType == 2) { // change password
				sql = "UPDATE users SET password = '" + newPassword + "'WHERE userID = '" + userID + "'";
			} else if (editType == 3) { // delete account
				sql = "DELETE users, entries FROM users JOIN entries ON users.userID = entries.userID WHERE users.userID = '" + userID + "'";
			}

			// The result set from the sql statement
			int rs = stmt.executeUpdate(sql);

			// if the result set is not empty
			if (rs > 0) {
				JOptionPane.showMessageDialog(null, "Updated Successfully...");
				System.out.println("Query Updated Successfully...");
			}
			if(editType == 3 && !(rs > 0)) {
				sql = "DELETE users, entries FROM users LEFT JOIN entries ON users.userID = entries.userID WHERE users.userID = '" + userID + "'";
				rs = stmt.executeUpdate(sql);
				if(rs > 0) {
					JOptionPane.showMessageDialog(null, "Updated Successfully...");
					System.out.println("Query Updated Successfully...");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// con.close();
	}
}
