package nachos.BJHospital;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MyNetworkLink {
	private NetworkLink netLink = Machine.networkLink();
	private Semaphore sem = new Semaphore(0);
	private Vector<Patient> patientList = new Vector<>();
	
	public MyNetworkLink() {
		Runnable receiveInterruptHandler = new Runnable() {
			@Override
			public void run() {
				Packet packet = netLink.receive();
				String terimaFile = new String(packet.contents);
				
				String pemisahIsiFile[] = terimaFile.split("#");
				Patient component = new Patient(pemisahIsiFile[0], pemisahIsiFile[1], pemisahIsiFile[2]);
				patientList.add(component);
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
	
	public void sendPatient(Patient patient, int destinasiKomputer){
		String isiFile = patient.getPatientName() + "#" + patient.getPatientGender() + "#" + patient.getPatientRoomType();
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

	public Vector<Patient> getPatient() {
		return patientList;
	}

	public void setPatient(Vector<Patient> patientList) {
		this.patientList = patientList;
	}
}
