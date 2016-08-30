package com.sim.particle.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.sim.game.MainGame;

public class Particle implements Disposable{
	private ParticleEffect effect;
	
	public Particle(){
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("particle/blood.p"), Gdx.files.internal("particle"));
		effect.setPosition(-10,-10);
		effect.scaleEffect(1/MainGame.SCALE);
	}
	
	public void update(float delta){
		effect.update(delta);
	}
	
	public void setPosition(float x, float y){
		effect.setPosition(x, y);
	}
	
	public void playAt(float x, float y){
		effect.setPosition(x, y);
		effect.reset();
	}
	
	public void render(SpriteBatch sb){
		effect.draw(sb);
	}
	
	public void dispose(){
		effect.dispose();
	}
}
