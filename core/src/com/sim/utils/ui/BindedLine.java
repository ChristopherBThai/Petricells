package com.sim.utils.ui;

import com.badlogic.gdx.math.Vector2;
import com.sim.game.MainGame;

public class BindedLine extends Line implements Binded{
	
	float x1Percent,y1Percent,x2Percent,y2Percent;
	
	float origionalWidth;
	
	public BindedLine(float x1, float y1, float x2, float y2){
		super();
		origionalWidth = 3;
		x1Percent = x1;
		y1Percent = y1;
		x2Percent = x2;
		y2Percent = y2;
		this.updateBind();
	}
	
	public BindedLine(float x1, float y1, float x2, float y2, float width){
		super();
		origionalWidth = width;
		x1Percent = x1;
		y1Percent = y1;
		x2Percent = x2;
		y2Percent = y2;
		this.updateBind();
	}

	@Override
	public void updateBind() {
		super.setWidth(origionalWidth*MainGame.camera.zoom);
		float camWidth = MainGame.camera.viewportWidth*MainGame.camera.zoom;
		float camHeight = MainGame.camera.viewportHeight*MainGame.camera.zoom;
		float camX = MainGame.camera.position.x;
		float camY = MainGame.camera.position.y;
		
		super.setStart(x1Percent*camWidth-camWidth/2f+camX,y1Percent*camHeight-camHeight/2f+camY);
		super.setEnd(x2Percent*camWidth-camWidth/2f+camX,y2Percent*camHeight-camHeight/2f+camY);
	}
	
	@Override
	public void setStart(float x, float y){
		this.x1Percent = x;
		this.y1Percent = y;
		this.updateBind();
	}
	@Override
	public void setStart(Vector2 start){
		this.x1Percent = start.x;
		this.y1Percent = start.y;
		this.updateBind();
	}
	@Override
	public void setEnd(float x, float y){
		this.x2Percent = x;
		this.y2Percent = y;
		this.updateBind();
	}
	@Override
	public void setEnd(Vector2 end){
		this.x2Percent = end.x;
		this.y2Percent = end.y;
		this.updateBind();
	}
	@Override
	public void set(float x1, float y1, float x2, float y2){
		this.x1Percent = x1;
		this.y1Percent = y1;
		this.x2Percent = x2;
		this.y2Percent = y2;
		this.updateBind();
	}
	@Override
	public void setWidth(float width){
		this.origionalWidth = width;
		super.setWidth(origionalWidth*MainGame.camera.zoom);
	}
	
	@Override
	public float getWidth(){
		return origionalWidth;
	}
	
	public float getX1Percent(){
		return this.x1Percent;
	}
	
	public float getX2Percent(){
		return this.x2Percent;
	}
	
	public float getY1Percent(){
		return this.y1Percent;
	}
	
	public float getY2Percent(){
		return this.y2Percent;
	}
	@Override
	public void add(Vector2 add){
		this.x1Percent += add.x;
		this.y1Percent += add.y;
		this.x2Percent += add.x;
		this.y2Percent += add.y;
		this.updateBind();
	}

	@Override
	public void leftAlign() {}

	@Override
	public void rightAlign() {}

	@Override
	public void centerXAlign() {}

	@Override
	public void centerAlign() {}

	@Override
	public void topAlign() {}

	@Override
	public void bottomAlign() {}

	@Override
	public void centerYAlign() {}

	@Override
	public float getOrigionalHeight() {return 0;}

	@Override
	public float getOrigionalWidth() {return 0;}

	@Override
	public float getXPercent() {return 0;}

	@Override
	public float getYPercent() {return 0;}

	@Override
	public float getHeight() {return 0;}

	@Override
	public float getX() {return 0;}

	@Override
	public float getY() {return 0;}

}
