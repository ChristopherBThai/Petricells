package com.sim.utils.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sim.game.MainGame;
import com.sim.manager.SpriteManager;

public class Box {
	float x,y,width,height;
	
	Sprite line,corner;
	
	float lineThickness;
	
	float widthCorner,heightCorner;
	float orginXCorner,orginYCorner;
	float xCorner1,yCorner1;	
	float xCorner2,yCorner2;						//2	1
	float xCorner3,yCorner3;						//3	4
	float xCorner4,yCorner4;	
	
	float xLine1,yLine1,widthLine1,heightLine1;								//	1
	float xLine2,yLine2,widthLine2,heightLine2;								//2	  4
	float xLine3,yLine3,widthLine3,heightLine3;								//	3
	float xLine4,yLine4,widthLine4,heightLine4;
	
	public Box(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		line = SpriteManager.getBorderLine();
		corner = SpriteManager.getBorderCorner();
		lineThickness = .1f;
		this.adjustValues();
	}
	
	public void setSize(float width, float height){
		this.width = width;
		this.height = height;
		this.adjustValues();
	}
	
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
		this.adjustValues();
	}
	
	public void set(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.adjustValues();
	}
	
	public void setThickness(float thickness){
		lineThickness = thickness/10f;
	}
	
	public void render(SpriteBatch sb){
		sb.draw(corner, xCorner1, yCorner1, orginXCorner, orginYCorner, widthCorner, heightCorner, 1f, 1f, 270);
		sb.draw(corner, xCorner2, yCorner2, widthCorner, heightCorner);
		sb.draw(corner, xCorner3, yCorner3, orginXCorner, orginYCorner, widthCorner, heightCorner, 1f, 1f, 90);
		sb.draw(corner, xCorner4, yCorner4, orginXCorner, orginYCorner, widthCorner, heightCorner, 1f, 1f, 180);
		
		sb.draw(line, xLine1, yLine1, widthLine1, heightLine1);
		sb.draw(line, xLine2, yLine2, 0f, 0f, widthLine2, heightLine2, 1f, 1f, 90);
		sb.draw(line, xLine3, yLine3, widthLine3, heightLine3);
		sb.draw(line, xLine4, yLine4, 0f, 0f, widthLine4, heightLine4, 1f, 1f, 90);
	}
	
	private void adjustValues(){
		float lineWidth = MainGame.camera.viewportHeight*lineThickness*.84f;
		widthCorner = MainGame.camera.viewportHeight*lineThickness;
		heightCorner = MainGame.camera.viewportHeight*lineThickness;
		orginXCorner = widthCorner/2f;
		orginYCorner = heightCorner/2f;
		
		xCorner1 = x+width-widthCorner;
		yCorner1 = y+height-heightCorner;
		
		xCorner2 = x;
		yCorner2 = y+height-heightCorner;
		
		xCorner3 = x;
		yCorner3 = y;
		
		xCorner4 = x+width-widthCorner;
		yCorner4 = y;
		
		xLine1 = xCorner2+widthCorner;
		yLine1 = yCorner2+heightCorner*.15f;
		widthLine1 = xCorner1 - xLine1;
		heightLine1 = lineWidth;
		
		xLine2 = xCorner3+widthCorner*.85f;
		yLine2 = yCorner3+heightCorner;
		heightLine2 = lineWidth;
		widthLine2 = yCorner2 - yLine2;
		
		xLine3 = xCorner3+widthCorner;
		yLine3 = yCorner3+heightCorner*.01f;
		widthLine3 = xCorner4 - xLine3;
		heightLine3 = lineWidth;
		
		xLine4 = xCorner4+widthCorner*.99f;
		yLine4 = yCorner4+heightCorner;
		heightLine4 = lineWidth;
		widthLine4 = yCorner1 - yLine4;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
