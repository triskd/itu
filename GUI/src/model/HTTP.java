package model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class HTTP{
	
	private String url;
	private String data;
	
	public HTTP(String url, String data){
		this.url = url;
		this.data = data;
	}
	
	public HTTP(){
		this.url = "";
		this.data = "";
	}
	
	void set(String url, String data){
		this.url = url;
		this.data = data;
	}
	
	public String POST(){
		String ret = "ERROR";
		
		try{
			ret = this.createPost();
		}catch(MalformedURLException e){
			
			ret = "REQERR-URL";
			
		}catch(ProtocolException e){
			
			ret = "REQERR-PROTOCOL";
			
		}catch(IOException e){
			
			ret = "REQERR-IO";
			
		}
		
		return ret;
	}
	
	public String POST(String url, String data){
		this.set(url, data);
		return this.POST();
	}
	
	/*	funkce zalozena na prikladu z
	 *  https://stackabuse.com/how-to-send-http-requests-in-java/
	 * 
	 * */
	public String createPost() throws MalformedURLException, ProtocolException, IOException {
		
		URL reqUrl = new URL(this.url);
			
		HttpURLConnection connection = (HttpURLConnection) reqUrl.openConnection();
		
		
		
		connection.setRequestMethod("POST");
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("data", this.data);

	    StringBuilder postData = new StringBuilder();
	    for (Map.Entry<String, String> param : params.entrySet()) {
	        if (postData.length() != 0) {
	            postData.append('&');
	        }
	        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	        postData.append('=');
	        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	    }

	    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	    connection.setDoOutput(true);
	    try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
	        writer.write(postDataBytes);
	        writer.flush();
	        writer.close();

	        StringBuilder content;

	        try (BufferedReader in = new BufferedReader(
	                new InputStreamReader(connection.getInputStream()))) {
	        String line;
	        content = new StringBuilder();
	           while ((line = in.readLine()) != null) {
	                content.append(line);
	                content.append(System.lineSeparator());
	            }
	        }
	       	System.out.println(content.toString());
	        return content.toString();
	    } finally {
	        connection.disconnect();
	    }
		
	}
	
}