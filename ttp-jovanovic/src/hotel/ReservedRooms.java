package hotel;

public class ReservedRooms {
	private int id;
	private Room idRoom;
	private Guest idGuest;
	
	public ReservedRooms(int id, Room idRoom, Guest idGuest) {
		super();
		this.id = id;
		this.idRoom = idRoom;
		this.idGuest = idGuest;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Room getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(Room idRoom) {
		this.idRoom = idRoom;
	}
	public Guest getIdGuest() {
		return idGuest;
	}
	public void setIdGuest(Guest idGuest) {
		this.idGuest = idGuest;
	}
	
	
}
