import java.awt.EventQueue;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class VirtualDiary {

	public static void main(String[] args) {
		// Create new object of mysql method
		MySql connect = new MySql();

		// If the connection was successfully established open up the application else
		// print could not connect
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

// class for all database and connecting handling
class MySql {
	public static boolean connect(String username, String password) throws SQLException {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");
			// Select all users from the database
			Statement stmt = con.createStatement();
			// Checks for case sensitive password as well with "BINARY"
			String sql = "SELECT * FROM users WHERE username ='" + username + "' AND BINARY password ='" + password
					+ "'";
			// The result set from the sql statement
			ResultSet rs = stmt.executeQuery(sql);
			// if theres a result in comparison with the sql statement
			if (rs.next()) {
				System.out.println("\nDatabase Connected Successfully...");
				con.close();
				rs.close();
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error Incorrect Username or Password");
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		con.close();
		return false;
	}
}

class UserInfo {

	public static List<String> getUserInfo(String username, String password) throws SQLException {
		Connection con = null;
		String columnValue = null;
		List<String> values = new ArrayList<>(Arrays.asList());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");

			// Select all users from the database
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);

			// Select all users information
			String sql = "SELECT * FROM users JOIN entries ON users.userID = entries.userID WHERE username ='"
					+ username + "'";

			// The result set from the sql statement
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			
			if(!rs.next()) {
				sql = "SELECT * FROM users LEFT JOIN entries ON users.userID = entries.userID WHERE username ='"
						+ username + "'";
				
				rs = stmt.executeQuery(sql);
				rsmd = rs.getMetaData();
				
				
				if(!rs.next()) {
					sql = "SELECT * FROM users RIGHT JOIN entries ON users.userID = entries.userID WHERE username ='"
							+ username + "'";
					
					rs = stmt.executeQuery(sql);
					rsmd = rs.getMetaData();					
				}
			}
			
			rs.beforeFirst();
			// Get the number of columns in the result set of the sql statement
			int columnsNumber = rsmd.getColumnCount();			
			// if the result set is not empty
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					columnValue = rs.getString(i);
					values.add(columnValue);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		con.close();
		return values;
	}

	public static DefaultListModel<String> getUserEntries(String userID) throws SQLException {
		Connection con = null;
		String columnValue = null;
		DefaultListModel<String> entries = new DefaultListModel<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");

			// Select all users from the database
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);

			// Select all users information
			String sql = "SELECT date, entry, entryID, entryTitle FROM entries WHERE userID = '" + userID + "'";

			// The result set from the sql statement
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int columns = rsmd.getColumnCount();
			
			// if the result set is not empty
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					columnValue = rs.getString(i);
					entries.addElement(columnValue);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		con.close();
		return entries;
	}

	public static void updateEntry(String userID, List<String> entryData) {				
		String entryName = entryData.get(0);
		String entry = entryData.get(1);
		
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualdiary", "root", "123456");

			Statement stmt = con.createStatement();

			String sql = "UPDATE entries SET entry = '"+ entry + "'" + ", entryTitle = '"+entryName+"'"+ ", lastUpdated"+
			"WHERE userID = '" + userID + "'";

			int rs = stmt.executeUpdate(sql);

			if (rs > 0) {
				System.out.println("Entry Updated Successfully!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
