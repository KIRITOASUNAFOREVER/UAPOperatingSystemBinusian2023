package nachos.FAmouzamoz;

public class Cookie implements Runnable{
	private String cookieName;
	private String cookieSize;
	private int cookiePrice;
	public Cookie(String cookieName, String cookieSize, int cookiePrice) {
		super();
		this.cookieName = cookieName;
		this.cookieSize = cookieSize;
		this.cookiePrice = cookiePrice;
	}
	public String getCookieName() {
		return cookieName;
	}
	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
	public String getCookieSize() {
		return cookieSize;
	}
	public void setCookieSize(String cookieSize) {
		this.cookieSize = cookieSize;
	}
	public int getCookiePrice() {
		return cookiePrice;
	}
	public void setCookiePrice(int cookiePrice) {
		this.cookiePrice = cookiePrice;
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
