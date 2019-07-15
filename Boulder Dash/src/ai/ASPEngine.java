package ai;

import java.io.File;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import view.Game;
import view.Level;

public class ASPEngine {
	// definisce l'handler per dlv...
	static String encodingResource;
	static Handler handler;

	// fatti presenti nel gioco...
	static InputProgram facts;

	// encoding delle regole...
	static InputProgram encoding;
	Output o;
	Thread t;

	Game game;

	public ASPEngine(Game game) {

		this.game = game;

		encodingResource = "." + File.separator + "resources" + File.separator + "encodings" + File.separator + "move";

		handler = new DesktopHandler(new DLV2DesktopService(
				"." + File.separator + "resources" + File.separator + "lib" + File.separator + "dlv2"));
	}

	public void run() {
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					// carica i fatti...
					facts = new ASPInputProgram();

					// TODO: metodo che carica i fatti dal world...

					handler.addProgram(facts);

					// carica encoding...
					encoding = new ASPInputProgram();
					encoding.addFilesPath(encodingResource);

					handler.addProgram(encoding);

					Output o = handler.startSync();
					callback(o, game.level);

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		// trova un modo di interrompere il thread...
	}

	// restituisci answer set...
	public void callback(Output o, Level level) {
		AnswerSets answers = (AnswerSets) o;
		for (AnswerSet a : answers.getAnswersets()) {

			try {
				for (Object obj : a.getAtoms()) {
					// if ((obj instanceof Move))
					// Move m = (Move) obj;
					// synchronized (this) {
					// level.updatePlayerOnPressing(m.getDir());
					// }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
