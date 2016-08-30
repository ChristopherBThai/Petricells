package com.sim.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sim.game.MainGame;
import com.sim.game.entities.Entity;
import com.sim.game.entities.Food;
import com.sim.game.entities.MobBlurred;
import com.sim.game.entities.MobAttribute;
import com.sim.game.entities.Wall;
import com.sim.manager.ScreenManager;
import com.sim.manager.SpriteManager;
import com.sim.utils.camera.OrthoCamera;
import com.sim.utils.create.BodyCreater;
import com.sim.utils.sound.Bgm;
import com.sim.utils.ui.Button;
import com.sim.utils.ui.Image;

public class MenuScreen extends Screen implements Disposable{
	public final double WIDTH = MainGame.WIDTH;
	public final double HEIGHT = MainGame.HEIGHT;
	public final float SCALE = MainGame.SCALE;
	
	OrthoCamera cam;
	Vector2 follow;
	
	Image title;
	Sprite titleEye;
	float titleEyeWidth,titleEyeHeight,titleEyeX,titleEyeY,rotate,eyeLookX,eyeLookY;
	Button[] menuButton;
	
	World world;
	public Box2DDebugRenderer b2dr;
	ArrayList<Entity> entities;
	float mapSize = 4f;
	Wall walls;
	
	Bgm bgm;
	
	@Override
	public void create() {
		cam = MainGame.camera;
		cam.zoom = 1f;
		cam.follow(null);
		middleCam();
		cam.setPos(follow.x, follow.y);
		
		title = new Image(SpriteManager.getPetricells());
		titleEye = SpriteManager.getCircle();
		titleEye.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		setTitle();
		eyeLookX = titleEyeX+titleEyeWidth;
		eyeLookY = titleEyeY+titleEyeHeight/2;
		menuButton = new Button[3];
		for(int i=0;i<menuButton.length;i++)
			menuButton[i] = new Button(SpriteManager.getButton());
		setButton();
		
		world = new World(new Vector2(0,0),true);
		walls = new Wall(cam.viewportWidth*3.5f,cam.viewportHeight*1.5f);
		walls.setPos(follow.x-walls.getWidth()/2f,follow.y-walls.getHeight()/2f);
		entities = new ArrayList<Entity>();
		this.createInitEntities();
		
		b2dr = new Box2DDebugRenderer();
		
		bgm = new Bgm("bgm/menu.mp3");
	}

	@Override
	public void update(float delta) {
		bgm.play();
		
		world.step(1 / 60f, 6, 2);
		for(Entity entity:entities){
			entity.update();
			walls.checkBounds(entity);
		}
		
		rotateEye();
		for(Button buttonT:menuButton)
			buttonT.update(delta);
		
		cam.update();
		cam.setPos(cam.position.x + (follow.x - cam.position.x) * .1f, cam.position.y + (follow.y - cam.position.y) * .1f);
		this.touch();
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		//b2dr.render(world, MainGame.camera.combined);
		
		sb.begin();
		for(Entity entity:entities)
			entity.render(sb);
		sb.draw(titleEye, titleEyeX+title.getWidth()*.007f, titleEyeY,
				titleEyeWidth/2-title.getWidth()*.007f,titleEyeHeight/2,
				titleEyeWidth, titleEyeHeight, 
				1f, 1f, 
				rotate);
		title.render(sb);
		for(Button button:menuButton)
			button.render(sb);
		sb.end();
		

		if(MainGame.DEBUG){
			sr.setColor(Color.WHITE);
			sr.begin();
			walls.render(sr);
			sr.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		cam.setPos((float) ((WIDTH*mapSize) /2f),(float) ((HEIGHT*mapSize) /2f));
		setTitle();
		setButton();
	}

	@Override
	public void dispose() {
		SpriteManager.dispose();
		b2dr.dispose();
		bgm.dispose();
		world.dispose();
		entities = null;
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	private void setTitle(){
		float temp = cam.viewportWidth/title.getWidth() * .7f;
		float width =  title.getWidth() * temp;
		float height = title.getHeight() * temp;;
		float x = cam.position.x - (width*.876f)/2f;
		float y = cam.position.y - height/2f + cam.viewportHeight*.3f;
		title.set(width, height, x, y);
		
		
		titleEyeWidth = width * .04f;
		titleEyeHeight = titleEyeWidth;
		titleEyeX = x - titleEyeWidth/2f + width * .461f;
		titleEyeY = y - titleEyeHeight/2f + height*.57f;
	}
	
	private void setButton(){
		float temp = cam.viewportWidth/menuButton[0].getWidth() * .3f;
		float width =  menuButton[0].getWidth() * temp;
		float height = menuButton[0].getHeight() * temp;;
		float x = cam.position.x - width/2f;
		float y = cam.position.y - height/2f;
		
		menuButton[0].set(width, height, x, y);
		menuButton[1].set(width, height, x, y-height*.84f);
		menuButton[2].set(width, height, x, y-height*1.68f);
	}
	
	private void createInitEntities(){
		MobAttribute tempMA = new MobAttribute();
		tempMA.setRadius(17);
		Body tempB  = BodyCreater.createCircle(30f, 30f, tempMA.getRadius(), false, false, world);
		MobBlurred tempM = new MobBlurred(tempB, tempMA);
		entities.add(tempM);
		
		for(int i=0;i<40;i++){
			tempMA = new MobAttribute();
			tempMA.setRadius(Math.random()*10+7);
			tempMA.setSpeedFreq((float) (10+Math.random()*40));
			tempMA.setSpeed((float) (4*Math.random()*4));
			if(i%2==0)
				tempB  = BodyCreater.createCircle((float)((WIDTH*mapSize) /2f + cam.viewportWidth), (float)((HEIGHT*mapSize) /2f), tempMA.getRadius(), Math.random()*360, false, false, world);
			else
				tempB  = BodyCreater.createCircle((float)((WIDTH*mapSize) /2f - cam.viewportWidth), (float)((HEIGHT*mapSize) /2f), tempMA.getRadius(), Math.random()*360, false, false, world);	
			tempM = new MobBlurred(tempB, tempMA);
			entities.add(tempM);
		}
	}
	
	private void rotateEye(){
		float yDiff = eyeLookY - titleEyeY-titleEyeHeight/2f;
		float xDiff = eyeLookX - titleEyeX-titleEyeWidth/2f;
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
		
		if(Math.abs(MathUtils.radiansToDegrees*radians-rotate)>180){
			if(rotate>-90)
				rotate -= 360;
			else if(rotate<-90)
				rotate += 360;
		}
			rotate = rotate + (MathUtils.radiansToDegrees*radians-rotate)*.1f;
	}
	
	public void middleCam(){
		follow = new Vector2((float)((WIDTH*mapSize) /2f),(float)((HEIGHT*mapSize) /2f));
	}
	public void rightCam(){
		follow = new Vector2((float)((WIDTH*mapSize)/2f + cam.viewportWidth),(float)((HEIGHT*mapSize) /2f));
	}
	public void leftCam(){
		follow = new Vector2((float)((WIDTH*mapSize) /2f - cam.viewportWidth),(float)((HEIGHT*mapSize) /2f));
	}
	
	boolean previousTouch;
	private void touch(){
		if(Gdx.input.isTouched()){
			previousTouch = true;
			
			Vector2 unprojected = cam.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
			
			for(Button buttonT:menuButton)
				buttonT.isPushed(unprojected.x,unprojected.y);
			
			eyeLookY = unprojected.y;
			eyeLookX = unprojected.x; 
		}else if(previousTouch){
			previousTouch = false;
			
			if(menuButton[0].isPushed()){
				ScreenManager.setScreen(new GameScreen());
			}else if(menuButton[1].isPushed()){
				this.rightCam();
			}else if(menuButton[2].isPushed()){
				this.leftCam();
			}else{
				this.middleCam();
			}
		}
	}

	@Override
	public void updateBinds() {
		
	}
}
