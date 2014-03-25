package it.carmine.bouncyrun.model;


public class BonusSpriter extends Spriter {

	private int point;
	
	public BonusSpriter(int x, int y, int w, int h,int point) {
		super(x, y, w, h);
		this.point=point;
	}
	public int getPoint(){
		return point;
	}
}
