package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class AudioPlayer {

	private boolean isOn;
	Clip clip;
	DataLine.Info info;
	URL soundURL;
	AudioInputStream inputStream;
	AudioFormat format;

	public AudioPlayer(String s) {
		
		try 
        {   
			soundURL = new URL("file:" + s);
            inputStream = AudioSystem.getAudioInputStream(soundURL);
            format = inputStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(inputStream);
        }

    catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
        {
            e.printStackTrace();
        }

		/*
		 * mixInfo = AudioSystem.getMixerInfo();
		 * 
		 * mixer = AudioSystem.getMixer(mixInfo[0]);
		 * 
		 * dataInfo = new DataLine.Info(Clip.class, null);
		 * 
		 * try { clip = (Clip) mixer.getLine(dataInfo);
		 * 
		 * } catch (LineUnavailableException e) { e.printStackTrace(); }
		 * 
		 * try { // soundURL=AudioPlayer.class.getResource(s); soundURL = new
		 * URL("file:" + s); AudioInputStream audioStream =
		 * AudioSystem.getAudioInputStream(soundURL); clip.open(audioStream); } catch
		 * (LineUnavailableException e) { e.printStackTrace(); } catch
		 * (UnsupportedAudioFileException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

	}

	public void loop() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
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
