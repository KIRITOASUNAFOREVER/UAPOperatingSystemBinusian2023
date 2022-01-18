package nachos.RegistrationForm;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MyNetworkLink {
	private NetworkLink netLink = Machine.networkLink();
	private Semaphore sem = new Semaphore(0);
	private Vector<Student> studentList = new Vector<>();
	
	public MyNetworkLink() {
		Runnable receiveInterruptHandler = new Runnable() {
			@Override
			public void run() {
				Packet packet = netLink.receive();
				String terimaFile = new String(packet.contents);
				
				String pemisahIsiFile[] = terimaFile.split("#");
				Student component = new Student(pemisahIsiFile[0], pemisahIsiFile[1], Integer.parseInt(pemisahIsiFile[2]), Integer.parseInt(pemisahIsiFile[3]), Integer.parseInt(pemisahIsiFile[4]));
				studentList.add(component);
			}
		};
		Runnable sendInterruptHandler = new Runnable() {
			@Override
			public void run() {
				sem.V();
			}
		};
		
		netLink.setInterruptHandlers(receiveInterruptHandler, sendInterruptHandler);
	}
	
	public void sendStudent(Student student, int destinasiKomputer){
		String isiFile = student.getStudentName() + "#" + student.getStudentMajor() + "#" + student.getStudentQuantity() + "#" + student.getStudentDiscount() + "#" + student.getStudentTotalPrice();
		try {
			Packet packet = new Packet(destinasiKomputer, this.getAddress(), isiFile.getBytes());
			netLink.send(packet);
			sem.P();
		} catch (MalformedPacketException e) {
			e.printStackTrace();
		}
	}
	
	public int getAddress(){
		return netLink.getLinkAddress();
	}

	public Vector<Student> getStudent() {
		return studentList;
	}

	public void setStudent(Vector<Student> studentList) {
		this.studentList = studentList;
	}
}
