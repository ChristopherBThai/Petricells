package com.sim.particle.hud.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sim.game.MainGame;
import com.sim.manager.SpriteManager;
import com.sim.utils.camera.OrthoCamera;
import com.sim.utils.ui.BindedButton;
import com.sim.utils.ui.BindedImage;
import com.sim.utils.ui.BindedLine;

public class Selector{

	private BindedButton options;
	private BindedLine[] optionsLine;
	private BindedImage[] dot;
	
	private float width = 1.5f;
	private float start = .003f;
	private float end = .4f;
	public Selector(){
		options = new BindedButton(SpriteManager.getOption(), 
				(MainGame.camera.viewportHeight*.14f/SpriteManager.getOption().getHeight())*SpriteManager.getOption().getWidth(),
				MainGame.camera.viewportHeight*.14f,
				0f, 0f); 
		options.setInner(SpriteManager.getOptionArrow(),.5f);
		options.bottomAlign(); options.leftAlign();
				
		optionsLine = new BindedLine[3];
		optionsLine[0] = new BindedLine(OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.09f), 
				OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.91f),
				width);	//BASE	
		optionsLine[1] = new BindedLine(OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.09f), 
				options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.09f),
				width);	//BOTTOM
		optionsLine[2] = new BindedLine(OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.91f), 
				options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.91f),
				width);	//TOP
		
		dot = new BindedImage[2];
		dot[0] = new BindedImage(SpriteManager.getCircle(),
				width/1.5f,width/1.5f,
				optionsLine[0].getX1Percent(),optionsLine[0].getY1Percent());
		dot[0].centerAlign();
		dot[1] = new BindedImage(SpriteManager.getCircle(),
				width/1.5f,width/1.5f,
				optionsLine[0].getX2Percent(),optionsLine[0].getY2Percent());
		dot[1].centerAlign();
	}
	
	public void render(SpriteBatch sb){
		options.render(sb);
		for(BindedLine line:optionsLine)
			line.render(sb);
		for(BindedImage image:dot)
			image.render(sb);
	}
	
	public void update(float delta){
		options.update(delta);
		if(!options.getState()){
			if(Math.abs(options.getXPercent()-start)>.001){
				options.getInner().addRotate((0-options.getInner().getRotate())*.1f);
				options.setPos(options.getXPercent()+(start-options.getXPercent())*.1f, 0);
				optionsLine[1].setEnd(options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.09f));
				optionsLine[2].setEnd(options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.91f));
			}
		}else{
			if(Math.abs(options.getXPercent()-end)>.001){
				options.getInner().addRotate((180-options.getInner().getRotate())*.1f);
				options.setPos(options.getXPercent()+(end-options.getXPercent())*.1f, 0);
				optionsLine[1].setEnd(options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.09f));
				optionsLine[2].setEnd(options.getXPercent()+OrthoCamera.lengthToPercentX(options.getWidth()*.13f), OrthoCamera.lengthToPercentY(options.getHeight()*.91f));
			}
		}
	}
	
	public void updateBind(){
		options.updateBind();
		for(BindedLine line:optionsLine)
			line.updateBind();
		for(BindedImage image:dot)
			image.updateBind();
	}
	
	public boolean tap(float x, float y, int count, int button) {
		return options.isPushed(x, y);
	}
}
