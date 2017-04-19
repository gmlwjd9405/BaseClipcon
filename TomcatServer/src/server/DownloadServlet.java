package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ������ ����Ǿ��ִ� root location
	private final String ROOT_LOCATION = "C:\\Users\\Administrator\\Desktop\\";

	private static final int CHUNKSIZE = 4096;
	private static final String LINE_FEED = "\r\n";
	private String charset = "UTF-8";

	private String userEmail = null;
	private String groupPK = null;
	private String downloadDataPK = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestMsgLog(request);

		// get Request Data
		userEmail = request.getParameter("userEmail");
		groupPK = request.getParameter("groupPK");
		downloadDataPK = request.getParameter("downloadDataPK");
		System.out.println("<Parameter> userEmail: " + userEmail + ", groupPK: " + groupPK + ", downloadDataPK: "
				+ downloadDataPK);
		System.out.println();

		// �������� groupPK�� �ش� history���� downloadDataPK�� Contents�� ã�´�.
		/* test�� ���� setting */
		Contents testcontent = new Contents();

		/* File�� ��� */
		// testcontent.setContentsPKName("2");
		// testcontent.setContentsSize(80451275);
		// testcontent.setContentsType(Contents.TYPE_FILE);
		// testcontent.setContentsValue("taeyeon.mp3");
		// testcontent.setUploadTime("2017-4-19 ��¥ 10:19:34");
		// testcontent.setUploadUserName("gmlwjd9405@naver.com");

		// testcontent.setContentsPKName("3");
		// testcontent.setContentsSize(387);
		// testcontent.setContentsType(Contents.TYPE_FILE);
		// testcontent.setContentsValue("bbbb.jpeg");
		// testcontent.setUploadTime("2017-4-19 ��¥ 10:19:34");
		// testcontent.setUploadUserName("gmlwjd9405@naver.com");

		/* String�� ��� */
		testcontent.setContentsPKName("1");
		testcontent.setContentsSize(45);
		testcontent.setContentsType(Contents.TYPE_STRING);
		testcontent.setContentsValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		testcontent.setUploadTime("2017-4-19 ��¥ 10:53:06");
		testcontent.setUploadUserName("gmlwjd9405@naver.com");

		/* Image�� ��� */
		// testcontent.setContentsPKName("1");
		// testcontent.setContentsSize(4733);
		// testcontent.setContentsType(Contents.TYPE_IMAGE);
		// testcontent.setContentsValue("");
		// testcontent.setUploadTime("2017-4-19 ��¥ 11:06:04");
		// testcontent.setUploadUserName("gmlwjd9405@naver.com");

		String contentsType = testcontent.getContentsType();

		// �ش� downloadDataPK�� ContentsŸ���� client�� �˸�
		// response.setHeader("contentsType", "");

		// �ش� downloadDataPK�� ContentsŸ�Կ� ���� �ٸ��� ó��(Set response Headers)
		switch (contentsType) {
		case "STRING":
			String stringData = testcontent.getContentsValue();

			response.setHeader("Content-Disposition", "form-data; name=stringData" + "\"" + LINE_FEED);
			response.setContentType("text/plain; charset=UTF-8");

			sendStringData(stringData, response.getOutputStream());

			break;
		case "IMAGE":
			String imageFileName = testcontent.getContentsPKName();

			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + imageFileName + LINE_FEED);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			// dir�� �ִ� image file�� ������ ����. (ByteArrayStream)
			sendFileData(imageFileName, response.getOutputStream());

			break;
		case "FILE":
			String fileName = testcontent.getContentsPKName();

			// response.setContentType("multipart/mixed");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + LINE_FEED);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			// dir�� �ִ� file�� ������ ����. (FileStream)
			sendFileData(fileName, response.getOutputStream());

			break;
		default:
			System.out.println("� ���Ŀ��� ������ ����.");
		}

		// responseMsgLog(response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
	}

	/** String Data ���� */
	public void sendStringData(String stringData, OutputStream outputStream) {
		try {

			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

			writer.append(stringData).append(LINE_FEED);
			writer.flush();
			writer.close();

			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Captured Image Data, File Data�� ���� */
	public void sendFileData(String fileName, OutputStream outputStream) {
		// ���� file data�� ��������
		File sendFileContents = new File(ROOT_LOCATION + groupPK + "\\" + fileName);

		try {
			FileInputStream inputStream = new FileInputStream(sendFileContents);
			byte[] buffer = new byte[CHUNKSIZE];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** ���� File Data�� ����(�ڷ� ���� �ʿ�) */
	public void sendMultipartData(ArrayList<String> fileFullPathList) {
		//
		// try {
		// MultipartUtility multipart = new MultipartUtility(SERVER_URL +
		// SERVER_SERVLET, charset);
		// setCommonParameter(multipart);
		//
		// // Iterator ���� ��ü ��ȸ
		// Iterator iterator = fileFullPathList.iterator();
		//
		// // ���� ������ ������� ó��
		// while (iterator.hasNext()) {
		// String fileFullPath = (String) iterator.next();
		//
		// System.out.println("fileFullPathList: " + fileFullPath);
		// System.out.println();
		//
		// // ���ε��� ���� ����
		// File uploadFile = new File(fileFullPath);
		//
		// /* uploadFilename is the name of the sequence input variable in the
		// called project the value is the name that will be given to the file
		// */
		// multipart.addFilePart("multipartFileData", uploadFile);
		// }
		//
		// List<String> response = multipart.finish();
		// System.out.println("SERVER REPLIED");
		// // responseMsgLog();
		//
		// for (String line : response) {
		// System.out.println(line);
		// }
		// } catch (IOException ex) {
		// System.err.println(ex);
		// }
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
