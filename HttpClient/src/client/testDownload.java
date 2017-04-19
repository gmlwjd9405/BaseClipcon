package client;

import java.net.MalformedURLException;

import model.Contents;
import model.History;

public class testDownload {
	public static DownloadData downloader = new DownloadData("gmlwjd9405@naver.com", "doyyyy");

	public static void main(String[] args) {
		/* test를 위한 setting (원래는 알림을 받았을 때 세팅) */
		Contents content1 = new Contents("1");
		content1.setContentsSize(400);
		content1.setContentsType(Contents.TYPE_STRING);
		content1.setFileOriginName("");
		content1.setUploadTime("");
		content1.setUploadUserName("testHee");

		// Contents content1 = new Contents("1");
		// content1.setContentsSize(10000);
		// content1.setContentsType(Contents.TYPE_IMAGE);
		// content1.setFileOriginName("");
		// content1.setUploadTime("");
		// content1.setUploadUserName("testHee");

		Contents content2 = new Contents("2");
		content2.setContentsSize(80451275);
		content2.setContentsType(Contents.TYPE_FILE);
		content2.setFileOriginName("taeyeon.mp3");
		content2.setUploadTime("");
		content2.setUploadUserName("testHee");

		Contents content3 = new Contents("3");
		content3.setContentsSize(387);
		content3.setContentsType(Contents.TYPE_FILE);
		content3.setFileOriginName("bbbb.jpeg");
		content3.setUploadTime("");
		content3.setUploadUserName("testHee");

		// test) 나의 History
		History myhistory = new History("doyyyy");
		myhistory.addContents(content1);
		myhistory.addContents(content2);
		myhistory.addContents(content3);

		// 요청할 데이터의 고유키 값
		String downloadDataPK = "1";

		try {
			downloader.requestDataDownload(downloadDataPK, myhistory);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
