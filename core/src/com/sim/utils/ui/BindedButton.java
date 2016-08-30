package com.sim.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sim.game.MainGame;
import com.sim.utils.camera.OrthoCamera;

public class BindedButton extends Button implements Binded{
	
	private float xPercent, yPercent;
	private boolean left,right,top,bottom;
	private float origionalWidth,origionalHeight;
	
	public BindedButton(Sprite sprite, float width, float height, float xPercent, float yPercent) {
		super(sprite, width, height, 0, 0);
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		origionalWidth = width;
		origionalHeight = height;
		updateBind();
	}
	
	public BindedButton(Sprite sprite, Sprite inner, float width, float height, float xPercent, float yPercent) {
		super(sprite, inner, width, height, 0, 0);
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		origionalWidth = width;
		origionalHeight = height;
		updateBind();
	}
	
	@Override
	public void updateBind() {
		super.setSize(origionalWidth*MainGame.camera.zoom, origionalHeight*MainGame.camera.zoom);
		float camWidth = MainGame.camera.viewportWidth*MainGame.camera.zoom;
		float camHeight = MainGame.camera.viewportHeight*MainGame.camera.zoom;
		float camX = MainGame.camera.position.x;
		float camY = MainGame.camera.position.y;
		float tempX = 0, tempY = 0;
		
		if(right)
			tempX = xPercent*camWidth-camWidth/2f+camX-this.getWidth();
		else if(left)
			tempX = xPercent*camWidth-camWidth/2f+camX;
		else
			tempX = xPercent*camWidth-camWidth/2f+camX-this.getWidth()/2f;
		
		if(top)
			tempY = yPercent*camHeight-camHeight/2f+camY-this.getHeight();
		else if(bottom)
			tempY = yPercent*camHeight-camHeight/2f+camY;
		else	
			tempY = yPercent*camHeight-camHeight/2f+camY-this.getHeight()/2f;
		
		super.setPos(tempX, tempY);
	}
	
	@Override
	public void set(float width, float height, float xPercent, float yPercent){
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		origionalWidth = width;
		origionalHeight = height;
		this.updateBind();
	}
	
	@Override
	public void setSize(float width, float height){
		origionalWidth = width;
		origionalHeight = height;
		this.updateBind();
	}
	
	@Override
	public void setPos(float xPercent, float yPercent){
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		this.updateBind();
	}
	@Override
	public void leftAlign() {
		left = true;
		right = false;
	}

	@Override
	public void rightAlign() {
		left = false;
		right = true;
	}


	@Override
	public void centerXAlign() {
		left = false;
		right = false;
		
	}

	@Override
	public void topAlign() {
		top = true;
		bottom = false;
		
	}

	@Override
	public void bottomAlign() {
		top = false;
		bottom = true;
		
	}

	@Override
	public void centerYAlign() {
		top = false;
		bottom = false;
		
	}
	
	@Override
	public void centerAlign() {
		left = false;
		right = false;
		
		top = false;
		bottom = false;
	}

	@Override
	public float getOrigionalHeight() {
		return this.origionalHeight;
	}

	@Override
	public float getOrigionalWidth() {
		return this.origionalWidth;
	}

	@Override
	public float getXPercent(){
		return xPercent;
	}

	@Override
	public float getYPercent(){
		return yPercent;
	}
}
