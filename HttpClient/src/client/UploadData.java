package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 */
public class UploadData {
	public final static String SERVER_URL = "http://localhost:8080/TomcatServer";
	public final static String SERVER_SERVLET = "/UploadServlet";
	private String charset = "UTF-8";

	public UploadData() {

	}

	/** String Data를 업로드 */
	public void uploadStringData(String stringData) {
		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			multipart.addHeaderField("User-Agent", "Heeee");

			// String example
			multipart.addFormField("stringData", stringData);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED");
			// responseMsgLog();

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/** Clipboard에 있는 Captured Image Data를 업로드 */
	public void uploadCapturedImageData(Image capturedImageData) {
		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			multipart.addHeaderField("User-Agent", "Heeee");

			// CapturedImage example
			System.out.println("<uploadCapturedImageData> getWidth: " + capturedImageData.getWidth(null));
			System.out.println("<uploadCapturedImageData> getHeight: " + capturedImageData.getHeight(null));
			multipart.addImagePart("imageData", capturedImageData);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED");
			// responseMsgLog();

			for (String line : response) {
				System.out.println(line);
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * 여러 File Data를 업로드
	 * 
	 * @param dir 업로드할 파일의 위치
	 * @param dir 업로드할 파일명
	 */
	public void uploadMultipartData(ArrayList<String> localDirList, ArrayList<String> fileNameList) {

		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			multipart.addHeaderField("User-Agent", "Heeee");
			
			// Iterator 통한 전체 조회
			Iterator iterator = localDirList.iterator();

			// 여러 파일을 순서대로 처리
			while (iterator.hasNext()) {
				String localDir = (String) iterator.next();
				int order = localDirList.indexOf(localDir);
				String fileName = (String) fileNameList.get(order);
				
				System.out.println("i: " + order);
				System.out.println("localDir: " + localDir + ", fileName: " + fileName);
				System.out.println();
				
				// 업로드할 파일 생성
				File uploadFile = new File(localDir + fileName);

				/* uploadFilename is the name of the sequence input variable in the called project the value is the name that will be given to the file */
				multipart.addFilePart("multipartFileData", uploadFile);
			}

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED");
			// responseMsgLog();

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
