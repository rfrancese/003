package it.carmine.bouncyrun.social_share;



import it.carmine.bouncyrun.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.easy.facebook.android.apicall.GraphApi;
import com.easy.facebook.android.data.User;
import com.easy.facebook.android.error.EasyFacebookError;
import com.easy.facebook.android.facebook.FBLoginManager;
import com.easy.facebook.android.facebook.Facebook;
import com.easy.facebook.android.facebook.LoginListener;

public class FacebookLoginActivity extends Activity implements LoginListener {

    private FBLoginManager fbLoginManager;

    //replace it with your own Facebook App ID
    public final String KODEFUNFBAPP_ID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.main);
            connectToFacebook();
    }

    public void connectToFacebook(){
           
            //read about Facebook Permissions here:
            //http://developers.facebook.com/docs/reference/api/permissions/
            String permissions[] = {
                            "publish_stream",
                            "publish_actions",
            };

           fbLoginManager = new FBLoginManager(this,
                            R.layout.fb_login, 
                            KODEFUNFBAPP_ID, 
                            permissions);

            if(fbLoginManager.existsSavedFacebook()){
                    fbLoginManager.loadFacebook();
            }
            else{
                    fbLoginManager.login();
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data){
            if(data!=null)
                    fbLoginManager.loginSuccess(data);
            else
                    finish();
    }
    
    private GraphApi graphApi;
    
    public void loginSuccess(Facebook facebook) {
         graphApi = new GraphApi(facebook);

         SetStatus ss=new SetStatus();
         ss.execute();
    }

    
    class SetStatus extends AsyncTask{
    	@Override
    	public void onPostExecute(Object a){
    		Log.i("riuscito","ok");
    	}
    	@Override
    	protected Object doInBackground(Object... arg0) {
    		try{
    			graphApi.setStatus(
    					getIntent().getStringExtra("nick")+
							" ha totalizzato un punteggio di "+
								getIntent().getStringExtra("points")+" salti", 
						"http://image-gj.9game.com/2013/5/2/9024810.jpg",
						"http://enimrac92.altervista.org/", 
						"Il mio punteggio su BouncyRun", 
						"http://enimrac92.altervista.org/",
                        "Il mio punteggio su BouncyRun"
                   );
    		} catch(EasyFacebookError e){
    			Log.d("TAG: ", e.toString());
    		}
    		return null;
    	}
    }
    
    public void logoutSuccess() {
    	fbLoginManager.displayToast("Logout Success!");
    	finish();
    }
    public void loginFail() {
    	fbLoginManager.displayToast("Login Failed!");
    	finish();
    }
}