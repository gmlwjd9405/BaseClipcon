package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Client {

	public static void main(String[] args) {
		// http request를 날릴 client
		HttpClient client = HttpClientBuilder.create().build();
		// http request를 날릴 목적지 URI
		HttpPost post = new HttpPost("http://localhost:8080/TomcatServer/PrintHttpRequestHeaders");

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
			/* server에 보낼 request body 정보 추가 */
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("username", "goddoy"));
			nameValuePairs.add(new BasicNameValuePair("Passwd", "dodo"));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

			// /* server에 요청하는 request header msg 출력 */
			// Header[] sendRequestHeaders = post.getAllHeaders();
			// System.out.println("=========<sendRequestHeaders>========");
			// for (Header header : sendRequestHeaders) {
			// System.out.print(header.getName() + ": " + header.getValue());
			// System.out.println();
			// }
			// System.out.println();

			/* client가 돌려받은 response header msg 출력 */
			HttpResponse response = client.execute(post);
			Header[] receiveResponseHeaders = response.getAllHeaders();
			System.out.println("=========<receiveResponseHeaders>========");
			System.out.println("Response ProtocolVersion: " + response.getProtocolVersion());
			System.out.println("Response TotalStatusLine: " + response.getStatusLine());
			for (Header header : receiveResponseHeaders) {
				// System.out.println(header.toString());
				System.out.print(header.getName() + ": " + header.getValue());
				System.out.println();
			}
			System.out.println();

			// /* client가 돌려받은 response body msg 결과 출력(방법 1) */
			// System.out.println("=========<getResponseResult>========");
			// System.out.println(EntityUtils.toString(response.getEntity()));

			/* client가 돌려받은 response body msg 결과 출력(방법 2) */
			System.out.println("=========<getResponseResult>========");
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			String line = "";

			while ((line = rd.readLine()) != null) {
				// result.append(line);
				// System.out.println(result);
				System.out.println(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printHttpMessage() {

	}

}