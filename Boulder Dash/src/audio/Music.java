package audio;

import java.io.File;

import menu.Options;

public class Music{
		
	public static final AudioPlayer menuSong = new AudioPlayer(
			"." + File.separator +
			"resources" + File.separator +
			"assets" + File.separator +
			"music" + File.separator +
			"menu_song" + ".wav");
	
	public static final AudioPlayer gameSong = new AudioPlayer(
			"." + File.separator +
			"resources" + File.separator +
			"assets" + File.separator +
			"music" + File.separator +
			"game_song" + ".wav");
	
	public static final AudioPlayer creditsSong = new AudioPlayer(
			"." + File.separator +
			"resources" + File.separator +
			"assets" + File.separator +
			"music" + File.separator +
			"credits_song" + ".wav");
	
	public static void setSong(AudioPlayer song)
	{
		Music.backgroundMusic.stop();
		Music.backgroundMusic.rewind();
		Music.backgroundMusic = song;
	}
	
	public static void start()
	{
		if (Options.music) {
			if (!Music.backgroundMusic.isPlaying()) {
				Music.backgroundMusic.loop();
			}
		} else {
			Music.backgroundMusic.stop();
			Music.backgroundMusic.rewind();
		}
	}
	
	public static void playTone(String name)
	{
		AudioPlayer tone = new AudioPlayer(
				"." + File.separator +
				"resources" + File.separator +
				"assets" + File.separator +
				"music" + File.separator +
				"sfx" + File.separator +
				name + ".wav");
		
		if (Options.music) {
			if (!tone.isPlaying()) {
				tone.play();
			}
		}
	}
	
	public static AudioPlayer backgroundMusic = null;
}
