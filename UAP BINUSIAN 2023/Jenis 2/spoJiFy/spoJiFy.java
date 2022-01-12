package nachos.spoJiFy;

import java.util.Vector;

import nachos.machine.Machine;
import nachos.threads.KThread;

public class spoJiFy {
	private MyConsole consoleScan = new MyConsole();
	private MyFileSystem fileSaya = new MyFileSystem();
	private Vector<Song> songList = new Vector<>();
	
	private void logo() {
		consoleScan.cetakEnter("                       _ _ ______     ");
		consoleScan.cetakEnter("                      | (_)  ____|    ");
		consoleScan.cetakEnter("  ___ _ __   ___      | |_| |__ _   _ ");
		consoleScan.cetakEnter(" / __| '_ \\ / _ \\ _   | | |  __| | | |");
		consoleScan.cetakEnter(" \\__ \\ |_) | (_) | |__| | | |  | |_| |");
		consoleScan.cetakEnter(" |___/ .__/ \\___/ \\____/|_|_|   \\__, |");
		consoleScan.cetakEnter("     | |                         __/ |");
		consoleScan.cetakEnter("     |_|                        |___/ ");
	}
	
	public spoJiFy() {
		int pilihan;
		do {
			loadData();
			logo(); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("1. Write Song");
			consoleScan.cetakEnter("2. Listen All Songs");
			consoleScan.cetakEnter("3. Delete Song");
			consoleScan.cetakEnter("4. Exit");
			consoleScan.cetak(">> ");
			pilihan = consoleScan.bacaInteger();
			switch(pilihan) {
				case 1:
					writeSong();
					break;
				case 2:
					listenAllSongs();
					break;
				case 3:
					deleteSong();
					break;
				case 4:
					long timerSeconds = Machine.timer().getTime()/ 10000000;
					long timerMinutes = timerSeconds /60;
					consoleScan.cetakEnter("You have been logged in for "+timerMinutes +" minute(s)");
					break;
			}
		}while(pilihan < 1 || pilihan > 4 || pilihan !=4);
	}
	
	private void loadData(){
		songList = new Vector<Song>();
		String data = fileSaya.scan();
		String[] lineDatas= data.split("\n");
		for (String string : lineDatas) {
			if(string.isEmpty())
				continue;
			String[] eachDatas=string.split("#");
			Song component= new Song(eachDatas[0], Integer.parseInt(eachDatas[1]), eachDatas[2]);
			songList.add(component);
		}
	}
	
	private boolean checkSongLyric(String SongLyric) {
		if(SongLyric.length() >= 10 && SongLyric.length() <= 50) {
			return true;
		}else {
			return false;
		}
	}
	
	private boolean checkSongDuration(int SongDuration) {
		if(SongDuration >= 1 && SongDuration <= 360) {
			return true;
		}else {
			return false;
		}
	}
	
	private void writeSong() {
		String songName = "", songLyric = "";
		int songDuration = 0;
		
		consoleScan.cetak("Insert song name: ");
		songName = consoleScan.bacaString();
		
		do {
			consoleScan.cetak("Insert song lyrics[10 - 50 characters](Inclusive): ");
			songLyric = consoleScan.bacaString();
		}while(!checkSongLyric(songLyric));
		
		do {
			consoleScan.cetak("Insert song duration [1 - 360 second(s)](Inclusive): ");
			songDuration = consoleScan.bacaInteger();
		}while(!checkSongDuration(songDuration));
		
		String isiFile = songName + "#" + songDuration + "#" + songLyric;
		fileSaya.write(isiFile);
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Song has been successfully Writed...");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	private void listenAllSongs() {
		if(songList.isEmpty()){
			consoleScan.cetak("No Song(s)....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		for (Song song : songList) {
			consoleScan.cetakEnter("Now Playing.. " + song.getSongName());
			consoleScan.cetakEnter("Time Elapsed: " + (song.getSongDuration()/60) + " minute(s)");
			consoleScan.cetakEnter("============================================="); consoleScan.cetakEnter("");
			consoleScan.cetakEnter("Song Lyrics:");
			consoleScan.cetakEnter(song.getSongLyric());
			consoleScan.cetakEnter(""); consoleScan.cetakEnter("");
			new KThread(song).fork();
		}
		
		consoleScan.cetakEnter("");
		consoleScan.cetakEnter("Press enter to continue...");
		consoleScan.bacaString();
	}
	
	public void overWrite(Vector<Song> SongList) {
		String txt="";
		for (Song song : SongList) {
			txt+=(song.getSongName() + "#" + song.getSongDuration() + "#" + song.getSongLyric() +"\n");	
		}
		fileSaya.overwrite(txt);
	}
	
	private int checkSongNameExists(String SongName) {
		int panjang = songList.size();
		int status = -1;
		for(int i = 0 ; i < panjang ; i++) {
			if(songList.get(i).getSongName().equals(SongName)) {
				status = 1;
				break;
			}else {
				status = 0;
			}
		}
		return status;
	}
	
	private int searchSongIndexs(String SongName) {
		int panjang = songList.size();
		int indexs = -1;
		for(int i = 0 ; i < panjang ; i++) {
			if(songList.get(i).getSongName().equals(SongName)) {
				indexs = i;
				break;
			}
		}
		return indexs;
	}
	
	private void deleteSong() {
		if(songList.isEmpty()){
			consoleScan.cetak("No Song(s)....");
			consoleScan.cetakEnter("");
			consoleScan.cetak("Press enter to continue...");
			consoleScan.bacaString();
			consoleScan.cetakEnter("");
			return;
		}
		String SongName = "";
		consoleScan.cetak("Insert song name (Case Sensitive): ");
		SongName = consoleScan.bacaString();
		
		int cek = -1;
		cek = checkSongNameExists(SongName);
		
		if(cek == 0) {
			consoleScan.cetakEnter("Couldn't find inputted song");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}else {
			int indeks = -1;
			indeks = searchSongIndexs(SongName);
			songList.remove(indeks);
			overWrite(songList);
			consoleScan.cetakEnter(SongName + " has been removed!");
			consoleScan.cetakEnter("Press enter to continue...");
			consoleScan.bacaString();
		}
		
	}

	public static void main(String[] args) {
		new spoJiFy();
	}

}
