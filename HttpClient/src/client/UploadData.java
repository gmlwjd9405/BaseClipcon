package client;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 */
public class UploadData {
	public final static String SERVER_URL = "http://localhost:8080/TomcatServer";
	public final static String SERVER_SERVLET = "/UploadServlet";
	private String charset = "UTF-8";

	private String userEmail = null;
	private String groupPK = null;
	private String dirName = null;

	/** 생성자 userEmail과 groupPK를 설정한다. */
	public UploadData(String userEmail, String groupPK) {
		this.userEmail = userEmail;
		this.groupPK = groupPK;
	}

	/** String Data를 업로드 */
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

	/** Clipboard에 있는 Captured Image Data를 업로드 */
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

	/** 여러 File Data를 업로드
	 * 
	 * @param dir 업로드할 파일의 위치
	 * @param dir 업로드할 파일명
	 */
	public void uploadMultipartData(Map<String, String> fileFullPathList) {
		try {
			MultipartUtility multipart = new MultipartUtility(SERVER_URL + SERVER_SERVLET, charset);
			setCommonParameter(multipart);

			// Iterator 통한 전체 조회
			Iterator iterator = fileFullPathList.keySet().iterator();

			/* 1. 하나의 보낼 FileData가 1개, Raw Data 형태 -> ismultipartData == FALSE */
			if (fileFullPathList.size() == 1 && fileFullPathList.get((String) iterator.next()) == null) {
				multipart.addFormField("ismultipartData", "FALSE");
			} 
			/* 2. 여러 개의 FileData를 복사한 경우
			 * 3. Folder가 하나라도 포함되어 있는 경우 */
			else {
				multipart.addFormField("ismultipartData", "TRUE");
			}

			// 여러 파일을 순서대로 처리
			while (iterator.hasNext()) {
				String fileFullPath = (String) iterator.next();
				String folderNameContainingFile = fileFullPathList.get(fileFullPath);

				System.out.println("fileFullPath: " + fileFullPath + ", folderNameContainingFile: " + folderNameContainingFile);

				// 업로드할 파일 생성
				File uploadFile = new File(fileFullPath);

				/* uploadFilename is the name of the sequence input variable in the called project the value is the name that will be given to the file */
				multipart.addFilePart("multipartFileData", uploadFile, folderNameContainingFile);
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

	/** 모든 Data에서 공통으로 설정해야하는 Parameter
	 * userEmail, groupPK, uploadTime */
	public void setCommonParameter(MultipartUtility multipart) {
		multipart.addHeaderField("User-Agent", "Heeee");
		multipart.addFormField("userEmail", userEmail);
		multipart.addFormField("groupPK", groupPK);
		multipart.addFormField("uploadTime", uploadTime());
	}

	/** @return YYYY-MM-DD_HH:MM:SS 형식의 현재 시간 */
	public String uploadTime() {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);

		String date = Integer.toString(cal.get(Calendar.DATE));
		String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		if (Integer.parseInt(hour) < 10) {
			hour = "0" + hour;
		}
		if (Integer.parseInt(hour) > 12) {
			hour = "PM " + Integer.toString(Integer.parseInt(hour) - 12);
		} else {
			hour = "AM " + hour;
		}

		String minute = Integer.toString(cal.get(Calendar.MINUTE));
		if (Integer.parseInt(minute) < 10) {
			minute = "0" + minute;
		}
		String sec = Integer.toString(cal.get(Calendar.SECOND));
		if (Integer.parseInt(sec) < 10) {
			sec = "0" + sec;
		}

		return year + "-" + month + "-" + date + "_" + hour + ":" + minute + ":" + sec;
	}
}
