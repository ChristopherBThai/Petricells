package com.sim.utils.camera;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.sim.game.MainGame;
import com.sim.manager.ScreenManager;
import com.sim.screen.GameScreen;

public class OrthoCamera extends OrthographicCamera {

	Vector3 tmp = new Vector3();
	Vector2 origin = new Vector2();
	VirtualViewport virtualViewport;
	
	public Vector2 follow;

	public OrthoCamera() {
		this(new VirtualViewport((int)MainGame.WIDTH, (int)MainGame.HEIGHT));
	}

	public OrthoCamera(VirtualViewport virtualViewport) {
		this(virtualViewport, 0f, 0f);
	}

	public OrthoCamera(VirtualViewport virtualViewport, float cx, float cy) {
		this.virtualViewport = virtualViewport;
		this.origin.set(cx, cy);
	}

	public void setVirtualViewport(VirtualViewport virtualViewport) {
		this.virtualViewport = virtualViewport;
	}

	@Override
	public void update() {
		if(follow!=null){
			position.x = this.position.x + (follow.x - this.position.x + virtualViewport.getWidth()/7f*this.zoom) * .1f;
			position.y = this.position.y + (follow.y - this.position.y) * .1f;
			ScreenManager.getCurrentScreen().updateBinds();
		}
		float left = zoom * -viewportWidth / 2
				+ virtualViewport.getVirtualWidth() * origin.x;
		float right = zoom * viewportWidth / 2
				+ virtualViewport.getVirtualWidth() * origin.x;
		float top = zoom * viewportHeight / 2
				+ virtualViewport.getVirtualHeight() * origin.y;
		float bottom = zoom * -viewportHeight / 2
				+ virtualViewport.getVirtualHeight() * origin.y;

		projection.setToOrtho(left, right, bottom, top, Math.abs(near),
				Math.abs(far));
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
	}
	
	public void setZoom(float zoom){
		this.zoom = zoom;
		ScreenManager.getCurrentScreen().updateBinds();
	}
	
	public void addZoom(float zoom){
		this.zoom += zoom;
		ScreenManager.getCurrentScreen().updateBinds();
	}
	
	public void setPos(float x, float y){
		this.position.x = x;
		this.position.y = y;
		ScreenManager.getCurrentScreen().updateBinds();
	}
	
	public void addPos(float x, float y){
		this.position.x += x;
		this.position.y += y;
		ScreenManager.getCurrentScreen().updateBinds();
	}

	/**
	 * This must be called in ApplicationListener.resize() in order to correctly
	 * update the camera viewport.
	 */
	public void updateViewport() {
		setToOrtho(false, virtualViewport.getWidth(),
				virtualViewport.getHeight());
	}

	public Vector2 unprojectCoordinates(float x, float y) {
		Vector3 rawtouch = new Vector3(x, y, 0);
		unproject(rawtouch);
		return new Vector2(rawtouch.x, rawtouch.y);
	}

	public void resize() {
		VirtualViewport virtualViewport = new VirtualViewport((int)MainGame.WIDTH,
				(int)MainGame.HEIGHT);
		setVirtualViewport(virtualViewport);
		updateViewport();
	}
	
	public void follow(Body body){
		if(body==null)
			follow = null;
		else
			follow = body.getPosition();
	}
	
	public void followVector(Vector2 location){
		follow = location;
	}
	
	public static float lengthToPercentX(float length){
		return length/(MainGame.camera.viewportWidth*MainGame.camera.zoom);
	}
	
	public static float lengthToPercentY(float length){
		return length/(MainGame.camera.viewportHeight*MainGame.camera.zoom);
	}

	public void reset() {
		this.zoom = 1f;
		this.position.set(0f, 0f, 0f);
	}
}