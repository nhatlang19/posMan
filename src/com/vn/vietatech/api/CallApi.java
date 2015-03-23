package com.vn.vietatech.api;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class CallApi extends AsyncTask<String, String, String> {
 
    @Override
    protected String doInBackground(String... params) {
 
    	String urlString=params[0]; // URL to call
    	 
        String resultToDisplay = "";
  
        InputStream in = null;
  
        // HTTP Get
        try {
  
            URL url = new URL(urlString);
  
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
  
            in = new BufferedInputStream(urlConnection.getInputStream());
  
         } catch (Exception e ) {
  
            System.out.println(e.getMessage());
  
            return e.getMessage();
  
         }    
  
         return resultToDisplay;  
 
    }
 
    protected void onPostExecute(String result) {
 
    }
    
} // end CallAPI 