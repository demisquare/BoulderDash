package logic;

import graphics.LivingSprite;

public class Player extends Living {

	public LivingSprite ls;
	
	public Player(int x, int y, int speed) {
		super(x, y, speed);
		ls = new LivingSprite("playerSpriteSheet", speed);	
	}

	@Override
	public void move(int dir) {
		x += k[dir][0] * speed;
		y += k[dir][1] * speed;	
	}
}
