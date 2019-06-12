package audio;

import java.io.File;

public class Music{
	public static final AudioPlayer menuSong = new AudioPlayer(
			"." + File.separator +
			"resources" + File.separator +
			"assets" + File.separator +
			"music" + File.separator +
			"menu_song" + ".wav");
	
	/*public static final AudioPlayer gameSong = new AudioPlayer(
			"." + File.separator +
			"resources" + File.separator +
			"assets" + File.separator +
			"music" + File.separator +
			"game_song" + ".wav");*/
	
	public static AudioPlayer currentSong = null;
}
