package it.carmine.bouncyrun;

import it.carmine.bouncyrun.control.GameOverListner;
import it.carmine.bouncyrun.social_share.FacebookLoginActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;


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
				ImageView altro_share=(ImageView)dialog1.findViewById(R.id.google_share);
				ImageView altro_classifica=(ImageView)dialog1.findViewById(R.id.altro_share);
								
				dialog1.show();
				
				
				facebook_share.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if(isOnline()){
							Intent i=new Intent(MainActivity.this,FacebookLoginActivity.class);
							i.putExtra("points", gw.getPoint().getP()+"");
							i.putExtra("nick", "carmine");
							
							startActivity(i);
							
							dialog1.cancel();
							MainActivity.this.finish();
						}else{
							Toast toast=Toast.makeText(MainActivity.this,
									"Problema connessione internet",Toast.LENGTH_LONG);
							toast.show();
						}
					}
				});
				
				altro_share.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(
								Intent.EXTRA_TEXT, "Ho raggiungo un punteggio di "+
										gw.getPoint().getP()+" su BouncyRun, scaricalo anche tu!"+
											" url"
										);
						sendIntent.setType("text/plain");
						startActivity(sendIntent);
					}
				});
				
				altro_classifica.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,ClassificaActivity.class));
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
	
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
