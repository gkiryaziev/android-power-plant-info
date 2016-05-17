package ua.kiryaziev.PowerPlantInfo;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;

public class AsyncHttp extends AsyncTask<String, Void, String> {
	
	HttpClient httpClient = new DefaultHttpClient();	// добавлено

	// добавим параметры
	List<NameValuePair> vPair = new ArrayList<NameValuePair>();
	
	private String url = "";
	private String userName = "";
	private String userPass = "";

	@Override
	protected String doInBackground(String... params) {
		try {
			
			url = params[0];
			userName = params[1];
			userPass = params[2];
			
			HttpPost httpPost = new HttpPost(url);
			
			vPair.add(new BasicNameValuePair("p1", userName));
			vPair.add(new BasicNameValuePair("p2", userPass));
			httpPost.setEntity(new UrlEncodedFormEntity(vPair, "UTF-8"));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			vPair.clear();
			
			return EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
