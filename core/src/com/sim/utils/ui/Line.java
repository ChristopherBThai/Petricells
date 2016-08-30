package com.sim.utils.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sim.manager.SpriteManager;

public class Line {
	private Vector2 start, end;
	private float width;
	private float angle,dist;
	
	Sprite sprite;
	
	public Line(){
		start = new Vector2(0,0);
		end = new Vector2(0,0);
		width = 3;
		sprite = SpriteManager.getLine();
		this.updateLine();
	}
	
	public Line(double x1, double y1, double x2, double y2){
		start = new Vector2((float)x1,(float)y1);
		end = new Vector2((float)x2,(float)y2);
		width = 3;
		sprite = SpriteManager.getLine();
		this.updateLine();
	}
	
	public Line(double x1, double y1, double x2, double y2, float width){
		start = new Vector2((float)x1,(float)y1);
		end = new Vector2((float)x2,(float)y2);
		this.width = width;
		sprite = SpriteManager.getLine();
		this.updateLine();
	}
	
	public Line(double x, double y){
		start = new Vector2((float)x,(float)y);
		end = new Vector2((float)x,(float)y);
		width = 3;
		sprite = SpriteManager.getLine();
		this.updateLine();
	}
	
	public void setStart(float x, float y){
		this.start.set(x, y);
		this.updateLine();
	}
	
	public void setStart(Vector2 start){
		this.start = start;
		this.updateLine();
	}
	
	public void setEnd(float x, float y){
		this.end.set(x, y);
		this.updateLine();
	}
	
	public void setEnd(Vector2 end){
		this.end = end;
		this.updateLine();
	}
	
	public void set(float x1, float y1, float x2, float y2){
		start.set((float)x1, (float)y1);
		end.set((float)x2,(float)y2);
		this.updateLine();
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public float getWidth(){
		return width;
	}
	
	public Vector2 getStart(){
		return start;
	}
	
	public Vector2 getEnd(){
		return end;
	}
	
	public void add(Vector2 add){
		start.add(add);
		end.add(add);
		this.updateLine();
	}
	
	public float getAngle(){
		return angle;
	}
	
	public static Vector2 add(Vector2 x, Vector2 y){
		Vector2 temp = new Vector2(0f,0f);
		temp.add(x);
		temp.add(y);
		return temp;
	}
	
	private void updateLine(){
		dist = start.dst(end);
		angle = (float) Math.toDegrees(Math.atan2(end.y - start.y, end.x - start.x));
	}
	
	public void render(SpriteBatch sb){
		sb.draw(sprite, start.x, start.y-width/2, 0, width/2, dist, width, 1f, 1f, angle);
	}
}
