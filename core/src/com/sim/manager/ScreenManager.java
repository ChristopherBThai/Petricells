package com.sim.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.sim.screen.Screen;

public class ScreenManager{
	
	private static Screen currentScreen;
	
	public static void setScreen(Screen screen){
		if(currentScreen!=null)
			currentScreen.dispose();
		currentScreen = screen;
		currentScreen.create();
	}
	
	public static Screen getCurrentScreen(){
		return currentScreen;
	}
	
	public static void create(){
		if(currentScreen!=null)
			currentScreen.create();
	}
	
	public static void render(ShapeRenderer sr,SpriteBatch sb){
		if(currentScreen!=null)
			currentScreen.render(sr,sb);
	}
	
	public static void update(float delta){
		if(currentScreen!=null)
			currentScreen.update(delta);
	}
	
	public static void resize(int width, int height){
		if(currentScreen!=null)
			currentScreen.resize(width, height);
	}
	
	public static void pause(){
		if(currentScreen!=null)
			currentScreen.pause();
	}
	
	public static void resume(){
		if(currentScreen!=null)
			currentScreen.resume();
	}
	
	public static void dispose(){
		if(currentScreen!=null)
			currentScreen.dispose();
	}

}
