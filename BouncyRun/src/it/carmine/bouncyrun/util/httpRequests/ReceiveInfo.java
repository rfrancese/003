package it.carmine.bouncyrun.util.httpRequests;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveInfo {

	public ArrayList<PosizioneClassifica>receiveData() throws ClientProtocolException, IOException {
	    ArrayList<PosizioneClassifica>apc=new ArrayList<PosizioneClassifica>();
	    
		String url="http://enimrac92.altervista.org/bdSir/api.php?key=V29445420Kg1715165A";
		
		DefaultHttpClient client = new DefaultHttpClient();  
		HttpGet httpGet = new HttpGet(url); 
		HttpResponse execute = client.execute(httpGet);  
		InputStream content = execute.getEntity().getContent(); 
		JSONParser js=new JSONParser();
		JSONArray jo=js.getJSON(content);
		//Log.i("json=",jo.toString());
		
		for(int i=0;i<jo.length();i++){
			try {
				JSONObject j=jo.getJSONObject(i);
				PosizioneClassifica pc=
						new PosizioneClassifica(j.getString("nick_utente"),
												j.getString("punteggio"),
												j.getString("difficolta")
												);
				apc.add(pc);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return apc;
	} 
}
