package ai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import model.*;
import view.*;

public class ASPEngine {
	// definisce l'handler per dlv...
	private String encodingResource = "." + File.separator + "resources" + File.separator + "encodings" + File.separator
			+ "test";
	// fatti presenti nel gioco...
	private String instanceResource = "." + File.separator + "resources" + File.separator + "encodings" + File.separator
			+ "facts";
	private Handler handler;

	// encoding delle regole...
	private InputProgram program;

	Thread t;

	Level level;
	GameMap map;
	Player player;

	public ASPEngine(Game game) {
		map = game.level.getWorld().getMap();
		player = (Player)game.level.getWorld().getPlayer();
		level = game.level;

		handler = new DesktopHandler(new DLVDesktopService(
				"." + File.separator + "resources" + File.separator + "lib" + File.separator + "dlv.mingw.exe"));
		
		// carica encoding...
		program = new ASPInputProgram();
		program.addFilesPath(encodingResource);
		handler.addProgram(program);

	}

	public void start() {
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					callback();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// trova un modo di interrompere il thread...
		t.start();
	}
	
	private void updateFacts() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(instanceResource));
			for(int x = 0; x < map.getDimX(); ++x)
				for(int y = 0; y < map.getDimY(); ++y)
					bw.write(map.getTile(x, y).toString()+"\n");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callback() {
		// carica i fatti...
		updateFacts();
		program.addFilesPath(instanceResource);
		handler.addProgram(program);

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		//System.out.println("size : " + answers.getAnswersets().size());
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				for (String s : a.getAnswerSet()) {
					if(s.indexOf("move")!=-1) {
						int move = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
						synchronized (this) {
							 level.updatePlayerOnPressing(move);
							 }
						if(player.hasMoved()) {
							System.out.println(player.toString());
							player.setMoved(false);
						}
					}
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
