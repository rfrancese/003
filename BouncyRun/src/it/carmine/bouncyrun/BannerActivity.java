package it.carmine.bouncyrun;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.banner.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BannerActivity extends Activity implements AdListener{
	private AdView mAdView;
	private AdManager mManager;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		
		
		mManager = new AdManager(this,"http://my.mobfox.com/request.php",
				"c8fb2e026eae007899da14f8a148859a", true);
		
		mManager.setInterstitialAdsEnabled(true); //enabled by default. Allows the SDK to request static interstitial ads.
		mManager.setVideoAdsEnabled(true); //disabled by default. Allows the SDK to request video fullscreen ads.
		mManager.setPrioritizeVideoAds(true); //disabled by default. If enabled, indicates that SDK should request video ads first, and only if there is no video request a static interstitial (if they are enabled).
		
		
		mManager.setListener(this);
		mManager.requestAd();
		
		 progressDialog=ProgressDialog.show(this,"","Caricamento in corso...");
		 progressDialog.setCancelable(false);
	}

	@Override
	public void adClicked() {
		finish();
	}

	@Override
	public void adClosed(Ad arg0, boolean arg1) {
		finish();
	}

	@Override
	public void adLoadSucceeded(Ad arg0) {
		if(mManager !=null && mManager.isAdLoaded()){
			mManager.showAd();
		}
		progressDialog.dismiss();
	}

	@Override
	public void adShown(Ad arg0, boolean arg1) {
	}

	@Override
	public void noAdFound() {
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		mManager.release();
		if(mAdView!=null)
			mAdView.release();
	}
}
