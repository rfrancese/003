package it.carmine.bouncyrun.threads;

import android.view.View;
import it.carmine.bouncyrun.GameView;
import it.carmine.bouncyrun.model.items.Terrace;

public class TerraceMove extends Thread{
	private Terrace terra;
	private int sleepTerrace;
	private View v;
	public TerraceMove(Terrace terra, int sleepTerrace,GameView v){
		this.terra=terra;
		this.sleepTerrace=sleepTerrace;
		this.v=v;
	}
	@Override
	public void run() {
		while(!this.isInterrupted()){
			try {
				Thread.sleep(sleepTerrace);
			} catch (InterruptedException e) {
				//
			}
			terra.move();
			v.postInvalidateDelayed(1);
		}
		//return null;
	}
	public Terrace getTerrace(){
		return terra;
	}
}