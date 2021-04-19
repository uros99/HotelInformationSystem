package gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import endpoints.ReservedEndpoint;
import endpoints.RoomEndpoint;
import endpoints.RoomTypeEndpoint;

public class ViewFrame extends Frame{
	enum Operation{
		READ_RESERVATION,
		READ_ROOM_TYPES,
		READ_FREEROOMS
	}
	private JTextArea info = new JTextArea();
	
	public ViewFrame(Operation operation) {
		setBounds(550, 50, 250, 550);
		setResizable(true);
		setVisible(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(250,550));
		JScrollPane scroll = new JScrollPane(info,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll);
		addListeners();
		switch(operation) {
			case READ_RESERVATION: readReservations();break;
			case READ_FREEROOMS: readFreeRooms();break;
			case READ_ROOM_TYPES: readRoomTypes();break;
		}
	}
	
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	private void readRoomTypes() {
		ResultSet rs = RoomTypeEndpoint.readTypeOfRooms();
		StringBuilder sb = new StringBuilder();
		sb.append("Information about all room types").append("\n");
		
		try {
			int i = 1;
			while(rs.next()) {
				sb.append("\n").append(i).append(": ").append(rs.getString("Type"));
				i++;
			}
			info.setText(sb.toString());
		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void readFreeRooms() {
		ResultSet rs = RoomEndpoint.readAllFreeRooms();
		ResultSet rsRoomType = null;
		StringBuilder sb = new StringBuilder();
		sb.append("Information about all free rooms").append("\n");
		
		try {
			int i = 1;
			while(rs.next()) {
				sb.append("\n").append(i).append(":").append("\n");
				int roomNumber = rs.getInt("ID");
				int bedsNumber = rs.getInt("BedsNumber");
				int type = rs.getInt("Type");
				rsRoomType = RoomTypeEndpoint.readRoomTypeById(type);
				String nameOfType = rsRoomType.getString("Type");
				int pricePerNight = rs.getInt("PricePerNight");
				sb.append("Room number is: ").append(roomNumber).append("\n");
				sb.append("Number of beds in room: ").append(bedsNumber).append("\n");
				sb.append("Room type is: ").append(nameOfType).append("\n");
				sb.append("Price per night in this room is: ").append(pricePerNight).append("\n");
				i++;
			}
			info.setText(sb.toString());
		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
				if(rs!=null)rs.close();
				if(rsRoomType!=null) rsRoomType.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void readReservations() {
		ResultSet rs = ReservedEndpoint.readAllReservations();
		StringBuilder sb = new StringBuilder();
		sb.append("Information about all reservations").append("\n");
		try {
			int i = 1;
			while(rs.next()) {
				sb.append("\n").append("Reservation number: "+i).append("\n");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				int roomId = rs.getInt("Room");
				int numOfNights = rs.getInt("NumberOfNights");
				sb.append("Guest name: ").append(firstName).append(lastName).append("\n");
				sb.append("Number of guest room: ").append(roomId).append("\n");
				sb.append("Number of nights the guest will stay: ").append(numOfNights).append("\n");
				i++;
			}
			info.setText(sb.toString());
		} catch (SQLException e) {
			System.out.println(e.toString());
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.toString());
			}
		}
	}
}
