package it.carmine.bouncyrun;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BannerActivity extends Activity implements AdListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
	}

	@Override
	public void adClicked() {
		
	}

	@Override
	public void adClosed(Ad arg0, boolean arg1) {
		
	}

	@Override
	public void adLoadSucceeded(Ad arg0) {
		
	}

	@Override
	public void adShown(Ad arg0, boolean arg1) {
		
	}

	@Override
	public void noAdFound() {

	}
}
