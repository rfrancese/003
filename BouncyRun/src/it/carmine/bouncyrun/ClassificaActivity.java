package it.carmine.bouncyrun;

import it.carmine.bouncyrun.util.httpRequests.ReceiveInfo;
import it.carmine.bouncyrun.util.httpRequests.SendInfo;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ClassificaActivity extends Activity {
	private ProgressDialog progress;
	private Intent i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_classifica);
		
		i=getIntent();
		
		Button share=(Button)findViewById(R.id.button1);
		share.setOnClickListener(new OnClick());
		
		Receive r=new Receive();
		r.execute();
	}
	
	
	class OnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Send s=new Send();
			s.execute();
			progress = ProgressDialog.show(ClassificaActivity.this, "Invio",
				    "Invio dei dati al server...", true);
			progress.setCancelable(false);
		}
	}
	
	class Receive extends AsyncTask{
		@Override
		protected Object doInBackground(Object... params) {
			ReceiveInfo ri=new ReceiveInfo();
			ri.receiveData();
			return null;
		}
	}
	class Send extends AsyncTask{
		
		@Override
		public void onPostExecute(Object o){
			progress.dismiss();
		}
		@Override
		protected Object doInBackground(Object... params) {
			
			SendInfo s=new SendInfo(i.getStringExtra("nick"),
									i.getStringExtra("punteggio"),
									i.getStringExtra("difficolta")
									);
			return null;
		}
	}
}
