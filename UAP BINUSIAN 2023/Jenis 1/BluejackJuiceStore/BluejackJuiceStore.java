package nachos.BluejackJuiceStore;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class BluejackJuiceStore {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Juices> juicesList = new Vector<>();
	
	public BluejackJuiceStore() {
		int pilihan;
		do {
			loadData();
			consoleScan.cetakEnter("Bluejack Juice Store");
			consoleScan.cetakEnter("======================");
			consoleScan.cetakEnter("1. Add Juice");
			consoleScan.cetakEnter("2. View Juice(s)");
			consoleScan.cetakEnter("3. Deliver");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan) {
				case 1:
					addJuice();
					break;
				case 2:
					viewJuices();
					break;
				case 3:
					deliverJuice();
					break;
				case 4:
					consoleScan.cetakEnter("Time: "+Machine.timer().getTime());
					consoleScan.cetakEnter("Thank you. Good Bye!");
					break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		juicesList = new Vector<Juices>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Juices component= new Juices(eachDatas[0], Integer.parseInt(eachDatas[1]), eachDatas[2]);
			juicesList.add(component);
		}
	}
	
	private static int onlyLetterSpaceQuotes(String str){
	    for(int x=0; x<str.length(); x++){
	        char ch = str.charAt(x);
	        if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ' 
	            || ch == '\'' || ch == '\"'))
	            return 0;               
	    }
	    return 1;            
	}
	
	private boolean checkJuicesPrice(int JuicesPrice) {
		if(JuicesPrice >= 10000 && JuicesPrice <= 50000) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean validateIce(String JuicesIce){
        if (JuicesIce.equals("Yes") || JuicesIce.equals("No")){
            return true;
        }
        return false;
    }
	
	private void addJuice() {
		String juicesName = "", juicesIce = "";
		int juicesPrice = 0, cek = 0;
		
		do {
			consoleScan.cetak("Juice name[3..20 characters](Inclusive)(Letters and spaces only): ");
			juicesName = consoleScan.bacaString();
			cek = onlyLetterSpaceQuotes(juicesName);
		}while(juicesName.length() < 3 || juicesName.length() > 20 || cek==0);
		
		do {
			consoleScan.cetak("Juice price[ 10000..50000 ] (Inclusive) : ");
			juicesPrice = consoleScan.bacaInteger();
		}while(!checkJuicesPrice(juicesPrice));
		
		do {
			consoleScan.cetak("Do you want Ice[ Yes | No ] (Case Sensitive) : ");
			juicesIce = consoleScan.bacaString();
		}while(!validateIce(juicesIce));
		
		String isiFile = juicesName + "#" + juicesPrice + "#" + juicesIce;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Juice has been successfully inserted...");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void viewJuices() {
		if(juicesList.isEmpty()){
			consoleScan.cetak("No Juice(s)....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		for (Juices juices : juicesList) {
			consoleScan.cetakEnter("Juice Name: "+juices.getJuicesName());
			consoleScan.cetakEnter("Juice Price: "+juices.getJuicesPrice());
			consoleScan.cetakEnter("Ice: "+juices.getJuicesIce());
			new KThread(juices).fork();
			consoleScan.cetakEnter("");
		}
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Juices> juicList) {
		String txt="";
		for (Juices juices : juicList) {
			txt+=(juices.getJuicesName() + "#" + juices.getJuicesPrice() + "#" + juices.getJuicesIce() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private void deliverJuice() {
		if(juicesList.isEmpty()){
			consoleScan.cetak("No juice to be delivered....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		for (Juices juices : juicesList) {
			consoleScan.cetakEnter("Juice Name: "+juices.getJuicesName());
			consoleScan.cetakEnter("Juice Price: "+juices.getJuicesPrice());
			consoleScan.cetakEnter("Ice: "+juices.getJuicesIce());
			new KThread(juices).fork();
			consoleScan.cetakEnter("");
		}
		juicesList.removeAllElements();
		overWrite(juicesList);
		consoleScan.cetakEnter("Juice has been successfully delivered");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public static void main(String[] args) {
		new BluejackJuiceStore();
	}
}