package client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import model.Contents;
import model.History;

public class DownloadData {
	// 다운로드 파일을 임시로 저장할 위치
	private final String DOWNLOAD_LOCATION = "C:\\Users\\Administrator\\Desktop\\Clipcon";

	public final static String SERVER_URL = "http://localhost:8080/TomcatServer";
	public final static String SERVER_SERVLET = "/DownloadServlet";

	// private static final int CHUNKSIZE = 4096;
	// private static final String LINE_FEED = "\r\n";
	// private final String boundary = "===" + System.currentTimeMillis() + "===";
	// private OutputStream outputStream;
	// private PrintWriter writer;

	private final String charset = "UTF-8";
	private HttpURLConnection httpConn;

	private String userEmail = null;
	private String groupPK = null;
	private String downloadDataPKName; // 다운로드할 Data의 고유키
	private Contents requestContents; // 다운로드할 Data 정보

	private History history; // 내가 속한 History

	/** 생성자 userEmail과 groupPK를 설정한다. */
	public DownloadData(String userEmail, String groupPK) {
		this.userEmail = userEmail;
		this.groupPK = groupPK;
	}

	/** 다운로드하기 원하는 Data를 request 
	 * 복수 선택은 File Data의 경우만 가능(추후 개선) */
	public void requestDataDownload(String downloadDataPK) throws MalformedURLException {
		createFileReceiveFolder(DOWNLOAD_LOCATION);

		requestContents = history.getContentsByPK(downloadDataPK); // 내가 속한 history에서 고유키에 해당하는 Contents 가져오기

		// GET방식으로 보낼 parameter
		String getParameters = "userEmail=" + userEmail + "&" + "groupPK=" + groupPK + "&" + "downloadData=" + downloadDataPKName;
		// 다운로드받을 데이터의 Type
		String contentsType = requestContents.getContentsType();

		try {
			URL url = new URL(SERVER_URL + SERVER_SERVLET + "?" + getParameters);

			httpConn = (HttpURLConnection) url.openConnection();

			httpConn.setRequestMethod("GET");
			httpConn.setUseCaches(false);
			httpConn.setDoOutput(false); // indicates GET method
			httpConn.setDoInput(true);

			// checks server's status code first
			int status = httpConn.getResponseCode();
			List<String> response = new ArrayList<String>(); // Server의 응답내용

			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					response.add(line);
				}
				reader.close();
				httpConn.disconnect();
			} else {
				throw new IOException("Server returned non-OK status: " + status);
			}

			/* response에서 contentsType 받아와야 함 */
//			String contentsType = httpConn.getHeaderField("contentsType");

			switch (contentsType) {
			case "STRING":
				// response body에 넣은 String 객체를 받아온다.
				String stringData = downloadStringData(httpConn.getInputStream());
				System.out.println("stringData 결과: " + stringData);

				break;
			case "IMAGE":
				// response body에 넣은 Image 객체를 받아온다.
				Image imageData = downloadCapturedImageData(httpConn.getInputStream());
				System.out.println("ImageData 결과: " + imageData.toString());

				break;
			case "FILE":
				String fileOriginName = requestContents.getFileOriginName();
				System.out.println("fileOriginName 결과: " + fileOriginName);

				/* Clipcon 폴더에 실제 File(파일명: 원본 파일명) 저장 */
				downloadMultipartData(httpConn.getInputStream(), fileOriginName);
				break;

			default:
				System.out.println("어떤 형식에도 속하지 않음.");
			}
			System.out.println();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** String Data를 다운로드 */
	private String downloadStringData(InputStream inputStream) {
		BufferedReader bufferedReader;
		StringBuilder stringBuilder = null;

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));

			stringBuilder = new StringBuilder();
			String line = null;

			try {
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line + "\n");
				}
				inputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/** Captured Image Data를 다운로드 
	 * file 형태의 Image Data를 전송받아 Image로 변경 */
	private Image downloadCapturedImageData(InputStream inputStream) {
		byte[] imageInByte = null;
		BufferedImage bImageFromConvert = null;

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF]; // 65536

			for (int len; (len = inputStream.read(buffer)) != -1;)
				byteArrayOutputStream.write(buffer, 0, len);

			byteArrayOutputStream.flush();
			imageInByte = byteArrayOutputStream.toByteArray();

			inputStream.close();

			// convert byte array back to BufferedImage
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageInByte);
			bImageFromConvert = ImageIO.read(byteArrayInputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
		Image ImageData = (Image) bImageFromConvert;

		return ImageData;
	}

	/** 여러 File Data를 다운로드 */
	private void downloadMultipartData(InputStream inputStream, String fileName) throws FileNotFoundException {
		// opens input stream from the HTTP connection
		// InputStream inputStream = httpConn.getInputStream();
		String saveFileFullPath = DOWNLOAD_LOCATION + "\\" + fileName;

		try {
			// opens an output stream to save into file
			FileOutputStream fileOutputStream = new FileOutputStream(saveFileFullPath);

			int bytesRead = -1;
			byte[] buffer = new byte[0xFFFF];

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 프로그램 실행할 때로 옮겨야 함. */
	/** 다운로드한 파일을 저장할 임시 폴더 생성 */
	private void createFileReceiveFolder(String saveFilePath) {
		// 다운로드한 파일을 저장할 폴더
		File downFolder;

		downFolder = new File(saveFilePath);

		// 폴더가 존재하지 않으면
		if (!downFolder.exists()) {
			downFolder.mkdir(); // 폴더 생성
			System.out.println("------------------" + saveFilePath + " 폴더 생성");
		}
	}
}
