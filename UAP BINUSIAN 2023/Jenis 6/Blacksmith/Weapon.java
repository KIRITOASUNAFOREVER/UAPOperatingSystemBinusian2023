package nachos.Blacksmith;

import java.util.Vector;

import nachos.threads.KThread;

public class Weapon implements Runnable{
	private String weaponID;
	private String weaponName;
	private String weaponQuality;
	private int weaponPower;
	private int weaponPrice;
	public Weapon(String weaponID, String weaponName, String weaponQuality, int weaponPower, int weaponPrice) {
		super();
		this.weaponID = weaponID;
		this.weaponName = weaponName;
		this.weaponQuality = weaponQuality;
		this.weaponPower = weaponPower;
		this.weaponPrice = weaponPrice;
	}
	public String getWeaponID() {
		return weaponID;
	}
	public void setWeaponID(String weaponID) {
		this.weaponID = weaponID;
	}
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	public String getWeaponQuality() {
		return weaponQuality;
	}
	public void setWeaponQuality(String weaponQuality) {
		this.weaponQuality = weaponQuality;
	}
	public int getWeaponPower() {
		return weaponPower;
	}
	public void setWeaponPower(int weaponPower) {
		this.weaponPower = weaponPower;
	}
	public int getWeaponPrice() {
		return weaponPrice;
	}
	public void setWeaponPrice(int weaponPrice) {
		this.weaponPrice = weaponPrice;
	}
	
	@Override
	public void run() {
		System.out.print("| " + this.weaponID + " | " + this.weaponName);
		int sisaNama = 14 - this.weaponName.length();
		for(int j = 0 ; j < sisaNama ; j++) {
			System.out.print(" ");
		}
		System.out.print("| " + this.weaponQuality);
		int sisaQuality = 8 - this.weaponQuality.length();
		for(int k = 0 ; k < sisaQuality ; k++) {
			System.out.print(" ");
		}
		System.out.print("| " + this.weaponPower);
		String weaponPowers = String.valueOf(this.weaponPower);
		int sisaPower = 6 - weaponPowers.length();
		for(int l = 0 ; l < sisaPower ; l++) {
			System.out.print(" ");
		}
		System.out.print("| " + this.weaponPrice);
		String weaponPrices = String.valueOf(this.weaponPrice);
		int sisaPrice = 6 - weaponPrices.length();
		for(int m = 0 ; m < sisaPrice ; m++) {
			System.out.print(" ");
		}
		System.out.println("gold |");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
