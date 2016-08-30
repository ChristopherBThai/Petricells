package com.sim.utils.helper;

import com.sim.game.MainGame;
import com.sim.utils.camera.OrthoCamera;

public class CameraHelper {
	public static OrthoCamera camera = MainGame.camera;
	
	public static boolean inCamera(float x, float y){
		if(camera.position.dst(x,y,camera.position.z)<MainGame.DIAMETER*camera.zoom*.5f){
			return true;
		}
		return false;
	}
}
