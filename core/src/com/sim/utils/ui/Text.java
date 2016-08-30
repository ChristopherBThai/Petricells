package com.sim.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sim.game.MainGame;
import com.sim.utils.create.FontCreater;

public class Text implements Disposable{
	
	private BitmapFont bitFont;
	private Color color;
	
	private String text;
	private GlyphLayout size;
	private float width,height;
	
	private Vector2 pos;
	
	public Text(String text,float x, float y){
		this.text = text;
		bitFont = FontCreater.createFonts(bitFont, 100);
		bitFont.setColor(1f,1f,1f,1f);
		bitFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bitFont.getData().setScale(.02f);
		bitFont.getData().scaleX *= .8f;
		this.size = new GlyphLayout(bitFont, text);
		pos = new Vector2(x,y);
		color = null;
		updateSize();
	}
	
	public void updateSize(){
		this.size.setText(bitFont, text);
		width = this.size.width;
		height = this.size.height;
	}
	
	public void render(SpriteBatch sb){
		if(color!=null){
			bitFont.setColor(color);
			bitFont.draw(sb, text, pos.x, pos.y);
			bitFont.setColor(Color.WHITE);
		}else
			bitFont.draw(sb, text, pos.x, pos.y);
	}
	
	public void dispose(){
		bitFont.dispose();
	}
	
	public void setSize(float size){
		bitFont.getData().setScale(.02f*size);
		bitFont.getData().scaleX *= .8f;
		updateSize();
	}
	
	public void setColor(Color color){
		if(!color.equals(Color.WHITE))
			this.color = color;
		else
			this.color = null;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setPos(Vector2 pos){
		this.pos = pos;
	}
	
	public void setPos(float x, float y) {
		this.pos.set(x, y);
	}
	
	public Vector2 getPos(){
		return pos;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getX(){
		return pos.x;
	}
	
	public float getY(){
		return pos.y;
	}
}
