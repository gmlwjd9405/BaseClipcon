package client;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 *
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader3 {
	public final static String SERVER_URL = "http://localhost:8080/TomcatServer";

	public MultipartFileUploader3() {

	}

	// public void uploadStringData(String stringData) {
	// final String SERVER_SERVLET = "/StringUploadServlet";
	// String charset = "UTF-8";
	// HttpURLConnection httpConn;
	// OutputStream outputStream;
	// PrintWriter writer;
	// List<String> response = new ArrayList<String>();
	//
	// URL url;
	//
	// try {
	// url = new URL(SERVER_URL);
	// httpConn = (HttpURLConnection) url.openConnection();
	// httpConn.setRequestMethod("POST");
	// httpConn.setDoOutput(true); // indicates POST method
	// httpConn.setDoInput(true);
	//
	// // 1. Request Msg 보냄.
	// httpConn.setRequestProperty("Content-Type", "text/plain");
	// httpConn.setRequestProperty("User-Agent", "Heeee");
	//
	// // 2. String data 보냄.
	// outputStream = httpConn.getOutputStream();
	// outputStream.write(stringData.getBytes());
	// outputStream.close();
	//
	// // 3. Response 받아야 함.
	// int status = httpConn.getResponseCode();
	// System.out.println("ResponseMessage: " + httpConn.getResponseMessage());
	//
	// if (status == HttpURLConnection.HTTP_OK) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// response.add(line);
	// }
	// reader.close();
	// httpConn.disconnect();
	// } else {
	// throw new IOException("Server returned non-OK status: " + status);
	// }
	//
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public void uploadCapturedImageData(Image capturedImage) {
	// final String SERVER_SERVLET = "/StringUploadServlet";
	// final String boundary = "===" + System.currentTimeMillis() + "===";
	// final String LINE_FEED = "\r\n";
	// final String imageData = "imageData";
	//
	// String charset = "UTF-8";
	// HttpURLConnection httpConn;
	// OutputStream outputStream;
	// PrintWriter writer;
	// List<String> response = new ArrayList<String>();
	//
	// URL url;
	//
	// try {
	// url = new URL(SERVER_URL + SERVER_SERVLET);
	//
	// httpConn = (HttpURLConnection) url.openConnection();
	// httpConn.setRequestMethod("POST");
	// httpConn.setUseCaches(false);
	// httpConn.setDoOutput(true); // indicates POST method
	// httpConn.setDoInput(true);
	//
	// // 1. Request Msg 보냄.
	// httpConn.setRequestProperty("User-Agent", "Heeee");
	// try {
	//
	// // 2. field data 보냄.
	// outputStream = httpConn.getOutputStream();
	// writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
	//
	// writer.append("--" + boundary).append(LINE_FEED);
	// writer.append("Content-Disposition: form-data; name=\"" + imageData + "\"").append(LINE_FEED);
	// writer.append("Content-Type: image/jpeg; charset=" + charset).append(LINE_FEED);
	// writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
	// writer.append(LINE_FEED);
	//// writer.append(value).append(LINE_FEED);
	// writer.flush();
	//
	// writer.append("--" + boundary).append(LINE_FEED);
	// writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
	// writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
	// writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
	// writer.append(LINE_FEED);
	// writer.flush();
	//
	// // 3. 이미지 data array 전송
	// BufferedImage originalImage = (BufferedImage) capturedImage;
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// ImageIO.write(originalImage, "jpg", baos);
	// baos.flush();
	// byte[] imageInByte = baos.toByteArray();
	// baos.close();
	//
	// outputStream.write(imageInByte);
	// outputStream.flush();
	//
	// writer.append(LINE_FEED);
	// writer.flush();
	//
	// } catch (IOException e) {
	// System.out.println(e.getMessage());
	// }
	//
	// // 3. Response 받아야 함.
	// int status = httpConn.getResponseCode();
	// System.out.println("ResponseMessage: " + httpConn.getResponseMessage());
	//
	// if (status == HttpURLConnection.HTTP_OK) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// response.add(line);
	// }
	// reader.close();
	// httpConn.disconnect();
	// } else {
	// throw new IOException("Server returned non-OK status: " + status);
	// }
	//
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * 여러 데이터가 있으므로 배열로 변경
	 * 
	 * @param dir 업로드할 파일의 위치
	 * @param dir 업로드할 파일명
	 */
	public void uploadMultipartData(String localDir, String fileName) {
		final String SERVER_SERVLET = "/UploadServlet";
		String charset = "UTF-8";

		String stringData = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image capturedImage = toolkit.getImage("aaaa.jpeg");
		System.out.println("capturedImage: " + capturedImage.toString());
		ImageIcon capturedImageIcon = new ImageIcon(capturedImage);
		System.out.println(capturedImage.toString());

		// 업로드할 파일 생성
		File uploadFile = new File(localDir + fileName);

		try {
			MultipartUtility2 multipart = new MultipartUtility2(SERVER_URL + SERVER_SERVLET, charset);
			multipart.addHeaderField("User-Agent", "Heeee");

			/**
			 * __sequence contains the name of the called sequence within the
			 * specified project (in the requestURL)
			 */
			// multipart.addFormField("__sequence", "uploadFile");
			// multipart.addFormField("uploadFilename", fileName);
			/**
			 * uploadFilename is the name of the sequence input variable in the
			 * called project the value is the name that will be given to the
			 * file
			 */
			// multipart.addFilePart("uploadFilename", uploadFile);

			// String example
			// multipart.addFormField("stringData", stringData);

			// CapturedImage example
			System.out.println("getWidth: " + capturedImageIcon.getIconWidth());
			System.out.println("getHeight: " + capturedImageIcon.getIconHeight());
			multipart.addImagePart("imageData", capturedImage);
			
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
