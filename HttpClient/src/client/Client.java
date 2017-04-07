package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class Client {

	public static void main(String[] args) {
		// http request를 날릴 client
		HttpClient client = HttpClientBuilder.create().build();
		// http request를 날릴 목적지 URI
		HttpPost post = new HttpPost("http://localhost:8080/GodDoyServer/PrintHttpRequestHeaders");

		// add header
		// post.setHeader("Host", "localhost");
		// post.setHeader("Request Method", "POST");
		// post.setHeader("Host", "accounts.google.com");
		// post.setHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		// post.setHeader("Accept-Language", "en-US,en;q=0.5");
		// post.setHeader("Connection", "keep-alive");
		// post.setHeader("Referer",
		// "https://accounts.google.com/ServiceLoginAuth");
		// post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			/* request 시에 필요한 정보들(request내용으로 보냄) */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("username", "goddoy"));
			nameValuePairs.add(new BasicNameValuePair("Passwd", "dodo"));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

			/* 요청하는 request msg 출력 */
			// Header[] sendRequestHeaders = post.getAllHeaders();
			// System.out.println("=========<sendRequestHeaders>========");
			// for (Header header : sendRequestHeaders) {
			// System.out.print(header.getName() + ": ");
			// System.out.println(header.getValue());
			// }
			// System.out.println();

			/* 돌려받은 response msg 출력 */
			HttpResponse response = client.execute(post);
			Header[] receiveResponseHeaders = response.getAllHeaders();
			System.out.println("=========<receiveResponseHeaders>========");
			for (Header header : receiveResponseHeaders) {
				System.out.print(header.getName() + ": ");
				System.out.println(header.getValue());
			}
			System.out.println();

			/* response 결과 출력 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			String line = "";

			System.out.println("=========<getResponseResult>========");
			while ((line = rd.readLine()) != null) {
				// result.append(line);
				// System.out.println(result);
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}