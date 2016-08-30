package com.sim.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sim.manager.ScreenManager;
import com.sim.manager.SpriteManager;
import com.sim.screen.GameScreen;
import com.sim.screen.MenuScreen;
import com.sim.utils.actions.GestureControl;
import com.sim.utils.camera.OrthoCamera;


public class MainGame extends ApplicationAdapter {
	public static boolean DEBUG = true;
	
	public static final int SIZE = 800;
	public static double WIDTH,HEIGHT; //800x400 / SCALE		//2:1 ratio * 400		//Phones are 16:9 ratio
	public static final float SCALE = 10;							//getW:getH ratio
	public static double MOVEMENTSCALE;
	public static float DIAMETER;
	
	public static OrthoCamera camera;
	public static SpriteBatch sb;
	public static ShapeRenderer sr;
	ShaderProgram shader;
	
	public static boolean pause;
	public static float cUpdate,mUpdate;
	
	@Override
	public void create () {
		WIDTH = (double)Gdx.app.getGraphics().getWidth()/Gdx.app.getGraphics().getHeight() * SIZE / SCALE;
		HEIGHT = SIZE / SCALE;
		
		camera = new OrthoCamera();
		camera.resize();
		DIAMETER = (float) Math.sqrt(Math.pow(camera.viewportHeight, 2)+Math.pow(camera.viewportWidth,2));
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		ShaderProgram.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shader/shader.vsh"),Gdx.files.internal("shader/shader.fsh"));
		//sb.setShader(shader);
		ScreenManager.setScreen(new MenuScreen());
		
		pause = false;
		mUpdate = 60f;
		cUpdate = 0;
	}

	@Override
	public void render () {
		camera.update();
		if(!pause){
			while(cUpdate>=1/mUpdate){
				ScreenManager.update(Gdx.graphics.getDeltaTime());
				cUpdate-=1/mUpdate;
			}
			cUpdate+=Gdx.graphics.getDeltaTime();
		}
		
		
		Gdx.gl.glClearColor(.1f,.1f,.1f, 2f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		sb.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(MainGame.camera.combined);
		sr.setAutoShapeType(true);
		
		ScreenManager.render(sr,sb);
	}
	
	@Override
	public void resize(int width, int height){
		WIDTH = (double)Gdx.app.getGraphics().getWidth()/Gdx.app.getGraphics().getHeight() * SIZE / SCALE;
		HEIGHT = (int) (SIZE / SCALE);
		DIAMETER = (float) Math.sqrt(Math.pow(HEIGHT, 2)+Math.pow(WIDTH,2));
		camera.resize();
		DIAMETER = (float) Math.sqrt(Math.pow(camera.viewportHeight, 2)+Math.pow(camera.viewportWidth,2));
		ScreenManager.resize(width,height);
	}
	
	@Override
	public void pause(){
		ScreenManager.pause();
	}
	
	@Override
	public void resume(){
		ScreenManager.resume();
	}
	
	@Override
	public void dispose(){
		ScreenManager.dispose();
		sb.dispose();
		sr.dispose();
	}
}
