package gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import endpoints.GuestEndpoint;
import endpoints.ReservedEndpoint;
import endpoints.RoomEndpoint;
import endpoints.RoomTypeEndpoint;
import hotel.Guest;
import hotel.ReservedRooms;
import hotel.Room;
import hotel.RoomType;

public class GuestAddDialog extends Dialog {
	private ReservedDialog rd;
	private TextField textFirstName = new TextField("Type First Name");
	private TextField textLastName = new TextField("Type Last Name");
	private TextField textNumberOfNights = new TextField("Number of days of stay");
	private Choice choiceTypeOfRoom = new Choice();
	private Button doneButton = new Button("DONE");
	private boolean[] endFlags = {false,false,false,false};
	private Label endLabel = new Label("");
	
	private String firstName;
	private String lastName;
	private int numberOfNights;
	private String typeOfRoom;
	
	public GuestAddDialog(ReservedDialog app) {
		super(app,"Guest Form", true);
		this.rd = app;
		setBounds(100,100,500,200);
		Label labelFirstName = new Label("First Name: ");
		Label labelLastName = new Label("Last Name: ");
		Label labelNumOfNights = new Label("How long would you like to stay:");
		Label labelTypeOfRoom = new Label("What type of room would you like:");
		
		fillChoice();
		
		setLayout(new GridLayout(5,2));
		add(labelFirstName);
		add(textFirstName);
		add(labelLastName);
		add(textLastName);
		add(labelNumOfNights);
		add(textNumberOfNights);
		add(labelTypeOfRoom);
		add(choiceTypeOfRoom);
		add(doneButton);
		add(endLabel);
		addListeners();
	}
	
	private void fillChoice() {
		ResultSet rs = RoomTypeEndpoint.readTypeOfRooms();
		boolean firstElem = true;
		if(rs != null) {
			try {
				while(rs.next()) {
					String type = rs.getString("Type");
					if(firstElem) {
						typeOfRoom = type;
						firstElem = false;
						endFlags[3] = true;
					}
					choiceTypeOfRoom.add(type);
				}
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
	
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs = null;
				ResultSet rsRoomType = null;
				try {
				if(endFlags[0] && endFlags[1] && endFlags[2] && endFlags[3]) {
					endFlags = new boolean[] {false,false,false,false};
					textFirstName.setText("First Name");
					textLastName.setText("Last Name");
					textNumberOfNights.setText("How long would you like to stay:");
					rsRoomType = RoomTypeEndpoint.readRoomTypeByType(typeOfRoom);
					RoomType rt = new RoomType(rsRoomType.getInt("ID"), rsRoomType.getString("Type"));
					rs = RoomEndpoint.getFreeRooms(rt);
					if(rs.isBeforeFirst()) {
						int roomId = rs.getInt("ID");
						int bedsNum = rs.getInt("BedsNumber");
						int pricePerNight = rs.getInt("PricePerNight");
						int taken = rs.getInt("Taken");
						Room r = new Room(roomId,bedsNum ,pricePerNight,rt,taken);
						Guest g = new Guest(1,firstName,lastName,r,numberOfNights);
						GuestEndpoint.insertGuest(g);
						g.setId(GuestEndpoint.getBiggestId());
						RoomEndpoint.setRoomTaken(r.getId());
						ReservedRooms rr = new ReservedRooms(1, r, g);
						ReservedEndpoint.insertReserved(rr);
						rd.setTextRoomId("Number of your room is: "+r.getId());
						rd.setLabelEnd("Price Guest have to pay is: "+r.getPrisePerNight()*g.getNumberOfNights());
						setVisible(false);
						endLabel.setText("");
					}else {
						endFlags[3]=false;
						endLabel.setText("There is no available rooms with that type");
					}	
				}else {
					endLabel.setText("You need to type all informations");
				}
				} catch (SQLException e) {
					System.out.println(e.toString());
				}finally {
					try {
						if(rs!=null) rs.close();
						if(rsRoomType!=null) rsRoomType.close();
					} catch (SQLException e) {
						System.out.println(e.toString());
					}
				}
			}
		});
		
		textFirstName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				firstName = textFirstName.getText();
				endFlags[0] = true;
			}
		});
		
		textLastName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lastName = textLastName.getText();
				endFlags[1] = true;
			}
		});
		
		textNumberOfNights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numberOfNights = Integer.parseInt(textNumberOfNights.getText());
				endFlags[2] = true;
			}
		});
		
		choiceTypeOfRoom.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				typeOfRoom = choiceTypeOfRoom.getSelectedItem();
				endFlags[3] = true;
			}
		});
		
	}
}
