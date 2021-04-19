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
import hotel.Room;
import hotel.RoomType;

public class RoomAddDialog extends Dialog{
	private Application app;
	private TextField textBedsNumber = new TextField();
	private TextField textType = new TextField();
	private TextField textPricePerNight = new TextField();
	private Button doneButton = new Button("DONE");
	private boolean [] endFlags = {false,false,false};
	private int bedsNumber;
	private String type;
	private int pricePerNight;
	
	public RoomAddDialog(Application app) {
		super(app,"Add Room",true);
		this.app = app;
		setBounds(100,100,500,200);
		setLayout(new GridLayout(4,2));
		
		Label labelBedsNumber = new Label("How many beds are there?");
		Label labelType = new Label("What is type of room?");
		Label labelPricePerNigh = new Label("How much is the room for single night?");
		
		add(labelBedsNumber);
		add(textBedsNumber);
		add(labelType);
		add(textType);
		add(labelPricePerNigh);
		add(textPricePerNight);
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
		
		textBedsNumber.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bedsNumber = Integer.parseInt(textBedsNumber.getText());
				endFlags[0] = true;
			}
		});
		
		textType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type = textType.getText();
				endFlags[1] = true;
			}
		});
	
		textPricePerNight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pricePerNight = Integer.parseInt(textPricePerNight.getText());
				endFlags[2] = true;
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(endFlags[0] && endFlags[1] && endFlags[2]) {
					ResultSet rs = null;
					try {
						rs = RoomTypeEndpoint.readRoomTypeByType(type);
						if(!rs.isBeforeFirst()) {
							RoomType rt = new RoomType(1,type);
							RoomTypeEndpoint.insertRoomType(rt);
							rs = RoomTypeEndpoint.readRoomTypeByType(type);
						}
						int id = rs.getInt("ID");
						RoomType rt = new RoomType(id,type);
						Room r = new Room(1,bedsNumber,pricePerNight,rt,0);
						RoomEndpoint.insertRoom(r);
						setVisible(false);
						endFlags = new boolean[] {false,false,false};
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
