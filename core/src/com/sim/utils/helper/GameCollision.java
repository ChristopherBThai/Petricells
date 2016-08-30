package com.sim.utils.helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.sim.game.entities.Food;
import com.sim.game.entities.Mob;
import com.sim.game.entities.Wall;
import com.sim.screen.GameScreen;

public class GameCollision implements ContactListener{
	
	GameScreen game;
	
	public GameCollision(GameScreen game){
		this.game = game;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
	    Body b = contact.getFixtureB().getBody();
	    
		if(a.getUserData() instanceof Mob){
			if(b.getUserData() instanceof Mob){
				((Mob)a.getUserData()).bump((Mob)(b.getUserData()));
			}else if(b.getUserData() instanceof Food&&((Mob)a.getUserData()).atr.isHerb()){
				((Mob)a.getUserData()).eat();
				game.entMan.food.remove(b.getUserData());
				game.entMan.destroy(b);
			}
		}else if(a.getUserData() instanceof Food){
			if(b.getUserData() instanceof Mob&&((Mob)b.getUserData()).atr.isHerb()){
				((Mob)b.getUserData()).eat();
				game.entMan.food.remove(a.getUserData());
				game.entMan.destroy(a);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
