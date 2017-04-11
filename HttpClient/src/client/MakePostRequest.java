package client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class MakePostRequest {
	public static void main(String[] args) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		// replace with your url
		HttpPost httpPost = new HttpPost("http://g1.botva.ru/login.php");

		httpPost.addHeader("Accept", "*/*");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate");
		// httpPost.addHeader("Content-Length", "83");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.addHeader("User-Agent", "runscope/0.1");

		Header[] arr = httpPost.getAllHeaders();

		String result123 = "";
		// Post Data
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);

		nameValuePair.add(new BasicNameValuePair("do_cmd", "login"));
		nameValuePair.add(new BasicNameValuePair("server", "1"));
		nameValuePair.add(new BasicNameValuePair("email", "avmalyutin@mail.ru"));
		nameValuePair.add(new BasicNameValuePair("password", "avmalyutin1234"));
		nameValuePair.add(new BasicNameValuePair("remember", "1"));

		// Encoding POST data
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// log exception
			e.printStackTrace();
		}

		// making POST request.
		try {
			HttpResponse response = httpClient.execute(httpPost);
			String getCookie = response.getHeaders("Pragma").length + "";
			result123 = response.getStatusLine().getStatusCode() + "";
			// write response to log
			System.out.println("Http Post Response: " + response.toString());
		} catch (ClientProtocolException e) {
			// Log exception
			e.printStackTrace();
		} catch (IOException e) {
			// Log exception
			e.printStackTrace();
		}

		System.out.println( result123);
	}

}
