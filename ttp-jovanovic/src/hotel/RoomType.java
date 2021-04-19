package hotel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import connection.DbConnection;

public class RoomType {
	private int id;
	private String type;
	private static LinkedList<String> roomTypesFromDB;
	
	public RoomType(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public static RoomType createRoomType(String type) {
	/*	ResultSet rs = DbConnection.readTypeOfRooms();
		try {
			while(rs.next()) {
				roomTypesFromDB = new LinkedList<String>();
				String typeOfRoom = rs.getString("Type");
				roomTypesFromDB.add(typeOfRoom);
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}*/
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
