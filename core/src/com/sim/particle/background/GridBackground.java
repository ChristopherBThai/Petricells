package com.sim.particle.background;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sim.utils.ui.Line;

public class GridBackground {
	float width,height,gridWidth;
	
	ArrayList<Line> lines;
	
	public GridBackground(float width, float height,float gridWidth){
		this.width = width;
		this.height = height;
		this.gridWidth = gridWidth;
		lines = new ArrayList<Line>();
		updateLines();
	}
	
	private void updateLines(){
		for(int i=0;i<=width;i+=gridWidth){
			lines.add(new Line(i, 0, i, height,3));	//x
		}
		for(int i=0;i<=height;i+=gridWidth){
			lines.add(new Line(0, i, width, i,3));	//y
		}
	}
	
	public void render(ShapeRenderer sr){
		sr.setColor(0, 10, 80, 1f);
		for(int i=0;i<width;i+=gridWidth){
			sr.line(i, 0, i, height);
		}
		for(int i=0;i<height;i+=gridWidth){
			sr.line(0, i, width, i);
		}
	}
	
	public void render(SpriteBatch sb){
		sb.setColor(.01f,.25f,.4f,1f);
		for(Line line:lines)
			line.render(sb);
		sb.setColor(Color.WHITE);
	}
}
