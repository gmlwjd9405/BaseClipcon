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
}
