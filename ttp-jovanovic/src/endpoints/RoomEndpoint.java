package endpoints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import hotel.Room;
import hotel.RoomType;

public class RoomEndpoint {
	public static void createRoomTable() {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS Room (\r\n" + 
					"	ID	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	BedsNumber	INTEGER NOT NULL,\r\n" + 
					"	Type	INTEGER NOT NULL,\r\n" + 
					"	PricePerNight	INTEGER NOT NULL,\r\n" + 
					"	Taken	INTEGER NOT NULL,\r\n" + 
					"	FOREIGN KEY(Type) REFERENCES RoomType(ID)\r\n" + 
					")";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.execute();
			System.out.println("Room table has been created");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet readRoom(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT r.* FROM Room r WHERE r.ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static ResultSet readRooms() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT * FROM Room";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static ResultSet getFreeRooms(RoomType rt) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			String sqlQuerry = "SELECT * FROM Room r WHERE r.Type = ? AND r.Taken = 0";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, rt.getId());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static void setRoomTaken(int id) {
		PreparedStatement ps = null;

		try {
			String sqlQuerry = "UPDATE Room SET Taken = 1 WHERE ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void resetRoomTaken(int id) {
		PreparedStatement ps = null;

		try {
			String sqlQuerry = "UPDATE Room SET Taken = 0 WHERE ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void insertRoom(Room r) {
		PreparedStatement ps = null;
		
		try {
			String sqlQuerry = "INSERT INTO Room(BedsNumber,Type,PricePerNight,Taken) VALUES(?,?,?,?)";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, r.getBedsNumber());
			ps.setInt(2, r.getType().getId());
			ps.setInt(3, r.getPrisePerNight());
			ps.setInt(4, 0);
			ps.execute();
			System.out.println("Room has been inserted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void deleteRoom(int id) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "DELETE FROM Room WHERE ID = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, id);
			ps.execute();
			System.out.println("Room has been deleted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet readAllFreeRooms() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT r.* FROM Room r WHERE r.Taken = 0";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
		
	}
}
