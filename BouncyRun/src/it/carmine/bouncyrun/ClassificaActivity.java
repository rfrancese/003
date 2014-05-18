package it.carmine.bouncyrun;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import it.carmine.bouncyrun.adapters_list.AdapterClassifica;
import it.carmine.bouncyrun.util.httpRequests.PosizioneClassifica;
import it.carmine.bouncyrun.util.httpRequests.ReceiveInfo;
import it.carmine.bouncyrun.util.httpRequests.SendInfo;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
		
		if(isOnline()){
			Receive r=new Receive();
			r.execute();
			progress = ProgressDialog.show(ClassificaActivity.this, "Ricezione",
					"Ricevo dati dal server...", true);
			progress.setCancelable(false);
		}
	}
	
	
	class OnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(isOnline()){
				Send s=new Send();
				s.execute();
				progress = ProgressDialog.show(ClassificaActivity.this, "Invio",
						"Invio dei dati al server...", true);
				progress.setCancelable(false);
			}
		}
	}
	
	class Receive extends AsyncTask{
		ArrayList<PosizioneClassifica>p;
		@Override
		public void onPostExecute(Object o){
			lv.setAdapter(new AdapterClassifica(ClassificaActivity.this,p));
			progress.dismiss();
		}
		@Override
		protected Object doInBackground(Object... params) {
			ReceiveInfo ri=new ReceiveInfo();
			try {
				p=ri.receiveData();
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
