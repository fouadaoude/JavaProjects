import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class NewEntry extends JFrame {

	private JPanel contentPane;
	private JTextField entryNameText;

	public NewEntry(int userID, List<String> userInfo) {
		setTitle("New Entry");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel entryNameLabel = new JLabel("Entry Name");
		entryNameLabel.setBounds(10, 11, 57, 14);
		entryNameLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		contentPane.add(entryNameLabel);
		
		entryNameText = new JTextField();
		entryNameText.setBounds(77, 8, 86, 20);
		entryNameText.setText((String) null);
		entryNameText.setColumns(10);
		contentPane.add(entryNameText);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		JLabel todaysDateLabel = new JLabel("Today's Date " + dtf.format(now));
		todaysDateLabel.setBounds(272, 11, 202, 14);
		todaysDateLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		contentPane.add(todaysDateLabel);
		
		JScrollPane scrollBar = new JScrollPane((Component) null);
		scrollBar.setBounds(10, 36, 464, 320);
		scrollBar.setBackground(Color.BLACK);
		contentPane.add(scrollBar);
		
		JTextArea textPane = new JTextArea(10, 20);
		textPane.setWrapStyleWord(true);
		textPane.setText((String) null);
		textPane.setLineWrap(true);
		textPane.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		textPane.setBackground(Color.LIGHT_GRAY);
		scrollBar.setViewportView(textPane);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
		                    ResultSet.CONCUR_UPDATABLE);
					String sql = "INSERT INTO entries SET userID = '"+userID+"', entryID = '"+0+"', date = '"+now+"', entry='"+textPane.getText()+"', entryTitle='"+entryNameText.getText()+"', lastUpdated='"+now+"'";
					int rs = stmt.executeUpdate(sql);
					
					if(rs > 0) {
						System.out.println("Successfully Submitted Query");
						try {
							JOptionPane.showMessageDialog(null, "Successfully Created!");
							dispose();							
							UserInterface frame = new UserInterface(userInfo);
							frame.setVisible(true);
						}catch(Exception ex) {
							System.out.println(ex);
						}
							
						
					}
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		createButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		createButton.setBackground(Color.WHITE);
		createButton.setBounds(355, 367, 119, 60);
		contentPane.add(createButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserInterface back;
				try {
					back = new UserInterface(userInfo);
					back.setVisible(true);
				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});
		
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(10, 367, 119, 60);
		contentPane.add(backButton);
		
		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		copyrightLabel.setBounds(7, 436, 156, 14);
		contentPane.add(copyrightLabel);
	}
}
