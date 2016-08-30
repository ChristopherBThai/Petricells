package com.sim.utils.ui;

import com.badlogic.gdx.math.Vector2;
import com.sim.game.MainGame;

public class BindedText extends Text implements Binded{
	private float xPercent,yPercent;
	private boolean left,right,top,bottom;
	
	private float origionalSize;
	
	private float disX,disY;
	
	public BindedText(String text,float xPercent, float yPercent){
		super(text,0,0);
		origionalSize = 1;
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		updateBind();
	}
	
	public void updateBind(){
		super.setSize(origionalSize*MainGame.camera.zoom);
		float camWidth = MainGame.camera.viewportWidth*MainGame.camera.zoom;
		float camHeight = MainGame.camera.viewportHeight*MainGame.camera.zoom;
		float camX = MainGame.camera.position.x;
		float camY = MainGame.camera.position.y;
		Vector2 pos = new Vector2();
		
		if(right)
			pos.x = xPercent*camWidth-camWidth/2f+camX-this.getOrigionalWidth();
		else if(left)
			pos.x = xPercent*camWidth-camWidth/2f+camX;
		else
			pos.x = xPercent*camWidth-camWidth/2f+camX-this.getOrigionalWidth()/2f;
		
		if(top)
			pos.y = yPercent*camHeight-camHeight/2f+camY;
		else if(bottom)
			pos.y = yPercent*camHeight-camHeight/2f+camY+this.getOrigionalHeight();
		else	
			pos.y = yPercent*camHeight-camHeight/2f+camY+this.getOrigionalHeight()/2f;
		
		pos.x += disX;
		pos.y += disY;
		
		super.setPos(pos);
	}
	
	public void setDisplacement(float x, float y){
		disX = x;
		disY = y;
		updateBind();
	}
	
	@Override
	public void setSize(float size){
		origionalSize = size;
		updateBind();
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
		return super.getHeight();
	}

	@Override
	public float getOrigionalWidth() {
		return super.getWidth();
	}

	@Override
	public float getXPercent() {
		return xPercent;
	}

	@Override
	public float getYPercent() {
		return yPercent;
	}
}
