package com.sim.utils.ui;

public interface Binded {
	void updateBind();
	
	void leftAlign();
	void rightAlign();
	void centerXAlign();
	
	void centerAlign();
	
	void topAlign();
	void bottomAlign();
	void centerYAlign();
	
	float getOrigionalHeight();
	float getOrigionalWidth();
	float getHeight();
	float getWidth();
	
	float getXPercent();
	float getYPercent();
	float getX();
	float getY();
}
