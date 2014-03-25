package it.carmine.bouncyrun;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;


public class MainActivity extends Activity {

	GameView gw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int height=metrics.heightPixels;
		int width=metrics.widthPixels;
		
		Log.i("size","w="+width+"h="+height);
		
		FrameLayout fl=new FrameLayout(this);
		fl.setBackgroundColor(getResources().getColor(R.color.bgcolor));
		
		gw=new GameView(this,width,height);
		gw.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		
		fl.addView(gw);
		setContentView(fl);
			
	}
	@Override
	public void onPause(){
		super.onPause();
		gw.stopListner();
		gw.stopAllExecution();
	}
	@Override
	public void onResume(){
		super.onResume();
		if(gw!=null)
			gw.startListner();
		gw.resumeAllExecution();
		
	}
}
