package com.sim.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.sim.manager.SpriteManager;
import com.sim.screen.GameScreen;

public abstract class Entity{
	public GameScreen game;
	
	protected Sprite sprite;
	
	Body body;
	double width, height;
	public MobAttribute atr;
	
	public int food = 0;
	
	public Entity(GameScreen game,Body body,MobAttribute atr,double width, double height){
		this.game = game;
		this.body = body;
		this.width = width;
		this.height = height;
		this.body.setLinearDamping(.8f);
		this.body.setAngularDamping(5f);
		this.atr = atr;
		sprite = SpriteManager.getCell();
	}
	
	public Entity(GameScreen game,Body body,MobAttribute atr,double radius){
		this.game = game;
		this.body = body;
		this.width = radius*2;
		this.height = radius*2;
		this.body.setLinearDamping(.8f);
		this.body.setAngularDamping(5f);
		this.atr = atr;
		sprite = SpriteManager.getCell();
	}
	
	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void setID(Entity id){
		this.body.setUserData(id); //1Mob 2Egg 3Food
	}
	
	public void push(Vector2 force){
		force.add(this.body.getLinearVelocity());
		this.body.setLinearVelocity(force);
	}
	
	public void turn(float angle){
		this.body.applyAngularImpulse(angle, true);
	}
	
	public Vector2 getVelocity(){
		return this.body.getLinearVelocity();
	}
	
	public void turnAround(){
		this.body.setAngularVelocity(10*this.body.getMass());
	}
	
	public boolean inBounds(float x, float y, float buff){
		double bx = this.getBody().getPosition().x-(this.getWidth()/2)-buff/2;
		double by = this.getBody().getPosition().y-(this.getHeight()/2)-buff/2;
		double width = this.getWidth()+buff;
		double height = this.getHeight()+buff;
		if(x>=bx&&x<=bx+width&&y>=by&&y<=by+height){
			return true;
		}
		return false;
	}
	
	public Body getBody(){return body;}
	public double getWidth(){return width;}
	public double getHeight(){return height;}
	
	public abstract void update();
	
	public abstract void render(SpriteBatch sb);
	
	public abstract boolean isDead();
	
	public abstract boolean bump(Entity ent);
	
}
