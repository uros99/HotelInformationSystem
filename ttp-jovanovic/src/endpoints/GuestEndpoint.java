package endpoints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import hotel.Guest;

public class GuestEndpoint {
	public static void createGuestTable() {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS Guest (\r\n" + 
					"	ID	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	FirstName	TEXT NOT NULL,\r\n" + 
					"	LastName	TEXT NOT NULL,\r\n" + 
					"	Room	INTEGER NOT NULL,\r\n" + 
					"	NumberOfNights	INTEGER NOT NULL,\r\n" + 
					"	FOREIGN KEY(Room) REFERENCES Room(ID)\r\n" + 
					")";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.execute();
			System.out.println("Guest table has been created");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void insertGuest(Guest g) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "INSERT INTO Guest(FirstName,LastName,Room,NumberOfNights) VALUES(?,?,?,?)";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setString(1, g.getFirstName());
			ps.setString(2, g.getLastName());
			ps.setInt(3, g.getRoom().getId());
			ps.setInt(4, g.getNumberOfNights());
			ps.execute();
			System.out.println("Guest has been inserted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static int getBiggestId() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			String sqlQuerry = "SELECT MAX(ID) AS ID FROM Guest";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			rs = ps.executeQuery();
			id = rs.getInt("ID");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
		return id;
	}
	
	public static void deleteGuest(int id) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "DELETE FROM Guest WHERE ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			ps.execute();
			System.out.println("Guest has been deleted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
}
