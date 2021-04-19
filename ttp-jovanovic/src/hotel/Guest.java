package hotel;

public class Guest {
	private int id;
	private String firstName;
	private String lastName;
	private Room room;
	private int numberOfNights;
	
	public Guest(int id, String firstName, String lastName, Room room, int numberOfNights) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.room = room;
		this.numberOfNights = numberOfNights;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public int getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	
}
