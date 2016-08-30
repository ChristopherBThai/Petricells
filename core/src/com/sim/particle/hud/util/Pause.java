package com.sim.particle.hud.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.sim.game.MainGame;
import com.sim.manager.ScreenManager;
import com.sim.manager.SpriteManager;
import com.sim.screen.GameScreen;
import com.sim.screen.MenuScreen;
import com.sim.utils.camera.OrthoCamera;
import com.sim.utils.ui.BindedBox;
import com.sim.utils.ui.BindedBoxButton;
import com.sim.utils.ui.BindedButton;
import com.sim.utils.ui.BoxButton;

public class Pause implements Disposable{
	
	private BindedButton pause;
	private BindedBox pauseScreen;
	private Sprite background;
	
	private BindedBoxButton[] buttons;
	
	boolean paused;
	boolean show;
	
	public Pause(){
		pause = new BindedButton(SpriteManager.getBorder(), SpriteManager.getPause(),
				MainGame.camera.viewportHeight*.1f,
				MainGame.camera.viewportHeight*.1f,
				1f, 1f);
		pause.setInnerScale(.7f);
		pause.topAlign();
		pause.rightAlign();
		pauseScreen = new BindedBox(.5f,.5f,0f,0f);
		pauseScreen.setThickness(.5f);
		pauseScreen.centerAlign();
		background = SpriteManager.getBlack();
		background.setAlpha(.7f);
		createButtons();
		updateBinds();
	}
	
	public void createButtons(){
		buttons = new BindedBoxButton[3];
		buttons[0] = new BindedBoxButton(.5f,.5f,0f,0f);
		buttons[0].setText("Main Menu");
		buttons[1] = new BindedBoxButton(.5f,.5f,0f,0f);
		buttons[1].setImage(SpriteManager.getCell());
		buttons[2] = new BindedBoxButton(.5f,.5f,0f,0f);
		for(BindedBoxButton button:buttons){
			button.setLineThickness(.4f);
			button.centerAlign();
			button.updateBind();
		}
	}

	public void render(SpriteBatch sb){
		pause.render(sb);
		if(show){
			background.draw(sb);
			pauseScreen.render(sb);
			for(BindedBoxButton button:buttons)
				button.render(sb);
		}
	}
	
	public void update(float delta){
		pause.update(delta);
		if(show){
			if(paused){
				float tempWidth = pauseScreen.getOrigionalWidth()+(MainGame.camera.viewportWidth*.8f-pauseScreen.getOrigionalWidth())*.25f;
				float tempHeight = pauseScreen.getOrigionalHeight()+(MainGame.camera.viewportHeight*.8f-pauseScreen.getOrigionalHeight())*.25f;
				pauseScreen.setSize(tempWidth, tempHeight);
			}else{
				float tempWidth = pauseScreen.getOrigionalWidth()+(-pauseScreen.getOrigionalWidth())*.25f;
				float tempHeight = pauseScreen.getOrigionalHeight()+(-pauseScreen.getOrigionalHeight())*.25f;
				if(tempWidth<5||tempHeight<5)
					show = false;
				pauseScreen.setSize(tempWidth, tempHeight);
			}
			
			float backgroundBuffW = pauseScreen.getWidth()*.027f;
			float backgroundBuffH = pauseScreen.getHeight()*.064f;
			background.setBounds(pauseScreen.getX()+backgroundBuffW/2f, pauseScreen.getY()+backgroundBuffH/2f,pauseScreen.getWidth()-backgroundBuffW, pauseScreen.getHeight()-backgroundBuffH);
			
			buttons[0].setPos(.5f,.5f+OrthoCamera.lengthToPercentY((pauseScreen.getWidth()*.5f)/3f));
			buttons[1].setPos(.5f, .5f);
			buttons[2].setPos(.5f,.5f-OrthoCamera.lengthToPercentY((pauseScreen.getWidth()*.5f)/3f));
			for(BindedBoxButton button:buttons){
				button.setSize(pauseScreen.getOrigionalWidth()*.3f, pauseScreen.getOrigionalHeight()*.3f);
				button.update(delta);
			}
		}
	}
	
	public void updateBinds() {
		pause.updateBind();
		if(paused){
			pauseScreen.updateBind();
			for(BindedBoxButton button:buttons)
				button.updateBind();
		}
	}
	
	public boolean tap(float x, float y, int count, int button) {
		boolean pause = this.pause.isPushed(x,y);
		if(pause&&ScreenManager.getCurrentScreen() instanceof GameScreen){
			if(((GameScreen)ScreenManager.getCurrentScreen()).pause){
				((GameScreen)ScreenManager.getCurrentScreen()).pause = false;
				paused = false;
				this.pause.setInner(SpriteManager.getPause());
			}else{
				((GameScreen)ScreenManager.getCurrentScreen()).pause = true;
				paused = true;
				show = true;
				pauseScreen.updateBind();
				for(BindedBoxButton box:buttons)
					box.updateBind();
				this.pause.setInner(SpriteManager.getPlay());
			}
		}else{
			if(buttons[0].isPushed(x, y)){
				ScreenManager.setScreen(new MenuScreen());
			}else if(buttons[1].isPushed(x, y)){
				
			}else if(buttons[2].isPushed(x, y)){
				
			}
		}
		return pause||paused;
	}

	@Override
	public void dispose() {
		for(BindedBoxButton bbb: buttons)
			bbb.dispose();
	}
}
