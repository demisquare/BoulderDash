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
	private String diamondsInstance = "." + File.separator + "resources" + File.separator + "encodings" + File.separator
			+ "diamonds";
	// fatti presenti nel gioco...
	private String instanceResource = "." + File.separator + "resources" + File.separator + "encodings" + File.separator
			+ "facts";
	private Handler handler;
	private Handler handlerDiamonds;

	Thread t;
	int closerX;
	int closerY;
	int xprec=0,yprec=0;
	Level level;
	GameMap map;
	Player player;

	public ASPEngine(Game game) {
		map = game.level.getWorld().getMap();
		player = (Player)game.level.getWorld().getPlayer();
		level = game.level;	
		closerX=0;
		closerY=0;
		
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(instanceResource));
		
		for(int x = 0; x < map.getDimX(); x++)
			for(int y = 0; y < map.getDimY(); y++)
				bw.write(map.getTile(x, y).toString()+"\n");
		bw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void start() {
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					handler = new DesktopHandler(new DLVDesktopService(
							"." + File.separator + "resources" + File.separator + "lib" + File.separator + "dlv.mingw.exe"));
					
					handlerDiamonds = new DesktopHandler(new DLVDesktopService(
							"." + File.separator + "resources" + File.separator + "lib" + File.separator + "dlv.mingw.exe"));
					InputProgram  program = new ASPInputProgram();
					InputProgram finder= new ASPInputProgram();
					
					finder.addFilesPath(diamondsInstance);
					finder.addFilesPath(instanceResource);
					handlerDiamonds.addProgram(finder);
					updateFacts();
					
					
					program.addFilesPath(encodingResource);
					program.addFilesPath(instanceResource);
					handler.addProgram(program);
					handlerDiamonds.addProgram(finder);
					callback();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// trova un modo di interrompere il thread...
		t.start();
	}
	
	public synchronized void stop() {
		if (t != null && t.isAlive())
			t.interrupt();
	}
	
	private void updateFacts() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(instanceResource));
			for(int x = 0; x < map.getDimX(); x++)
				for(int y = 0; y < map.getDimY(); y++)
					bw.write(map.getTile(x, y).toString()+"\n");
			bw.close();
			
			System.out.println("godverdomme"+ closerX + " "+ closerY +" "+ map.getPlayer().getY() + " "+ map.getPlayer().getX());
			if( (closerX==0 || closerY==0) || (closerX==map.getPlayer().getY() && closerY==map.getPlayer().getX()) )
			{
				Output o = handlerDiamonds.startSync();
				AnswerSets answers = (AnswerSets) o;
				System.out.println("godverdomme"+ closerX + closerY);
				for (AnswerSet a : answers.getAnswersets()) {
					try {
						for (String s : a.getAnswerSet()) {
							if(s.indexOf("closer")!=-1) {
								System.out.println(s);
								closerX = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(",")));
								closerY = Integer.parseInt(s.substring(s.indexOf(",")+1,s.indexOf(")")));
								
					break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(instanceResource));
			bw2.write("closer("+closerX + "," + closerY +")."+"\n");
			
			for(int x = 0; x < map.getDimX(); x++)
				for(int y = 0; y < map.getDimY(); y++)
					bw2.write(map.getTile(x, y).toString()+"\n");
					
			bw2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callback() {
		String m="";
		String playerNow="player("+ (map.getPlayer().getY()) + "," + map.getPlayer().getX() + ").";
		int xprov=0,yprov=0;
		int move=-1;
		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		
		/*inizio parte
		
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				for (String s : a.getAnswerSet()) {
					if(s.indexOf("move")!=-1) {
						System.out.println(s);
						move = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
						
						if(move==0)
							{m="player("+ (map.getPlayer().getY()+1) + "," + map.getPlayer().getX() + ")."; xprov=map.getPlayer().getY()+1; yprov=map.getPlayer().getX(); }
						else if(move==1)
							{m="player("+ map.getPlayer().getY() + "," + (map.getPlayer().getX()-1) + ")."; xprov=map.getPlayer().getY(); yprov=map.getPlayer().getX()-1;}
						else if(move==2)
							{m="player("+ map.getPlayer().getY() + "," + (map.getPlayer().getX()+1) + ")."; xprov=map.getPlayer().getY(); yprov=map.getPlayer().getX()+1;}
						else
							{m="player("+ (map.getPlayer().getY()-1) + "," + map.getPlayer().getX() + ")."; xprov=map.getPlayer().getY()-1; yprov=map.getPlayer().getX();}
						
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		
		*/ //fine parte
		
		
		
		//System.out.println(answers.getAnswersets().size());
		 
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				for (String s : a.getAnswerSet()) {
					if(s.indexOf("move")!=-1) {
						System.out.println(s);
						move = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
						
						if(move==0)
							{m="player("+ (map.getPlayer().getY()+1) + "," + map.getPlayer().getX() + ")."; xprov=map.getPlayer().getY()+1; yprov=map.getPlayer().getX(); }
						else if(move==1)
							{m="player("+ map.getPlayer().getY() + "," + (map.getPlayer().getX()-1) + ")."; xprov=map.getPlayer().getY(); yprov=map.getPlayer().getX()-1;}
						else if(move==2)
							{m="player("+ map.getPlayer().getY() + "," + (map.getPlayer().getX()+1) + ")."; xprov=map.getPlayer().getY(); yprov=map.getPlayer().getX()+1;}
						else
							{m="player("+ (map.getPlayer().getY()-1) + "," + map.getPlayer().getX() + ")."; xprov=map.getPlayer().getY()-1; yprov=map.getPlayer().getX();}
						
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}//fine for
		
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(instanceResource));
		for(int x = 0; x < map.getDimX(); x++)
			for(int y = 0; y < map.getDimY(); y++)
				if(map.getTile(x, y) instanceof Player)
					bw.write("emptyblock("+ x +"," + y +")." +"\n");
				else
					bw.write(map.getTile(x, y).toString()+"\n");
		bw.write("closer("+closerX + "," + closerY +")."+"\n");
		bw.write(m);
		bw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String l="";
		o = handler.startSync();
		answers = (AnswerSets) o; 
		//System.out.println(answers.getAnswersets().size());
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				for (String s : a.getAnswerSet()) {
					if(s.indexOf("move")!=-1) {
						System.out.println(s);
						int move2 = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
						
						if(move2==0)
							l="player("+ (xprov+1) + "," + yprov + ").";
						else if(move2==1)
							l="player("+ xprov + "," + (yprov-1) + ").";
						else if(move2==2)
							l="player("+ xprov + "," + (yprov+1) + ").";
						else
							l="player("+ (xprov-1) + "," + yprov + ").";
						
					
			
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} 
		
		//fine 2 for
			//ricalcola se va in loop o si blocca per un motivo diverso dall'essere sul closer
			if(playerNow.equals(l) || (l.contentEquals("") &&  (xprov!=closerX || yprov!=closerY))) 
			
			{
				
				System.out.print(playerNow + "  " + m + l);
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(instanceResource));
					
					for(int x = 0; x < map.getDimX(); x++)
						for(int y = 0; y < map.getDimY(); y++)
							bw.write(map.getTile(x, y).toString()+"\n");
					bw.write("closer("+closerX + "," + closerY +")."+"\n");
					bw.write("loop(" + move +")."+"\n");
					
					bw.close();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				
				o = handler.startSync();
				answers = (AnswerSets) o;
				//System.out.println(answers.getAnswersets().size());
				for (AnswerSet a : answers.getAnswersets()) {
					try {
						for (String s : a.getAnswerSet()) {
							if(s.indexOf("move")!=-1) {
								System.out.println(s);
								move = Integer.parseInt(s.substring(s.indexOf("(")+1,s.indexOf(")")));
								
													
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
			} // fine if 

			
			if(!player.hasMoved()) {
				synchronized (this) {
					 level.updatePlayerOnPressing(move);
					 }
				player.setMoved(false);
			}
		
	}
}
