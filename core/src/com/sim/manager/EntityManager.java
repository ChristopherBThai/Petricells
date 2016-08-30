package com.sim.manager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.sim.game.entities.Coin;
import com.sim.game.entities.Egg;
import com.sim.game.entities.Entity;
import com.sim.game.entities.Food;
import com.sim.game.entities.Mob;
import com.sim.game.entities.MobAttribute;
import com.sim.game.entities.Wall;
import com.sim.screen.GameScreen;
import com.sim.utils.create.BodyCreater;
import com.sim.utils.helper.CameraHelper;

public class EntityManager implements Disposable{
	public GameScreen game;
	public ArrayList<Entity> entities;
	public Wall walls;
	public ArrayList<Entity> food;
	public ArrayList<Body> destroyList;
	public ArrayList<Coin> coins;
	
	public int worldSize = 300;
	
	public EntityManager(GameScreen game,double WIDTH,double HEIGHT,float SCALE,World world){
		this.game = game;
		entities = new ArrayList<Entity>();
		food = new ArrayList<Entity>();
		if(!this.load()){
			this.createInitEntities(5);
		}
		coins = new ArrayList<Coin>();
		walls = new Wall(worldSize,worldSize);
		destroyList = new ArrayList<Body>();
	}
	
	public void update(){
		for(int i=0;i<entities.size();i++){
			Entity entity = entities.get(i);
			walls.checkBounds(entity);
			entity.update();				
			game.hudMan.playerHud.countCarnHerb(entity);
			this.isDead(entity);
		}
		
		for(int i=0;i<coins.size();i++){
			coins.get(i).update();
			if(coins.get(i).isDead()){
				coins.remove(i);
				i--;
			}
		}
		
		for(Entity temp:food)
			walls.checkBounds(temp);
		this.addFood();
		
		if(destroyList.size()>0)
			destroyBodiesInList();
		
	}
	
	public void render(SpriteBatch sb){
		
		for(int i=0;i<coins.size();i++){
			coins.get(i).render(sb);
		}
		for(int i=0;i<entities.size();i++){
			Entity ent = entities.get(i);
			if(CameraHelper.inCamera(ent.getBody().getPosition().x,ent.getBody().getPosition().y))
				ent.render(sb);
			
		}
		
		for(int i=0;i<food.size();i++)
			if(CameraHelper.inCamera(food.get(i).getBody().getPosition().x, food.get(i).getBody().getPosition().y))
				food.get(i).render(sb);
	}
	
	private void destroyBodiesInList(){
		for(int i=0;i<destroyList.size();i++){
			if(game.camCon.isFollowing(destroyList.get(i)))
				game.camCon.follow(null);
			game.world.destroyBody(destroyList.get(i));
			destroyList.get(i).setUserData(null);
			destroyList.remove(destroyList.get(i));
			i--;
		}
	}
	
	private boolean isDead(Entity e){
		if(e.isDead()){
			entities.remove(e);
			destroy(e.getBody());
			return true;
		}
		return false;
	}
	
	public void destroy(Body body){
		if(!destroyList.contains(body))
			destroyList.add(body);
	}
	
	public int foodLimit = (int) (worldSize/10.0);
	public int timer = 0;
	private void addFood(){
		timer++;
		if(timer/20>1&&food.size()<foodLimit){
			Food fd = new Food(game,(float)(worldSize*Math.random()),(float)(worldSize*Math.random()));
			food.add(fd);
			timer/=20;
		}
	}
	
	private void createInitEntities(int count){
		for(int i=0;i<count;i++){
			MobAttribute tempMA = new MobAttribute();
			Body tempB  = BodyCreater.createCircle(worldSize*Math.random(),worldSize*Math.random(), tempMA.getRadius(), false, false, game.world);
			Mob tempM = new Mob(game,tempB, tempMA);
			entities.add(tempM);
		}
	}

	public void dispose() {
		game = null;
		entities = null;
		walls = null;
		food = null;
		destroyList = null;
		
	}

	public boolean moveFood(float x, float y, float deltaX, float deltaY) {
		/*
		Vector2 temp = MainGame.camera.unprojectCoordinates(x, y);
		x = temp.x;
		y = temp.y;
		float buff = 10;
		for(Entity entity:food){
			if(entity.inBounds(x, y, buff)){
				temp = MainGame.camera.unprojectCoordinates(deltaX,deltaY);
				x+=deltaX;
				y+=deltaY;
				entity.getBody().setLinearVelocity((x-entity.getBody().getPosition().x)*4f, (y-entity.getBody().getPosition().y)*4f);
				return true;
			}
		}
		*/
		return false;
	}
	
	public void save(){
		while(coins.size()>0){
			game.hudMan.playerHud.addGold(coins.remove(0).getValue());
		}
		
		Json save = new Json();
		ArrayList<Object> saveObject = new ArrayList<Object>();
		for(Entity e:entities){
			if(e instanceof Mob)
				saveObject.add(((Mob)e).getJson());
			else if(e instanceof Egg)
				saveObject.add(((Egg)e).getJson());
		}
		
		for(Entity e:food){
			if(e instanceof Food)
				saveObject.add(((Food)e).getJson());
		}
		
		sizeAndCoin sac = new sizeAndCoin();
		sac.coin = game.hudMan.playerHud.getGold();
		sac.worldSize = this.worldSize;
		saveObject.add(sac);
		
		String saveText = save.prettyPrint(saveObject);
		FileHandle file = null;
		try{
			file = Gdx.files.local("save.json");
		}finally{
			if(!Gdx.files.local("save.json").exists())
				file.writeString("", false);
		}
		file.writeString(saveText, false);
	}
	

	
	@SuppressWarnings({ "unchecked" })
	public boolean load(){
		if(!Gdx.files.local("save.json").exists())
			return false;
		Json save = new Json();
		ArrayList<Object> saveObject = save.fromJson(ArrayList.class,Gdx.files.local("save.json"));
		try{
			for(int i=0;i<saveObject.size();i++){
				if(saveObject.get(i) instanceof Mob.MobJson)
					entities.add(((Mob.MobJson)saveObject.get(i)).convert(game));
				else if(saveObject.get(i) instanceof Egg.EggJson)
					entities.add(((Egg.EggJson)saveObject.get(i)).convert(game));
				else if(saveObject.get(i) instanceof Food.FoodJson)
					food.add(((Food.FoodJson)saveObject.get(i)).convert(game));
				else if(saveObject.get(i) instanceof sizeAndCoin){
					this.worldSize = ((sizeAndCoin)saveObject.get(i)).worldSize;
					this.game.hudMan.playerHud.setGold(((sizeAndCoin)saveObject.get(i)).coin);
				}
			}
		}finally{}
		
		return true;
	}
	
	static public class sizeAndCoin{
		public int coin,worldSize;
	}
}
