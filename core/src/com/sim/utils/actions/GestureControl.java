package com.sim.utils.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.sim.screen.GameScreen;
import com.sim.game.MainGame;
import com.sim.game.entities.Coin;
import com.sim.manager.ScreenManager;

public class GestureControl implements GestureDetector.GestureListener{

	public static GestureDetector gd; 
	
	public GestureControl(){
		gd = new GestureDetector(this);
		Gdx.input.setInputProcessor(gd);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		if(ScreenManager.getCurrentScreen() instanceof GameScreen){
			Vector2 temp = MainGame.camera.unprojectCoordinates(x, y);
			x = temp.x;
			y = temp.y;
			((GameScreen)ScreenManager.getCurrentScreen()).tap(x,y,count,button);
			return true;
			
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		if(ScreenManager.getCurrentScreen() instanceof GameScreen){
			Vector2 temp = MainGame.camera.unprojectCoordinates(x, y);
			x = temp.x;
			y = temp.y;
			((GameScreen)ScreenManager.getCurrentScreen()).longPress(x, y);
		}
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(ScreenManager.getCurrentScreen() instanceof GameScreen){
			if(!((GameScreen)ScreenManager.getCurrentScreen()).entMan.moveFood(x,y,deltaX,deltaY)){
				((GameScreen)ScreenManager.getCurrentScreen()).camCon.pan(x,y,deltaX,deltaY);
				((GameScreen)ScreenManager.getCurrentScreen()).hudMan.mobHud.rescan();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if(ScreenManager.getCurrentScreen() instanceof GameScreen){
			((GameScreen)ScreenManager.getCurrentScreen()).camCon.zoom(initialDistance, distance);
			((GameScreen)ScreenManager.getCurrentScreen()).hudMan.mobHud.rescan();
			return true;
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
