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
	    HttpPost httppost = new HttpPost("http://enimrac92.altervista.org/bdSir/api.php");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("key", "V29445420Kg1715165A"));
	        nameValuePairs.add(new BasicNameValuePair("nick", nick));
	        nameValuePairs.add(new BasicNameValuePair("punteggio", nick));
	        nameValuePairs.add(new BasicNameValuePair("difficolta", nick));
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
}
