package com.sim.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.sim.manager.SpriteManager;
public class MobBlurred extends Entity{
	
	public int layed;
	
	public MobBlurred(Body body, MobAttribute atr){
		super(null, body, atr, atr.getRadius());
		this.setID(this);
		sprite = SpriteManager.getCellBlur();
	}

	@Override
	public void update() {
		if(atr != null){
			if(atr.getSpeedFreq().pushable()){
				this.move();
			}
		}
	}

	private void move() {
		float rad = this.getBody().getAngle();
		float xVel = (float) Math.cos(rad);
		float yVel = (float) Math.sin(rad);
		this.push(new Vector2(xVel*atr.getSpeed(),yVel*atr.getSpeed()));
		this.getBody().setAngularVelocity((float)(Math.random()*(atr.getMaxTurnVel()-atr.getMinTurnVel())+atr.getMinTurnVel()));
		
	}
	

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(this.sprite, 
				(float)(getBody().getPosition().x-this.width/2), (float)(getBody().getPosition().y-this.height/2), 
				(float)this.width/2, (float)this.height/2, 
				(float)this.width, (float)this.height, 
				1.77f, 1.77f, 
				MathUtils.radiansToDegrees * this.getBody().getAngle());
	}

	@Override
	public boolean isDead() {
		return false;
	}
	
	public void eat(){
		this.atr.getLifeTime().currentTime += this.atr.getFoodWorth();
		food++;
	}

	@Override
	public boolean bump(Entity ent) {
		if(ent instanceof Mob){
			if(this.atr.isCarn()){
				if(ent.atr.isHerb()){
					game.entMan.destroy(ent.getBody());
					game.entMan.entities.remove(ent);
					this.eat();
					return true;
				}
			}else if(this.atr.isHerb()){
				if(ent.atr.isCarn()){
					game.entMan.destroy(this.getBody());
					((Mob)ent).eat();
					game.entMan.entities.remove(this);
					return true;
				}
			}
		}
		return false;
	}
	
}
