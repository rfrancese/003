package it.carmine.bouncyrun;

import it.carmine.bouncyrun.sql.SqlStorage;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrazioneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrazione);
		final EditText et=(EditText)findViewById(R.id.editText1);
		Button b=(Button)findViewById(R.id.button1);
		
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String nick=et.getText().toString();
				saveNewNick(nick);
			}
		});
	}
	public void saveNewNick(String n){
		SqlStorage sql=new SqlStorage(this);
		sql.open();
		sql.insertNick(n);
		Toast t=Toast.makeText(this, "Nick saved!", Toast.LENGTH_LONG);
		t.show();
	}
}
