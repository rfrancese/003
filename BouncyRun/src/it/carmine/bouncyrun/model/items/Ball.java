package it.carmine.bouncyrun.model.items;

import it.carmine.bouncyrun.model.Spriter;

public class Ball extends Spriter{
	private int x,y,raggio,w,h;
	private final int jump=200;
	private final int relativeH=320;
	
	public Ball(int x,int y,int raggio,int w,int h){
		super(x,y,w,h);
		this.x=x;
		this.y=y;
		this.raggio=raggio;
		this.w=w;
		this.h=h;
	}
	public int getR(){
		return raggio;
	}
	public void moveXF(){
		x+=2;
		setX(x);
	}
	public void moveXB(){
		x-=2;
		setX(x);
	}
	public void moveYU(){
		y--;
		setY(y);
	}
	public void moveYD(){
		y++;
		setY(y);
	}
	public int jump(){
		return (jump*h)/relativeH;
	}
}
