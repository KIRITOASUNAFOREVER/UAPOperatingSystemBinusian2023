package nachos.ItemMuW;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class ItemMuW {
	private MyConsole consoleScan = new MyConsole();
	private MyNetworkLink netLink = new MyNetworkLink();

	public ItemMuW() {
		int pilihan;
		do {
			consoleScan.cetakEnter(""); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("User ID: " + netLink.getAddress());
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("|             MENU             |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("| 1. Send Product              |");
			consoleScan.cetakEnter("| 2. Receive Product(s)        |");
			consoleScan.cetakEnter("| 3. Exit                      |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				sendProduct();
				break;
			case 2:
				receiveProducts();
				break;
			case 3:
				consoleScan.cetakEnter("Ticks of time: "+Machine.timer().getTime());
				consoleScan.cetakEnter("Thank You! See You Next Time");
				break;
			}
		}while(pilihan < 1 || pilihan > 3 || pilihan !=3);
	}
	
	private boolean checkProductName(String ProductName) {
		if(ProductName.length() >= 6 && ProductName.length() <= 20) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkProductType(String ProductType) {
		if(ProductType.equals("Currency") || ProductType.equals("Item") || ProductType.equals("Account")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkProductPrice(int ProductPrice) {
		if(ProductPrice >= 10000 && ProductPrice <= 1000000) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkProductQuantity(int ProductQuantity) {
		if(ProductQuantity >= 1 && ProductQuantity <= 50) {
			return true;
		}else {
			return false;
		}
	}
	
	private void sendProduct() {
		String productName;
		do {
			consoleScan.cetak("Input product name [ 6 - 20 characters ] : ");
			productName = consoleScan.bacaString();
		}while(!checkProductName(productName));
		
		String productType;
		do {
			consoleScan.cetak("Input product type [ Currency | Item | Account ] : ");
			productType = consoleScan.bacaString();
		}while(!checkProductType(productType));
		
		int productPrice;
		do {
			consoleScan.cetak("Input product Price [ 10000 - 1000000 ] : ");
			productPrice = consoleScan.bacaInteger();
		}while(!checkProductPrice(productPrice));
		
		int productQuantity;
		do {
			consoleScan.cetak("Input product quantity [ 1 - 50 ] : ");
			productQuantity = consoleScan.bacaInteger();
		}while(!checkProductQuantity(productQuantity));
		
		int destinasiUserID;
		consoleScan.cetak("Input User ID You Want to Send This Product: ");
		destinasiUserID = consoleScan.bacaInteger();
		
		Product products = new Product(productName, productType, productPrice, productQuantity);
		netLink.sendProduct(products, destinasiUserID);
		consoleScan.cetakEnter("Product has Successfully sent to User With ID " + destinasiUserID);
	}
	
	private void receiveProducts() {
		if(netLink.getProducts().isEmpty()){
			consoleScan.cetakEnter("There is No Product Sent to You. Check Again Another Time!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		while(!netLink.getProducts().isEmpty()){
			new KThread(netLink.getProducts().remove(0)).fork();;
		}
	}

	public static void main(String[] args) {
		new ItemMuW();
	}
}