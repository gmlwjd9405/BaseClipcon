package client;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class testUpload {
	static UploadData uploader = new UploadData();

	public static void main(String[] args) {
		// �������� ����
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
			ArrayList<String> localDirList = new ArrayList<String>();
			localDirList.add("C:\\Users\\Administrator\\Desktop\\hello\\");
			localDirList.add("C:\\Users\\Administrator\\Desktop\\");
			localDirList.add("C:\\Users\\Administrator\\Desktop\\hello\\qwerqer\\");

			ArrayList<String> fileNameList = new ArrayList<String>();
			fileNameList.add("ccccc.txt");
			fileNameList.add("taeyeon.mp3");
			fileNameList.add("bbbb.jpeg");

			uploader.uploadMultipartData(localDirList, fileNameList);
			break;

		// Folder
		case 3:
			break;
		default:

		}
	}

	// Server������ �Űܾ� �ҵ�
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
