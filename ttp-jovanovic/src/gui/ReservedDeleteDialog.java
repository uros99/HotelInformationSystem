package gui;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import endpoints.GuestEndpoint;
import endpoints.ReservedEndpoint;
import endpoints.RoomEndpoint;

public class ReservedDeleteDialog extends Dialog {
	private Application app;
	private TextField textRoomId = new TextField("Type room ID");
	private Button doneButton = new Button("DONE");
	private boolean endFlag = false;
	private int roomId;
	
	public ReservedDeleteDialog(Application app) {
		super(app,"Delete Reservation", true);
		this.app = app;
		setBounds(100,100,400,100);
		setLayout(new GridLayout(2,2));
		Label labelRoomId = new Label("What is the room number you stayed in");
		
		add(labelRoomId);
		add(textRoomId);
		add(doneButton);
		
		addListeners();
	}
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		textRoomId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomId = Integer.parseInt(textRoomId.getText());
				endFlag = true;
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = null;
				if(endFlag) {
					try {
					RoomEndpoint.resetRoomTaken(roomId);
					rs = ReservedEndpoint.readGuestInRoom(roomId);
					ReservedEndpoint.deleteReservation(roomId);
					if(rs.isBeforeFirst()) {
						int idGuest = rs.getInt("IdGuest");
						GuestEndpoint.deleteGuest(idGuest);
					}
					endFlag = false;
					setVisible(false);
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}finally {
						try {
							if(rs!=null)
								rs.close();
						} catch (SQLException e1) {
							System.out.println(e1.toString());
						}
					}
				}
			}
		});
	}
}
