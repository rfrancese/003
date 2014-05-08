package it.carmine.bouncyrun;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

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
import android.widget.ListView;


public class ClassificaActivity extends Activity {
	private ProgressDialog progress;
	private Intent i;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_classifica);
		
		i=getIntent();
		
		Button share=(Button)findViewById(R.id.button1);
		share.setOnClickListener(new OnClick());
		lv=(ListView)findViewById(R.id.listView1);
		
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
		public void onPostExecute(Object o){
			//qui si setta la roba per la list view
		}
		@Override
		protected Object doInBackground(Object... params) {
			ReceiveInfo ri=new ReceiveInfo();
			try {
				ri.receiveData();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
