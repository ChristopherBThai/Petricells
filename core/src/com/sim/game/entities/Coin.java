package com.sim.game.entities;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sim.game.MainGame;
import com.sim.manager.SpriteManager;
import com.sim.particle.hud.PlayerHud;

public class Coin implements Comparable<Coin>{
	PlayerHud phud;
	
	Sprite sprite,fx;
	float size;
	float value;

	Vector2 pos;
	float pX,pY;
	Vector2 collectPos;
	public static Vector2 collectStart;
	boolean collecting;
	
	float rotate;
	
	boolean dead;
	
	public Coin(float x, float y,float value,PlayerHud phud){
		this.phud = phud;
		sprite = SpriteManager.getCoin();
		fx = SpriteManager.getCoinFX();
		this.value = value;
		this.size = 6f + value;
		pos = new Vector2(x,y);
		pX = x;
		pY = y;
		rotate = (float) (Math.random()*Math.PI*2);
		collectPos = phud.getGoldPos();
		this.collect();
	}
	
	public void update(){
		if(collecting){
			collectPos = phud.getGoldPos();
			pos.x = pos.x + (pX - pos.x)*.05f;
			pos.y = pos.y + (pY - pos.y)*.05f;
		}
		
		rotate += .5f;
		
		if(collecting&&Math.abs(pX-pos.x)<2&&Math.abs(pY-pos.y)<2){
			dead = true;
		}
	}
	
	public void render(SpriteBatch sb){
		sb.draw(sprite, pos.x-size/2f, pos.y-size/2f, size, size);
		sb.draw(fx, pos.x-size/2f, pos.y-size/2f, size/2f, size/2f, size, size, .8f, .8f, rotate);
	}
	
	public void setPos(float x, float y){
		this.pos.x = x;
		this.pos.y = y;
	}
	
	public void moveTo(float x, float y){
		pX = x;
		pY = y;
	}
	
	public void collect(){
		collecting = true;
		moveTo(collectPos.x,collectPos.y);
	}

	public boolean isDead() {
		if(dead){
			phud.addGold(value);
			value = 0;
		}
		
		return dead;
	}

	@Override
	public int compareTo(Coin arg0) {
		if(collectStart!=null){
			return -(int)(arg0.pos.dst(collectStart)-this.pos.dst(collectStart));
		}
		return 0;
	}
	
	public float dist(){
		return this.pos.dst(collectStart);
	}

	public float getValue() {
		return value;
		
	}
}
