package nachos.BJHospital;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class BJHospital {
	private MyConsole consoleScan = new MyConsole();
	private MyNetworkLink netLink = new MyNetworkLink();

	public BJHospital() {
		int pilihan;
		do {
			consoleScan.cetakEnter("        ===============     ");
			consoleScan.cetakEnter("        | BJ Hospital |     ");
			consoleScan.cetakEnter("        ===============     ");
			consoleScan.cetakEnter(""); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("Hospital ID: " + netLink.getAddress());
			consoleScan.cetakEnter("");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("|             MENU             |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetakEnter("| 1. Transfer Patient          |");
			consoleScan.cetakEnter("| 2. Receive Patient(s)        |");
			consoleScan.cetakEnter("| 3. Exit                      |");
			consoleScan.cetakEnter("+==============================+");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan){
			case 1:
				transferPatient();
				break;
			case 2:
				receivePatients();
				break;
			case 3:
				consoleScan.cetakEnter("Ticks of time: "+Machine.timer().getTime());
				consoleScan.cetakEnter("Thank You! See You Next Time");
				break;
			}
		}while(pilihan < 1 || pilihan > 3 || pilihan !=3);
	}
	
	private boolean checkPatientName(String PatientName) {
		if(PatientName.startsWith("Mr.") || PatientName.startsWith("Mrs.")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkPatientNameLength(String PatientName[]) {
		if(PatientName.length >= 2) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkPatientGender(String PatientGender) {
		if(PatientGender.equals("MALE") || PatientGender.equals("FEMALE")) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkPatientRoomType(String PatientRoomType) {
		if(PatientRoomType.equals("VIP") || PatientRoomType.equals("Normal")) {
			return true;
		}else {
			return false;
		}
	}
	
	private void transferPatient() {
		String PatientName = "";
		String arr[];
		do {
			consoleScan.cetak("Input Patient Name [ starts with 'Mr.' or 'Mrs.' ] : ");
			PatientName = consoleScan.bacaString();
			arr = PatientName.split(" ");
		}while(!checkPatientName(PatientName) || !checkPatientNameLength(arr));
		
		String PatientGender = "", tempPatientGender = "";
		do {
			consoleScan.cetak("Input Patient Gender [ 'Male' or 'Female' (Case Insensitive) ] : ");
			PatientGender = consoleScan.bacaString();
			tempPatientGender = PatientGender.toUpperCase();
		}while(!checkPatientGender(tempPatientGender));
		
		PatientGender = PatientGender.toLowerCase();
		String firstLetter = PatientGender.substring(0, 1);
        String remainingLetters = PatientGender.substring(1, PatientGender.length());
        firstLetter = firstLetter.toUpperCase();
        PatientGender = firstLetter.concat(remainingLetters);
		
		String PatientRoomType = "";
		do {
			consoleScan.cetak("Choose Patient Room Type [ 'VIP' or 'Normal' ] : ");
			PatientRoomType = consoleScan.bacaString();
		}while(!checkPatientRoomType(PatientRoomType));
		
		int destinasiHospitalID;
		consoleScan.cetak("Input Hospital ID You Want to Transfer This Patient: ");
		destinasiHospitalID = consoleScan.bacaInteger();
		
		Patient patient = new Patient(PatientName, PatientGender, PatientRoomType);
		netLink.sendPatient(patient, destinasiHospitalID);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Patient has Successfully Transfer to Hospital With ID " + destinasiHospitalID);
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press Enter to continue...");
		consoleScan.bacaString();
	}
	
	private void receivePatients() {
		if(netLink.getPatient().isEmpty()){
			consoleScan.cetakEnter("There is No Transferred Patient to You. Check Again Another Time!");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		
		while(!netLink.getPatient().isEmpty()){
			new KThread(netLink.getPatient().remove(0)).fork();
		}
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press Enter to continue...");
		consoleScan.bacaString();
	}

	public static void main(String[] args) {
		new BJHospital();
	}

}
