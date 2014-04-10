package it.carmine.bouncyrun.model;

public class GameOverText {
	private String what="Game Over";
	private int x,y;
	private int width,height;
	public GameOverText(int w,int h, int textsize){
		width=w;
		height=h;
		
		x=width/2;
		y=height/2;
		
		x-=53;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
