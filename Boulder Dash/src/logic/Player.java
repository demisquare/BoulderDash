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
		x += directions[dir][0] * speed;
		y += directions[dir][1] * speed;	
	}
}
