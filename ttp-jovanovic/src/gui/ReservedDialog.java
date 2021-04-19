package gui;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import endpoints.RoomEndpoint;

public class ReservedDialog extends Dialog {
	private Application app;
	private ReservedDialog curr;
	private Label labelRoom = new Label();
	private TextField textRoomId = new TextField();
	private Label labelGuest = new Label();
	private Label labelEnd = new Label();
	private Button buttonGuest = new Button("Guest Form");
	private Button buttonDone = new Button("DONE");
	private boolean endFlag = false;
	
	public ReservedDialog(Application app) {
		super(app,"Reservation Form",true);
		this.app = app;
		this.curr = this;
		setBounds(100,100,500,200);
		setLayout(new GridLayout(3,2));
		Font font = new Font("Arial",Font.BOLD,10);
		labelRoom.setFont(font);
		labelRoom.setText("Room ID:");
		labelGuest.setFont(font);
		labelGuest.setText("Enter Guests Info: ");
		labelEnd.setFont(font);
		labelEnd.setText("");
		add(labelGuest);
		add(buttonGuest);
		add(labelRoom);
		add(textRoomId);
		add(labelEnd);
		add(buttonDone);
		addListeners();
	}
	
	public void setTextRoomId(String s) {
		textRoomId.setText(s);
	}
	
	public void setLabelEnd(String s) {
		labelEnd.setText(s);
	}
	
	public void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		buttonGuest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					endFlag = true;
					GuestAddDialog gad = new GuestAddDialog(curr);
					gad.setVisible(true);
				}
		});
		
		buttonDone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(endFlag) {
					endFlag = false;
					setVisible(false);
					textRoomId.setText("");
					labelEnd.setText("");
				}
				else {
					labelEnd.setText("You need to fill Guest Form");
				}
			}
			
		});
	}
}
