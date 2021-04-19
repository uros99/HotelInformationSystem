package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hotel.Guest;
import hotel.ReservedRooms;
import hotel.Room;
import hotel.RoomType;

public class DbConnection {
	public static Connection con = null;
	public static Connection connect(String s) {
		try {
			Class.forName("org.sqlite.JDBC");
			DbConnection.con = DriverManager.getConnection("jdbc:sqlite:"+s);
			System.out.println("Connected!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e+"");
		}
		return DbConnection.con;
	}
	public static void closeConnection() {
		if(DbConnection.con != null) {
			try {
				DbConnection.con.close();
				DbConnection.con = null;
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
}
