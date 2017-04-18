package server;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;

/* maxFileSize: �ִ� ���� ũ��(100MB)
 * fileSizeThreshold: 1MB ������ ������ �޸𸮿��� �ٷ� ���
 * maxRequestSize:  */

//@MultipartConfig(location = "C:\\Users\\Administrator\\Desktop\\heeuploads", maxFileSize = 1024 * 1024
//		* 100, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 100)
@MultipartConfig(maxFileSize = 1024 * 1024 * 100, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 100)
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	// ���ε� ������ ������ ��ġ
	private final String RECEIVE_LOCATION = "C:\\Users\\Administrator\\Desktop\\";
	// ���ε��� ������ ������ ����
	private File receiveFolder;

	private String userEmail = null;
	private String groupPK = null;
	private String uploadTime = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		requestMsgLog(request);

		userEmail = request.getParameter("userEmail");
		groupPK = request.getParameter("groupPK");
		uploadTime = request.getParameter("uploadTime");
		System.out.println("userEmail: " + userEmail + ", groupPK: " + groupPK + ", uploadTime: " + uploadTime);
		System.out.println();

		// Part filePart = null;

		// ���� file���� ������
		for (Part part : request.getParts()) {

			String partName = part.getName();
			Contents uploadContents = new Contents();

			/*
			 * To find out file name, parse header value of content-disposition
			 * e.g. form-data; name="file"; filename=""
			 */
			System.out.println("<headerName: headerValue>");
			for (String headerName : part.getHeaderNames()) {
				System.out.println(headerName + ": " + part.getHeader(headerName));
			}

			System.out.println("...........>> " + partName);

			switch (partName) {
			case "stringData":
				String paramValue = getStringFromStream(part.getInputStream());
				System.out.println("paramValue: " + paramValue);

				setContentsInfo(uploadContents, Contents.TYPE_STRING, part.getSize(), paramValue);
				saveContentsToHidtory(uploadContents);

				break;
			case "imageData":
				Image imageData = getImageDataStream(part.getInputStream(), groupPK,
						uploadContents.getContentsPKName());
				System.out.println(imageData.toString());

				setContentsInfo(uploadContents, Contents.TYPE_IMAGE, part.getSize(), null);
				saveContentsToHidtory(uploadContents);

				break;
			case "multipartFileData":
				String fileName = getFilenameInHeader(part.getHeader("content-disposition"));
				System.out.println("fileName: " + fileName);

				setContentsInfo(uploadContents, Contents.TYPE_FILE, part.getSize(), fileName);
				saveContentsToHidtory(uploadContents);

				// filePart = part; // Absolute path doesn't work.
				// filePart.write(fileName);
				/* groupPK ������ ���� File(���ϸ�: ����Ű) ���� */
				getFileDatStream(part.getInputStream(), groupPK, uploadContents.getContentsPKName());
				break;
			default:
				System.out.println("� ���Ŀ��� ������ ����.");
			}
			System.out.println();
		}
		responseMsgLog(response);
	}

	/** String Data�� �����ϴ� Stream */
	public String getStringFromStream(InputStream stream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

	/** Image Data�� �����ϴ� Stream */
	public Image getImageDataStream(InputStream stream, String groupPK, String imagefileName) throws IOException {
		byte[] imageInByte;
		String saveFilePath = RECEIVE_LOCATION + groupPK; // ����ڰ� ���� �׷��� ������ ����

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF]; // 65536

			for (int len; (len = stream.read(buffer)) != -1;)
				byteArrayOutputStream.write(buffer, 0, len);

			byteArrayOutputStream.flush();

			imageInByte = byteArrayOutputStream.toByteArray();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// convert byte array back to BufferedImage
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageInByte);
		BufferedImage bImageFromConvert = ImageIO.read(byteArrayInputStream);

		// file ���·� ����
		ImageIO.write(bImageFromConvert, "jpg", new File(saveFilePath, imagefileName));

		// image ��ü�� ��ȯ
		Image ImageData = (Image) bImageFromConvert;
		return ImageData;
	}

	/** File Data�� �����ϴ� Stream */
	public void getFileDatStream(InputStream stream, String groupPK, String fileName) throws IOException {
		String saveFilePath = RECEIVE_LOCATION + groupPK; // ����ڰ� ���� �׷��� ������ ����

		String saveFileFullPath = saveFilePath + "\\" + fileName;

		createFileReceiveFolder(saveFilePath); // �׷� ���� ���� Ȯ��

		// opens an output stream to save into file
		FileOutputStream fileOutputStream = new FileOutputStream(saveFileFullPath);

		int bytesRead = -1;
		byte[] buffer = new byte[0xFFFF]; // 65536

		try {
			// input stream from the HTTP connection
			while ((bytesRead = stream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}
			fileOutputStream.flush();

		} finally {
			try {
				fileOutputStream.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Request Header "content-disposition"���� filename ���� */
	private String getFilenameInHeader(String requestHeader) {
		int beginIndex = requestHeader.indexOf("filename") + 10;
		int endIndex = requestHeader.length() - 1;

		String fileName = requestHeader.substring(beginIndex, endIndex);

		return fileName;
	}

	/** ���ε��� ������ ������ �׷� ���� ���� */
	private void createFileReceiveFolder(String saveFilePath) {
		receiveFolder = new File(saveFilePath);

		// C:\\Program Files�� LinKlipboard������ �������� ������
		if (!receiveFolder.exists()) {
			receiveFolder.mkdir(); // ���� ����
			System.out.println("------------------" + saveFilePath + " ���� ����");
		}
	}

	/** Contents�� ���� ���� Setting */
	private void setContentsInfo(Contents uploadContents, String contentsType, long contentsSize,
			String contentsValue) {
		uploadContents.setContentsType(contentsType);
		uploadContents.setContentsSize(contentsSize);
		uploadContents.setUploadUserName(userEmail);
		uploadContents.setUploadTime(uploadTime);
		uploadContents.setContentsValue(contentsValue);
	}

	/** �ش� �׷� history�� contents ���� */
	private void saveContentsToHidtory(Contents uploadContents) {
		// �ش� �׷��� history�� �����´�.

		// content�� �����Ѵ�.
	}

	/** Image�� Resizing�� ImageIcon���� return */
	public ImageIcon getResizingImageIcon(Image imageData) {
		imageData = imageData.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		ImageIcon resizingImageIcon = new ImageIcon(imageData);

		return resizingImageIcon;
	}

	/** request Msg ��� */
	public void requestMsgLog(HttpServletRequest request) {

		/* server�� ���� request ������ ���� */
		System.out.println("==================STARTLINE==================");
		System.out.println("Request Method: " + request.getMethod());
		System.out.println("Request RequestURI: " + request.getRequestURI());
		System.out.println("Request Protocol: " + request.getProtocol());

		/* server�� ���� request ��� ���� */
		/* server�� ���� �⺻���� request header msg ���� */
		System.out.println("===================HEADER====================");
		Enumeration headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();

			System.out.println(headerName + ": " + request.getHeader(headerName));
		}
		System.out.println("-------------------------------------------");
		System.out.println("Request LocalAddr: " + request.getLocalAddr());
		System.out.println("Request LocalName: " + request.getLocalName());
		System.out.println("Request LocalPort: " + request.getLocalPort());
		System.out.println("-------------------------------------------");
		System.out.println("Request RemoteAddr: " + request.getRemoteAddr());
		System.out.println("Request RemoteHost: " + request.getRemoteHost());
		System.out.println("Request RemotePort: " + request.getRemotePort());
		System.out.println("Request RemoteUser: " + request.getRemoteUser());

		System.out.println("==================ENTITY====================");
		System.out.println("userEmail: " + request.getParameter("userEmail"));
		System.out.println("groupPK: " + request.getParameter("groupPK"));
		System.out.println("downloadDataPK: " + request.getParameter("downloadDataPK"));
		System.out.println("===========================================");
		System.out.println();
		System.out.println();
	}

	/** Client�� response Msg ���� */
	public void responseMsgLog(HttpServletResponse response) {
		PrintWriter writer;
		try {
			writer = response.getWriter();

			response.setContentType("text/html");
			// response.setCharacterEncoding("UTF-8");

			writer.println("Http Post Response: " + response.toString());

			/* client�� ���� response ������ ���� */
			writer.println("==================STARTLINE==================");
			writer.println("Response Status: " + response.getStatus());
			writer.println("Response ContentType: " + response.getContentType());

			/* client�� ���� response ��� ���� */
			writer.println("==================HEADER=====================");
			Collection<String> headerNames = response.getHeaderNames();

			while (!headerNames.isEmpty()) {
				String headerName = (String) headerNames.toString();

				writer.println(headerName + ": " + response.getHeader(headerName));
			}

			writer.println("===================ENTITY====================");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
