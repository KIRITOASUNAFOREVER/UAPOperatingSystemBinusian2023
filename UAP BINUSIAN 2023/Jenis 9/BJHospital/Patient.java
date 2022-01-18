package nachos.BJHospital;

public class Patient implements Runnable{
	private String patientName;
	private String patientGender;
	private String patientRoomType;
	public Patient(String patientName, String patientGender, String patientRoomType) {
		super();
		this.patientName = patientName;
		this.patientGender = patientGender;
		this.patientRoomType = patientRoomType;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getPatientRoomType() {
		return patientRoomType;
	}
	public void setPatientRoomType(String patientRoomType) {
		this.patientRoomType = patientRoomType;
	}
	
	@Override
	public void run() {
		System.out.println("=============================================================");
		System.out.println("| Patient Name         | Patient Gender | Patient Room Type |");
		System.out.println("=============================================================");
		System.out.print("| " + this.patientName);
		int sisaNama = 20 - this.patientName.length();
		for(int i = 0 ; i < sisaNama ; i++) {
			System.out.print(" ");
		}
		System.out.print(" | " + this.patientGender);
		int sisaGender = 14 - this.patientGender.length();
		for(int j = 0 ; j < sisaGender ; j++) {
			System.out.print(" ");
		}
		System.out.print(" | " + this.patientRoomType);
		int sisaTipeKamar = 18 - this.patientRoomType.length();
		for(int k = 0 ; k < sisaTipeKamar ; k++) {
			System.out.print(" ");
		}
		System.out.println("|");
		System.out.println("=============================================================");
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
