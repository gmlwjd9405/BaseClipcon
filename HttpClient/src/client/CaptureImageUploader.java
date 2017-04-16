//package client;
//
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//
//import org.apache.http.Header;
//import org.apache.http.HttpResponse;
//
//public class CaptureImageUploader {
//	private final static String SERVER_URL = "http://localhost:8080/TomcatServer";
//	private final static String SERVER_SERVLET = "/UploadServlet";
//
//	public static void main(String[] args) {
//		// 업로드 파일의 위치
//		final String DIRECTORY = "C:\\Users\\Administrator\\Desktop\\";
//		// 업로드할 파일명
//		final String UPLOAD_FILENAME = "taeyeon.mp3";
//
//		String charset = "UTF-8";
//		
//		
//		 Toolkit toolkit = Toolkit.getDefaultToolkit();
//
//		    // Application resource URL - Best method
//		    URL daffyURL = getClass().getResource("/cartoons/images/daffy.gif");
//		    Image daffyDuckImage = toolkit.getImage( daffyURL );
//
//		    // Absolute URL -
//		    URL monaURL = new URL( "http://myserver/images/mona_lisa.png");
//		    Image monaImage = toolkit.getImage( monaURL );
//
//		    // Local file -
//		    Image elvisImage = toolkit.getImage("c:/elvis/lateryears/fatelvis1.jpg" );
//		    
//		    
//
//		// 업로드할 파일 생성
//		File uploadFile = new File(DIRECTORY + UPLOAD_FILENAME);
//
//		try {
//			MultipartUtility2 multipart = new MultipartUtility2(SERVER_URL + SERVER_SERVLET, charset);
//			multipart.addHeaderField("User-Agent", "Heeee");
//
//			/**
//			 * __sequence contains the name of the called sequence within the
//			 * specified project (in the requestURL)
//			 */
//			multipart.addFormField("__sequence", "uploadFile");
//			multipart.addFormField("uploadFilename", UPLOAD_FILENAME);
//
//			/**
//			 * uploadFilename is the name of the sequence input variable in the
//			 * called project the value is the name that will be given to the
//			 * file
//			 */
//			multipart.addFilePart("uploadFilename", uploadFile);
//
//			List<String> response = multipart.finish();
//
//			System.out.println("SERVER REPLIED");
//			// responseMsgLog();
//
//			for (String line : response) {
//				System.out.println(line);
//			}
//		} catch (IOException ex) {
//			System.err.println(ex);
//		}
//	}
//
//	public static void responseMsgLog(HttpResponse response) {
//
//		System.out.println("Http Post Response: " + response.toString());
//
//		/* client가 받은 response 시작줄 정보 */
//		System.out.println("==================시작줄==================");
//		System.out.println("Response StatusLine: " + response.getStatusLine());
//		System.out.println("Response ProtocolVersion: " + response.getProtocolVersion());
//
//		/* client가 받은 response 헤더 정보 */
//		System.out.println("==================헤더====================");
//		Header[] headers = response.getAllHeaders();
//		for (Header header : headers) {
//			System.out.println(headers.toString());
//		}
//		System.out.println("================엔터티 본문================");
//		System.out.println("===========================================");
//	}
//}