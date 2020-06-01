import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Color;

public class UserInterface extends JFrame {

	private JPanel userInterfacePane;

	public UserInterface(List<String> userInfo) throws SQLException {
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		userInterfacePane = new JPanel();
		userInterfacePane.setBackground(UIManager.getColor("ScrollBar.background"));
		userInterfacePane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(userInterfacePane);
		userInterfacePane.setLayout(null);

		JLabel copyrightLabel = new JLabel("Copyright \u00A9 2020 Fouad Aoude");
		copyrightLabel.setBounds(10, 496, 171, 14);
		copyrightLabel.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		userInterfacePane.add(copyrightLabel);
		
		JButton newEntryButton = new JButton("New Entry");
		newEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userID = Integer.parseInt(userInfo.get(0));
				dispose();
				NewEntry newEntry;
				try {
					newEntry = new NewEntry(userID, userInfo);
					newEntry.setVisible(true);
				}catch(Exception e1) {
					System.out.println(e1);
				}
			}
		});
		newEntryButton.setBackground(Color.WHITE);
		newEntryButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		newEntryButton.setBounds(275, 145, 184, 50);
		userInterfacePane.add(newEntryButton);
		
		JButton viewAndUpdateEntryButton = new JButton("View & Update Entry");
		viewAndUpdateEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewAndUpdateEntry update = null;
				try {
					update = new ViewAndUpdateEntry(userInfo);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				update.setVisible(true);
			}
		});
		viewAndUpdateEntryButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		viewAndUpdateEntryButton.setBackground(Color.WHITE);
		viewAndUpdateEntryButton.setBounds(275, 218, 184, 50);
		userInterfacePane.add(viewAndUpdateEntryButton);
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Log Out?", "Log Out",
				   JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {					
					try {
						dispose();					
						LoginWindow frame = new LoginWindow();
						frame.setVisible(true);
					}catch(Exception ex) {
						System.out.println(ex);
					}
				}
			}
		});
		logOutButton.setForeground(Color.BLACK);
		logOutButton.setFont(new Font("Maiandra GD", Font.PLAIN, 14));
		logOutButton.setBackground(Color.WHITE);
		logOutButton.setBounds(540, 450, 184, 50);
		userInterfacePane.add(logOutButton);
		
		final int userID = Integer.parseInt(userInfo.get(0));
		
		JButton settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new ActionListener() {
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
		settingsButton.setFont(new Font("Maiandra GD", Font.PLAIN, 15));
		settingsButton.setBackground(Color.WHITE);
		settingsButton.setBounds(275, 294, 184, 50);
		userInterfacePane.add(settingsButton);
		
		EnterLoginDetails loginDetails = new EnterLoginDetails();
		UserInfo info = new UserInfo();
		
		// Greet user
		JLabel greetingLabel = new JLabel("Welcome, " + userInfo.get(1).substring(0,1).toUpperCase()
				+userInfo.get(1).substring(1)+"!");
		greetingLabel.setFont(new Font("Maiandra GD", Font.BOLD, 14));
		greetingLabel.setBounds(275, 51, 184, 39);
		userInterfacePane.add(greetingLabel);
		
		JLabel greetingLabel2 = new JLabel("What's On Your Mind Today?");
		greetingLabel2.setFont(new Font("Maiandra GD", Font.BOLD, 14));
		greetingLabel2.setBounds(275, 89, 184, 39);
		userInterfacePane.add(greetingLabel2);
	}

}
