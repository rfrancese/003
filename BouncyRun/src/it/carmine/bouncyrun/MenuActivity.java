package it.carmine.bouncyrun;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
	}
	
	class OnCLick implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button_exit:
				finish();
				break;
			case R.id.button_play:
				break;
			case R.id.button_classifica:
				break;
			case R.id.button_web:
				break;	
			}
		}
	}
}
