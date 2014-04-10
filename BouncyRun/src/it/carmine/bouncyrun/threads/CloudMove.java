package it.carmine.bouncyrun.threads;

import android.view.View;
import it.carmine.bouncyrun.GameView;
import it.carmine.bouncyrun.model.items.Cloud;

public class CloudMove extends Thread{
	private Cloud cloud;
	private int sleepCloud;
	private View v;
	private boolean muststop;
	public CloudMove(Cloud cloud,int sleepCloud,GameView v){
		this.cloud=cloud;
		this.sleepCloud=sleepCloud;
		this.v=v;
		muststop=true;
	}
	@Override
	public void run() {
		while(!this.isInterrupted() && muststop){
			try {
				Thread.sleep(sleepCloud);
			} catch (InterruptedException e) {
				return;
			}
			cloud.move();
			v.postInvalidateDelayed(1);
		}
		//return null;
	}
	public Cloud getCloud(){
		return cloud;
	}
	 public void interrupt() {
		 super.interrupt();		  
		 muststop=false;
	 } 
}
