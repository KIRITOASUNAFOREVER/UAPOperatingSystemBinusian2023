package nachos.RegistrationForm;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class RegistrationForm {
	private MyConsole consoleScan = new MyConsole();
	private MyNetworkLink netLink = new MyNetworkLink();
	
	public RegistrationForm() {
		int pilihan;
		do {
			consoleScan.cetakEnter("        =====================        ");
			consoleScan.cetakEnter("        | Registration Form |        ");
			consoleScan.cetakEnter("        =====================        ");
			consoleScan.cetakEnter(""); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("University ID: " + netLink.getAddress());
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("|             MENU             |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("| 1. Transfer Student          |");
			consoleScan.cetakEnter("| 2. Receive Student(s)        |");
			consoleScan.cetakEnter("| 3. Exit                      |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				transferStudent();
				break;
			case 2:
				receiveStudents();
				break;
			case 3:
				consoleScan.cetakEnter("Ticks of time: "+Machine.timer().getTime());
				consoleScan.cetakEnter("Thank You! See You Next Time");
				break;
			}
		}while(pilihan < 1 || pilihan > 3 || pilihan !=3);
	}
	
	private boolean checkStudentName(String StudentName) {
		if(StudentName.length() >= 5 && StudentName.length() <= 30) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkStudentMajor(String StudentMajor) {
		if(StudentMajor.equals("Computer Science") || StudentMajor.equals("Information System")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkStudentQuantity(int StudentQuantity) {
		if(StudentQuantity >= 1 && StudentQuantity <= 10) {
			return true;
		}else {
			return false;
		}
	}
	
	private int getMajorPrice(String StudentMajor) {
		int MajorPrice = 0;
		if(StudentMajor.equals("Computer Science")) {
			MajorPrice =  200000;
		}else if(StudentMajor.equals("Information System")) {
			MajorPrice =  150000;
		}
		return MajorPrice;
	}
	
	private int getDiscountPrice(int StudentQuantity, int StudentMajorPrice) {
		int DiscountPrice = 0;
		if(StudentQuantity <= 3) {
			DiscountPrice =  ((StudentMajorPrice * 10)/100);
		}else if(StudentQuantity >= 5) {
			DiscountPrice =  ((StudentMajorPrice * 20)/100);
		}
		return DiscountPrice;
	}
	
	private void transferStudent() {
		String StudentName = "";
		do {
			consoleScan.cetak("Input Student Name [ 5..30 characters ] : ");
			StudentName = consoleScan.bacaString();
		}while(!checkStudentName(StudentName));
		
		String StudentMajor = "";
		do {
			consoleScan.cetak("Input Student Major [ Computer Science | Information System ] : ");
			StudentMajor = consoleScan.bacaString();
		}while(!checkStudentMajor(StudentMajor));
		
		int StudentQuantity = 0;
		do {
			consoleScan.cetak("Input Student Quantity [ 1..10 ] : ");
			StudentQuantity = consoleScan.bacaInteger();
		}while(!checkStudentQuantity(StudentQuantity));
		
		int StudentMajorPrice = 0;
		StudentMajorPrice = getMajorPrice(StudentMajor);
		
		int StudentDiscountPrice = 0;
		StudentDiscountPrice = getDiscountPrice(StudentQuantity, StudentMajorPrice);
		
		int totalStudentDiscountPrice = 0;
		totalStudentDiscountPrice = StudentQuantity * StudentDiscountPrice;
		
		int totalPrice = 0;
		totalPrice = (StudentQuantity * StudentMajorPrice) - totalStudentDiscountPrice;
		
		int destinasiUniversityID;
		consoleScan.cetak("Input University ID You Want to Transfer This Student: ");
		destinasiUniversityID = consoleScan.bacaInteger();
		
		Student student = new Student(StudentName, StudentMajor, StudentQuantity, totalStudentDiscountPrice, totalPrice);
		netLink.sendStudent(student, destinasiUniversityID);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Student has Successfully Transfer to University With ID " + destinasiUniversityID);
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press Enter to continue...");
		consoleScan.bacaString();
	}
	
	private void receiveStudents() {
		if(netLink.getStudent().isEmpty()){
			consoleScan.cetakEnter("There is No Transferred Student to You. Check Again Another Time!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		while(!netLink.getStudent().isEmpty()){
			new KThread(netLink.getStudent().remove(0)).fork();
		}
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press Enter to continue...");
		consoleScan.bacaString();
	}

	public static void main(String[] args) {
		new RegistrationForm();
	}

}
