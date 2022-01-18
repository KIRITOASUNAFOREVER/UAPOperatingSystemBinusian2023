package nachos.ItemMuW;

public class Product implements Runnable{
	private String productName;
	private String productType;
	private int productPrice;
	private int productQuantity;
	public Product(String productName, String productType, int productPrice, int productQuantity) {
		super();
		this.productName = productName;
		this.productType = productType;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	@Override
	public void run() {
		System.out.println("Product Name : " + this.productName + " ( Type : " + this.productType + " )");
		System.out.println("Quantity     : " + this.productQuantity);
		System.out.println("Price        : " + this.productPrice);
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
