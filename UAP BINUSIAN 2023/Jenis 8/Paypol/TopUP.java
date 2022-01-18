package nachos.Paypol;

public class TopUP implements Runnable{
	private String email;
	private String Username;
	private String creditCard;
	private int TopUpBalance;
	private int adminFee;
	private int finalBalance;
	public TopUP(String email, String username, String creditCard, int topUpBalance, int adminFee, int finalBalance) {
		super();
		this.email = email;
		Username = username;
		this.creditCard = creditCard;
		TopUpBalance = topUpBalance;
		this.adminFee = adminFee;
		this.finalBalance = finalBalance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public int getTopUpBalance() {
		return TopUpBalance;
	}
	public void setTopUpBalance(int topUpBalance) {
		TopUpBalance = topUpBalance;
	}
	public int getAdminFee() {
		return adminFee;
	}
	public void setAdminFee(int adminFee) {
		this.adminFee = adminFee;
	}
	public int getFinalBalance() {
		return finalBalance;
	}
	public void setFinalBalance(int finalBalance) {
		this.finalBalance = finalBalance;
	}
	@Override
	public void run() {
		System.out.println("+====================================================+");
		System.out.print("| Email              : " + this.email);
		int sisaEmail = 30 - this.email.length();
		for(int i = 0 ; i < sisaEmail ; i++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.print("| Username           : " + this.Username);
		int sisaUsername = 30 - this.Username.length();
		for(int j = 0 ; j < sisaUsername ; j++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.print("| Credit Card        : " + this.creditCard);
		int sisaCreditCard = 30 - this.creditCard.length();
		for(int k = 0 ; k < sisaCreditCard ; k++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.print("| Top Up             : $ " + this.TopUpBalance);
		String TopUpBalances = String.valueOf(this.TopUpBalance);
		int sisaTopUpBalances = 28 - TopUpBalances.length();
		for(int l = 0 ; l < sisaTopUpBalances ; l++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.print("| Administration Fee : $ " + this.adminFee);
		String Administration = String.valueOf(this.adminFee);
		int sisaAdministration = 28 - Administration.length();
		for(int m = 0 ; m < sisaAdministration ; m++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.print("| Balance            : $ " + this.finalBalance);
		String Balance = String.valueOf(this.finalBalance);
		int sisaBalance = 28 - Balance.length();
		for(int n = 0 ; n < sisaBalance ; n++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.println("+====================================================+");
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
