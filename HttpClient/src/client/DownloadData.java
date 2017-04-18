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
	// �ٿ�ε� ������ �ӽ÷� ������ ��ġ
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
	private String downloadDataPKName; // �ٿ�ε��� Data�� ����Ű
	private Contents requestContents; // �ٿ�ε��� Data ����

	private History history; // ���� ���� History

	/** ������ userEmail�� groupPK�� �����Ѵ�. */
	public DownloadData(String userEmail, String groupPK) {
		this.userEmail = userEmail;
		this.groupPK = groupPK;
	}

	/** �ٿ�ε��ϱ� ���ϴ� Data�� request 
	 * ���� ������ File Data�� ��츸 ����(���� ����) */
	public void requestDataDownload(String downloadDataPK) throws MalformedURLException {
		createFileReceiveFolder(DOWNLOAD_LOCATION);

		requestContents = history.getContentsByPK(downloadDataPK); // ���� ���� history���� ����Ű�� �ش��ϴ� Contents ��������

		// GET������� ���� parameter
		String getParameters = "userEmail=" + userEmail + "&" + "groupPK=" + groupPK + "&" + "downloadData=" + downloadDataPKName;
		// �ٿ�ε���� �������� Type
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
			List<String> response = new ArrayList<String>(); // Server�� ���䳻��

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

			/* response���� contentsType �޾ƿ;� �� */
//			String contentsType = httpConn.getHeaderField("contentsType");

			switch (contentsType) {
			case "STRING":
				// response body�� ���� String ��ü�� �޾ƿ´�.
				String stringData = downloadStringData(httpConn.getInputStream());
				System.out.println("stringData ���: " + stringData);

				break;
			case "IMAGE":
				// response body�� ���� Image ��ü�� �޾ƿ´�.
				Image imageData = downloadCapturedImageData(httpConn.getInputStream());
				System.out.println("ImageData ���: " + imageData.toString());

				break;
			case "FILE":
				String fileOriginName = requestContents.getFileOriginName();
				System.out.println("fileOriginName ���: " + fileOriginName);

				/* Clipcon ������ ���� File(���ϸ�: ���� ���ϸ�) ���� */
				downloadMultipartData(httpConn.getInputStream(), fileOriginName);
				break;

			default:
				System.out.println("� ���Ŀ��� ������ ����.");
			}
			System.out.println();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** String Data�� �ٿ�ε� */
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

	/** Captured Image Data�� �ٿ�ε� 
	 * file ������ Image Data�� ���۹޾� Image�� ���� */
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

	/** ���� File Data�� �ٿ�ε� */
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

	/* ���α׷� ������ ���� �Űܾ� ��. */
	/** �ٿ�ε��� ������ ������ �ӽ� ���� ���� */
	private void createFileReceiveFolder(String saveFilePath) {
		// �ٿ�ε��� ������ ������ ����
		File downFolder;

		downFolder = new File(saveFilePath);

		// ������ �������� ������
		if (!downFolder.exists()) {
			downFolder.mkdir(); // ���� ����
			System.out.println("------------------" + saveFilePath + " ���� ����");
		}
	}
}
