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

	/** String Data�� ���ε� */
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

	/** Clipboard�� �ִ� Captured Image Data�� ���ε� */
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
	 * ���� File Data�� ���ε�
	 * 
	 * @param dir ���ε��� ������ ��ġ
	 * @param dir ���ε��� ���ϸ�
	 */
	public void uploadMultipartData(ArrayList<String> localDirList, ArrayList<String> fileNameList) {

		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			multipart.addHeaderField("User-Agent", "Heeee");
			
			// Iterator ���� ��ü ��ȸ
			Iterator iterator = localDirList.iterator();

			// ���� ������ ������� ó��
			while (iterator.hasNext()) {
				String localDir = (String) iterator.next();
				int order = localDirList.indexOf(localDir);
				String fileName = (String) fileNameList.get(order);
				
				System.out.println("i: " + order);
				System.out.println("localDir: " + localDir + ", fileName: " + fileName);
				System.out.println();
				
				// ���ε��� ���� ����
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
