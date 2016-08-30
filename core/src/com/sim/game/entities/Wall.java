package com.sim.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sim.utils.create.BodyCreater;

public class Wall {
	float x,y,width,height;
	float force;
	
	public Wall(float width, float height){
		x = 0;
		y = 0;
		this.width = width;
		this.height = height;
		force = 20;
	}
	
	public void checkBounds(Entity e){
		if(e.getBody().getPosition().x+e.width<x){
			e.getBody().applyForceToCenter(force*e.getBody().getMass(), 0, true);
			e.getBody().setAngularVelocity(force/20);
		}else if(e.getBody().getPosition().x-e.width>x+width){
			e.getBody().applyForceToCenter(-force*e.getBody().getMass(), 0, true);
			e.getBody().setAngularVelocity(force/20);
		}else if(e.getBody().getPosition().y+e.height<y){
			e.getBody().applyForceToCenter(0, force*e.getBody().getMass(), true);
			e.getBody().setAngularVelocity(force/20);
		}else if(e.getBody().getPosition().y-e.height>y+height){
			e.getBody().applyForceToCenter(0, -force*e.getBody().getMass(), true);
			e.getBody().setAngularVelocity(force/20);
		}
	}

	public void render(ShapeRenderer sr) {
		sr.rect(x, y, width, height);
		
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getWidth() {
		return width;
	}
	
	public float getHeight(){
		return height;
	}
}
