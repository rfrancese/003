package it.carmine.bouncyrun.model.items;

import it.carmine.bouncyrun.model.Spriter;

public class Star extends Spriter {
	private int size;
	public Star(int x, int y, int w, int h,int s) {
		super(x, y, w, h);
		this.size=s;
	}
	public int move(){
		if(getX()<(size*-1)){
			setX(getW());
			setY((int) (Math.random()*getH()));
		}else{
			setX(getX()-1);
		}
		return getX();
	}
}
