package com.sim.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class BoxButton implements Disposable{
	Box border;
	Text text;
	Image image;
	
	boolean isPushed,state;
	float downTimer;
	
	public BoxButton(float x, float y, float width, float height){
		border = new Box(x,y,width,height);
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
	
	public void render(SpriteBatch sb){
		if(isPushed){
			sb.setColor(Color.GRAY);
			if(text!=null)
				text.setColor(Color.GRAY);
		}
		
		border.render(sb);
		if(text!=null)
			text.render(sb);
		if(image!=null)
			image.render(sb);
		
		sb.setColor(Color.WHITE);
		if(text!=null)
			text.setColor(Color.WHITE);
	}
	
	private void align(){
		if(this.image!=null){
			if(image.getSprite().getWidth()/border.getWidth()>image.getSprite().getHeight()/border.getHeight()){
				image.setSize(border.getWidth(), (image.getSprite().getHeight()*border.getWidth())/image.getSprite().getWidth());
			}else{
				image.setSize((image.getSprite().getWidth()*border.getHeight())/image.getSprite().getHeight(), border.getHeight());
			}
			image.setPos(border.getX()+border.getWidth()/2f-image.getWidth()/2f, border.getY()+border.getHeight()/2f-image.getHeight()/2f);
		}
		
		if(this.text!=null){
			text.setPos(border.getX()+border.getWidth()/2f-text.getWidth()/2f, border.getY()+border.getHeight()/2f);
		}
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
	
	public void setImage(Sprite image){
		if(this.image==null){
			this.image = new Image(image, 0, 0, 0, 0);
			this.image.setOriginPercent(.5f, .5f);
			this.image.setScale(.8f);
		}else
			this.image.setSprite(image);
		align();
	}
	
	public void setText(String text){
		if(this.text==null)
			this.text = new Text(text,0,0);
		else
			this.text.setText(text);
		align();
	}
	
	public void setPos(float x, float y){
		border.setPos(x, y);
		align();
	}
	
	public void setSize(float width, float height){
		border.setSize(width, height);
		align();
	}
	
	public void set(float x, float y, float width, float height){
		border.set(x, y, width, height);
		align();
	}
	
	public float getX(){
		return border.getX();
	}
	
	public float getY(){
		return border.getY();
	}
	
	public float getWidth(){
		return border.getWidth();
	}
	
	public float getHeight(){
		return border.getHeight();
	}
	
	public Image getImage(){
		return image;
	}
	
	public Text getText(){
		return text;
	}
	
	public Box getBorder(){
		return border;
	}
	@Override
	public void dispose(){
		text.dispose();
	}
}
