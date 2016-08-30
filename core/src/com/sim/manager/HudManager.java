package com.sim.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.sim.particle.hud.MobDescriptionHud;
import com.sim.particle.hud.PlayerHud;

public class HudManager implements Disposable{
	public MobDescriptionHud mobHud;
	public PlayerHud playerHud;
	
	public HudManager(){
		mobHud = new MobDescriptionHud();
		playerHud = new PlayerHud();
	}
	
	public void update(float delta){
		mobHud.update();
		playerHud.update(delta);
	}
	
	public void render(SpriteBatch sb){
		mobHud.render(sb);
		playerHud.render(sb);
	}
	
	public void render(ShapeRenderer sr){
		mobHud.renderHud(sr);
	}

	public void dispose() {
		mobHud.dispose();
		playerHud.dispose();
		
	}
	
	public void updateBinds(){
		playerHud.updateBinds();
	}

	public boolean tap(float x, float y, int count, int button) {
		return playerHud.tap(x,y,count,button);
	}

	public void longPress(float x, float y) {
		playerHud.longPress(x,y);
	}
}
