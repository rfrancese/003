package it.carmine.bouncyrun.model;

import android.graphics.Rect;

public class Spriter {
	private int x,y,w,h;
	private Rect r;
	public Spriter(int x,int y,int w,int h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		r=new Rect(x,y,x-w,y-h);
	}
	public int getX(){
		return x;
	}
	public void setX(int x){
		this.x=x;
		r.set(x,y,x-w,y-h);
	}
	public int getY(){
		return y;
	}
	public void setY(int y){
		this.y=y;
		r.set(x,y,x-w,y-h);
	}
	public int getW(){
		return w;
		
	}
	public int getH(){
		return h;
	}
	public void setH(int h){
		this.h=h;
		r.set(x,y,x-w,y-h);
	}
	public void setW(int w){
		this.w=w;
		r.set(x,y,x-w,y-h);
	}
	public Rect getRect(){
		return r;
	}
}
