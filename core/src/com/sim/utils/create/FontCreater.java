package com.sim.utils.create;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontCreater {
	public static BitmapFont createFonts(BitmapFont font,int size) {
	    FileHandle fontFile = Gdx.files.internal("font/pond.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = size*2;
	    parameter.genMipMaps = true;
	    font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
}
