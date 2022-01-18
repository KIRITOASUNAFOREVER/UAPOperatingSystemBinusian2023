package nachos.FashionNova;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class FashionNova {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Fashion> fashionList = new Vector<>();
	
	public FashionNova() {
		int pilihan;
		do {
			loadData();
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetakEnter("Welcome to FashionNova Store");
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetakEnter("1. View Catalogue");
			consoleScan.cetakEnter("2. Sell Product");
			consoleScan.cetakEnter("3. Add Product to Catalogue");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan) {
				case 1:
					viewCatalogue();
					break;
				case 2:
					SellProduct();
					break;
				case 3:
					addClothData();
					break;
				case 4:
					consoleScan.cetakEnter("Time: "+Machine.timer().getTime());
					consoleScan.cetakEnter("Thank you. Good Bye!");
					break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		fashionList = new Vector<Fashion>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Fashion component= new Fashion(Integer.parseInt(eachDatas[0]), eachDatas[1], eachDatas[2], Integer.parseInt(eachDatas[3]), Integer.parseInt(eachDatas[4]));
			fashionList.add(component);
		}
	}
	
	private boolean checkClothID(int ClothID) {
		if(ClothID >= 1 && ClothID <= 100) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkUniqueClothID(int ClothID) {
		int panjang = fashionList.size();
		for(int i = 0 ; i < panjang ; i++) {
			if(fashionList.get(i).getClothID() == ClothID) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkClothName(String ClothName) {
		if(ClothName.length() >= 5 && ClothName.length() <= 25) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkClothMaterial(String ClothMaterial) {
		if(ClothMaterial.equals("cotton") || ClothMaterial.equals("silk")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkClothStock(int ClothStock) {
		if(ClothStock >= 5 && ClothStock <= 50) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkClothPrice(int ClothPrice) {
		if(ClothPrice >= 100000) {
			return true;
		}else {
			return false;
		}
	}
	
	private void addClothData() {
		int ClothID;
		do {
			consoleScan.cetak("input ClothId [must be unique & is numeric(1-100)]: ");
			ClothID = consoleScan.bacaInteger();
			if(checkUniqueClothID(ClothID)) {
				consoleScan.cetakEnter("ClothID has Been Taken");
			}else if(!checkClothID(ClothID)) {
				consoleScan.cetakEnter("ClothID Must Between 1 - 100!");
			}
		}while(!checkClothID(ClothID) || checkUniqueClothID(ClothID));
		
		String ClothName;
		do {
			consoleScan.cetak("input ClothName [5-25 characters]: ");
			ClothName = consoleScan.bacaString();
		}while(!checkClothName(ClothName));
		
		String ClothMaterial;
		do {
			consoleScan.cetak("input ClothMaterial [cotton|silk, case sensitive]: ");
			ClothMaterial = consoleScan.bacaString();
		}while(!checkClothMaterial(ClothMaterial));
		
		int ClothStock;
		do {
			consoleScan.cetak("input stock [5-50]: ");
			ClothStock = consoleScan.bacaInteger();
		}while(!checkClothStock(ClothStock));
		
		int ClothPrice;
		do {
			consoleScan.cetak("input price [min. 100000]: ");
			ClothPrice = consoleScan.bacaInteger();
		}while(!checkClothPrice(ClothPrice));
		
		String isiFile = ClothID + "#" + ClothName + "#" + ClothMaterial + "#" + ClothStock + "#" + ClothPrice;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Cloth successfully added!");
		consoleScan.cetakEnter("press Enter to continue...");
		consoleScan.bacaString();
	}
	
	private void viewCatalogue() {
		if(fashionList.isEmpty()){
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetakEnter("        FashionNova Catalogue                ");
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetak("Nothing to display here");
			consoleScan.cetakEnter("");
			consoleScan.cetak("press Enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		consoleScan.cetakEnter("        FashionNova Catalogue                ");
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		for (Fashion fashion : fashionList) {
			consoleScan.cetakEnter("Cloth(ID-Name) : " + fashion.getClothID() + "-" + fashion.getClothName());
			consoleScan.cetakEnter("Details:");
			consoleScan.cetakEnter("- Material : " + fashion.getClothMaterial());
			consoleScan.cetakEnter("- Stock : " + fashion.getClothStock());
			consoleScan.cetakEnter("- Price(per pcs) : " + fashion.getClothPrice());
			new KThread(fashion).fork();
			consoleScan.cetakEnter("");
		}
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("press Enter to continue...");
		consoleScan.bacaString();
	}
	
	private boolean checkClothIDExistBuy(int ClothID) {
		int panjang = fashionList.size();
		for(int i = 0 ; i < panjang ; i++) {
			if(fashionList.get(i).getClothID() == ClothID) {
				return true;
			}
		}
		return false;
	}
	
	private int cariIndeksAsli(int ClothID) {
		int indeks = -1;
		int panjang = fashionList.size();
		for(int i = 0 ; i < panjang ; i++) {
			if(fashionList.get(i).getClothID() == ClothID) {
				indeks = i;
				break;
			}
		}
		return indeks;
	}
	
	public void overWrite(Vector<Fashion> fashList) {
		String txt="";
		for (Fashion fashion : fashList) {
			txt+=(fashion.getClothID() + "#" + fashion.getClothName() + "#" + fashion.getClothMaterial() + "#" + fashion.getClothStock() + "#" + fashion.getClothPrice() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void SellProduct() {
		if(fashionList.isEmpty()){
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetakEnter("Shopping Cart                ");
			consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
			consoleScan.cetak("No clothes on our store right now");
			consoleScan.cetakEnter("");
			consoleScan.cetak("press Enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		consoleScan.cetakEnter("        FashionNova Catalogue                ");
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		for (Fashion fashion : fashionList) {
			consoleScan.cetakEnter("Cloth(ID-Name) : " + fashion.getClothID() + "-" + fashion.getClothName());
			consoleScan.cetakEnter("Details:");
			consoleScan.cetakEnter("- Material : " + fashion.getClothMaterial());
			consoleScan.cetakEnter("- Stock : " + fashion.getClothStock());
			consoleScan.cetakEnter("- Price(per pcs) : " + fashion.getClothPrice());
			consoleScan.cetakEnter("");
		}
		
		int buyClothID;
		
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		consoleScan.cetakEnter("Shopping Cart                ");
		consoleScan.cetakEnter("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~");
		do {
			consoleScan.cetak("Cloth to buy (ClothID) : ");
			buyClothID = consoleScan.bacaInteger();
			if(!checkClothIDExistBuy(buyClothID)) {
				consoleScan.cetakEnter("ClothID didn't Exists!");
			}
		}while(!checkClothIDExistBuy(buyClothID));
		
		int indeksAsli = 0;
		indeksAsli = cariIndeksAsli(buyClothID);
		
		int price = (fashionList.get(indeksAsli).getClothPrice() * fashionList.get(indeksAsli).getClothStock());
		int tax = ((price*10)/100);
		int totalPrice = price + tax;
		consoleScan.cetakEnter("Price            : " + price);
		consoleScan.cetakEnter("tax              : " + tax);
		consoleScan.cetakEnter("Total Price      : " + totalPrice);
		consoleScan.cetakEnter("");
		
		String StokCloth = String.valueOf(fashionList.get(indeksAsli).getClothStock());
		String NamaCloth = fashionList.get(indeksAsli).getClothName();
		consoleScan.cetakEnter(StokCloth + " item(s) of " + NamaCloth + " sold successfully!");
		fashionList.remove(indeksAsli);
		overWrite(fashionList);
		consoleScan.cetakEnter("press Enter to continue...");
		consoleScan.bacaString();
	}

	public static void main(String[] args) {
		new FashionNova();
	}

}
