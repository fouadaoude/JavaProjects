import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.ListModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class ViewAndUpdateEntry extends JFrame {

	private JPanel contentPane;

	public ViewAndUpdateEntry(List<String> userInfo) throws SQLException {
		setTitle("View Entries");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 500, 500);

		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setBackground(UIManager.getColor("ScrollBar.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel entryNameLabel = new JLabel("Entry Name");
		entryNameLabel.setBounds(256, 66, 67, 17);
		entryNameLabel.setBackground(new Color(0, 255, 102));
		entryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		entryNameLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		contentPane.add(entryNameLabel);

		JLabel dateLabel = new JLabel("Date");
		dateLabel.setBounds(153, 66, 25, 17);
		dateLabel.setBackground(Color.MAGENTA);
		dateLabel.setForeground(Color.BLACK);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Maiandra GD", Font.BOLD, 13));
		contentPane.add(dateLabel);

		DefaultListModel<String> list = new DefaultListModel<String>();
		DefaultListModel<String> entryList = new DefaultListModel<String>();
		
		Connection con = null;
		String entryName = null;
		String entryDate = null;
		String entryID = null;
		int i = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
			
			String userID = userInfo.get(0);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT date, entry, entryID, entryTitle FROM entries WHERE userID = '" + userID + "'";
			ResultSet rs = stmt.executeQuery(sql);	
			
			rs.beforeFirst();
			
			while(rs.next()) {
				String foundType = rs.getString(1);
				entryName = rs.getString("entryTitle");
				entryDate = rs.getString("date");
				entryID = rs.getString("entryID");
				
				try {
					list.add(i, "[" + (entryID) + "]" + "      [" + entryDate + "]                   ["
							+ entryName + "]");
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		JList<String> entriesList = new JList<String>(list);
		entriesList.setBounds(1, 1, 254, 298);
		entriesList.setSelectedIndex(0);
		entriesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 1) {
					JList<String> entriesList = (JList<String>) me.getSource();
					int index = entriesList.locationToIndex(me.getPoint());
					if (index >= 0) {
						Object item = entriesList.getModel().getElementAt(index);
						UpdateEntry updateEntry = new UpdateEntry(item, userInfo);
						dispose();
						updateEntry.setVisible(true);
					}
				}
			}
		});

		entriesList.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		entriesList.setForeground(Color.BLACK);
		contentPane.add(entriesList);
		entriesList.setBackground(Color.LIGHT_GRAY);
		entriesList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollBar = new JScrollPane(entriesList);
		scrollBar.setBounds(67, 106, 256, 300);
		scrollBar.setBackground(Color.RED);
		scrollBar.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollBar.getVerticalScrollBar().setForeground(Color.WHITE);
		contentPane.add(scrollBar);

		JButton backButton = new JButton("Back");
		backButton.setBounds(10, 11, 61, 20);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				UserInterface frame;
				try {
					frame = new UserInterface(userInfo);
					frame.setVisible(true);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}

		});
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		backButton.setBackground(Color.WHITE);
		contentPane.add(backButton);

		JLabel IDLabel = new JLabel("ID");
		IDLabel.setFont(new Font("Maiandra GD", Font.BOLD, 11));
		IDLabel.setBounds(67, 68, 46, 14);
		contentPane.add(IDLabel);
	}

}
