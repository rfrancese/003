package it.carmine.bouncyrun.model.items;

import it.carmine.bouncyrun.model.Spriter;

public class Terrace extends Spriter{

	private int size;
	private boolean hasobstacle;
	private final int startx;
	private final int delta;
	public Terrace(int x,int y,int h,int w,int size,int d){
		super(x,y,w,h);
		this.size=size;
		startx=x;
		delta=d;
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
	public void addObstacle(){
		hasobstacle=true;
	}
	public void removeObstacle(){
		hasobstacle=false;
	}
	public boolean hasObstacle(){
		return hasobstacle;
	}
}
