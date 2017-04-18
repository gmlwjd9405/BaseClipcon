package server;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

	private static final String LINE_FEED = "\r\n";

	private String userEmail = null;
	private String groupPK = null;
	private String downloadData = null;

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

		// get Request Data
		userEmail = request.getParameter("userEmail");
		groupPK = request.getParameter("groupPK");
		downloadData = request.getParameter("downloadData");

		// �������� groupPK�� �ش� history���� downloadDataPK�� Contents�� ã�´�.
		String contentsType = "";

		// �ش� downloadDataPK�� ContentsŸ���� client�� �˸�
		// response.setHeader("contentsType", "");
		
		
		
		

		// �ش� downloadDataPK�� ContentsŸ�Կ� ���� �ٸ��� ó��(Set response Headers)
		switch (contentsType) {
		case "STRING":
			String stringData = "";

			response.setHeader("Content-Disposition", "form-data; name=stringData" + "\"" + LINE_FEED);
			response.setContentType("text/plain; charset=UTF-8");

			sendStringData(stringData);

			break;
		case "IMAGE":
			String imageFileName = "";
			// dir�� �ִ� image file�� �����´�. (ByteArrayStream)

			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + imageFileName);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			sendCapturedImageData(imageFileName);

			break;
		case "FILE":
			String fileName = "";
			// dir�� �ִ� file�� �����´�. (FileStream)

			// response.setContentType("multipart/mixed");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			// file ����
			sendFileData(fileName);

			break;
		default:
			break;
		}
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
	public void sendStringData(String stringData) {
		// try {
		// "Content-Type: text/plain; charset=" + charset
		// MultipartUtility multipart = new MultipartUtility(SERVER_URL +
		// SERVER_SERVLET, charset);
		// setCommonParameter(multipart);
		//
		// multipart.addFormField("stringData", stringData);
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

	/** Captured Image Data�� ���� */
	public void sendCapturedImageData(String imageFileName) {
		// try {
		// MultipartUtility multipart = new MultipartUtility(SERVER_URL +
		// SERVER_SERVLET, charset);
		// setCommonParameter(multipart);
		//
		// System.out.println("<uploadCapturedImageData> getWidth: " +
		// capturedImageData.getWidth(null));
		// System.out.println("<uploadCapturedImageData> getHeight: " +
		// capturedImageData.getHeight(null));
		// multipart.addImagePart("imageData", capturedImageData);
		//
		// List<String> response = multipart.finish();
		// System.out.println("SERVER REPLIED");
		// // responseMsgLog();
		//
		// for (String line : response) {
		// System.out.println(line);
		// }
		//
		// } catch (IOException ex) {
		// System.err.println(ex);
		// }
	}

	/** File Data�� ���� */
	public void sendFileData(String fileName) {
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

	/** ���� File Data�� ���� */
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
