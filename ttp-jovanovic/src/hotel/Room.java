package hotel;

public class Room {
	private int id;
	private int bedsNumber;
	private int prisePerNight;
	private RoomType type;
	private int taken = 0;
	
	public Room(int id, int bedsNumber, int prisePerNight, RoomType type, int taken) {
		super();
		this.id = id;
		this.bedsNumber = bedsNumber;
		this.prisePerNight = prisePerNight;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBedsNumber() {
		return bedsNumber;
	}
	public void setBedsNumber(int bedsNumber) {
		this.bedsNumber = bedsNumber;
	}
	public int getPrisePerNight() {
		return prisePerNight;
	}
	public void setPrisePerNight(int prisePerNight) {
		this.prisePerNight = prisePerNight;
	}
	public RoomType getType() {
		return type;
	}
	public void setType(RoomType type) {
		this.type = type;
	}
	
	public int getTaken() {
		return taken;
	}
	
	public void setTaken(int taken) {
		this.taken = taken;
	}
}
