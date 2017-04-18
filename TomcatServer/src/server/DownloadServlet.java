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

		// 서버에서 groupPK로 해당 history에서 downloadDataPK인 Contents를 찾는다.
		String contentsType = "";

		// 해당 downloadDataPK의 Contents타입을 client에 알림
		// response.setHeader("contentsType", "");
		
		
		
		

		// 해당 downloadDataPK의 Contents타입에 따라 다르게 처리(Set response Headers)
		switch (contentsType) {
		case "STRING":
			String stringData = "";

			response.setHeader("Content-Disposition", "form-data; name=stringData" + "\"" + LINE_FEED);
			response.setContentType("text/plain; charset=UTF-8");

			sendStringData(stringData);

			break;
		case "IMAGE":
			String imageFileName = "";
			// dir에 있는 image file을 가져온다. (ByteArrayStream)

			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + imageFileName);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			sendCapturedImageData(imageFileName);

			break;
		case "FILE":
			String fileName = "";
			// dir에 있는 file을 가져온다. (FileStream)

			// response.setContentType("multipart/mixed");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);
			response.setHeader("Content-Transfer-Encoding", "binary" + "\"" + LINE_FEED);

			// file 전송
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

	/** String Data 전송 */
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

	/** Captured Image Data를 전송 */
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

	/** File Data를 전송 */
	public void sendFileData(String fileName) {
		//
		// try {
		// MultipartUtility multipart = new MultipartUtility(SERVER_URL +
		// SERVER_SERVLET, charset);
		// setCommonParameter(multipart);
		//
		// // Iterator 통한 전체 조회
		// Iterator iterator = fileFullPathList.iterator();
		//
		// // 여러 파일을 순서대로 처리
		// while (iterator.hasNext()) {
		// String fileFullPath = (String) iterator.next();
		//
		// System.out.println("fileFullPathList: " + fileFullPath);
		// System.out.println();
		//
		// // 업로드할 파일 생성
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

	/** 여러 File Data를 전송 */
	public void sendMultipartData(ArrayList<String> fileFullPathList) {
		//
		// try {
		// MultipartUtility multipart = new MultipartUtility(SERVER_URL +
		// SERVER_SERVLET, charset);
		// setCommonParameter(multipart);
		//
		// // Iterator 통한 전체 조회
		// Iterator iterator = fileFullPathList.iterator();
		//
		// // 여러 파일을 순서대로 처리
		// while (iterator.hasNext()) {
		// String fileFullPath = (String) iterator.next();
		//
		// System.out.println("fileFullPathList: " + fileFullPath);
		// System.out.println();
		//
		// // 업로드할 파일 생성
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

	/** request Msg 출력 */
	public void requestMsgLog(HttpServletRequest request) {

		/* server가 받은 request 시작줄 정보 */
		System.out.println("==================STARTLINE==================");
		System.out.println("Request Method: " + request.getMethod());
		System.out.println("Request RequestURI: " + request.getRequestURI());
		System.out.println("Request Protocol: " + request.getProtocol());

		/* server가 받은 request 헤더 정보 */
		/* server가 받은 기본적인 request header msg 정보 */
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

	/** Client로 response Msg 전달 */
	public void responseMsgLog(HttpServletResponse response) {
		PrintWriter writer;
		try {
			writer = response.getWriter();

			response.setContentType("text/html");
			// response.setCharacterEncoding("UTF-8");

			writer.println("Http Post Response: " + response.toString());

			/* client가 받은 response 시작줄 정보 */
			writer.println("==================STARTLINE==================");
			writer.println("Response Status: " + response.getStatus());
			writer.println("Response ContentType: " + response.getContentType());

			/* client가 받은 response 헤더 정보 */
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
