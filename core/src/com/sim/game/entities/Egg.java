package com.sim.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sim.manager.SpriteManager;
import com.sim.particle.hud.MobDescriptionHud;
import com.sim.screen.GameScreen;
import com.sim.utils.create.BodyCreater;

public class Egg extends Entity{
	
	public int hatchTime;
	
	public Egg(GameScreen game,Body body,MobAttribute atr) {
		super(game, body, atr, atr.getRadius());
		this.hatchTime = atr.getHatchTime();
		this.getBody().setAngularDamping(0f);
		this.getBody().setAngularVelocity(10f);
		this.setID(this);
		this.sprite = SpriteManager.getCell();
	}

	@Override
	public void update() {
		hatchTime--;
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.draw(sprite, (float)(getBody().getPosition().x-this.width/2), (float)(getBody().getPosition().y-this.height/2), (float)(this.width),(float)(this.height));
	}

	@Override
	public boolean isDead() {
		if(hatchTime<=0){
			MobAttribute tempAtr = atr.getMutated();
			Mob tempM = new Mob(game,BodyCreater.createCircle(this.getBody().getPosition().x, this.getBody().getPosition().y, this.atr.getRadius(), false, false, this.getBody().getWorld()), tempAtr);
			ArrayList<Entity> tempEnt = game.entMan.entities;
			MobDescriptionHud tempMH = game.hudMan.mobHud;
			
			tempEnt.add(tempM);
			if(game.camCon.isFollowing(this))
				game.camCon.follow(tempM);
			
			return true;
		}
		return false;
	}

	@Override
	public boolean bump(Entity ent) {
		return false;
	}
	
	public EggJson getJson(){
		EggJson json = new EggJson();
		json.x = this.body.getPosition().x;
		json.y = this.body.getPosition().y;
		json.hatchTime = this.hatchTime;
		json.atr = this.atr;
		return json;
	}

	static public class EggJson implements Json.Serializable{
		float x,y;
		int hatchTime;
		MobAttribute atr;
		@Override
		public void write(Json json) {
			json.writeValue("x",x);
			json.writeValue("y",y);
			json.writeValue("hatchTime",hatchTime);
			json.writeValue("atr",atr);
		}

		@Override
		public void read(Json json, JsonValue jsonData) {
			x = json.readValue("x", float.class, jsonData);
			y = json.readValue("y", float.class, jsonData);
			hatchTime = json.readValue("hatchTime", int.class, jsonData);
			atr = json.readValue("atr", MobAttribute.class, jsonData);
		}
		
		public Egg convert(GameScreen game){
			Body body = BodyCreater.createCircle(x, y, atr.getRadius(), false, false, game.world);
			Egg egg = new Egg(game, body, atr);
			return egg;
		}
	}

}
