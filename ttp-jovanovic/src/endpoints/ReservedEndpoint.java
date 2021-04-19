package endpoints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.core.DB;

import connection.DbConnection;
import hotel.ReservedRooms;

public class ReservedEndpoint {
	public static void createReservedTable() {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "CREATE TABLE IF NOT EXISTS ReservedRooms (\r\n" + 
					"	ID	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
					"	IdRoom	INTEGER NOT NULL REFERENCES Room(ID),\r\n" + 
					"	IdGuest	INTEGER NOT NULL REFERENCES Guest(ID)\r\n" + 
					")";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.execute();
			System.out.println("Room table has been created");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void insertReserved(ReservedRooms r) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "INSERT INTO ReservedRooms(IdRoom,IdGuest) VALUES(?,?)";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, r.getIdRoom().getId());
			ps.setInt(2, r.getIdGuest().getId());
			ps.execute();
			System.out.println("Reservation has been inserted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet readGuestInRoom(int roomId) {
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			String sqlQuerry = "SELECT r.IdGuest FROM ReservedRooms r WHERE r.IdRoom = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, roomId);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
	
	public static void deleteReservation(int roomId) {
		PreparedStatement ps = null;
		try {
			String sqlQuerry = "DELETE FROM ReservedRooms WHERE IdRoom = ?";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			ps.setInt(1, roomId);
			ps.execute();
			System.out.println("Reservation has been deleted");
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public static ResultSet readAllReservations() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String sqlQuerry = "SELECT g.* FROM ReservedRooms r JOIN Guest g ON (r.IdGuest = g.ID)";
			ps = DbConnection.con.prepareStatement(sqlQuerry);
			rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		return rs;
	}
}
