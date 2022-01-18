package nachos.Paypol;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class Paypol {
	private MyConsole consoleScan = new MyConsole();
	private MyNetworkLink netLink = new MyNetworkLink();
	
	private void logo() {
		consoleScan.cetakEnter("  _____                        _ ");
		consoleScan.cetakEnter(" |  __ \\                      | |");
		consoleScan.cetakEnter(" | |__) |_ _ _   _ _ __   ___ | |");
		consoleScan.cetakEnter(" |  ___/ _` | | | | '_ \\ / _ \\| |");
		consoleScan.cetakEnter(" | |  | (_| | |_| | |_) | (_) | |");
		consoleScan.cetakEnter(" |_|   \\__,_|\\__, | .__/ \\___/|_|");
		consoleScan.cetakEnter("              __/ | |            ");
		consoleScan.cetakEnter("             |___/|_|            ");
	}
	
	public Paypol() {
		int pilihan;
		do {
			logo();
			consoleScan.cetakEnter(""); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("Bank Account ID: " + netLink.getAddress());
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("|             MENU             |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("| 1. Transfer Money            |");
			consoleScan.cetakEnter("| 2. Receive Money(s)          |");
			consoleScan.cetakEnter("| 3. Exit                      |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				transferMoney();
				break;
			case 2:
				receiveMoneys();
				break;
			case 3:
				consoleScan.cetakEnter("Ticks of time: "+Machine.timer().getTime());
				consoleScan.cetakEnter("Thank You! See You Next Time");
				break;
			}
		}while(pilihan < 1 || pilihan > 3 || pilihan !=3);
	}
	
	private boolean checkUserEmail(String UserEmail) {
		if(UserEmail.endsWith("@mail.com")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUserUsername(String UserUsername[]) {
		if(UserUsername.length >= 2) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUserPassword(String UserPassword) {
		if(UserPassword.length() >= 6 && UserPassword.length() <= 20) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUserConfirmPassword(String UserPassword, String UserConfirmPassword) {
		if(UserConfirmPassword.equals(UserPassword)) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUserCreditCard(String CreditCard) {
		if(CreditCard.equals("Mastercard") || CreditCard.equals("Visa") || CreditCard.equals("Discover")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUserTopUpBalance(int TopUpBalance) {
		if(TopUpBalance >= 100 && TopUpBalance <= 1000) {
			return true;
		}else {
			return false;
		}
	}
	
	private void transferMoney() {
		String userEmail;
		do {
			consoleScan.cetak("Input Email [ end with @mail.com ] : ");
			userEmail = consoleScan.bacaString();
		}while(!checkUserEmail(userEmail));
		
		String userUsername;
		String arr[];
		do {
			consoleScan.cetak("Input username [ 2 words ] : ");
			userUsername = consoleScan.bacaString();
			arr = userUsername.split(" ");
		}while(!checkUserUsername(arr));
		
		String userPassword;
		do {
			consoleScan.cetak("Input Password [ 6 - 20 characters ] : ");
			userPassword = consoleScan.bacaString();
		}while(!checkUserPassword(userPassword));
		
		String userConfirmPassword;
		do {
			consoleScan.cetak("Confirm Password [ Case Sensitive ] : ");
			userConfirmPassword = consoleScan.bacaString();
		}while(!checkUserConfirmPassword(userPassword,userConfirmPassword));
		
		String userCreditCard;
		do {
			consoleScan.cetak("Input Credit Card [ Mastercard | Visa | Discover ] : ");
			userCreditCard = consoleScan.bacaString();
		}while(!checkUserCreditCard(userCreditCard));
		
		int userTopUpBalance;
		do {
			consoleScan.cetak("Insert Top Up [ 100 - 1000 ] : ");
			userTopUpBalance = consoleScan.bacaInteger();
		}while(!checkUserTopUpBalance(userTopUpBalance));
		
		int userAdminFee;
		userAdminFee = ((userTopUpBalance * 5)/100);
		
		int userFinalBalance;
		userFinalBalance = userTopUpBalance - userAdminFee;
		
		int destinasiBankAccountID;
		consoleScan.cetak("Input Bank Account ID You Want to Transfer This Money: ");
		destinasiBankAccountID = consoleScan.bacaInteger();
		
		TopUP topup = new TopUP(userEmail, userUsername, userCreditCard, userTopUpBalance, userAdminFee, userFinalBalance);
		netLink.sendTopUP(topup, destinasiBankAccountID);
		consoleScan.cetakEnter("Money has Successfully Transfer to User With ID " + destinasiBankAccountID);
	}
	
	private void receiveMoneys() {
		if(netLink.getTopUP().isEmpty()){
			consoleScan.cetakEnter("There is No Transferred Money to You. Check Again Another Time!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		while(!netLink.getTopUP().isEmpty()){
			new KThread(netLink.getTopUP().remove(0)).fork();
		}
	}

	public static void main(String[] args) {
		new Paypol();
	}
}