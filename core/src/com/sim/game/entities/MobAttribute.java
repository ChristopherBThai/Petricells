package com.sim.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;


public class MobAttribute implements Json.Serializable{
		//Movement
		private float speed;
		private Timer speedFreq;
		private double minTurnVel;
		private double maxTurnVel;
		//Reproduction
		private Timer layTime;
		private int hatchTime;
		//Life
		private Timer lifeSpan;
		private int foodWorth;
		//Body
		private double radius;
		//Type
		private float Herb;
		//Think
		private float sight;
		//Currency
		private Timer coinTime;
		private float coinAmount;
		//Color
		private Color color;
		public MobAttribute(){
			//Movement
			speed = 7;
			speedFreq = new Timer(30);
			minTurnVel = -5;
			maxTurnVel = 5;
			//Reproduction
			layTime = new Timer(2500);
			hatchTime = 400;
			//Life
			lifeSpan = new Timer(3000);
			foodWorth = 500;
			//Body
			radius = 2;
			//Type
			Herb = .7f;
			sight = 10;
			//Currency
			coinTime = new Timer(4000);
			coinAmount = 5;
			//Color
			color = new Color(1f,1f,1f,1f);
		}
		
		public MobAttribute getMutated(){
			MobAttribute result = new MobAttribute();
			float part = .01f;
			
			result.speed = (float) (this.speed + 5*part*random());
			result.speedFreq.maxTime = this.speedFreq.maxTime + 7*part*random();
			result.speedFreq.currentTime = result.speedFreq.maxTime;
			result.minTurnVel = this.minTurnVel + 10*part*random();
			result.maxTurnVel = this.maxTurnVel + 10*part*random();
			if(result.minTurnVel>result.maxTurnVel){
				double temp = result.minTurnVel;
				result.minTurnVel = result.maxTurnVel;
				result.maxTurnVel = temp;
			}
			
			result.lifeSpan.maxTime = (int) (this.lifeSpan.maxTime + 700*part * random());
			result.lifeSpan.currentTime = result.lifeSpan.maxTime;
			result.foodWorth = (int) (this.foodWorth + 70*part * random());
			
			result.layTime.maxTime = this.layTime.maxTime + 700*part * random();
			result.layTime.currentTime = result.layTime.maxTime;
			result.hatchTime = (int) (this.hatchTime + 70*part * random());
			
			result.radius = this.radius + 8*part * random();
			
			result.Herb = (float) (this.Herb + .1 * random());
			if(result.Herb>1)
				result.Herb = 1f;
			else if(result.Herb<-1)
				result.Herb = -1f;
			
			result.sight = this.sight + random()*2;
			
			result.coinTime.maxTime = this.coinTime.maxTime + 700*part*random();
			result.coinAmount = coinAmount + random()*2f;
			if(result.coinAmount<1)
				result.coinAmount = 1;
			
			float r = this.color.r + .05f * random();
			float g = this.color.g + .05f * random();
			float b = this.color.b + .05f * random();
			//if(r<.3f)r=.3f;
			//if(g<.3f)g=.3f;
			//if(b<.3f)b=.3f;
			result.color.set(r, g, b, 1f);
			//System.out.println(r+":"+g+":"+b);
			return result;
		}
		
		private float random(){
			return (float) (Math.random()*2 -1);
		}
		
		static public class Timer{
			public double currentTime;
			public double maxTime;
			
			public Timer(){
				currentTime = 0;
				maxTime = 0;
			}
			
			public Timer(double delay){
				maxTime = delay;
				currentTime = delay;
			}
			
			public boolean pushable(){
				if(currentTime<=0){
					currentTime+=maxTime;
					return true;
				}
				currentTime--;
				return false;
			}
		}
		
		public boolean isHerb(){
			if(this.Herb>=0)
				return true;
			return false;
		}
		
		public boolean isCarn(){
			if(this.Herb<0)
				return true;
			return false;
		}
		
		public int getFoodWorth(){
			return foodWorth;
		}
		
		public int getHatchTime(){
			return hatchTime;
		}
		
		public float getHerbPercent(){
			return Herb;
		}
		
		public double getCurrentLayTime(){
			return layTime.currentTime;
		}
		
		public double getMaxLayTime(){
			return layTime.maxTime;
		}
		
		public Timer getLayTime(){
			return layTime;
		}
		
		public double getCurrentLifeTime(){
			return lifeSpan.currentTime;
		}
		
		public double getMaxLifeTime(){
			return lifeSpan.maxTime;
		}
		
		public Timer getLifeTime(){
			return lifeSpan;
		}
		
		public double getMaxTurnVel(){
			return maxTurnVel;
		}
		
		public double getMinTurnVel(){
			return minTurnVel;
		}
		
		public double getRadius(){
			return radius;
		}
		
		public void setRadius(double radius){
			this.radius = radius;
		}
		
		public float getSight(){
			if(isCarn())
				return sight*.8f;
			return sight;
		}
		
		public float getSpeed(){
			if(isCarn())
				return speed*1.1f;
			return speed;
		}
		
		public double getCurrentSpeedFreq(){
			if(isHerb())
				return speedFreq.currentTime;
			return speedFreq.currentTime;
		}
		
		public double getMaxSpeedFreq(){
			return speedFreq.maxTime;
		}
		
		public Timer getSpeedFreq(){
			return speedFreq;
		}
		
		public double getMaxCoinTime(){
			return coinTime.maxTime;
		}
		
		public double getCurrentCoinTime(){
			return coinTime.currentTime;
		}
		
		public Timer getCoinTime(){
			return coinTime;
		}
		
		public float getCoinAmount(){
			return coinAmount;
		}
		

		public Color getColor(){
			return color;
		}
		
		public void setHerbPercentage(float herbPercent){
			Herb = herbPercent;
		}
		
		public void setSpeedFreq(float time){
			this.speedFreq.maxTime = time;
		}
		
		public void setSpeed(float speed){
			this.speed = speed;
		}

		@Override
		public void write(Json json) {
			json.writeValue("speed",speed);
			json.writeValue("speedFreq",speedFreq);
			json.writeValue("minTurnVel",minTurnVel);
			json.writeValue("maxTurnVel",maxTurnVel);
			//Reproduction
			json.writeValue("layTime",layTime);
			json.writeValue("hatchTime",hatchTime);
			//Life
			json.writeValue("lifeSpan",lifeSpan);
			json.writeValue("foodWorth",foodWorth);
			//Body
			json.writeValue("radius",radius);
			//Type
			json.writeValue("Herb",Herb);
			//Think
			json.writeValue("sight",sight);
			//Currency
			json.writeValue("coinTime",coinTime);
			json.writeValue("coinAmount",coinAmount);
			//Color
			json.writeValue("color",color);
		}

		@Override
		public void read(Json json, JsonValue jsonData) {
			this.speed = json.readValue("speed", float.class, jsonData);
			this.speedFreq = json.readValue("speedFreq", MobAttribute.Timer.class, jsonData);
			this.minTurnVel = json.readValue("minTurnVel", double.class, jsonData);
			this.maxTurnVel = json.readValue("maxTurnVel", double.class, jsonData);
			this.layTime = json.readValue("layTime", MobAttribute.Timer.class, jsonData);
			this.hatchTime = json.readValue("hatchTime", int.class, jsonData);
			this.lifeSpan = json.readValue("lifeSpan", MobAttribute.Timer.class, jsonData);
			this.foodWorth = json.readValue("foodWorth", int.class, jsonData);
			this.radius = json.readValue("radius", double.class, jsonData);
			this.Herb = json.readValue("Herb", float.class, jsonData);
			this.sight = json.readValue("sight", float.class, jsonData);
			this.coinTime = json.readValue("coinTime", MobAttribute.Timer.class, jsonData);
			this.coinAmount = json.readValue("coinAmount", float.class, jsonData);
			this.color = json.readValue("color", Color.class, jsonData);
			
		}
}
