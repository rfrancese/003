package it.carmine.bouncyrun;

import it.carmine.bouncyrun.control.GameOverListner;
import it.carmine.bouncyrun.social_share.FacebookLoginActivity;
import it.carmine.bouncyrun.sql.SqlStorage;
import it.carmine.bouncyrun.user.GameSettings;
import it.carmine.bouncyrun.user.UserSettings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private boolean hasStart,mustclose;
	GameView gw;
	private Dialog dialog;
	private RadioGroup rg;
	private GameSettings gs;
	private SqlStorage sql;
	private String nick;
	private Typeface tf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tf=Typeface.createFromAsset(this.getAssets(),
				"font/pipe.ttf");
		
		
		sql=new SqlStorage(this);
		sql.open();
		
		if(!checkNick()) nick="anonymous";
				
		Log.i("nickname", nick);
		gs=new GameSettings();
			noHavePreviousNick(nick);
	}
	
	private void noHavePreviousNick(String nick){
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
				dialog1.setCancelable(false);
				TextView tv=(TextView)dialog1.findViewById(R.id.textView1);
				//tv.setText(tv.getText().toString()+points);
				Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(),
						"font/pipe.ttf");
				tv.setTypeface(tf);
				tv.setTextColor(Color.BLACK);
				
				
				ImageView facebook_share=(ImageView) dialog1.findViewById(R.id.facebook_share);
				ImageView altro_share=(ImageView)dialog1.findViewById(R.id.google_share);
				ImageView altro_classifica=(ImageView)dialog1.findViewById(R.id.altro_share);
				
				Button close=(Button)dialog1.findViewById(R.id.close);
				Button regame=(Button)dialog1.findViewById(R.id.button2);
				
				close.setTypeface(tf);
				regame.setTypeface(tf);
				dialog1.show();

				close.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						showCloseDialog();		
					}
					
				});
				regame.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						finish();
						startActivity(new Intent(MainActivity.this,MainActivity.class));
					}
				});
				facebook_share.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						if(isOnline()){
							Intent i=new Intent(MainActivity.this,FacebookLoginActivity.class);
							i.putExtra("points", gw.getPoint().getP()+"");
							i.putExtra("nick", gw.getNick());
							
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
						Intent i=new Intent(MainActivity.this,ClassificaActivity.class);
						i.putExtra("nick",  gw.getNick());
						i.putExtra("difficolta", gw.getDifficolta());
						i.putExtra("punteggio", gw.getPoint().getP()+"");
						startActivity(i);
					}
				});
			}
		});
		
		fl.addView(gw);
		fl.addView(addCloseButton());
		setContentView(fl);
		
		
		//select nick
		dialog = new Dialog(this,R.style.PauseDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_alert_start);
		Button b=(Button) dialog.findViewById(R.id.btn_startgame);
		
		rg=(RadioGroup)dialog.findViewById(R.id.radioGroup1);
		
		
		Button exit_game=(Button)dialog.findViewById(R.id.close);
		
		b.setTypeface(tf);
		exit_game.setTypeface(tf);
		
		exit_game.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showCloseDialog();
			}
		});
		
				
		b.setOnClickListener(new OnClick_());
		
		dialog.setCancelable(false);
		dialog.show();
	}
	
	//questo qui invece salva il nickname
	private void saveNickname(String n){
		sql.insertNick(n);
	}
	
	
	private boolean checkNick(){
		Cursor c=sql.fetchNick();
		if(c.getCount()>0){
			c.moveToLast();
			nick=c.getString(1);
			Log.i("nickname",nick);
			return true;
		}
		else return false;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private Button addCloseButton(){
		Button b=new Button(this);
		
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			b.setBackgroundDrawable(getResources().
					getDrawable(R.drawable.close_btn));
		} else {
			b.setBackground(getResources().getDrawable(R.drawable.close_btn));
		}
		
		FrameLayout.LayoutParams ll=new FrameLayout.LayoutParams(50,50);
		ll.gravity=Gravity.TOP|Gravity.RIGHT;
		b.setLayoutParams(ll);
		
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showCloseDialog();
			}
		});
		return b;
	}
	
	private void showCloseDialog(){
		
		//fermo tutto
		gw.stopListner();
		gw.stopAllExecution();
		
		
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Conferma uscita")
        .setMessage("Sicuro di voler uscire?")
        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Stop the activity
                MainActivity.this.finish(); 
                mustclose=true;
            }
        })
        .setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	//se è no riprendo
            	gw.startListner();
        		gw.resumeAllExecution();   
            }
        })
        .show();

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
		if(gw!=null && hasStart){
			gw.startListner();
			gw.resumeAllExecution();
		}
	}
	@Override
	public void onDestroy(){
		super.onDestroy(); 
		sql.close();
		
		startActivity(new Intent(MainActivity.this,BannerActivity.class));
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
	
	class OnClick_ implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_startgame:
				switch(rg.getCheckedRadioButtonId()){
				case R.id.radio0:
					gs.setDifficult(gs.DIFFICULT_EASY);
					break;
				case R.id.radio1:
					gs.setDifficult(gs.DIFFICULT_NORMAL);
					break;
				case R.id.radio2:
					gs.setDifficult(gs.DIFFICULT_HARD);
					break;
				}
					
				gs.setUserSettings(new UserSettings());
				gs.getUserSettings().setNick(nick);
				//salvo il nick
				saveNickname(nick);
				gw.setGameSettings(gs);
					
				//gw.startGame();
				dialog.dismiss();
				hasStart=true;
			break;
			}
			
		}
	}
}
