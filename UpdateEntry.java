import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UpdateEntry extends JFrame {

	private JPanel contentPane;
	private JTextField entryNameText;

	public UpdateEntry(Object item, List<String> userInfo) {
		getContentPane().setBackground(UIManager.getColor("ScrollBar.background"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrame updateEntry = new JFrame("Update Entry");
		setBounds(200, 100, 500, 500);
		setResizable(true);
		setAlwaysOnTop(true);
		contentPane = new JPanel();

		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setLayout(null);
		
		int index = 0;
		
		String entryTitle = null;
		String entryDate = null;
		String entryID = null;
		String entry = null;
		
		entryID = (String) item;
		entryID = entryID.replace("[", "");
		entryID = entryID.replace("]", "");
		
		index = entryID.indexOf(" ");
		if(index > 0) {
			entryID = entryID.substring(0, index);
		}
		
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
			String userID = userInfo.get(0);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT date, entry, entryID, entryTitle FROM entries WHERE entryID = '" + entryID + "'";
			ResultSet rs = stmt.executeQuery(sql);	
			
			rs.beforeFirst();
			while(rs.next()) {
				String foundType = rs.getString(1);
				
				entry = rs.getString("entry");
				entryTitle = rs.getString("entryTitle");
				entryDate = rs.getString("date");
				entryID = rs.getString("entryID");
				
				System.out.println(entry);
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		final int entry_ID = Integer.parseInt(entryID);
		
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		getContentPane().setLayout(null);

		JLabel entryNameLabel = new JLabel("Entry Name");
		entryNameLabel.setBounds(10, 3, 57, 14);
		entryNameLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		getContentPane().add(entryNameLabel);

		entryNameText = new JTextField();
		entryNameText.setBounds(80, 0, 86, 20);
		entryNameText.setText(entryTitle);
		getContentPane().add(entryNameText);
		entryNameText.setColumns(10);

		JLabel dateTimeCreatedLabel = new JLabel(entryDate);
		dateTimeCreatedLabel.setBounds(355, 3, 227, 14);
		getContentPane().add(dateTimeCreatedLabel);

		JLabel dateCreatedLabel = new JLabel("Date Created");
		dateCreatedLabel.setBounds(290, 3, 61, 14);
		dateCreatedLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		getContentPane().add(dateCreatedLabel);

		JTextArea textPane = new JTextArea(10, 20);
		textPane.setFont(new Font("Maiandra GD", Font.PLAIN, 13));
		JScrollPane scrollBar = new JScrollPane(textPane);
		scrollBar.setBounds(10, 25, 464, 320);
		scrollBar.setBackground(Color.BLACK);
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setBounds(20, 70, 470, 338);
		textPane.setLineWrap(true);
		textPane.setWrapStyleWord(true);
		textPane.setText(entry);
		getContentPane().add(scrollBar);

		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setBounds(10, 440, 156, 14);
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		getContentPane().add(copyrightLabel);

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String entryInfo = textPane.getText();
				String entryTitle = entryNameText.getText();
				ArrayList<String> entryData = new ArrayList<String>();
				entryData.add(entryTitle);
				entryData.add(entryInfo);
								
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				
				Connection con = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
					String userID = userInfo.get(0);
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
		                    ResultSet.CONCUR_UPDATABLE);
					String sql = "UPDATE entries set entry = '" + entryInfo + "', entryTitle = '" + entryTitle + "' , lastUpdated = '" + now + "' WHERE entryID = '" + entry_ID + "'";
					int rs = stmt.executeUpdate(sql);	
					
					if(rs > 0) {
						System.out.println("Updated Successfully");
					}

				}catch(Exception ex) {
					System.out.println(ex);
				}
			}
		});

		updateButton.setBackground(Color.WHITE);
		updateButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		updateButton.setBounds(177, 356, 119, 60);
		getContentPane().add(updateButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewAndUpdateEntry back;
				try {
					back = new ViewAndUpdateEntry(userInfo);
					back.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(10, 356, 119, 60);
		getContentPane().add(backButton);
		
		JButton deleteEntryButton = new JButton("Delete");
		deleteEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 				
				int reply = JOptionPane.showConfirmDialog(null, "Are You Sure You'd Like To Delete This Entry?", "Delete Entry?", JOptionPane.YES_NO_OPTION); 
				if(reply == JOptionPane.YES_OPTION){
					Connection con = null;
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
						Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
			                    ResultSet.CONCUR_UPDATABLE);
						String sql = "DELETE FROM entries WHERE entryID = '"+entry_ID+"'";
						int rs = stmt.executeUpdate(sql);	
						
						if(rs > 0) {
							JOptionPane.showMessageDialog(null, "Deleted Successfully!");
							try {
								dispose();
								ViewAndUpdateEntry frame = new ViewAndUpdateEntry(userInfo);
								frame.setVisible(true);
							}catch(Exception ex) {
								System.out.println(ex);
							}
							System.out.println("Deleted Successfully");
						}

					}catch(Exception ex) {
						System.out.println(ex);
					}
				}
			}
		});
		deleteEntryButton.setFont(new Font("Maiandra GD", Font.PLAIN, 12));
		deleteEntryButton.setBackground(Color.WHITE);
		deleteEntryButton.setBounds(355, 356, 119, 60);
		getContentPane().add(deleteEntryButton);

		/*
		 * Create function that will close the current jpanel/jframe This function will
		 * allow the user to update current journal entry as well as view the text This
		 * function will have a back button This function will have an update button, as
		 * well as a confirm update button Possibly create new file? Complete By:
		 * 5/22/2020
		 */
	}
}
