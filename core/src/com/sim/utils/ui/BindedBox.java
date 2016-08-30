package com.sim.utils.ui;

import com.sim.game.MainGame;

public class BindedBox extends Box implements Binded{

	private float xPercent,yPercent;
	private boolean left,right,top,bottom;
	
	private float origionalWidth,origionalHeight;
	private float origionalThickness;
	
	public BindedBox(float xPercent, float yPercent, float width, float height) {
		super(0, 0, width, height);
		origionalWidth = width;
		origionalHeight = height;
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		origionalThickness = 1f;
		updateBind();
	}
	
	@Override
	public void setThickness(float thickness){
		origionalThickness = thickness;
		super.setThickness(origionalThickness*MainGame.camera.zoom);
	}
	
	@Override
	public void setSize(float width, float height){
		this.origionalWidth = width;
		this.origionalHeight = height;
		this.updateBind();
	}
	
	@Override
	public void setPos(float x, float y){
		this.xPercent = x;
		this.yPercent = y;
		this.updateBind();
	}
	
	@Override
	public void set(float x, float y, float width, float height){
		this.origionalWidth = width;
		this.origionalHeight = height;
		this.xPercent = x;
		this.yPercent = y;
		this.updateBind();
	}

	@Override
	public void updateBind() {
		super.setSize(origionalWidth*MainGame.camera.zoom, origionalHeight*MainGame.camera.zoom);
		super.setThickness(origionalThickness*MainGame.camera.zoom);
		float camWidth = MainGame.camera.viewportWidth*MainGame.camera.zoom;
		float camHeight = MainGame.camera.viewportHeight*MainGame.camera.zoom;
		float camX = MainGame.camera.position.x;
		float camY = MainGame.camera.position.y;
		float tempX,tempY;
		
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
	public float getXPercent() {
		return this.xPercent;
	}

	@Override
	public float getYPercent() {
		return this.yPercent;
	}

}
