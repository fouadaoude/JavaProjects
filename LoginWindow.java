import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class LoginWindow extends JFrame {

	private JPanel mainMenu;

	public LoginWindow() {
		setForeground(UIManager.getColor("ScrollBar.thumbDarkShadow"));
		setTitle("Virtual Diary");
		setResizable(false);
		setBackground(UIManager.getColor("ScrollBar.trackHighlight"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 440);
		mainMenu = new JPanel();
		mainMenu.setBackground(UIManager.getColor("ScrollBar.background"));
		mainMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainMenu);
		mainMenu.setLayout(null);
		
		JLabel loginTextLogo = new JLabel("Virtual Diary");
		loginTextLogo.setForeground(Color.BLACK);
		loginTextLogo.setFont(new Font("Maiandra GD", Font.BOLD, 24));
		loginTextLogo.setBounds(165, 47, 170, 64);
		mainMenu.add(loginTextLogo);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If the user clicks "login"
				login();
			}
		});
		loginButton.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 11));
		loginButton.setForeground(Color.BLACK);
		loginButton.setBackground(Color.WHITE);
		loginButton.setBounds(165, 130, 152, 48);
		mainMenu.add(loginButton);
		
		JButton registerButton = new JButton("Register");
			registerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Register frame;
					try {
						frame = new Register();
						frame.setVisible(true);
					}catch(Exception e1) {
						System.out.println(e1);
					}
				}
			});
			
		registerButton.setBackground(Color.WHITE);
		registerButton.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 11));
		registerButton.setBounds(165, 209, 152, 48);
		mainMenu.add(registerButton);
		
		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		copyrightLabel.setBounds(10, 386, 171, 14);
		mainMenu.add(copyrightLabel);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 11));
		exitButton.setBackground(Color.WHITE);
		exitButton.setBounds(165, 288, 152, 48);
		mainMenu.add(exitButton);
	}
	
	public void login() {
		// Hide main menu frame
		dispose();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterLoginDetails frame = new EnterLoginDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
	
