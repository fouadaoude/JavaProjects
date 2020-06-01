import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class ChangeEmail extends JFrame {

	private JPanel contentPane;
	private JTextField emailTextField;
	private JTextField reEnterEmailTextField;

	public ChangeEmail(int userID, List<String>userInfo) {
		setResizable(false);			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel newEmailLabel = new JLabel("New Email");
		newEmailLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 24));
		newEmailLabel.setBounds(197, 11, 114, 34);
		contentPane.add(newEmailLabel);
		
		JLabel enterNewEmailLabel = new JLabel("Enter New Email");
		enterNewEmailLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		enterNewEmailLabel.setBounds(179, 56, 114, 34);
		contentPane.add(enterNewEmailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(169, 90, 158, 20);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		JLabel lblReenterNewEmail = new JLabel("Re-Enter New Email");
		lblReenterNewEmail.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		lblReenterNewEmail.setBounds(179, 121, 114, 34);
		contentPane.add(lblReenterNewEmail);
		
		reEnterEmailTextField = new JTextField();
		reEnterEmailTextField.setBounds(169, 155, 158, 20);
		contentPane.add(reEnterEmailTextField);
		reEnterEmailTextField.setColumns(10);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(emailTextField.getText().equals(reEnterEmailTextField.getText())) {
					changeEmail(emailTextField.getText(), userID, userInfo);
				}
			}
		});
		updateButton.setBounds(169, 196, 158, 41);
		contentPane.add(updateButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					Settings frame = new Settings(userID, userInfo);
					frame.setVisible(true);
				}catch(Exception ex) {
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
	
	public void changeEmail(String newEmail, int userID, List<String>userInfo) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
    						"[a-zA-Z0-9_+&*-]+)*@" + 
    						"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
    						"A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		boolean emailState = pat.matcher(newEmail).matches();
		
		if(emailState == true) {
			Settings frame = new Settings(userID, userInfo);
			frame.editAccount(userID, newEmail, null, 1);
			dispose();
			frame.setVisible(true);
		}
	}
}
