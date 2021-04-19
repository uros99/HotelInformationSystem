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

import endpoints.RoomEndpoint;
import endpoints.RoomTypeEndpoint;
import gui.RoomTypeDialog.Operation;
import hotel.RoomType;

public class RoomDeleteDialog extends Dialog{
	private Application app;
	private Button doneButton = new Button("DONE");
	private TextField textRoomId = new TextField();
	private boolean endFlag = false;
	private Label endLabel = new Label("");
	private int id;
	
	public RoomDeleteDialog(Application app) {
		super(app,"Room Delete",true);
		setBounds(100,100,400,100);
		setLayout(new GridLayout(2,2));
		this.app = app;
		Label label = new Label("Number of room you'd like to remove: ");
		textRoomId.setText("Type ID here");
		
		add(label);
		add(textRoomId);
		add(doneButton);
		add(endLabel);
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
				id = Integer.parseInt(textRoomId.getText());
				endFlag = true;
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(endFlag) {
					ResultSet rs = RoomEndpoint.readRoom(id);
					try {
					if(rs.isBeforeFirst()) {
						int taken = rs.getInt("Taken");
						if(taken == 0) {
							RoomEndpoint.deleteRoom(id);
							endFlag = false;
							setVisible(false);
							endLabel.setText("");
						}
						else {
							endLabel.setText("Room is taken so it cant be deleted");
						}
					}else {
						endLabel.setText("There is no room with that ID");
					}
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}finally {
						try {
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
