package it.carmine.bouncyrun;

public class Ball {
	private int x,y,raggio;
	public Ball(int x,int y,int raggio){
		this.x=x;
		this.y=y;
		this.raggio=raggio;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getR(){
		return raggio;
	}
	public void moveXF(){
		x+=4;
	}
	public void moveXB(){
		x-=4;
	}
	public void moveYU(){
		y--;
	}
	public void moveYD(){
		y++;
	}
	public void setY(int y){
		this.y=y;
	}
	public int jump(){
		return 70;
	}
}
