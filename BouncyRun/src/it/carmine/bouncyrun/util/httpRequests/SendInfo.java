package it.carmine.bouncyrun.util.httpRequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class SendInfo {

	private String nick,punteggio,difficolta;
	public SendInfo(String n,String p,String d){
		nick=n;
		punteggio=p;
		difficolta=d;
		postData(); 	
	}
	
	public void postData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = 
	    		new HttpPost("http://enimrac92.altervista.org/bdSir/api.php?key=V29445420Kg1715165A");
	    try {
	        // Add your data	
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("post","invio"));
	        nameValuePairs.add(new BasicNameValuePair("nick", nick));
	        nameValuePairs.add(new BasicNameValuePair("punteggio", punteggio));
	        nameValuePairs.add(new BasicNameValuePair("difficolta", difficolta));
	        //modificare
	        nameValuePairs.add(new BasicNameValuePair("nazione","italia"));
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        Log.i("spediti",nick+punteggio+difficolta);
	        
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	        		response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }

            Log.i("response",sb.toString());
            
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
}
