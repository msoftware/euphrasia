package com.jbj.euphrasia.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class AbstractRemoteTask extends AsyncTask {
	
	protected String myServiceUrl;
	protected BasicHttpParams myParams;
	protected JSONObject myJsonObject;
	protected Activity mySourceActivity;
	
	/**
	 * @param Object[] params : an array of objects where each object is a array of Objects
	 * Converts each nested object array to a string array and uses to create BasicHttpParams.
	 */
	protected void setParams(Object[] params){
		myParams = new BasicHttpParams();
		for(int i = 0;i<params.length;i++){
			String[] param = (String[])params[i];
			myParams.setParameter(param[0], param[1]);
		}
	}
	
	public void setActivity(Activity activity){
		mySourceActivity = activity;
	}
	
	@Override
	protected void onPreExecute(){
		myServiceUrl = this.getServiceUrl();
	}

	@Override
	protected Object doInBackground(Object... params) {
		this.setParams(params);
		HttpUriRequest post = this.getUriRequest();
		post.setHeader("Content-type", "application/json");
		InputStream inputStream = null;
		String result = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient(myParams);
		    
			HttpResponse response = client.execute(post);           
		    HttpEntity entity = response.getEntity();
		    inputStream = entity.getContent();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		    myJsonObject = new JSONObject(result);
		} catch (Exception e) { 
		    e.printStackTrace();
		}
		return null;
	}
	
	protected abstract HttpUriRequest getUriRequest();
	
	protected abstract String getServiceUrl();

}