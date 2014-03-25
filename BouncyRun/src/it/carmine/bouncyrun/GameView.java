package it.carmine.bouncyrun;

import it.carmine.bouncyrun.model.items.Ball;
import it.carmine.bouncyrun.model.items.Cloud;
import it.carmine.bouncyrun.model.items.Terrace;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.AsyncTask;
import android.os.Build;

import android.util.Log;

import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	
	private final int sleepCloud=200;
	private final int sleepTerrace=15;
	private final int sleepBall=5;
	
	private Context c;
	private int x,y,radius;
	private Ball b;
	private BallJump a;
	private Sensor accel;
	private SensorManager sm;
	private SensorEventListener sel;
	private int width,height;
	private Paint p;
	private CloudMove clm;
	private TerraceMove trm;
	private Cloud cloud;
	private Terrace terra;
	private Bitmap icon,ball,terrace,obstacled_terrace;
	private boolean jumping=false;
	private ArrayList<Cloud>clA;
	private ArrayList<CloudMove>clmA;
	private ArrayList<Terrace>trA;
	private ArrayList<TerraceMove>trmA;
	
	private final int cloudNum,terraceNum;
	private float vector_x,vector_y;
	private BallMove bm;
	private boolean onexec;
	
	private boolean isOnTerrace;
	
	private int onTerracenum=-1;
	
	private int maxObstacle=1;
	
	private int[] terracePos;
	public GameView(Context c,int width,int height){
		super(c);
		this.c=c;
		
		this.width=width;
		this.height=height;

		//per il salto
		a=new BallJump();
		
		//array per le nuvole
		clA=new ArrayList<Cloud>();
		//numero nuvole
		cloudNum=1;
		terraceNum=6;
		
		icon = BitmapFactory.decodeResource(GameView.this.c.getResources(),
                R.drawable.cloud_small);
		ball = BitmapFactory.decodeResource(GameView.this.c.getResources(),
                R.drawable.ball);
		terrace = BitmapFactory.decodeResource(GameView.this.c.getResources(),
                R.drawable.terrace);
		obstacled_terrace= BitmapFactory.decodeResource(GameView.this.c.getResources(),
                R.drawable.obstacled_terrace);
		
		radius=ball.getHeight();
		x=radius;
		y=height-radius;
		
		b=new Ball(x,y,radius,width,height);
		//come disegnare
		p=new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(this.c.getResources().getColor(R.color.ballColor));

		//creo l'array per la lista delle nuvole in movimento
		clmA=new ArrayList<CloudMove>();
		trA=new ArrayList<Terrace>();
		trmA=new ArrayList<TerraceMove>();

		
		//setto le posizioni dei terrazzi
		
		terracePos=new int[terraceNum];
		
		for(int i=0;i<terraceNum;i++){
			terracePos[i]=proporzione(30*i);
		}
		
		//creo nuvole e terrazzi
		makeCloud();
		makeTerraces();

		//avvio il movimento laterale della palla
		bm=new BallMove();
		bm.start();	
		
		startListner();
		onexec=true;
		jumping=false;
	}

	
	private int proporzione(int p){
		p=((p*width)/480)-terrace.getWidth();
		return p;
	}
	private void makeCloud(){
		//creo e lancio tutte le nuvole
		for(int i=0;i<cloudNum;i++){
			int my=(int)Math.random()*height;
			int nx=(int)Math.random()*width;
			
			if(i>0) {
				nx+=clA.get(i-1).getX();
			}
				
			Random rm=new Random(my);
			Random rn=new Random(nx);

			cloud=new Cloud(
					rn.nextInt(width),
					rm.nextInt(height),
					height,
					width,
					icon.getWidth()
					);
			
			clm=new CloudMove(cloud); 	
			clmA.add(clm);
				
			clm.start();	
			clA.add(cloud);
		}//fine ciclo for cloud
	}
	private void makeTerraces(){
		
		int numobs=0;
		//creo e lancio tutti i terrazzamenti
		for(int i=0;i<terraceNum;i++){
			int my=(int)Math.random()*height;
				int nx=(int)Math.random()*width;
				
				if(i>0){
					nx+=trA.get(i-1).getX();
					nx+=terrace.getWidth();
				}
					
				Random rm=new Random(my);
				Random rn=new Random(nx);
				terra=new Terrace(
					terracePos[i],
					rm.nextInt(height),
					height,
					width,
					terrace.getWidth()
					);
				
				Random ho=new Random();
				if(numobs<=maxObstacle){
					if(ho.nextBoolean()){
						terra.addObstacle();
						numobs++;
					}
				}
				
				trm=new TerraceMove(terra); 	
				trA.add(terra);
				trm.start();	
				trmA.add(trm);
		}//fine ciclo for terrazzamenti
	}
	//disegna e ridisegna
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		
		//stampo tutte le nuvole!
		for(int i=0;i<cloudNum;i++)
			c.drawBitmap(icon, clA.get(i).getX(),clA.get(i).getY(),p);
		
		for(int i=0;i<terraceNum;i++){
			if(!trA.get(i).hasObstacle())
				c.drawBitmap(terrace, trA.get(i).getX(),trA.get(i).getY(),p);
			else
				c.drawBitmap(obstacled_terrace, trA.get(i).getX(),trA.get(i).getY(),p);
		}
		
		c.drawBitmap(ball, b.getX(), b.getY(),p);	
	}
	//movimento nuvole
	class CloudMove extends Thread{
		private Cloud cloud;
		CloudMove(Cloud cloud){
			this.cloud=cloud;
		}
		@Override
		public void run() {
			while(!this.isInterrupted()){
				try {
					Thread.sleep(sleepCloud);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cloud.move();
				postInvalidateDelayed(1);
			}
			//return null;
		}
		public Cloud getCloud(){
			return cloud;
		}
	}
	//classe che muove i terrazzini
	class TerraceMove extends Thread{
		private Terrace terra;
		TerraceMove(Terrace terra){
			this.terra=terra;
		}
		@Override
		public void run() {
			while(!this.isInterrupted()){
				try {
					Thread.sleep(sleepTerrace);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				terra.move();
				postInvalidateDelayed(1);
			}
			//return null;
		}
		public Terrace getTerrace(){
			return terra;
		}
	}
	
	
	class BallMove extends Thread{
		@Override
		public void run() {
			while(!this.isInterrupted()){
				try {
					Thread.sleep(sleepBall);
				} catch (InterruptedException e) {
					Log.i("interrupted exception",e.toString());
				}
				//sposto
				if(vector_y>3){
					if(b.getX()<width)
						b.moveXF();
				}else if(vector_y<-3){
					if(b.getX()>0)
						b.moveXB();
				}
			}
			//return null;
		}
	}
	//riavvio il listner
	public void startListner(){
		//prendo i sensori necessari allo spostamento della palla
		sm=(SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
		accel=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//ora setto i controlli per tali sensori
		sel=new SensorEventListener(){
			float[] v;
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {}
			@Override
			public void onSensorChanged(SensorEvent event) {
				v=event.values;
				vector_y=v[1];
			}
		};
		sm.registerListener(sel, accel, SensorManager.SENSOR_DELAY_FASTEST);
	}
	//fermo il listner
	public void stopListner(){
		sm.unregisterListener(sel);
	}

	//fermo tutto
	public void stopAllExecution(){
		onexec=false;
		for(int i=0;i<clmA.size();i++)
			clmA.get(i).interrupt();
		for(int i=0;i<trmA.size();i++)
			trmA.get(i).interrupt();
		bm.interrupt();
	}
	//ricomincio tutto
	public void resumeAllExecution(){
		if(!onexec){
			//ripristino l'esecuzione delle nuvole
			ArrayList<CloudMove>cc=new ArrayList<CloudMove>();
			ArrayList<TerraceMove>tt=new ArrayList<TerraceMove>();
			for(int i=0;i<clmA.size();i++){
				cc.add(new CloudMove(clmA.get(i).getCloud()));
				cc.get(i).start();	
			}
			clmA=cc;
			//riavvio i terrazzini
			for(int i=0;i<trmA.size();i++){
				tt.add(new TerraceMove(trmA.get(i).getTerrace()));
				tt.get(i).start();	
			}
			trmA=tt;
			//ripristino l'esecuzione del movimento della pallina
			bm=new BallMove();
			bm.start();
			
			onexec=true;
		}
	}
	
	//controllo che la palla sia sul terrazzo
	
	private int isOnTerrace(Ball b){
		for(int i=0;i<terraceNum;i++)
			//getWidth/2 per determinare il centro
			if(b.getX()+(ball.getWidth()/2) > trA.get(i).getX()){//la palla è oltre il bordo sx
				if(b.getX() < trA.get(i).getX()+terrace.getWidth()){//la palla non è oltre il bordo dx
					if((b.getY()+(ball.getHeight()/2)) == (trA.get(i).getY()-terrace.getHeight())){//palla sopra la barra - perché è a scendere!
						return i;
					}
				}
			}
		return -1;
	}
	
	//questo controlla il touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);	
		if(!jumping){
			if(a!=null){
				if(a.isAlive()) a.interrupt();
			}
			a=new BallJump();
			a.start();
			jumping=true;
			return false;
		}
		//se sono su un terrazzino devo fermare e risaltare
		if(isOnTerrace(b)>=0){
			if(a!=null && a.isAlive()){
				a.interrupt();
			}
			a=new BallJump();
			a.start();
			jumping=true;
			return false;
		}
		return false;
	}

	//l'animazione per il salto, devo confrontare la discesa con la pos del terrace!
	class BallJump extends Thread{
		public void onPostExecute(Object o){
			//finisco di saltare
			jumping=false;
		}
		@Override
		public void run() {
			int k=height-(ball.getHeight());
			int jump=b.getY()-b.jump();
			//vado su JUMPUP
			int i;
			for(i=b.getY();i>=jump;i--){
				try{
					//aspetto e sposto
					Thread.sleep(sleepBall);
					b.setY(i);
					//invalida per ridisegnare
					postInvalidateDelayed(1);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			boolean forceExit=true;
			//vado giu, parto da i JUMPDOWN
			for(int j=i;forceExit && !Thread.currentThread().isInterrupted() && j<=k;j++){
				try{
					isOnTerrace=false;
					//aspetto e sposto
					Thread.sleep(sleepBall);
					b.setY(j);
					
					//qui controllo se vado su un terrazzino
					while(!Thread.currentThread().isInterrupted() && 
							(onTerracenum=isOnTerrace(b))>=0 && b.getX()>0
						){
						isOnTerrace=true;
						Thread.sleep(sleepTerrace);
						b.setX(b.getX()-1);
						postInvalidateDelayed(1);
					}//quando cado devo scendere
					
					if(isInterrupted()){
						continue;
					}
					//invalida per ridisegnare
					postInvalidateDelayed(1);
				}catch (InterruptedException e) {
					jumping=false;
					return;
				}
			}
			jumping=false;
		}
	}
}
