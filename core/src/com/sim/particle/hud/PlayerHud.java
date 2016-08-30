package com.sim.particle.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sim.game.MainGame;
import com.sim.game.entities.Entity;
import com.sim.particle.hud.util.Pause;
import com.sim.particle.hud.util.Selector;
import com.sim.utils.ui.BindedText;

public class PlayerHud implements Disposable{
	private int carn, herb;
	private int gold;
	
	private BindedText entCount,fps,goldCount;
	
	private Selector selector;
	private Pause pause;
	
	public PlayerHud(){
		entCount = new BindedText("H: "+herb+" C: "+carn, 0.01f,.99f);entCount.leftAlign();entCount.topAlign();
		fps =new BindedText("Fps: "+Gdx.graphics.getFramesPerSecond()+String.format("x%.2f", MainGame.mUpdate/60), .99f,.01f);fps.bottomAlign();fps.rightAlign();
		goldCount = new BindedText("Gold: "+gold, 0.01f, .99f);goldCount.setColor(Color.YELLOW);goldCount.leftAlign();goldCount.topAlign();goldCount.setDisplacement(0, -entCount.getOrigionalHeight()*1.2f);
		selector = new Selector();
		pause = new Pause();
		updateBinds();
	}
	
	public void render(SpriteBatch sb){
		sb.setColor(Color.WHITE);
		updateTexts();
		entCount.render(sb);
		fps.render(sb);
		goldCount.render(sb);
		selector.render(sb);
		pause.render(sb);
	}
	
	public void update(float delta){
		selector.update(delta);
		pause.update(delta);
		this.resetCount();
	}
	
	public void updateTexts(){
		fps.setText("Fps:"+Gdx.graphics.getFramesPerSecond()+String.format("x%.2f", MainGame.mUpdate/60));
		entCount.setText("H: "+herb+" C: "+carn);
		goldCount.setText("Gold: "+gold);
	}
	
	public Vector2 getGoldPos(){
		return goldCount.getPos();
	}
	
	public void countCarnHerb(Entity e){
		if(e.atr.isHerb()){
			herb++;
		}else if(e.atr.isCarn()){
			carn++;
		}
		
	}
	
	public void resetCount(){
		herb = 0;
		carn = 0;
	}
	
	public void dispose(){
		entCount.dispose();
		fps.dispose();
		goldCount.dispose();
	}

	public void updateBinds() {
		entCount.updateBind();
		fps.updateBind();
		goldCount.updateBind();
		goldCount.setDisplacement(0, -entCount.getOrigionalHeight()*1.2f);
		selector.updateBind();
		pause.updateBinds();
	}

	public void addGold(float value) {
		gold += value;
		
	}

	public boolean tap(float x, float y, int count, int button) {
		boolean selectB = selector.tap(x, y, count, button);
		boolean pauseB = pause.tap(x, y, count, button);
		return selectB||pauseB;
	}

	public void longPress(float x, float y) {
	}

	public int getGold() {
		return this.gold;
	}

	public void setGold(int coin) {
		this.gold = coin;
		
	}
}
