package client;

import java.net.MalformedURLException;

public class testDownload {
	public static DownloadData downloader = new DownloadData("gmlwjd9405@naver.com", "doyyyy");

	public static void main(String[] args) {
		// 데이터의 고유키 값
		String downloadDataPK = "123";

		try {
			downloader.requestDataDownload(downloadDataPK);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
