package it.carmine.bouncyrun.model;

public class Cloud extends Spriter{
	int startx,starty;
	int x,y;
	int h,w;
	int size;
	public Cloud(int x,int y,int h,int w,int size){
		super(x,y,w,h);
		startx=w;
		starty=y;
		this.x=x;
		this.y=y;
		this.h=h;
		this.w=w;
		this.size=size;
	}
	public int move(){
		if(getX()<(size*-1)){
			setX(w+size);
			setY((int) (Math.random()*getH()));
		}else{
			setX(getX()-1);
		}
		return getX();
	}
}
