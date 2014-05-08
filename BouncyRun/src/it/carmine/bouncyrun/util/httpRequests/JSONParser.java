package it.carmine.bouncyrun.util.httpRequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";
	
	//prendo l'input stream che ho ricevuto
	 public JSONArray getJSON(InputStream is) {
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "n");
	            }
	            is.close();
	            json = sb.toString();
	            Log.e("JSON", json);
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	 
	        // creo l'oggetto json
	        try {
	            jObj = new JSONArray(json);            
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }
	 
	        // oggetto json
	        return jObj;
	    }
}
