package nachos.FashionNova;

public class Fashion implements Runnable{
	private int clothID;
	private String clothName;
	private String clothMaterial;
	private int clothStock;
	private int clothPrice;
	public Fashion(int clothID, String clothName, String clothMaterial, int clothStock, int clothPrice) {
		super();
		this.clothID = clothID;
		this.clothName = clothName;
		this.clothMaterial = clothMaterial;
		this.clothStock = clothStock;
		this.clothPrice = clothPrice;
	}
	public int getClothID() {
		return clothID;
	}
	public void setClothID(int clothID) {
		this.clothID = clothID;
	}
	public String getClothName() {
		return clothName;
	}
	public void setClothName(String clothName) {
		this.clothName = clothName;
	}
	public String getClothMaterial() {
		return clothMaterial;
	}
	public void setClothMaterial(String clothMaterial) {
		this.clothMaterial = clothMaterial;
	}
	public int getClothStock() {
		return clothStock;
	}
	public void setClothStock(int clothStock) {
		this.clothStock = clothStock;
	}
	public int getClothPrice() {
		return clothPrice;
	}
	public void setClothPrice(int clothPrice) {
		this.clothPrice = clothPrice;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
