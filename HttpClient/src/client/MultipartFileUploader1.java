package client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 * 
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader1 {

	public static void main(String[] args) {
		System.out.println("<MultipartFileUploader1>");
		String charset = "UTF-8";
		File uploadFile1 = new File("C:\\Users\\Administrator\\Desktop\\aaaa.jpeg");
		File uploadFile2 = new File("C:\\Users\\Administrator\\Desktop\\bbbb.jpeg");
		String requestURL = "http://localhost:8080/TomcatServer/FileUpload";

		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			/* 헤더에 추가 */
			multipart.addHeaderField("User-Agent", "CodeJava");
			multipart.addHeaderField("Test-Header", "Header-Value");
			multipart.addHeaderField("Content-Disposition", "form-data; name=\"fileUpload1\"; filename=\"bbbb.jpg\"") ;
			multipart.addHeaderField("Test-Header", "Header-Value");

			/* 본문에 추가 */
			multipart.addFormField("description", "Cool Pictures");
			multipart.addFormField("keywords", "Java,upload,Spring");

			/* 파일 업로드 */
			multipart.addFilePart("fileUpload1", uploadFile1);
//			multipart.addFilePart("fileUpload2", uploadFile2);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED:");

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	/** request msg 출력 */
	public void responseMsgLog(HttpResponse response) {
		
//		/* client가 돌려받은 response header msg 출력 */
//		HttpResponse response = client.execute(post);
//		Header[] receiveResponseHeaders = response.getAllHeaders();
//		System.out.println("=========<receiveResponseHeaders>========");
//		System.out.println("Response ProtocolVersion: " + response.getProtocolVersion());
//		System.out.println("Response TotalStatusLine: " + response.getStatusLine());
//		for (Header header : receiveResponseHeaders) {
//			// System.out.println(header.toString());
//			System.out.print(header.getName() + ": " + header.getValue());
//			System.out.println();
//		}
//
//		/* server가 받은 request 시작줄 정보 */
//		System.out.println("==================시작줄==================");
//		System.out.println("Request Method: " + request.getMethod());
//		System.out.println("Request RequestURI: " + request.getRequestURI());
//		System.out.println("Request Protocol: " + request.getProtocol());
//		
//		/* server가 받은 request 헤더 정보 */
//		/* server가 받은 기본적인 request header msg 정보 */
//		System.out.println("==================헤더====================");
//		Enumeration headerNames = request.getHeaderNames();
//
//		while (headerNames.hasMoreElements()) {
//			String headerName = (String) headerNames.nextElement();
//
//			System.out.println(headerName + ": " + request.getHeader(headerName));
//		}
//		System.out.println("-------------------------------------------");
//		System.out.println("Request LocalAddr: " + request.getLocalAddr());
//		System.out.println("Request LocalName: " + request.getLocalName());
//		System.out.println("Request LocalPort: " + request.getLocalPort());
//		System.out.println("-------------------------------------------");
//		System.out.println("Request RemoteAddr: " + request.getRemoteAddr());
//		System.out.println("Request RemoteHost: " + request.getRemoteHost());
//		System.out.println("Request RemotePort: " + request.getRemotePort());
//		System.out.println("Request RemoteUser: " + request.getRemoteUser());
//
//		System.out.println("================엔터티 본문================");
//		System.out.println("description: " + request.getParameter("description"));
//		System.out.println("keywords: " + request.getParameter("keywords"));
//		System.out.println("===========================================");
	}
}