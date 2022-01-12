package nachos.spoJiFy;

public class Song implements Runnable{
	private String songName;
	private int songDuration;
	private String songLyric;
	
	public Song(String songName, int songDuration, String songLyric) {
		super();
		this.songName = songName;
		this.songDuration = songDuration;
		this.songLyric = songLyric;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public int getSongDuration() {
		return songDuration;
	}

	public void setSongDuration(int songDuration) {
		this.songDuration = songDuration;
	}

	public String getSongLyric() {
		return songLyric;
	}

	public void setSongLyric(String songLyric) {
		this.songLyric = songLyric;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
