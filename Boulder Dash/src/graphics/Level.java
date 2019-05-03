package graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Level extends JPanel implements KeyListener {
	 private static final long serialVersionUID = -2545695383117923190L;

	Living player;

	public Level() {
		super();

		// crea un player...
		player = new Player(10, 10, 10);
	}

	@Override
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// disegna lo sprite del player...
		g.drawImage(player.getAnimation().getSprite(), player.x, player.y, null);

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
			player.setDirection(Player.LEFT);
			player.getAnimation().start();
			player.walk(Player.LEFT);
			break;

		case KeyEvent.VK_RIGHT:
			player.setDirection(Player.LEFT);
			player.getAnimation().start();
			player.walk(Player.LEFT);
			break;

		case KeyEvent.VK_UP:
			player.setDirection(Player.LEFT);
			player.getAnimation().start();
			player.walk(Player.LEFT);
			break;

		case KeyEvent.VK_DOWN:
			player.setDirection(Player.LEFT);
			player.getAnimation().start();
			player.walk(Player.LEFT);
			break;

		}
		
		player.getAnimation().update();
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.getAnimation().stop();
		player.getAnimation().reset();

	}
}
