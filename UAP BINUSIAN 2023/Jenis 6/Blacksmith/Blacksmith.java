package nachos.Blacksmith;

import java.util.Random;
import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class Blacksmith {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Weapon> weaponList = new Vector<>();
	private Random rand = new Random();
	
	public Blacksmith() {
		int pilihan;
		do {
			loadData();
			consoleScan.cetakEnter("  Blacksmith  ");
			consoleScan.cetakEnter("==============");
			consoleScan.cetakEnter("1. Add Weapon");
			consoleScan.cetakEnter("2. View Weapon");
			consoleScan.cetakEnter("3. Delete Weapon");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan) {
				case 1:
					addWeaponData();
					break;
				case 2:
					viewWeaponData();
					break;
				case 3:
					deleteWeaponData();
					break;
				case 4:
					consoleScan.cetakEnter("Time: "+Machine.timer().getTime());
					consoleScan.cetakEnter("Thank you. Good Bye!");
					break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		weaponList = new Vector<Weapon>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Weapon component= new Weapon(eachDatas[0], eachDatas[1], eachDatas[2], Integer.parseInt(eachDatas[3]), Integer.parseInt(eachDatas[4]));
			weaponList.add(component);
		}
	}
	
	private boolean checkUniqueWeaponName(String WeaponName){
		for(int i=0; i < weaponList.size();i++){
			if(weaponList.get(i).getWeaponName().equals(WeaponName)){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkWeaponQuality(String WeaponQuality) {
		if(WeaponQuality.equals("Low") || WeaponQuality.equals("Medium") || WeaponQuality.equals("High")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkWeaponNameLength(String WeaponName) {
		if(WeaponName.length() >= 3 && WeaponName.length() <= 12) {
			return true;
		}else {
			return false;
		}
	}
	
	private int generateWeaponPower(String WeaponQuality) {
		int tempWeaponPower = 0;
		if(WeaponQuality.equals("Low")){
			tempWeaponPower = rand.nextInt(5000);
		}else if(WeaponQuality.equals("Medium")){
			tempWeaponPower = 5000 + rand.nextInt(5000);
		}else if(WeaponQuality.equals("High")){
			tempWeaponPower = 10000 + rand.nextInt(5000);
		}
		return tempWeaponPower;
	}
	
	private int generateWeaponPrice(String WeaponQuality, int WeaponPower) {
		int tempWeaponPrice = 0;
		if(WeaponQuality.equals("Low")){
			tempWeaponPrice = 2000 + WeaponPower;
		}else if(WeaponQuality.equals("Medium")){
			tempWeaponPrice = 3000 + WeaponPower;
		}else if(WeaponQuality.equals("High")){
			tempWeaponPrice = 4000 + WeaponPower;
		}
		return tempWeaponPrice;
	}
	
	private void addWeaponData() {
		String tempWeaponID;
		tempWeaponID = Character.toString((char)(rand.nextInt(26) + 'A')) + Character.toString((char)(rand.nextInt(26) + 'A')) + rand.nextInt(9) + rand.nextInt(9)  + rand.nextInt(9);
		
		String tempWeaponName;
		boolean check = false;
		do{
			consoleScan.cetak("Input weapon name [3-12 Character] : ");
			tempWeaponName = consoleScan.bacaString();
			check = checkUniqueWeaponName(tempWeaponName);
		}while(check || !checkWeaponNameLength(tempWeaponName));
		
		String tempWeaponQuality;
		do{
			consoleScan.cetak("Input weapon quality [Low | Medium | High](Case Sensitive) : ");
			tempWeaponQuality = consoleScan.bacaString();
		}while(!checkWeaponQuality(tempWeaponQuality));
		
		int tempWeaponPower = 0;
		tempWeaponPower = generateWeaponPower(tempWeaponQuality);
		
		int tempWeaponPrice = 0;
		tempWeaponPrice = generateWeaponPrice(tempWeaponQuality,tempWeaponPower);
		
		String isiFile = tempWeaponID + "#" + tempWeaponName + "#" + tempWeaponQuality + "#" + tempWeaponPower + "#" + tempWeaponPrice;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Add weapon success...");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void cetakDataView() {
		consoleScan.cetakEnter("========================================================");
		consoleScan.cetakEnter("| ID    | Name          | Quality | Power | Price      |");
		consoleScan.cetakEnter("========================================================");
		while (!weaponList.isEmpty()) {
			new KThread(weaponList.remove(0)).fork();
		}
		consoleScan.cetakEnter("========================================================");
	}

	private void viewWeaponData() {
		if(weaponList.isEmpty()){
			consoleScan.cetak("There is no data..");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		cetakDataView();
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void cetakDataDelete() {
		consoleScan.cetakEnter("========================================================");
		consoleScan.cetakEnter("| ID    | Name          | Quality | Power | Price      |");
		consoleScan.cetakEnter("========================================================");
		
		int panjang = weaponList.size();
		for(int i = panjang-1 ; i >= 0 ; i--) {
			consoleScan.cetak("| " + weaponList.get(i).getWeaponID() + " | " + weaponList.get(i).getWeaponName());
			int sisaNama = 14 - weaponList.get(i).getWeaponName().length();
			for(int j = 0 ; j < sisaNama ; j++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetak("| " + weaponList.get(i).getWeaponQuality());
			int sisaQuality = 8 - weaponList.get(i).getWeaponQuality().length();
			for(int k = 0 ; k < sisaQuality ; k++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetak("| " + weaponList.get(i).getWeaponPower());
			String weaponPowers = String.valueOf(weaponList.get(i).getWeaponPower());
			int sisaPower = 6 - weaponPowers.length();
			for(int l = 0 ; l < sisaPower ; l++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetak("| " + weaponList.get(i).getWeaponPrice());
			String weaponPrices = String.valueOf(weaponList.get(i).getWeaponPrice());
			int sisaPrice = 6 - weaponPrices.length();
			for(int m = 0 ; m < sisaPrice ; m++) {
				consoleScan.cetak(" ");
			}
			consoleScan.cetakEnter("gold |");
		}
		consoleScan.cetakEnter("========================================================");
	}
	
	public void overWrite(Vector<Weapon> weaponList) {
		String txt="";
		for (Weapon weapon : weaponList) {
			txt+=(weapon.getWeaponID() + "#" + weapon.getWeaponName() + "#" + weapon.getWeaponQuality() + "#" + weapon.getWeaponPower() + "#" + weapon.getWeaponPrice() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private boolean checkIDExists(String WeaponID) {
		int panjang = weaponList.size();
		for(int i = 0 ; i < panjang ; i++) {
			if(weaponList.get(i).getWeaponID().equals(WeaponID)) {
				return true;
			}
		}
		return false;
	}
	
	private int findRealIndex(String WeaponID) {
		int indexs = -1;
		int panjang = weaponList.size();
		for(int i = 0 ; i < panjang ; i++) {
			if(weaponList.get(i).getWeaponID().equals(WeaponID)) {
				indexs = i;
				break;
			}
		}
		return indexs;
	}
	
	private void deleteWeaponData() {
		if(weaponList.isEmpty()){
			consoleScan.cetak("There is no data..");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		cetakDataDelete();
		consoleScan.cetakEnter("");
		
		String delete = "";
		do{
			consoleScan.cetak("Input Weapon ID to delete | 0 to cancel]: ");
			delete = consoleScan.bacaString();
			if(delete.equals("0")){
				return;
			}
		}while(!checkIDExists(delete));
		
		int indeksAsli = -1;
		indeksAsli = findRealIndex(delete);
		
		weaponList.remove(indeksAsli);
		overWrite(weaponList);
		consoleScan.cetakEnter("Delete success..");
		consoleScan.cetak("Press enter to continue...");
		consoleScan.bacaString();
	}

	public static void main(String[] args) {
		new Blacksmith();
	}

}
