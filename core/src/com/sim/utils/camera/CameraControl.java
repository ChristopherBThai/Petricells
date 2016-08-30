package com.sim.utils.camera;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sim.game.MainGame;
import com.sim.game.entities.Entity;
import com.sim.screen.GameScreen;

public class CameraControl {
	GameScreen game;
	OrthoCamera camera;
	ArrayList<Entity> entities;
	double XSCALE,YSCALE;
	
	Entity follow;
	public CameraControl(GameScreen game,OrthoCamera camera,ArrayList<Entity> entities){
		this.game = game;
		this.camera = camera;
		this.entities = entities;
		XSCALE = ((double)Gdx.app.getGraphics().getWidth()/(MainGame.SIZE*2))*MainGame.SCALE;
		YSCALE = ((double)Gdx.app.getGraphics().getHeight()/(MainGame.SIZE))*MainGame.SCALE;
	}
	
	public void update(){
		if(Gdx.input.isKeyPressed(Keys.UP)){
			camera.addZoom(.1f);
			game.hudMan.mobHud.rescan();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.addZoom(-.1f);
			game.hudMan.mobHud.rescan();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)&&MainGame.mUpdate>1/1000f)
			MainGame.mUpdate += 1;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			MainGame.mUpdate -= 1;
	}
	
	float pRatio;
	public void zoom(float initialDistance, float distance){
		float ratio = initialDistance / distance; 
		if(Math.abs(ratio-1)<0.01)
			pRatio=ratio;
		if((pRatio-ratio<0&&camera.zoom<5f)||(pRatio-ratio>0&&camera.zoom>0f))
			camera.addZoom(ratio-pRatio);
	    pRatio=ratio;
	}
	
	public boolean focus(float x, float y){
		float buff = 10;
		for(Entity entity:entities){
			if(entity.inBounds(x, y, buff)){
				follow(entity);
				return true;
			}
		}
		camera.follow(null);
		game.hudMan.mobHud.pointTo(null);
		return false;
	}
	public void pan(float x,float y,float deltaX, float deltaY){
		follow(null);
		deltaX*=camera.zoom;
		deltaY*=camera.zoom;
		camera.addPos((float)(-deltaX/XSCALE), (float)(deltaY/YSCALE));
	}
	
	public void follow(Entity e){
		follow = e;
		if(e!=null)
			camera.follow(e.getBody());
		else
			camera.follow(null);
		game.hudMan.mobHud.pointTo(e);
	}
	
	public boolean isFollowing(Body b){
		if(follow==null)
			return false;
		if(b.getUserData().equals(follow))
			return true;
		return false;
	}
	
	public boolean isFollowing(Entity e){
		if(follow==null)
			return false;
		if(e.equals(follow))
			return true;
		return false;
	}
	
	public void resize(){
		XSCALE = ((double)Gdx.app.getGraphics().getWidth()/(MainGame.SIZE*2))*MainGame.SCALE;
		YSCALE = ((double)Gdx.app.getGraphics().getHeight()/(MainGame.SIZE))*MainGame.SCALE;
	}
	
}
