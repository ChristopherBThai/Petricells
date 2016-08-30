package com.sim.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	Image border,borderIn;
	Image text,textIn;
	
	boolean isPushed,state;
	float downTimer;
	
	float innerScale;
	
	public Button(Sprite button){
		innerScale = .8f;
		border = new Image(button);
		borderIn = null;
		text = null;
		textIn = null;
	}
	
	public Button(Sprite button, Sprite inner){
		innerScale = .8f;
		border = new Image(button);
		borderIn = null;
		this.setInner(inner, innerScale);
		textIn = null;
	}
	
	public Button(Sprite button, float width, float height, float x, float y){
		innerScale = .8f;
		this.border = new Image(button,width,height,x,y);
		this.borderIn = null;
		this.text = null;
		this.textIn = null;
	}
	
	public Button(Sprite button,Sprite inner, float width, float height, float x, float y){
		innerScale = .8f;
		this.border = new Image(button,width,height,x,y);
		this.borderIn = null;
		this.setInner(inner, innerScale);
		this.textIn = null;
	}
	
	public void render(SpriteBatch sb){
		if(isPushed)
			sb.setColor(Color.GRAY);
		
		border.render(sb);
		if(borderIn!=null)
			borderIn.render(sb);
		if(text!=null)
			text.render(sb);
		if(textIn!=null)
			textIn.render(sb);
		
		sb.setColor(Color.WHITE);
	}
	
	public void update(float delta){
		if(downTimer<0)
			downTimer = 0;
		else if(downTimer>0){
			downTimer -= delta;
			isPushed = true;
		}else if(isPushed)
			isPushed = false;
		
	}
	
	public void setInner(Sprite inner){
		text = new Image(inner,border.width,border.height,border.x,border.y);
		text.setOriginPercent(.5f, .5f);
		text.setScale(this.innerScale);
	}
	
	public void setInner(Sprite inner, float scale){
		this.setInner(inner);
		this.setInnerScale(scale);
	}
	
	public void setPushed(Sprite button){
		borderIn = new Image(button,border.width,border.height,border.x,border.y);
	}
	
	public void setPushed(Sprite button, Sprite inner){
		borderIn = new Image(button,border.width,border.height,border.x,border.y);
		if(text!=null)
			textIn = new Image(inner,text.width,text.height,text.x,text.y);
	}
	
	public void set(float width, float height, float x, float y){
		border.set(width, height, x, y);
		if(borderIn!=null)
			borderIn.set(width, height, x, y);
		if(text!=null)
			text.set(width, height, x, y);
		if(textIn!=null)
			textIn.set(width, height, x, y);
	}
	
	public void setSize(float width, float height){
		border.setSize(width, height);
		if(borderIn!=null)
			borderIn.setSize(width, height);
		if(text!=null)
			text.setSize(width, height);
		if(textIn!=null)
			textIn.setSize(width, height);
	}
	
	public void setPos(float x, float y){
		border.setPos(x, y);
		if(borderIn!=null)
			borderIn.setPos(x, y);
		if(text!=null)
			text.setPos(x, y);
		if(textIn!=null)
			textIn.setPos(x, y);
	}
	
	public void setInnerScale(float scale){
		text.setScale(scale);
		innerScale = scale;
	}
	
	public float getWidth(){
		return border.getWidth();
	}
	
	public float getHeight(){
		return border.getHeight();
	}
	
	public float getX(){
		return border.getX();
	}
	
	public float getY(){
		return border.getY();
	}
	
	public Image getInner(){
		return text;
	}
	
	public boolean getState(){
		return state;
	}
	
	public boolean isPushed(float x, float y){
		if(x>=border.getX()&&x<=border.getX()+border.getWidth()&&
			y>=border.getY()&&y<=border.getY()+border.getHeight()){
				if(!isPushed)
					state = !state;
				isPushed = true;
				downTimer = .1f;
		}else
			isPushed = false;
		
		return isPushed;
	}
	
	public boolean isPushed(){
		return isPushed;
	}
}
