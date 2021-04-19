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

import endpoints.RoomTypeEndpoint;
import hotel.RoomType;

public class RoomTypeDialog extends Dialog{
	enum Operation{
		ADD,
		DELETE
	}
	private Application app;
	private Button doneButton = new Button("DONE");
	private TextField textType = new TextField();
	private boolean endFlag = false;
	private String nameForType;
	private Operation operation;
	private Label endLabel = new Label("");
	
	public RoomTypeDialog(Application app, Operation operation) {
		super(app,"Room Type",true);
		setBounds(100,100,400,100);
		setLayout(new GridLayout(2,2));
		this.app = app;
		this.operation = operation;
		Label label = new Label("Name for room type: ");
		textType.setText("Type name here");
		
		add(label);
		add(textType);
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
		
		textType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameForType = textType.getText();
				endFlag = true;
			}
		});
		
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(endFlag) {
					if(operation == Operation.ADD) {
						ResultSet rs = RoomTypeEndpoint.readRoomTypeByType(nameForType);
						try {
							if(!rs.isBeforeFirst()) {
								RoomType rt = new RoomType(1, nameForType);
								RoomTypeEndpoint.insertRoomType(rt);
								endFlag = false;
								endLabel.setText("");
								setVisible(false);
							}
							else {
								endLabel.setText("Room type already exists");
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
					}else {
						ResultSet rsId = RoomTypeEndpoint.readRoomTypeByType(nameForType);
						ResultSet rsCount = null;
						try {
							if(rsId.isBeforeFirst()) {
								int id = rsId.getInt("ID");
								rsCount = RoomTypeEndpoint.countType(id);
								int count = rsCount.getInt("NumberOfType");
								if(count == 0) {
									RoomTypeEndpoint.deleteRoomType(nameForType);
									setVisible(false);
									endFlag = false;
									endLabel.setText("");
								}
								else {
									endLabel.setText("There is still rooms with that type");
								}
							}
							else {
								endLabel.setText("That type does not exists");
							}
						} catch (SQLException e1) {
							System.out.println(e1.toString());
						}finally {
							try {
								if(rsId!=null)rsId.close();
								if(rsCount!=null)rsCount.close();
							} catch (SQLException e1) {
								System.out.println(e1.toString());
							}
						}
					}
				}
			}
		});
	}

}
