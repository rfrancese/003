package it.carmine.bouncyrun;

import it.carmine.bouncyrun.util.httpRequests.SendInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ClassificaActivity extends Activity {

	private String nick,punteggio,difficolta;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_classifica);
		
		Intent i=getIntent();
		
		nick=i.getStringExtra("nick");
		punteggio=i.getStringExtra("punteggio");
		difficolta=i.getStringExtra("difficolta");
		
		Button share=(Button)findViewById(R.id.button1);
		share.setOnClickListener(new OnClick());
	}
	
	
	class OnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Send s=new Send();
			s.execute();
		}
	}
	
	class Send extends AsyncTask{
		@Override
		protected Object doInBackground(Object... params) {
			SendInfo s=new SendInfo(nick,punteggio,difficolta);
			return null;
		}
	}
}
