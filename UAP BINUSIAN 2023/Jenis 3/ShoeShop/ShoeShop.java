package nachos.ShoeShop;

import java.util.Random;
import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class ShoeShop {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Shoe> shoeList = new Vector<>();
	Random rand  = new Random();
	
	public ShoeShop() {
		int choose;
		do{
			loadData();
			consoleScan.cetakEnter("Shoe Shop");
			consoleScan.cetakEnter("=========");
			consoleScan.cetakEnter("1. View Shoes");
			consoleScan.cetakEnter("2. Add Shoe");
			consoleScan.cetakEnter("3. Delete Shoe");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			choose = consoleScan.bacaInteger();
			switch(choose)
			{
			case 1:
				viewShoeData();
				break;
			case 2:
				addShoeData();
				break;
			case 3:
				deleteShoeData();
				break;
			case 4:
				consoleScan.cetakEnter("The program ended in "+Machine.timer().getTime() / 10000000 +" second(s).");
				break;
			}
		}while(choose > 4 || choose < 1 || choose!=4);
	}
	
	private void loadData(){
		shoeList = new Vector<Shoe>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Shoe component= new Shoe(eachDatas[0], eachDatas[1], eachDatas[2], eachDatas[3], Integer.parseInt(eachDatas[4]));
			shoeList.add(component);
		}
	}
	
	private void viewShoeData() {
		if(shoeList.isEmpty()){
			consoleScan.cetak("No shoes avaiable..");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		int indeks = 0;
		for (Shoe shoe : shoeList) {
			consoleScan.cetakEnter((++indeks) +" " +shoe.getShoeName()+"-"+shoe.getShoeID());
			consoleScan.cetakEnter("================");
			consoleScan.cetakEnter("Category: "+shoe.getShoeCategory());
			consoleScan.cetakEnter("Release date: "+shoe.getShoeReleaseDate());
			consoleScan.cetakEnter("Price: "+shoe.getShoePrice());
			consoleScan.cetakEnter("");
			new KThread(shoe).fork();
		}
		consoleScan.cetak("Press enter to continue...");
		consoleScan.bacaString();
		consoleScan.cetakEnter("");
	}
	
	private boolean checkShoeName(String ShoeName) {
		if(ShoeName.endsWith(" shoe") || ShoeName.endsWith(" Shoe")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkShoeCategory(String ShoeCategory) {
		if(ShoeCategory.equals("Sneaker") || ShoeCategory.equals("Running") || ShoeCategory.equals("Boot")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkShoePrice(int ShoePrice) {
		if(ShoePrice >= 5000) {
			return true;
		}else {
			return false;
		}
	}
	
	private void addShoeData() {
		String tempShoeName;
		do{
			consoleScan.cetak("Input shoe's name[name ends with shoe, example: \"Fire shoe\"]: ");
			tempShoeName = consoleScan.bacaString();
		}while(!checkShoeName(tempShoeName));
		
		String tempShoeCategory;
		do{
			consoleScan.cetak("Input shoe's category[Sneaker | Running | Boot] (case sensitive): ");
			tempShoeCategory = consoleScan.bacaString();
		}while(!checkShoeCategory(tempShoeCategory));
		
		String releaseDate, tempShoeReleaseDate;
		String[] tampung;
		int tempDay = 0,tempMonth = 0,tempYear = 0;
		do {
			consoleScan.cetak("Input shoe's release date[dd-mm-yyyy]: ");
			releaseDate = consoleScan.bacaString();
			
		    tampung  = releaseDate.split("-");
			try {
				tempDay = Integer.parseInt(tampung[0]);
				tempMonth = Integer.parseInt(tampung[1]);
				tempYear = Integer.parseInt(tampung[2]);
			} catch (NumberFormatException e) {
				consoleScan.cetak("Input shoe's release date[dd-mm-yyyy]: ");
				releaseDate = consoleScan.bacaString();
			}
			
			tempShoeReleaseDate = tampung[0] + "-" + tampung[1] + "-" + tampung[2];
		} while ((tempDay  < 1 || tempDay > 30) || (tempMonth < 1 || tempMonth > 12) || (tempYear < 2000 || tempYear > 2022) );
		
		int tempShoePrice;
		do{
			consoleScan.cetak("Input shoe's price[more than or equals to 5000]: ");
			tempShoePrice = consoleScan.bacaInteger();
		}while(!checkShoePrice(tempShoePrice));
		
		String tempShoeID;
		tempShoeID = "SH" + rand.nextInt(9) + rand.nextInt(9)  + rand.nextInt(9);
		
		String isiFile = tempShoeID + "#" + tempShoeName + "#" + tempShoeCategory + "#" + tempShoeReleaseDate + "#" + tempShoePrice;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Shoe added!");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Shoe> ShoeList) {
		String txt="";
		for (Shoe shoe : ShoeList) {
			txt+=(shoe.getShoeID() + "#" + shoe.getShoeName() + "#" + shoe.getShoeCategory() + "#" + shoe.getShoeReleaseDate() + "#" + shoe.getShoePrice() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void deleteShoeData() {
		if(shoeList.isEmpty()){
			consoleScan.cetak("No shoes avaiable..");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		int indeks = 0;
		for (Shoe shoe : shoeList) {
			consoleScan.cetakEnter((++indeks) +" " +shoe.getShoeName()+"-"+shoe.getShoeID());
			consoleScan.cetakEnter("================");
			consoleScan.cetakEnter("Category: "+shoe.getShoeCategory());
			consoleScan.cetakEnter("Release date: "+shoe.getShoeReleaseDate());
			consoleScan.cetakEnter("Price: "+shoe.getShoePrice());
			consoleScan.cetakEnter("");
		}
		int delete;
		do{
			System.out.println("Choose shoe's number to delete[1.."+shoeList.size()+"]: ");
			delete = consoleScan.bacaInteger();
		}while(delete < 0 || delete > shoeList.size());
		
		shoeList.remove(delete-1);
		overWrite(shoeList);
		consoleScan.cetak("Shoe removed!");
		consoleScan.cetakEnter("");
		consoleScan.cetak("Press enter to continue...");
		consoleScan.bacaString();
		consoleScan.cetakEnter("");
	}

	public static void main(String[] args) {
		new ShoeShop();
	}

}
