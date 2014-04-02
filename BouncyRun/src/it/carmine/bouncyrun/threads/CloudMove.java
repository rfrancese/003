package it.carmine.bouncyrun.threads;

import android.view.View;
import it.carmine.bouncyrun.GameView;
import it.carmine.bouncyrun.model.items.Cloud;

public class CloudMove extends Thread{
	private Cloud cloud;
	private int sleepCloud;
	private View v;
	public CloudMove(Cloud cloud,int sleepCloud,GameView v){
		this.cloud=cloud;
		this.sleepCloud=sleepCloud;
		this.v=v;
	}
	@Override
	public void run() {
		while(!this.isInterrupted()){
			try {
				Thread.sleep(sleepCloud);
			} catch (InterruptedException e) {
				//
			}
			cloud.move();
			v.postInvalidateDelayed(1);
		}
		//return null;
	}
	public Cloud getCloud(){
		return cloud;
	}
}
