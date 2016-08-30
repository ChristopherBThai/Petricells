package com.sim.utils.sound;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class Bgm implements Disposable{
	Music sound;
	AssetManager asset;
	String loc;
	float volume;
	
	public Bgm(String loc){
		this.loc = loc;
		asset = new AssetManager();
		asset.load(loc,Music.class);
		volume = .3f;
	}
	
	public void play(){
		asset.update();
		if(sound==null&&asset.isLoaded(loc)){
			sound = asset.get(loc,Music.class);
			sound.setVolume(volume);
			sound.play();
			sound.setLooping(true);
		}
	}
	
	public void setVolume(float volume){
		this.volume = volume;
		if(sound!=null){
			sound.setVolume(this.volume);
		}
	}
	
	public void stop(){
		sound.stop();
	}
	
	public void pause(){
		sound.pause();
	}
	
	public void dispose(){
			sound.dispose();
		asset.dispose();
	}
}
