package graphics;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;

import javax.swing.JPanel;

import logic.World;
import logic.Living;


public class Level extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = -2545695383117923190L;

	//Questa classe far� da interfaccia a TUTTA la logica di un livello
	World world;
	LocalTime lastTimePressed;

	public Level() {
		super();

		// crea un world...
		world = new World();
		lastTimePressed=java.time.LocalTime.now();
		Renderer.init(world);
	}

	@Override
	// permette di ridefinire i componenti del pannello di default.
	// questo metodo viene invocato ogni volta che usiamo il metodo repaint().
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		world.update();
		// disegna lo sprite del player...
		//g.drawImage(world.getPlayer().ls.getAnimation().getSprite(), world.getPlayer().getX(), world.getPlayer().getY(), null);
		Renderer.render(g, world);

	}

	// eventi di movimento da tastiera del player...

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( (java.time.LocalTime.now().minusNanos(100000000)).compareTo(lastTimePressed) > 0 )
		{
			lastTimePressed=java.time.LocalTime.now();
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			world.getPlayer().ls.setDirection(Living.LEFT);
			world.getPlayer().ls.getAnimation().start();
			world.getPlayer().move(Living.LEFT);
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			world.getPlayer().ls.setDirection(Living.RIGHT);
			world.getPlayer().ls.getAnimation().start();
			world.getPlayer().move(Living.RIGHT);
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			world.getPlayer().ls.setDirection(Living.UP);
			world.getPlayer().ls.getAnimation().start();
			world.getPlayer().move(Living.UP);
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			world.getPlayer().ls.setDirection(Living.DOWN);
			world.getPlayer().ls.getAnimation().start();
			world.getPlayer().move(Living.DOWN);
			break;

		}
		
		world.getPlayer().ls.getAnimation().update();
		repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		world.getPlayer().ls.getAnimation().stop();
		world.getPlayer().ls.getAnimation().reset();
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			//System.out.println("left");
			world.getPlayer().ls.setStandPose(Living.LEFT);
			world.getPlayer().ls.getAnimation().start();
			break;

		case KeyEvent.VK_RIGHT:
			//System.out.println("right");
			world.getPlayer().ls.setStandPose(Living.RIGHT);
			world.getPlayer().ls.getAnimation().start();
			break;

		case KeyEvent.VK_UP:
			//System.out.println("up");
			world.getPlayer().ls.setStandPose(Living.UP);
			world.getPlayer().ls.getAnimation().start();
			break;

		case KeyEvent.VK_DOWN:
			//System.out.println("down");
			world.getPlayer().ls.setStandPose(Living.DOWN);
			world.getPlayer().ls.getAnimation().start();
			break;

		}
		world.getPlayer().ls.getAnimation().update();
		repaint();
	}
}
