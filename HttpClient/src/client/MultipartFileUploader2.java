package client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MultipartFileUploader2 {

	public static String SERVER_URI = "http://localhost:8080/TomcatServer";
	public static String REQUEST_SERVER_SERVLET = "/FileUpload";

	public static void main(String[] args) {
		System.out.println("<MultipartFileUploader2>");

		/* 1. */
		// http request를 날릴 client
		HttpClient client = HttpClientBuilder.create().build();
		// http request를 날릴 목적지 URI
		HttpPost post = new HttpPost(SERVER_URI + REQUEST_SERVER_SERVLET);

		/* add header info(업로드할 파일에 대한 정보) */
		post.addHeader("Accept", "*/*");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Content-Disposition", "form-data; name=\"fieldName\" filename=\"ccccc.txt\"");
		post.addHeader("Content-Length", "888");
		post.addHeader("Content-Type", "multipart/form-data; boundary=------delimiter------");
		// post.addHeader("Authorization", "Basic
		// "+Base64.encodeToString("rat#1:rat".getBytes(),Base64.NO_WRAP));

		/* 서버에 보낼 파일 생성 */
		File file = new File("C:\\Users\\Administrator\\Desktop\\ccccc.txt");
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);

		/* add entity about file */
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("fieldName", fileBody);

		HttpEntity entity = builder.build();

		post.setEntity(entity);

		/* post request 날리기 */
		try {
			System.out.println("1111111111");
			HttpResponse response = client.execute(post);
			System.out.println("2222222222");
			responseMsgLog(response);
			String responseBackFromServer = EntityUtils.toString(response.getEntity());
			System.out.println(responseBackFromServer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/* 2. */
		// // http request를 날릴 client
		// HttpClient client = HttpClientBuilder.create().build();
		// // http request를 날릴 목적지 URI
		// HttpPost post = new HttpPost(SERVER_URI + REQUEST_SERVER_SERVLET);
		//
		// /* 서버에 보낼 파일 생성 */
		// File file = new File("C:\\Users\\Administrator\\Desktop\\bbbb.jpeg");
		// FileEntity reqEntity = new FileEntity(file);
		// reqEntity.setContentType("binary/octet-stream");
		//
		// /* add entity about file */
		// post.setEntity(reqEntity);
		//
		// System.out.println("executing RequestLine: " +
		// post.getRequestLine());
		//
		// try {
		// HttpResponse response = client.execute(post);
		// HttpEntity resEntity = response.getEntity();
		//
		// System.out.println("executing StatusLine: " +
		// response.getStatusLine());
		// if (resEntity != null) {
		// System.out.println(EntityUtils.toString(resEntity));
		// }
		// // if (resEntity != null) {
		// // resEntity.consumeContent();
		// // }
		//
		// // client..getConnectionManager().shutdown();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static void responseMsgLog(HttpResponse response) {

		System.out.println("Http Post Response: " + response.toString());

		/* client가 받은 response 시작줄 정보 */
		System.out.println("==================시작줄==================");
		System.out.println("Response StatusLine: " + response.getStatusLine());
		System.out.println("Response ProtocolVersion: " + response.getProtocolVersion());

		/* client가 받은 response 헤더 정보 */
		System.out.println("==================헤더====================");
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			System.out.println(headers.toString());
		}
		System.out.println("================엔터티 본문================");
		System.out.println("===========================================");
	}

}