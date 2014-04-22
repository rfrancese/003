package it.carmine.bouncyrun;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
		
		
		final Dialog dialog = new Dialog(this,R.style.PauseDialog);
		dialog.setContentView(R.layout.custom_alert_start);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Button b=(Button) dialog.findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				EditText et=(EditText)dialog.findViewById(R.id.editText1);
				gw.startGame(et.getText().toString());
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
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
