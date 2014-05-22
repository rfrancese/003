package it.carmine.bouncyrun;

import it.carmine.bouncyrun.sql.SqlStorage;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	private Button exit,play,reg,stats,web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		exit=(Button)findViewById(R.id.button_exit);
		play=(Button)findViewById(R.id.button_play);
		reg=(Button)findViewById(R.id.button_registrazione);
		stats=(Button)findViewById(R.id.button_classifica);
		web=(Button)findViewById(R.id.button_web);
		
		OnCLick oc=new OnCLick();
		exit.setOnClickListener(oc);
		play.setOnClickListener(oc);
		reg.setOnClickListener(oc);
		stats.setOnClickListener(oc);
		web.setOnClickListener(oc);
	}
	
	class OnCLick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button_exit:
				finish();
				break;
			case R.id.button_play:
				Intent i=new Intent(MenuActivity.this,MainActivity.class);
				startActivity(i);
				break;
			case R.id.button_classifica:
				startActivity(new Intent(MenuActivity.this,ClassificaActivity.class));
				break;
			case R.id.button_web:
				startActivity(new Intent(MenuActivity.this,TutorialActivity.class));
				break;	
			case R.id.button_registrazione:
				startActivity(new Intent(MenuActivity.this,RegistrazioneActivity.class));
				break;
			}
		}
	}
}
