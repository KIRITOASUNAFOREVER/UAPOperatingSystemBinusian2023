package nachos.Paypol;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MyNetworkLink {
	private NetworkLink netLink = Machine.networkLink();
	private Semaphore sem = new Semaphore(0);
	private Vector<TopUP> TopUPList = new Vector<>();
	
	public MyNetworkLink() {
		Runnable receiveInterruptHandler = new Runnable() {
			@Override
			public void run() {
				Packet packet = netLink.receive();
				String terimaFile = new String(packet.contents);
				
				String pemisahIsiFile[] = terimaFile.split("#");
				TopUP component= new TopUP(pemisahIsiFile[0], pemisahIsiFile[1], pemisahIsiFile[2], Integer.parseInt(pemisahIsiFile[3]), Integer.parseInt(pemisahIsiFile[4]), Integer.parseInt(pemisahIsiFile[5]));
				TopUPList.add(component);
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
	
	public void sendTopUP(TopUP topup, int destinasiKomputer){
		String isiFile = topup.getEmail() + "#" + topup.getUsername() + "#" + topup.getCreditCard() + "#" + topup.getTopUpBalance() + "#" + topup.getAdminFee() + "#" + topup.getFinalBalance();
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

	public Vector<TopUP> getTopUP() {
		return TopUPList;
	}

	public void setTopUP(Vector<TopUP> TopUPList) {
		this.TopUPList = TopUPList;
	}
}
