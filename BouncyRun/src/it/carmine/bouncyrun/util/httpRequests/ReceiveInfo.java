package it.carmine.bouncyrun.util.httpRequests;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ReceiveInfo {

	public ArrayList<PosizioneClassifica>receiveData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://enimrac92.altervista.org/bdSir/api.php");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("key", "V29445420Kg1715165A"));
	       	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        JSONParser jsp=new JSONParser();
	        JSONObject jo=jsp.getJSON(response.getEntity().getContent());
	        JSONArray ja=new JSONArray(jo.toString());
	        ArrayList<PosizioneClassifica>pa=new ArrayList<PosizioneClassifica>();
	        
	        for(int i=0;i<ja.length();i++){
	        	jo=ja.getJSONObject(i);
	        	pa.add(new PosizioneClassifica(
	        									jo.getString("nick_utente"),
	        									jo.getString("difficolta"),
	        									jo.getString("punteggio")
	        									)
	        		  );
	        	Log.i("json result", jo.getString("nick_utente"));
	 
	        }
	        return pa;
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
}
