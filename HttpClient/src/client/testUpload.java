package client;

import java.awt.Image;
import java.awt.Toolkit;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class testUpload {
	static MultipartFileUploader3 multipartFileUploader3 = new MultipartFileUploader3();

	public static void main(String[] args) {
		// �������� ����
		int type = 2;

		switch (type) {
		// String
		case 0:
			String stringData = "aa";
			//multipartFileUploader3.uploadStringData(stringData);
			break;
		// Captured Image
		case 1:
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image capturedImage = toolkit.getImage("C:\\Users\\Administrator\\Desktop\\aaaa.jpeg");
			//multipartFileUploader3.uploadCapturedImageData(capturedImage);
			break;
		// Multiple File
		case 2:
			multipartFileUploader3.uploadMultipartData("C:\\Users\\Administrator\\Desktop\\", "taeyeon.mp3");
			break;
		// Folder
		case 3:
			break;
		default:

		}
	}

	/** ������� response Msg ��� */
	public static void responseMsgLog(HttpResponse response) {

		System.out.println("Http Post Response: " + response.toString());

		/* client�� ���� response ������ ���� */
		System.out.println("==================������==================");
		System.out.println("Response StatusLine: " + response.getStatusLine());
		System.out.println("Response ProtocolVersion: " + response.getProtocolVersion());

		/* client�� ���� response ��� ���� */
		System.out.println("==================���====================");
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			System.out.println(headers.toString());
		}
		System.out.println("================����Ƽ ����================");
		System.out.println("===========================================");
	}

}
