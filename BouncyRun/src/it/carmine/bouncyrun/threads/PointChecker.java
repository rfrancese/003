package it.carmine.bouncyrun.threads;

import it.carmine.bouncyrun.model.bonus.Incrementer;
import it.carmine.bouncyrun.model.items.Ball;
import it.carmine.bouncyrun.model.items.Star;

public class PointChecker extends Thread {
	
	private Ball ball;
	private Star star;
	private Incrementer i;
	
	public PointChecker(Ball b,Star s,Incrementer i){
		super();
		ball=b;
		star=s;
		this.i=i;
	}
	
	@Override
	public void run(){
		while(isInterrupted()){
			if(ball.getRect().intersect(star.getRect())){
				i.incrementa();
			}
		}
	}
}