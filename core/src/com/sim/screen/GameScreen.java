package com.sim.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.sim.game.MainGame;
import com.sim.game.entities.Coin;
import com.sim.manager.EntityManager;
import com.sim.manager.HudManager;
import com.sim.manager.SpriteManager;
import com.sim.particle.background.GridBackground;
import com.sim.utils.actions.GestureControl;
import com.sim.utils.camera.CameraControl;
import com.sim.utils.helper.GameCollision;
import com.sim.utils.ui.BindedButton;
import com.sim.utils.ui.BindedImage;

public class GameScreen extends Screen implements Disposable{
	public final double WIDTH = MainGame.WIDTH;
	public final double HEIGHT = MainGame.HEIGHT;
	public final float SCALE = MainGame.SCALE;
	public float worldMult;
	public boolean pause;
	
	public HudManager hudMan;
	public EntityManager entMan;
	
	public World world;
	public Box2DDebugRenderer b2dr;
	public CameraControl camCon;
	public GameCollision gamCol;
	public GestureControl gesCon;
	
	public GridBackground GridB;
	@Override
	public void create() {
		worldMult = 3f;
		
		world = new World(new Vector2(0,0),true);
		gamCol = new GameCollision(this);
		world.setContactListener(gamCol);
		b2dr = new Box2DDebugRenderer();
		
		hudMan = new HudManager();
		entMan = new EntityManager(this, WIDTH, HEIGHT, SCALE, world);
		
		camCon = new CameraControl(this, MainGame.camera,entMan.entities);
		gesCon = new GestureControl();
		
		GridB = new GridBackground(entMan.worldSize, entMan.worldSize, 30);
		this.updateBinds();
		
	}
	
	@Override
	public void update(float delta) {
		if(!pause){
			world.step(1 / 60f, 6, 2);
			entMan.update();
		}

		hudMan.update(delta);
		
		camCon.update();
	}
	
	@Override
	public void render(ShapeRenderer sr,SpriteBatch sb) {
		if(MainGame.DEBUG)
			b2dr.render(world, MainGame.camera.combined);
		
		sr.begin();
		hudMan.render(sr);
		sr.end();
		
		sb.begin();
		GridB.render(sb);
		entMan.render(sb);
		hudMan.render(sb);
		sb.end();
	}
	
	@Override
	public void resize(int width, int height) {
		this.updateBinds();
		camCon.resize();
	}
	
	@Override
	public void dispose() {
		this.save();
		world.dispose();
		hudMan.dispose();
		entMan.dispose();
		SpriteManager.dispose();
		b2dr.dispose();
	}
	
	@Override
	public void pause() {
		save();
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void updateBinds(){
		hudMan.updateBinds();
	}
	
	public void tap(float x, float y, int count, int button){
		if(!hudMan.tap(x,y,count,button))
			camCon.focus(x, y);
		hudMan.mobHud.rescan();
	}
	
	public void longPress(float x, float y) {
		hudMan.longPress(x,y);
	}
	
	public void save(){
		entMan.save();
	}
}
