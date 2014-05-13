package it.carmine.bouncyrun.threads;

import android.view.View;
import it.carmine.bouncyrun.GameView;
import it.carmine.bouncyrun.model.items.Terrace;

public class TerraceMove extends Thread{
	private Terrace terra;
	private int sleepTerrace;
	private View v;
	private boolean muststop;
	
	public TerraceMove(Terrace terra, int sleepTerrace,GameView v){
		this.terra=terra;
		this.sleepTerrace=sleepTerrace;
		this.v=v;
		muststop=true;
	}
	public void setSleep(int sleep){
		sleepTerrace=sleep;
	}
	@Override
	public void run() {
		while(!this.isInterrupted() && muststop){
			try {
				Thread.sleep(sleepTerrace);
			} catch (InterruptedException e) {
				return;
			}
			terra.move();
			v.postInvalidateDelayed(1);
		}
		//return null;
	}
	public Terrace getTerrace(){
		return terra;
	}
	public void interrupt() {
		super.interrupt();		  
		muststop=false;
	}
	public int getSleep(){
		return sleepTerrace;
	}
}