package gui;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;

import connection.DbConnection;
import endpoints.GuestEndpoint;
import endpoints.ReservedEndpoint;
import endpoints.RoomEndpoint;
import endpoints.RoomTypeEndpoint;
import gui.RoomTypeDialog.Operation;

public class Application extends Frame{
	private BufferedImage bi;
	private Application app;
	
	public Application(String s) {
		DbConnection.connect(s);
		GuestEndpoint.createGuestTable();
		ReservedEndpoint.createReservedTable();
		RoomEndpoint.createRoomTable();
		RoomTypeEndpoint.createRoomTypeTable();
		try {
			bi = ImageIO.read(new File("ttp-jovanovic//image//background.jpg"));
			revalidate();
		} catch (IOException e) {
			System.out.println("Can not find picture file");
		}
		setSize(800,600);
		setVisible(true);
		setResizable(false);
		addMenu();
		addListeners();
		app = this;
	}
	
	public void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				DbConnection.closeConnection();
				dispose();
			}
		});
	}
	
	public void addMenu() {
		MenuBar mb = new MenuBar();
		Menu add = new Menu("Add");
		Menu remove = new Menu("Delete");
		Menu view = new Menu("View");
		
		MenuItem addReservation = new MenuItem("Reservation");
		MenuItem addRoom = new MenuItem("Room");
		MenuItem addRoomType = new MenuItem("Room Type");
		
		addReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ReservedDialog rd = new ReservedDialog(app);
				rd.setVisible(true);
			}
		});
		
		addRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomAddDialog rad = new RoomAddDialog(app);
				rad.setVisible(true);
			}
		});
		
		addRoomType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomTypeDialog rtd = new RoomTypeDialog(app, Operation.ADD);
				rtd.setVisible(true);
			}
		});
		add.add(addReservation);
		add.add(addRoom);
		add.add(addRoomType);
		
		MenuItem deleteReservation = new MenuItem("Reservation");
		MenuItem deleteRoomType = new MenuItem("Room Type");
		MenuItem deleteRoom = new MenuItem("Room");
		
		deleteReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReservedDeleteDialog rdd = new ReservedDeleteDialog(app);
				rdd.setVisible(true);
			}
		});
		
		deleteRoomType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomTypeDialog rtd = new RoomTypeDialog(app, Operation.DELETE);
				rtd.setVisible(true);
			}
		});
		
		deleteRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RoomDeleteDialog rdd = new RoomDeleteDialog(app);
				rdd.setVisible(true);
			}
		});
		
		remove.add(deleteReservation);
		remove.add(deleteRoomType);
		remove.add(deleteRoom);
		
		MenuItem viewReservations = new MenuItem("Reservations");
		MenuItem viewRooms = new MenuItem("Free Rooms");
		MenuItem viewTypeOfRooms = new MenuItem("Type of Rooms");
		
		viewReservations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewFrame vf = new ViewFrame(ViewFrame.Operation.READ_RESERVATION);
			}
		});
		
		viewRooms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewFrame vf = new ViewFrame(ViewFrame.Operation.READ_FREEROOMS);
			}
		});
		
		viewTypeOfRooms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ViewFrame vf = new ViewFrame(ViewFrame.Operation.READ_ROOM_TYPES);
			}
		});
		
		view.add(viewReservations);
		view.add(viewRooms);
		view.add(viewTypeOfRooms);
		
		mb.add(add);
		mb.add(remove);
		mb.add(view);
		setMenuBar(mb);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bi, 0, 0,getWidth(),getHeight(), null);
		validate();
	}

	public static void main(String [] argv) {
		if(argv.length==0)
			System.out.println("You need to add the path to the data base");
		else {
			new Application(argv[0]);
		}
	}
}
