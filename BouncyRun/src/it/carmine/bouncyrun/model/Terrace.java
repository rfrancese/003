package it.carmine.bouncyrun.model;

public class Terrace extends Spriter{

	private int size;
	public Terrace(int x,int y,int h,int w,int size){
		super(x,y,w,h);
		this.size=size;
	}
	public int move(){
		if(getX()<(size*-1)){
			setX(getW()+size);
			setY((int) (Math.random()*getH()));
		}else{
			setX(getX()-1);
		}
		return getX();
	}
}
