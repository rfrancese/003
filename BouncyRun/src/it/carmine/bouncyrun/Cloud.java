package it.carmine.bouncyrun;

public class Cloud{
	int startx,starty;
	int x,y;
	int h;
	public Cloud(int x,int y,int h){
		startx=x;
		starty=y;
		this.x=x;
		this.y=y;
		this.h=h;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int move(){
		if(x<-30){
			x=startx;
			y=(int) (Math.random()*h);
		}else{
			x--;
		}
		return x;
	}
}
