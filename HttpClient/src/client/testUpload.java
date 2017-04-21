package client;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;

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
			Map<String, String> fileFullPathList = new LinkedHashMap<String, String>();
//			 fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\data1.zip", null);
			// fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\data2.txt", null);
			// fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\folder2\\data3.hwp", null);

			
			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\data1.zip", null);
			
//			 fileFullPathList.add("C:\\Users\\Administrator\\Desktop\\folder1"); // DIR
			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\data2.txt", "folder1"); // Folder가 아닌 데이터의 정보를 먼저
			
//			 fileFullPathList.add("C:\\Users\\Administrator\\Desktop\\folder1\\folder2"); // DIR

			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\folder2\\data1.jpeg", "folder1/folder2");
			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\folder2\\data2.jpeg", "folder1/folder2");
			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\folder2\\data2.txt", "folder1/folder2");
			fileFullPathList.put("C:\\Users\\Administrator\\Desktop\\folder1\\folder2\\data3.hwp", "folder1/folder2");

			uploader.uploadMultipartData(fileFullPathList);
			break;

		// Include Folder
		case 3:

			break;
		default:

		}
	}
}
