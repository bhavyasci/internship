package com.ecs.ppp.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.util.Log;

public class WebServerCall {
	//LIVE URL
	private static String URL = "http://ecsprojects.com/PPPAPI/api.php";
	
	public static String putDataToServer(JSONObject returnedJObject)
			throws Throwable {

		HttpPost request = new HttpPost(URL);
		JSONStringer json = new JSONStringer();
		StringBuilder sb = new StringBuilder();

		if (returnedJObject != null) {
			Iterator<String> itKeys = returnedJObject.keys();
			if (itKeys.hasNext())
				json.object();
			while (itKeys.hasNext()) {
				String k = itKeys.next();
				json.key(k).value(returnedJObject.get(k));
				Log.e("keys " + k, "value " + returnedJObject.get(k).toString());
			}
		}
		json.endObject();

		StringEntity entity = new StringEntity(json.toString());
		entity.setContentType("application/json;charset=UTF-8");
		entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json;charset=UTF-8"));
		// request.setHeader("Accept", "application/json");
		request.setHeader("Content-Type", "application/json");
		request.setEntity(entity);

		HttpResponse response = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpConnectionParams.setSoTimeout(httpClient.getParams(),
				com.ecs.ppp.utils.Constants.ANDROID_CONNECTION_TIMEOUT * 1000);
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				com.ecs.ppp.utils.Constants.ANDROID_CONNECTION_TIMEOUT * 1000);
		try {

			response = httpClient.execute(request);
		} catch (SocketException se) {
			Log.e("SocketException", se + "");
			throw se;
		}

		InputStream in = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);

		}

		return sb.toString();
	}

	public static String getDataFromServer() throws Throwable {

		String line, response;
		HttpURLConnection conn = (HttpURLConnection) (new URL(URL))
				.openConnection();

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		response = "";
		while ((line = rd.readLine()) != null) {
			response = response + line;
		}

		rd.close();

		return response;
	}
}
