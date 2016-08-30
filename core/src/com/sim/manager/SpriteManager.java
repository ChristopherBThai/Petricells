package com.sim.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class SpriteManager{
	private static Sprite cell;
	private static Sprite teeth;
	private static Sprite circle;
	private static Sprite sight;
	
	private static Sprite coin;
	private static Sprite coinFX;
	
	private static Sprite food;
	
	private static Sprite black;
	private static Sprite line;
	private static Sprite option;
	private static Sprite optionArrow;
	private static Sprite pause;
	private static Sprite play;
	private static Sprite border;
	private static Sprite borderLine;
	private static Sprite borderCorner;
	
	private static Sprite petricells;
	private static Sprite cellBlur;
	private static Sprite button;
	
	public static void setCell(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/cell.png"));
		cell = new Sprite(texture);
	}
	
	public static Sprite getCell(){
		if(cell==null)
			setCell();
		return cell;
	}
	
	public static void setCellBlur(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/cell_blurred.png"));
		cellBlur = new Sprite(texture);
	}
	
	public static Sprite getCellBlur(){
		if(cellBlur==null)
			setCellBlur();
		return cellBlur;
	}
	
	public static void setTeeth(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/teeth.png"));
		teeth = new Sprite(texture);
	}
	
	public static Sprite getTeeth(){
		if(teeth==null)
			setTeeth();
		return teeth;
	}
	
	public static void setCircle(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/eye.png"));
		circle = new Sprite(texture);
	}
	
	public static Sprite getCircle(){
		if(circle==null)
			setCircle();
		return circle;
	}
	
	public static void setSight(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/sight.png"));
		sight = new Sprite(texture);
	}
	
	public static Sprite getSight(){
		if(sight==null)
			setSight();
		return sight;
	}
	
	public static void setBlack(){
		Texture texture = new Texture(Gdx.files.internal("sprites/black.png"));
		black = new Sprite(texture);
	}
	
	public static Sprite getBlack(){
		if(black==null)
			setBlack();
		return black;
	}
	
	public static void setPetricells(){
		Texture texture = new Texture(Gdx.files.internal("sprites/menu/PetriCells.png"));
		petricells = new Sprite(texture);
	}
	
	public static Sprite getPetricells(){
		if(petricells==null)
			setPetricells();
		return petricells;
	}
	
	public static void setButton(){
		Texture texture = new Texture(Gdx.files.internal("sprites/menu/button.png"));
		button = new Sprite(texture);
	}
	
	public static Sprite getButton(){
		if(button==null)
			setButton();
		return button;
	}
	
	public static void setCoin(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/coin.png"));
		coin = new Sprite(texture);
	}
	
	public static Sprite getCoin(){
		if(coin==null)
			setCoin();
		return coin;
	}
	
	public static void setCoinFX(){
		Texture texture = new Texture(Gdx.files.internal("sprites/fx/coin_fx.png"));
		coinFX = new Sprite(texture);
	}
	
	public static Sprite getCoinFX(){
		if(coinFX==null)
			setCoinFX();
		return coinFX;
	}
	
	public static void setLine(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/line.png"));
		line = new Sprite(texture);
	}
	
	public static Sprite getLine(){
		if(line==null)
			setLine();
		return line;
	}
	
	public static void setOption(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/option.png"));
		option = new Sprite(texture);
	}
	
	public static Sprite getOption(){
		if(option==null)
			setOption();
		return option;
	}
	
	public static void setFood(){
		Texture texture = new Texture(Gdx.files.internal("sprites/entities/food.png"));
		food = new Sprite(texture);
	}
	
	public static Sprite getFood(){
		if(food==null)
			setFood();
		return food;
	}
	
	public static void setOptionArrow(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/option_arrow.png"));
		optionArrow = new Sprite(texture);
	}
	
	public static Sprite getOptionArrow(){
		if(optionArrow==null)
			setOptionArrow();
		return optionArrow;
	}
	
	public static void setPause(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/pause.png"));
		pause = new Sprite(texture);
	}
	
	public static Sprite getPause(){
		if(pause==null)
			setPause();
		return pause;
	}
	
	public static void setPlay(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/play.png"));
		play = new Sprite(texture);
	}
	
	public static Sprite getPlay(){
		if(play==null)
			setPlay();
		return play;
	}
	
	public static void setBorder(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/border.png"));
		border = new Sprite(texture);
	}
	
	public static Sprite getBorder(){
		if(border==null)
			setBorder();
		return border;
	}
	
	public static void setBorderLine(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/border_line.png"));
		borderLine = new Sprite(texture);
	}
	
	public static Sprite getBorderLine(){
		if(borderLine==null)
			setBorderLine();
		return borderLine;
	}
	
	public static void setBorderCorner(){
		Texture texture = new Texture(Gdx.files.internal("sprites/ui/border_corner.png"));
		borderCorner = new Sprite(texture);
	}
	
	public static Sprite getBorderCorner(){
		if(borderCorner==null)
			setBorderCorner();
		return borderCorner;
	}
	
	public static void dispose(){
		cell = null;
		teeth = null;
		circle = null;
		sight = null;
		coin = null;
		black = null;
		petricells = null;
		cellBlur = null;
		button = null;
		coinFX = null;
		line = null;
		option = null;
		food = null;
		optionArrow = null;
		pause = null;
		play = null;
		border = null;
		borderLine= null;
		borderCorner = null;
	}
}
