package client;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class testUpload {
	public static UploadData uploader = new UploadData("gmlwjd9405@naver.com", "doyyyy");

	public static void main(String[] args) {
		// 데이터의 종류
		int type = 2;

		switch (type) {
		// String
		case 0:
			String exStringData = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			System.out.println("<testUpload> exStringData: " + exStringData);

			uploader.uploadStringData(exStringData);
			break;

		// Captured Image
		case 1:
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			Image exCapturedImage = toolkit.getImage("aaaa.jpeg");
			System.out.println("<testUpload> capturedImage: " + exCapturedImage.toString());

			ImageIcon excapturedImageIcon = new ImageIcon(exCapturedImage);
			System.out.println("<testUpload> capturedImageIcon: " + excapturedImageIcon.toString());

			uploader.uploadCapturedImageData(exCapturedImage);
			break;

		// Multiple File
		case 2:
			ArrayList<String> fileFullPathList = new ArrayList<String>();
			fileFullPathList.add("C:\\Users\\Administrator\\Desktop\\hello\\ccccc.txt");
			fileFullPathList.add("C:\\Users\\Administrator\\Desktop\\taeyeon.mp3");
			fileFullPathList.add("C:\\Users\\Administrator\\Desktop\\hello\\qwerqer\\bbbb.jpeg");

			uploader.uploadMultipartData(fileFullPathList);
			break;

		// Folder
		case 3:
			break;
		default:

		}
	}

	// Server쪽으로 옮겨야 할듯
	/** 응답받은 response Msg 출력 */
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
