package com.sim.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sim.manager.SpriteManager;
import com.sim.screen.GameScreen;
import com.sim.utils.create.BodyCreater;

public class Food extends Entity{

	public static MobAttribute stat = new MobAttribute();
	
	private float spriteDiameter;
	
	public Food(GameScreen game,float x, float y) { 
		super(game,BodyCreater.createCircle(x, y, .6f, false, true, game.world), stat, .6f);
		this.sprite = SpriteManager.getFood();
		spriteDiameter = (float) (8.8*this.getWidth());
		this.setID(this);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch sb) {
		spriteDiameter = (float) (8.8*this.getWidth());
		sb.draw(sprite, this.getBody().getPosition().x-spriteDiameter/2, this.getBody().getPosition().y-spriteDiameter/2, spriteDiameter, spriteDiameter);
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean bump(Entity ent) {
		return false;
	}
	
	public FoodJson getJson(){
		FoodJson json = new FoodJson();
		json.x = this.body.getPosition().x;
		json.y = this.body.getPosition().y;
		return json;
	}
	
	static public class FoodJson implements Json.Serializable{
		float x,y;
		
		@Override
		public void write(Json json) {
			json.writeValue("x", x);
			json.writeValue("y",y);
		}

		@Override
		public void read(Json json, JsonValue jsonData) {
			x = json.readValue("x", float.class, jsonData);
			y = json.readValue("y", float.class, jsonData);
		}
		
		public Food convert(GameScreen game){
			return new Food(game,x,y);
		}
		
	}
	
}
