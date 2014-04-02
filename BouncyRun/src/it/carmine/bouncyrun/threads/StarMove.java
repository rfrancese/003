package it.carmine.bouncyrun.threads;

import android.view.View;
import it.carmine.bouncyrun.GameView;
import it.carmine.bouncyrun.model.items.Star;

public class StarMove extends Thread{
	private Star star;
	private int sleepStar;
	private View v;
	private boolean invertStar;
	public StarMove(Star s,int sleepStar,GameView v){
		this.star=s;
		this.sleepStar=sleepStar;
		this.v=v;
	}
	@Override
	public void run() {
		while(!this.isInterrupted()){
			try {
				Thread.sleep(sleepStar);
			} catch (InterruptedException e) {
				//
			}
			star.move();
			v.postInvalidateDelayed(1);
			invertStar();
		}
		//return null;
	}
	private void invertStar(){
		if(invertStar) invertStar=false;
		else invertStar=true;
	}
	public boolean getStarStatus(){
		return invertStar;
	}
}
