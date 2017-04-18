package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 */
public class UploadData {
	public final static String SERVER_URL = "http://localhost:8080/TomcatServer";
	public final static String SERVER_SERVLET = "/UploadServlet";
	private String charset = "UTF-8";
	
	private String userEmail = null;
	private String groupPK = null;

	/** ������ userEmail�� groupPK�� �����Ѵ�. */
	public UploadData(String userEmail, String groupPK) {
		this.userEmail = userEmail;
		this.groupPK = groupPK;
	}

	/** String Data�� ���ε� */
	public void uploadStringData(String stringData) {
		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			setCommonParameter(multipart);
			
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
			setCommonParameter(multipart);

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

	/** ���� File Data�� ���ε�
	 * 
	 * @param dir ���ε��� ������ ��ġ
	 * @param dir ���ε��� ���ϸ�
	 */
	public void uploadMultipartData(ArrayList<String> fileFullPathList) {

		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			setCommonParameter(multipart);

			// Iterator ���� ��ü ��ȸ
			Iterator iterator = fileFullPathList.iterator();

			// ���� ������ ������� ó��
			while (iterator.hasNext()) {
				String fileFullPath = (String) iterator.next();

				System.out.println("fileFullPathList: " + fileFullPath);
				System.out.println();

				// ���ε��� ���� ����
				File uploadFile = new File(fileFullPath);

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
	
	/** ��� Data���� �������� �����ؾ��ϴ� Parameter
	 * userEmail, groupPK, uploadTime */
	public void setCommonParameter(MultipartUtility multipart){
		multipart.addHeaderField("User-Agent", "Heeee");
		multipart.addFormField("userEmail", userEmail);
		multipart.addFormField("groupPK", groupPK);
		multipart.addFormField("uploadTime", uploadTime());
	}
	
	/** @return YYYY-MM-DD HH:MM:SS ������ ���� �ð� */
	public String uploadTime() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		
		String date = Integer.toString(cal.get(Calendar.DATE));
		String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		if(Integer.parseInt(hour) < 10) {
			hour = "0" + hour;
		}
		if(Integer.parseInt(hour) > 12) {
			hour = "���� " + Integer.toString(Integer.parseInt(hour)-12);
		}
		else {
			hour = "���� " + hour;
		}
		
		String minute = Integer.toString(cal.get(Calendar.MINUTE));
		if(Integer.parseInt(minute) < 10) {
			minute = "0" + minute;
		}
		String sec = Integer.toString(cal.get(Calendar.SECOND));
		if(Integer.parseInt(sec) < 10) {
			sec = "0" + sec;
		}

		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + sec;
	}
}
