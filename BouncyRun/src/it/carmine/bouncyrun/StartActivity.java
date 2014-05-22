package it.carmine.bouncyrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Asinc a=new Asinc();
		a.execute();
	}
	
	class Asinc extends AsyncTask{
		@Override
		public void onPostExecute(Object o){
			startActivity(new Intent(StartActivity.this,MenuActivity.class));
			finish();
		}
		@Override
		protected Object doInBackground(Object... o){
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
