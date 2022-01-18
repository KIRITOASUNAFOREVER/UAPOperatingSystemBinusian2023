package nachos.RegistrationForm;

public class Student implements Runnable{
	private String studentName;
	private String studentMajor;
	private int studentQuantity;
	private int studentDiscount;
	private int studentTotalPrice;
	public Student(String studentName, String studentMajor, int studentQuantity, int studentDiscount,
			int studentTotalPrice) {
		super();
		this.studentName = studentName;
		this.studentMajor = studentMajor;
		this.studentQuantity = studentQuantity;
		this.studentDiscount = studentDiscount;
		this.studentTotalPrice = studentTotalPrice;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentMajor() {
		return studentMajor;
	}
	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
	}
	public int getStudentQuantity() {
		return studentQuantity;
	}
	public void setStudentQuantity(int studentQuantity) {
		this.studentQuantity = studentQuantity;
	}
	public int getStudentDiscount() {
		return studentDiscount;
	}
	public void setStudentDiscount(int studentDiscount) {
		this.studentDiscount = studentDiscount;
	}
	public int getStudentTotalPrice() {
		return studentTotalPrice;
	}
	public void setStudentTotalPrice(int studentTotalPrice) {
		this.studentTotalPrice = studentTotalPrice;
	}
	@Override
	public void run() {
		System.out.println("                  Registration Form");
		System.out.println("                  =================");
		System.out.println("Name                              : " + this.studentName);
		System.out.println("Major                             : " + this.studentMajor);
		System.out.println("Quantity                          : " + this.studentQuantity);
		System.out.println("Discount                          : " + this.studentDiscount);
		System.out.println("Total Price                       : " + this.studentTotalPrice);
		System.out.println();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
