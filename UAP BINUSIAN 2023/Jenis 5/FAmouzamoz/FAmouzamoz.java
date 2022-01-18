package nachos.FAmouzamoz;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class FAmouzamoz {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Cookie> cookieList = new Vector<>();
	
	
	public FAmouzamoz() {
		int pilihan;
		do {
			loadData();
			consoleScan.cetakEnter("+=====================+");
			consoleScan.cetakEnter("|    FAmouz amoz      |");
			consoleScan.cetakEnter("+=====================+");
			consoleScan.cetakEnter("| 1. Add Cookie       |");
			consoleScan.cetakEnter("| 2. Remove Cookie    |");
			consoleScan.cetakEnter("| 3. View All Cookie  |");
			consoleScan.cetakEnter("| 4. Exit             |");
			consoleScan.cetakEnter("+=====================+");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				AddCookie();
				break;
			case 2:
				removeCookie();
				break;
			case 3:
				viewAllCookie();
				break;
			case 4:
				consoleScan.cetakEnter("Tick of time: "+Machine.timer().getTime());
				break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan != 4);
	}
	
	private void loadData(){
		cookieList = new Vector<Cookie>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Cookie cookie = new Cookie(eachDatas[0], eachDatas[1], Integer.parseInt(eachDatas[2]));
			cookieList.add(cookie);
		}
	}
	
	private boolean checkCookieName(String CookieName) {
		if(CookieName.endsWith("Cookie")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkCookieSize(String CookieSize) {
		if(CookieSize.equals("Small") || CookieSize.equals("Medium") || CookieSize.equals("Large")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkCookiePrice(int CookiePrice) {
		if(CookiePrice >= 20000 && CookiePrice <= 100000) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkCookieNameLength(String arr[]) {
		if(arr.length >= 2) {
			return true;
		}else {
			return false;
		}
	}
	
	private void AddCookie() {
		String CookieName;
		String arr[];
		do {
			consoleScan.cetak("Input Cookie Name [must ends with 'Cookie']: ");
			CookieName = consoleScan.bacaString();
			arr = CookieName.split(" ");
		}while(!checkCookieName(CookieName) || !checkCookieNameLength(arr));
		
		String CookieSize;
		do {
			consoleScan.cetak("Input Cookie Size [Small | Medium | Large] (case sensitive): ");
			CookieSize = consoleScan.bacaString();
		}while(!checkCookieSize(CookieSize));
		
		int CookiePrice;
		do {
			consoleScan.cetak("Input Cookie Price [20000 - 100000]: ");
			CookiePrice = consoleScan.bacaInteger();
		}while(!checkCookiePrice(CookiePrice));
		
		String isiFile = CookieName + "#" + CookieSize + "#" + CookiePrice;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("New cookie has been added successfully!");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Cookie> CookieList) {
		String txt="";
		for (Cookie cookie : CookieList) {
			txt+=(cookie.getCookieName() + "#" + cookie.getCookieSize() + "#" + cookie.getCookiePrice() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void removeCookie() {
		if(cookieList.isEmpty()){
			consoleScan.cetak("There are no cookies yet!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		int hitung = 0;
		for (Cookie cookie : cookieList) {
			consoleScan.cetakEnter("==========================");
			consoleScan.cetakEnter("No. " + (++hitung) + " - " + cookie.getCookieName());
			consoleScan.cetakEnter("==========================");
			consoleScan.cetakEnter("Size  : " + cookie.getCookieSize());
			consoleScan.cetakEnter("Price : " + cookie.getCookiePrice());
			consoleScan.cetakEnter("");
		}
		
		int indeks = -1;
		do {
			consoleScan.cetak("Input Cookie's index to remove [1 - " + cookieList.size() + "]: ");
			indeks = consoleScan.bacaInteger();
		}while(indeks < 1 || indeks > cookieList.size());
		
		cookieList.remove(indeks-1);
		overWrite(cookieList);
		consoleScan.cetakEnter("Cookie removed successfully!");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void viewAllCookie() {
		if(cookieList.isEmpty()){
			consoleScan.cetak("There are no cookies to display!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		int hitung = 0;
		consoleScan.cetakEnter("Your Cookies:");
		for (Cookie cookie : cookieList) {
			consoleScan.cetakEnter("==========================");
			consoleScan.cetakEnter("No. " + (++hitung) + " - " + cookie.getCookieName());
			consoleScan.cetakEnter("==========================");
			consoleScan.cetakEnter("Size  : " + cookie.getCookieSize());
			consoleScan.cetakEnter("Price : " + cookie.getCookiePrice());
			new KThread(cookie).fork();
			consoleScan.cetakEnter("");
		}
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}

	public static void main(String[] args) {
		new FAmouzamoz();
	}

}
