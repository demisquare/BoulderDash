package ai;

import java.io.File;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import model.*;
import network.packet.PacketMove;
import view.Game;
import view.Level;

public class ASPEngine {
	// definisce l'handler per dlv...
	private String encodingResource = "." + File.separator + "resources" + File.separator + "encodings" + File.separator
			+ "move";
	private Handler handler;

	// fatti presenti nel gioco...
	// private InputProgram facts;

	// encoding delle regole...
	private InputProgram encoding;
	Output o;
	Thread t;

	Game game;

	public ASPEngine(Game game) {

		this.game = game;

		handler = new DesktopHandler(new DLVDesktopService(
				"." + File.separator + "resources" + File.separator + "lib" + File.separator + "dlv.mingw.exe"));

	}

	/*public void start() {
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// trova un modo di interrompere il thread...
		t.start();
	}*/
	
	public void start() {
		// carica i fatti...
		/* facts = new ASPInputProgram();
		
		for(int x = 0; x < game.level.getWorld().getMap().getDimX(); ++x)
			for(int y = 0; y < game.level.getWorld().getMap().getDimY(); ++y)
			{
				GameObject g = game.level.getWorld().getMap().getTile(x, y);
				try {
						if(g instanceof Rock)
						facts.addObjectInput((Rock)g);
					
					else if(g instanceof Diamond)
						facts.addObjectInput((Diamond)g);
					
					else if(g instanceof Ground)
						facts.addObjectInput((Ground)g);
					
					else if(g instanceof Door)
						facts.addObjectInput((Door)g);
					
					else if(g instanceof Wall)
						facts.addObjectInput((Wall)g);
					
					else if(g instanceof Player)
						facts.addObjectInput((Player)g);
					
					else if(g instanceof EmptyBlock)
						facts.addObjectInput((EmptyBlock)g);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
		handler.addProgram(facts);
		*/
		// carica encoding...
		encoding = new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		handler.addProgram(encoding);
		
		// register the class for reflection
		try {
			/*ASPMapper.getInstance().registerClass(Rock.class);
			ASPMapper.getInstance().registerClass(Diamond.class);
			ASPMapper.getInstance().registerClass(Ground.class);
			ASPMapper.getInstance().registerClass(Door.class);
			ASPMapper.getInstance().registerClass(Wall.class);
			ASPMapper.getInstance().registerClass(Player.class);
			ASPMapper.getInstance().registerClass(EmptyBlock.class);*/
			ASPMapper.getInstance().registerClass(PacketMove.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		for (AnswerSet a : answers.getAnswersets()) {

			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof PacketMove)
					/*{
						PacketMove move = (PacketMove) obj;
						synchronized (this) {
						 game.level.updatePlayerOnPressing(move.getDir());
						 }
					}*/
					System.out.println(obj);
						
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized void stop() {
		if (t != null && t.isAlive())
			t.interrupt();
	}

}
