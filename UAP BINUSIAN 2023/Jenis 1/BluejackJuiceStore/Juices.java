package nachos.BluejackJuiceStore;

public class Juices implements Runnable{
	private String juicesName;
	private int juicesPrice;
	private String juicesIce;
	
	public Juices(String juicesName, int juicesPrice, String juicesIce) {
		super();
		this.juicesName = juicesName;
		this.juicesPrice = juicesPrice;
		this.juicesIce = juicesIce;
	}

	public String getJuicesName() {
		return juicesName;
	}

	public void setJuicesName(String juicesName) {
		this.juicesName = juicesName;
	}

	public int getJuicesPrice() {
		return juicesPrice;
	}

	public void setJuicesPrice(int juicesPrice) {
		this.juicesPrice = juicesPrice;
	}

	public String getJuicesIce() {
		return juicesIce;
	}

	public void setJuicesIce(String juicesIce) {
		this.juicesIce = juicesIce;
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
