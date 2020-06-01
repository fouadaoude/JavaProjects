import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JPasswordField enterPasswordField;
	private JPasswordField reEnterPasswordField;

	public ChangePassword(int userID, List<String> userInfo) {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel changePasswordLabel = new JLabel("Change Password");
		changePasswordLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 24));
		changePasswordLabel.setBounds(139, 11, 183, 34);
		contentPane.add(changePasswordLabel);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					Settings frame = new Settings(userID, userInfo);
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
		
		JLabel enterNewPasswordLabel = new JLabel("Enter New Password");
		enterNewPasswordLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		enterNewPasswordLabel.setBounds(155, 68, 128, 34);
		contentPane.add(enterNewPasswordLabel);
		
		JLabel reEnterNewPasswordLabel = new JLabel("Re-Enter New Password");
		reEnterNewPasswordLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		reEnterNewPasswordLabel.setBounds(155, 133, 146, 34);
		contentPane.add(reEnterNewPasswordLabel);
		
		enterPasswordField = new JPasswordField();
		enterPasswordField.setBounds(155, 102, 167, 20);
		contentPane.add(enterPasswordField);
		
		reEnterPasswordField = new JPasswordField();
		reEnterPasswordField.setBounds(155, 167, 167, 20);
		contentPane.add(reEnterPasswordField);		
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = enterPasswordField.getText();
				if(password.length() > 5) {
					if(password.equals(reEnterPasswordField.getText())) {						
						dispose();
						Settings frame = new Settings(userID, userInfo);
						frame.editAccount(userID, null, password, 2);
						frame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Passwords Do Not Match");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Password is Too Short");
				}
				
			}
		});
		updateButton.setBounds(155, 198, 158, 41);
		contentPane.add(updateButton);
	}
}
