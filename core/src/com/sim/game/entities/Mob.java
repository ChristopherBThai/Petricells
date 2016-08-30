package com.sim.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sim.game.MainGame;
import com.sim.manager.SpriteManager;
import com.sim.screen.GameScreen;
import com.sim.utils.create.BodyCreater;

public class Mob extends Entity{
	
	public int layed;
	private double spriteScaleH,spriteScaleW;
	private Entity follow;
	
	Sprite eye;
	
	public Mob(GameScreen game,Body body, MobAttribute atr){
		super(game,body, atr, atr.getRadius());
		this.setID(this);
		spriteScaleH = 1.2*this.height/this.sprite.getHeight();
		spriteScaleW = 1.2*this.width/this.sprite.getWidth();
		eye = SpriteManager.getCircle();
	}

	@Override
	public void update() {
		if(atr != null){
			
			if(follow!=null)
				this.smartTurn(follow);
			
			if(atr.getSpeedFreq().pushable()){

				follow = this.getFollow();
				this.move();
			}
			
			if(atr.getLayTime().pushable()){
				if(food>0){
					layed++;
					food--;
					MobAttribute temp = atr.getMutated();
					Egg egg = new Egg(game,BodyCreater.createCircle(this.getBody().getPosition().x, this.getBody().getPosition().y, temp.getRadius()/2f, false, false, this.body.getWorld()), temp);//(Egg) BodyCreater.createCircle(this.getBody().getPosition().x, this.getBody().getPosition().y, atr.radius, false, false, this.getBody().getWorld());
					game.entMan.entities.add(egg);
				}else{
					this.atr.getLayTime().currentTime = 0;
				}
			}
			
			if(atr.getCoinTime().pushable()){
				game.entMan.coins.add(new Coin(body.getPosition().x,body.getPosition().y,atr.getCoinAmount(),game.hudMan.playerHud));
			}
		}
	}

	private Entity getFollow(){
		Entity target1 = null;
		Entity target2 = null;
		
			
		ArrayList<Entity> tempEnt = game.entMan.entities;
		ArrayList<Entity> tempF = game.entMan.food;
		
		if(this.atr.isHerb()){
			for(int i=0;i<tempF.size();i++){
				if(this.getBody().getPosition().dst(tempF.get(i).getBody().getPosition())<=this.atr.getSight()){
					target1 = this.getCloser(target1,tempF.get(i));
				}
			}
		}
		
		for(int i=0;i<tempEnt.size();i++){
			if(this.getBody().getPosition().dst(tempEnt.get(i).getBody().getPosition())<=this.atr.getSight()){
				if(this.atr.isHerb()){
					if(tempEnt.get(i) instanceof Mob && ((Mob)tempEnt.get(i)).atr.isCarn()){
						target2 = this.getCloser(target2, tempEnt.get(i));
					}
				}else{
					if(tempEnt.get(i) instanceof Mob && ((Mob)tempEnt.get(i)).atr.isHerb()){
						target2 = this.getCloser(target2, tempEnt.get(i));
					}
				}
			}
		}
		
		if(target2!=null)
			target1 = target2;
		
		return target1;
	}
	
	private Entity getCloser(Entity a, Entity b){
		if(a==null)
			return b;
		if(b==null)
			return a;
		float dst1 = this.getBody().getPosition().dst(a.getBody().getPosition());
		float dst2 = this.getBody().getPosition().dst(b.getBody().getPosition());
		if(dst1>dst2)
			return b;
		else
			return a;
	}

	private void move() {
		float rad = this.getBody().getAngle();
		float xVel = (float) Math.cos(rad);
		float yVel = (float) Math.sin(rad);
		this.push(new Vector2(xVel*atr.getSpeed(),yVel*atr.getSpeed()));
		if(follow==null)
			this.getBody().setAngularVelocity((float)(Math.random()*(atr.getMaxTurnVel()-atr.getMinTurnVel())+atr.getMinTurnVel()));
		
	}
	
	private void smartTurn(Entity e){
		double r = this.getRadDiff(e)%6.2831-this.getBody().getAngle()%MathUtils.PI2;
		double l = this.getRadDiff(e)%6.2831+MathUtils.PI2-this.getBody().getAngle()%MathUtils.PI2;
		if(Math.abs(r)>Math.abs(l)){
			this.getBody().setAngularVelocity((float)l*atr.getSpeed()*4);
		}else{
			this.getBody().setAngularVelocity((float)r*atr.getSpeed()*4);
		}
		
	}
	
	private float getRadDiff(Entity e){
		if(e==null||this.getBody()==null||e.getBody()==null)
			return 0f;
		float yDiff = e.getBody().getPosition().y - this.getBody().getPosition().y;
		float xDiff = e.getBody().getPosition().x - this.getBody().getPosition().x;
		float radians;
		
		if(xDiff==0)
			if(yDiff<0)
				radians = (float) (Math.PI/2f);
			else
				radians = -(float) (Math.PI/2f);
		else
			radians = (float) (Math.atan(yDiff/xDiff));
		
		if(xDiff<0){
			radians -= (float) (Math.PI);
		}
			
		if(this.atr.isHerb()&&!(e instanceof Food)){
			radians -= Math.PI;
		}
		
		return radians;
	}

	@Override
	public void render(SpriteBatch sb) {
		
		sb.setColor(atr.getColor());
		
		float x,y;
		if(MainGame.camera.zoom<=1f){
			x = (float)(getBody().getPosition().x+MathUtils.cos(this.getBody().getAngle())*this.width/5f-this.width/(3.6f*2));
			y = (float)(getBody().getPosition().y+MathUtils.sin(this.getBody().getAngle())*this.height/5f-this.height/(3.6f*2));
			if(follow==null){
				x += MathUtils.cos(this.getBody().getAngle())*this.width/10f;
				y += MathUtils.sin(this.getBody().getAngle())*this.width/10f;
			}else{
				x += MathUtils.cos(this.getRadDiff(follow))*this.width/10f;
				y += MathUtils.sin(this.getRadDiff(follow))*this.width/10f;
			}
		}else{
			x = (float)(getBody().getPosition().x+MathUtils.cos(this.getBody().getAngle())*this.width/3.6f-this.width/(3.6f*2));
			y = (float)(getBody().getPosition().y+MathUtils.sin(this.getBody().getAngle())*this.height/3.6f-this.height/(3.6f*2));
		}
		
		sb.draw(eye, 
				x,y,
				(float)0, (float)0, 
				(float)this.width/3.6f, (float)this.height/3.6f, 
				1f,1f, 
				0f);
		
		if(this.atr.isCarn()){
			Sprite teeth = SpriteManager.getTeeth();
			sb.draw(teeth, 
					(float)(getBody().getPosition().x), (float)(getBody().getPosition().y-teeth.getHeight()*this.spriteScaleH/2), 
					(float)0f, (float)(teeth.getHeight()*this.spriteScaleH/2), 
					(float)(teeth.getWidth()*this.spriteScaleW), (float)(teeth.getHeight()*this.spriteScaleH), 
					1f, 1f, 
					MathUtils.radiansToDegrees * this.getBody().getAngle());
		}
		
		sb.draw(this.sprite, 
				(float)(getBody().getPosition().x-this.width/2), (float)(getBody().getPosition().y-this.height/2), 
				(float)this.width/2, (float)this.height/2, 
				(float)this.width, (float)this.height, 
				1.2f, 1.2f, 
				MathUtils.radiansToDegrees * this.getBody().getAngle());
	}

	@Override
	public boolean isDead() {
		if(atr!=null&&atr.getLifeTime().pushable())
			return true;
		return false;
	}
	
	public void eat(){
		this.atr.getLifeTime().currentTime += this.atr.getFoodWorth();
		food++;
	}

	@Override
	public boolean bump(Entity ent) {
		if(ent instanceof Mob){
			if(this.atr.isCarn()){
				if(ent.atr.isHerb()){
					game.entMan.destroy(ent.getBody());
					game.entMan.entities.remove(ent);
					this.eat();
					return true;
				}
			}else if(this.atr.isHerb()){
				if(ent.atr.isCarn()){
					game.entMan.destroy(this.getBody());
					((Mob)ent).eat();
					game.entMan.entities.remove(this);
					return true;
				}
			}
		}
		return false;
	}
	
	public MobJson getJson(){
		MobJson mob = new MobJson();
		mob.atr = this.atr;
		mob.food = this.food;
		mob.layed = this.layed;
		mob.rotation = this.body.getAngle();
		mob.x = this.body.getPosition().x;
		mob.y = this.body.getPosition().y;
		return mob;
	}
	
	static public class MobJson implements Json.Serializable{
		private float x, y;
		private MobAttribute atr;
		private int food;
		private int layed;
		private float rotation;
		
		@Override
		public void write(Json json) {
			json.writeValue("x",x);
			json.writeValue("y",y);
			json.writeValue("food",food);
			json.writeValue("layed",layed);
			json.writeValue("rotation",rotation);
			json.writeValue("atr",atr);
		}

		@Override
		public void read(Json json, JsonValue jsonData) {
			x = json.readValue("x", float.class, jsonData);
			y = json.readValue("y", float.class, jsonData);
			food = json.readValue("food", int.class, jsonData);
			layed = json.readValue("layed", int.class, jsonData);
			rotation = json.readValue("rotation", float.class, jsonData);
			atr = json.readValue("atr", MobAttribute.class, jsonData);
		}
		
		public Mob convert(GameScreen game){
			Body body = BodyCreater.createCircle(x, y, atr.getRadius(),rotation, false, false, game.world);
			Mob mob = new Mob(game, body, atr);
			mob.food = food;
			mob.layed = layed;
			return mob;
		}
	}
	
}
