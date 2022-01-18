package nachos.ItemMuW;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MyNetworkLink {
	private NetworkLink netLink = Machine.networkLink();
	private Semaphore sem = new Semaphore(0);
	private Vector<Product> products = new Vector<>();
	
	public MyNetworkLink() {
		Runnable receiveInterruptHandler = new Runnable() {
			@Override
			public void run() {
				Packet packet = netLink.receive();
				String terimaFile = new String(packet.contents);
				
				String pemisahIsiFile[] = terimaFile.split("#");
				Product component= new Product(pemisahIsiFile[0], pemisahIsiFile[1], Integer.parseInt(pemisahIsiFile[2]), Integer.parseInt(pemisahIsiFile[3]));
				products.add(component);
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
	
	public void sendProduct(Product product, int destinasiKomputer){
		String isiFile = product.getProductName() + "#" + product.getProductType() + "#" + product.getProductPrice() + "#" + product.getProductQuantity();
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

	public Vector<Product> getProducts() {
		return products;
	}

	public void setProducts(Vector<Product> products) {
		this.products = products;
	}
}
