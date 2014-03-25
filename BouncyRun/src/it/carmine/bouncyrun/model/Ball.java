package it.carmine.bouncyrun.model;

public class Ball extends Spriter{
	private int x,y,raggio,w,h;
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
		return (70*h)/320;
	}
}
