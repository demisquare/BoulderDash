package start;

import java.awt.Color;

import javax.swing.JFrame;

import graphics.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("Boulder Dash");
		f.setSize(800, 600);
		f.setResizable(false);

		// crea pannello di gioco...
		Level p = new Level();
		p.setBackground(Color.WHITE);
		p.addKeyListener(p);
		f.add(p);

		// imposta il focus sul pannello...
		p.setFocusable(true);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
