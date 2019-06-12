package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class AudioPlayer {

	private boolean isOn;
	Clip clip;
	Mixer mixer;
	Mixer.Info[] mixInfo;
	DataLine.Info dataInfo;
	URL soundURL;

	public AudioPlayer(String s) {

		mixInfo = AudioSystem.getMixerInfo();

		mixer = AudioSystem.getMixer(mixInfo[0]);

		dataInfo = new DataLine.Info(Clip.class, null);

		try {
			clip = (Clip) mixer.getLine(dataInfo);

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		try {
			// soundURL=AudioPlayer.class.getResource(s);
			soundURL = new URL("file:" + s);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loop() {
		if (clip == null)
			return;
		stop();
		if (clip.getFramePosition() == clip.getFrameLength())
			clip.setFramePosition(0);
		clip.start();
		isOn = true;
	}

	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
		isOn = true;
	}

	public void stop() {
		if (clip.isRunning())
			clip.stop();
		isOn = false;
	}

	public void close() {
		stop();
		clip.close();

	}

	public boolean isPlaying() {
		return isOn;
	}

	public void rewind() {
		clip.setFramePosition(0);
	}

}
