package com.sim.utils.ui;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Image {
	protected Sprite sprite;
	float width, height, x, y;
	float originX,originY,rotate;
	
	boolean center;
	
	float scale;
	
	public Image(Sprite sprite){
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		width = sprite.getWidth();
		height = sprite.getHeight();
		x = 0;
		y = 0;
		scale = 1f;
	}
	
	public Image(Sprite sprite, float width, float height){
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.width = width;
		this.height = height;
		x = 0;
		y = 0;
		scale = 1f;
	}
	
	public Image(Sprite sprite, float width, float height, float x, float y){
		this.sprite = sprite;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		scale = 1f;
	}
	
	public void set(float width, float height, float x, float y){
		this.setSize(width,height);
		this.setPos(x, y);
	}
	
	public void setSize(float width, float height){
		this.width = width;
		this.height = height;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public void centerImage(boolean center){
		this.center = center;
	}
	
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void setRotate(float rotate){
		this.rotate = rotate;
	}
	
	public void setOriginPercent(float x, float y){
		originX = x;
		originY = y;
	}
	
	public void addRotate(float rotate){
		this.rotate += rotate;
	}
	
	
	public Sprite getSprite(){
		return sprite;
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
	
	public float getRotate(){
		return rotate;
	}
	
	
	public void render(SpriteBatch sb){
		if(center)
			if(originX==0&&originY==0&&rotate==0&&scale==1)
				sb.draw(sprite, x-(width)/2f, y-(height)/2f, width, height);
			else
				sb.draw(sprite, x-(width)/2f, y-(height)/2f, originX*width, originY*height, width, height, scale, scale, rotate);
		else
			if(originX==0&&originY==0&&rotate==0&&scale==1)
				sb.draw(sprite, x, y, width, height);
			else
				sb.draw(sprite, x, y, originX*width, originY*height, width, height, scale, scale, rotate);
	}
}
