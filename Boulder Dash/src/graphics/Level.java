package graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import logic.Player;

public class Level extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = -2545695383117923190L;

	Player player;

	public Level() {
		super();

		// crea un player...
		player = new Player(10, 10, 3);
	}

	@Override
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// disegna lo sprite del player...
		g.drawImage(player.ls.getAnimation().getSprite(), player.getX(), player.getY(), null);

	}

	// eventi di movimento da tastiera del player...

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			player.ls.setDirection(Player.LEFT);
			player.ls.getAnimation().start();
			player.walk(Player.LEFT);
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			player.ls.setDirection(Player.RIGHT);
			player.ls.getAnimation().start();
			player.walk(Player.RIGHT);
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			player.ls.setDirection(Player.UP);
			player.ls.getAnimation().start();
			player.walk(Player.UP);
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			player.ls.setDirection(Player.DOWN);
			player.ls.getAnimation().start();
			player.walk(Player.DOWN);
			break;

		}
		
		player.ls.getAnimation().update();
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.ls.getAnimation().stop();
		player.ls.getAnimation().reset();
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			player.ls.setStandPose(Player.LEFT);
			player.ls.getAnimation().start();
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			player.ls.setStandPose(Player.RIGHT);
			player.ls.getAnimation().start();
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			player.ls.setStandPose(Player.UP);
			player.ls.getAnimation().start();
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			player.ls.setStandPose(Player.DOWN);
			player.ls.getAnimation().start();
			break;

		}
		player.ls.getAnimation().update();
		repaint();
	}
}
