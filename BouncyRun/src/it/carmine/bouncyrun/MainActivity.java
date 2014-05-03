package it.carmine.bouncyrun;

import it.carmine.bouncyrun.control.GameOverListner;
import it.carmine.bouncyrun.social_share.FacebookLoginActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private boolean hasStart;
	GameView gw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int height=metrics.heightPixels;
		int width=metrics.widthPixels;
		
		//Log.i("size","w="+width+"h="+height);
		
		FrameLayout fl=new FrameLayout(this);
		fl.setBackgroundColor(getResources().getColor(R.color.bgcolor));
		
		gw=new GameView(this,width,height);
		gw.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		
		gw.setOnGameOverListener(new GameOverListner(){
			@Override
			public void onGameOver(int points) {
				final Dialog dialog1 = new Dialog(MainActivity.this,R.style.PauseDialog);
				dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog1.setContentView(R.layout.custom_alert_gameover);
				TextView tv=(TextView)dialog1.findViewById(R.id.textView1);
				tv.setText(tv.getText().toString()+points);
				Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(),
						"font/pipe.ttf");
				tv.setTypeface(tf);
				
				
				ImageView facebook_share=(ImageView) dialog1.findViewById(R.id.facebook_share);
				ImageView google_share=(ImageView)dialog1.findViewById(R.id.google_share);
				ImageView altro_classifica=(ImageView)dialog1.findViewById(R.id.altro_share);
								
				dialog1.show();
				
				
				facebook_share.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent i=new Intent(MainActivity.this,FacebookLoginActivity.class);
						i.putExtra("points", gw.getPoint().getP()+"");
						startActivity(i);
						dialog1.cancel();
						MainActivity.this.finish();
					}
				});
				}
		});
		
		fl.addView(gw);
		setContentView(fl);
			

		final Dialog dialog = new Dialog(this,R.style.PauseDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_alert_start);
		Button b=(Button) dialog.findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				EditText et=(EditText)dialog.findViewById(R.id.editText1);
				gw.startGame(et.getText().toString());
				dialog.dismiss();
				hasStart=true;
			}
		});
		
		dialog.show();
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
		if(gw!=null && hasStart)
			gw.startListner();
		gw.resumeAllExecution();
	}
}
