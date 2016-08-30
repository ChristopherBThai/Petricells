package com.sim.particle.hud;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sim.game.MainGame;
import com.sim.game.entities.Egg;
import com.sim.game.entities.Entity;
import com.sim.game.entities.Mob;
import com.sim.manager.SpriteManager;
import com.sim.utils.create.FontCreater;
import com.sim.utils.ui.Line;

public class MobDescriptionHud implements Disposable{
	Line pointer[];
	Line side[]; //<side[i] ^w1 vw2 >h2
	Rectangle info;
	String stat;
	Vector2 pos;
	float radius;
	
	BitmapFont bitFont;
	
	float animation1,animation2;
	
	public Entity point;
	
	public MobDescriptionHud(){
		animation1 = 0f;
		animation2 = 0f;
		point = null;
		info = new Rectangle();
		pointer = new Line[4];
		for(int i=0;i<pointer.length;i++)
			pointer[i] = new Line();
		side = new Line[4];
		for(int i=0;i<side.length;i++)
			side[i] = new Line();
		stat = "";
		bitFont = FontCreater.createFonts(bitFont, 100);
		//bitFont = new BitmapFont(Gdx.files.internal("font.fnt"),false);
		bitFont.setColor(1f,1f,1f,1f);
		bitFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bitFont.getData().setScale(.02f);
		bitFont.getData().scaleX*=.8;
		//bitFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public void update(){
		if(point!=null){
			if(animation1<1f)
				animation1 += .1f;
			else if(animation2<1f){
				animation1 = 1f;
				animation2 += .09f;
				if(animation2>1f)
					animation2 = 1f;
			} 
		}else{
			if(animation2>0f)
				animation2 -= .09f;
			else if(animation1>0f){
				animation2 = 0;
				animation1 -= .1f;
				if(animation1<0f)
					animation1 = 0;
			}
		}
		
	}
	
	public void render(SpriteBatch sb){
		renderSight(sb);
		renderText(sb);
		//for(Line line: pointer)
		//	line.render(sb);
	}
	
	public void renderSight(SpriteBatch sb){
		if(animation1!=0f){
			float tempR = radius*(animation1+animation2)/2;
			sb.draw(SpriteManager.getSight(), pos.x-tempR, pos.y-tempR, tempR*2, tempR*2);
		}
	}
	
	public void renderText(SpriteBatch sb){
		
		if(animation1==1f){
			Vector2 cam = new Vector2(MainGame.camera.position.x,MainGame.camera.position.y);
			
			if(point!=null){
				stat = "";
				if(point instanceof Egg)
					stat += String.format("Hatches: %.1f/%.1fs%n", ((Egg)(point)).hatchTime/60f,point.atr.getHatchTime()/60f);	
				stat += String.format("Life: %.1f/%.1fs%n",point.atr.getCurrentLifeTime()/60f,point.atr.getMaxLifeTime()/60f);
				stat += String.format("Egg: %.1f/%.1fs%n", point.atr.getCurrentLayTime()/60f,point.atr.getMaxLayTime()/60f);
				stat += String.format("Gold: %.1f - %.1f/%.1f%n", point.atr.getCoinAmount(),point.atr.getCurrentCoinTime()/60f,point.atr.getMaxCoinTime()/60f);
				stat += String.format("Food: %1d - %04.1fs%n", point.food,point.atr.getFoodWorth()/60f);
				stat += String.format("Speed: %.1fmm/%04.1fs%n",point.atr.getSpeed(),point.atr.getCurrentSpeedFreq()/60);
				stat += String.format("Turn: %.1f-%.1f mm/s%n", point.atr.getMinTurnVel(),point.atr.getMaxTurnVel());
				stat += String.format("Sight: %.1fmm%n", point.atr.getSight());
				stat += String.format("Size: %.1fmm%n", point.atr.getRadius()*2);
				stat += String.format("Mass: %.1fg%n", point.getBody().getMass());
				if(point.atr.isHerb())
					stat += String.format("Diet: Herbivore %3.1f%n",Math.abs(point.atr.getHerbPercent()));
				else
					stat += String.format("Diet: Carnivore %3.1f%n",Math.abs(point.atr.getHerbPercent()));
				if(point instanceof Mob)
					stat += String.format("Eggs Layed: %1d", ((Mob)point).layed);
			}
			
			bitFont.getData().setScale(.02f * MainGame.camera.zoom);
			bitFont.getData().scaleX *= .8f;
			bitFont.draw(sb, stat, cam.x+info.getX()*1.1f,cam.y-info.getY()*.9f);
		}
	}
	
	public void renderHud(ShapeRenderer sr){
		if(animation1!=0f){
			Vector2 cam = new Vector2(MainGame.camera.position.x,MainGame.camera.position.y);
			
			sr.set(ShapeType.Line);
			
			sr.setColor(Color.WHITE);
			
			for(int i=0;i<pointer.length;i++){
				if(animation1!=1f)
					sr.line(pointer[i].getStart(), Line.add(cam,pointer[i].getEnd()).sub(pointer[i].getStart()).scl(animation1).add(pointer[i].getStart()));
				else
					sr.line(pointer[i].getStart(), Line.add(cam,pointer[i].getEnd()));
			}
			
			if(animation2==1f){
				sr.set(ShapeType.Filled);
				sr.setColor(Color.BLACK);
				sr.rect(info.getX()+cam.x, info.getY()+cam.y, info.getWidth(), info.getHeight());
				sr.set(ShapeType.Line);
				sr.setColor(Color.WHITE);
			}
			
			Vector2 temp1 = new Vector2(0,0);
			Vector2 temp2 = new Vector2(0,0);
			if(animation2!=1f){
				for(int i=0;i<side.length;i++){
					temp1.set(cam).add(side[i].getStart());
					temp2.set(cam).add(side[i].getEnd()).sub(temp1).scl(animation2/2).add(temp1);
					sr.line(temp1, temp2);
					temp2.set(cam).add(side[i].getEnd());
					temp1.set(cam).add(side[i].getStart()).sub(temp2).scl(animation2/2).add(temp2);
					sr.line(temp1,temp2);
				}
			}else{
				for(int i=0;i<side.length;i++)
					sr.line(Line.add(cam,side[i].getStart()), Line.add(cam,side[i].getEnd()));
			}
		}
	}
	
	public void pointTo(Entity body){
		this.point = body;
		updatePoint(body);
	}
	
	private void updatePoint(Entity body){
		if(body!=null){
			for(int i=0;i<pointer.length;i++)
				pointer[i].setStart(point.getBody().getPosition());
			pos =	point.getBody().getPosition();
			radius = point.atr.getSight();
		}
	}
	
	public void rescan(){
		float zoom = MainGame.camera.zoom;
		info.set(MainGame.camera.viewportWidth/16*zoom,(float)(-MainGame.camera.viewportHeight/2.4*zoom),(float)(MainGame.camera.viewportWidth/2.5)*zoom,(float)(MainGame.camera.viewportHeight/1.2)*zoom);
		pointer[0].setEnd(info.getX(),info.getY()+info.getHeight());
		pointer[1].setEnd(info.getX()+info.getWidth(),info.getY());
		pointer[2].setEnd(info.getX(),info.getY());
		pointer[3].setEnd(info.getX()+info.getWidth(),info.getY()+info.getHeight());
		side[0].set(info.getX(),info.getY(),info.getX(),info.getY()+info.getHeight());
		side[1].set(info.getX()+info.getWidth(),info.getY(),info.getX()+info.getWidth(),info.getY()+info.getHeight());
		side[2].set(info.getX(),info.getY()+info.getHeight(),info.getX()+info.getWidth(),info.getY()+info.getHeight());
		side[3].set(info.getX(),info.getY(),info.getX()+info.getWidth(),info.getY());
	}

	public void dispose(){
		bitFont.dispose();
	}
}
