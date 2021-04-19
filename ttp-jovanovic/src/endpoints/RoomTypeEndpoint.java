package endpoints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import hotel.RoomType;

public class RoomTypeEndpoint {
	public static void createRoomTypeTable() {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS RoomType (\r\n" + 
					"	ID	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	Type	TEXT NOT NULL\r\n" + 
					")";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.execute();
			System.out.println("Room type table has been created");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet readRoomTypeByType(String type) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT * FROM RoomType r WHERE r.Type = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setString(1, type);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static ResultSet readRoomTypeById(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT * FROM RoomType r WHERE r.ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static ResultSet readTypeOfRooms() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT * FROM RoomType";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static void insertRoomType(RoomType r) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "INSERT INTO RoomType(Type) VALUES(?)";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setString(1, r.getType());
			ps.execute();
			System.out.println("RoomType has been inserted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void deleteRoomType(String s) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "DELETE FROM RoomType WHERE Type = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setString(1, s);
			ps.execute();
			System.out.println("RoomType has been deleted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet countType(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT count(*) as NumberOfType FROM Room r WHERE r.Type = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
}
