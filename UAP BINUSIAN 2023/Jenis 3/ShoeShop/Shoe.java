package nachos.ShoeShop;

public class Shoe implements Runnable{
	private String shoeID;
	private String shoeName;
	private String shoeCategory;
	private String shoeReleaseDate;
	private int shoePrice;
	public Shoe(String shoeID, String shoeName, String shoeCategory, String shoeReleaseDate, int shoePrice) {
		super();
		this.shoeID = shoeID;
		this.shoeName = shoeName;
		this.shoeCategory = shoeCategory;
		this.shoeReleaseDate = shoeReleaseDate;
		this.shoePrice = shoePrice;
	}
	public String getShoeID() {
		return shoeID;
	}
	public void setShoeID(String shoeID) {
		this.shoeID = shoeID;
	}
	public String getShoeName() {
		return shoeName;
	}
	public void setShoeName(String shoeName) {
		this.shoeName = shoeName;
	}
	public String getShoeCategory() {
		return shoeCategory;
	}
	public void setShoeCategory(String shoeCategory) {
		this.shoeCategory = shoeCategory;
	}
	public String getShoeReleaseDate() {
		return shoeReleaseDate;
	}
	public void setShoeReleaseDate(String shoeReleaseDate) {
		this.shoeReleaseDate = shoeReleaseDate;
	}
	public int getShoePrice() {
		return shoePrice;
	}
	public void setShoePrice(int shoePrice) {
		this.shoePrice = shoePrice;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
