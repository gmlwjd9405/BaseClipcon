package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/UploadServletUsingFileUpload")
@MultipartConfig
public class UploadServletUsingFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 업로드 파일을 저장할 위치
	private final String UPLOAD_DIRECTORY = "C:\\Users\\Administrator\\Desktop\\heeuploads";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServletUsingFileUpload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
		// response.setContentType("text/html");

		/* 1. */
		// requestMsgLog(request);
		//
		// boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// System.out.println("isMultipart: " + isMultipart);
		//
		// if (isMultipart) {
		// FileItemFactory factory = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// //업로드할 때의 기준값을 설정
		// factory.setSizeThreshold(1024);
		// upload.setSizeMax(1024);
		// //upload.setHeaderEncoding(encoding);
		//
		// try {
		// List items = upload.parseRequest(request);
		// System.out.println("items size: " + items.size());
		//
		// Iterator iterator = items.iterator();
		// FileItem item = (FileItem) iterator.next();
		//
		// if (!item.isFormField()) {
		// String fileName = item.getName();
		//
		// File path = new File(UPLOAD_DIRECTORY);
		// if (!path.exists()) {
		// boolean status = path.mkdirs();
		// }
		//
		// File uploadedFile = new File(path + "\\" + fileName);
		// item.write(uploadedFile);
		// }
		// } catch (Exception e) {
		// }
		// }

		/* 2. */
		requestMsgLog(request);

		PrintWriter out = response.getWriter();
		// multipart로 전송되었는가를 체크
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// multipart로 전송 되었을 경우
		if (isMultipart) {
			// 업로드 된 파일의 임시 저장 폴더를 설정
			File temporaryDir = new File("C:\\Users\\Administrator\\Desktop\\heeuploads");

			// 톰켓의 전체 경로를 가져오고 upload라는 폴더를 만들고 거기다 tmp의 폴더의 전송된 파일을 upload 폴더로
			// 카피 한다.
			// String realDir =
			// config.getServletContext().getRealPath("/upload/");

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 1MB 이하: 메모리에서 바로 사용, 1MB 이상: temporaryDir 경로 폴더로 이동
			factory.setSizeThreshold(1 * 1024 * 1024);
			factory.setRepository(temporaryDir);

			// 실제 구현단계 아님 설정단계였음. 최대 파일 크기(10MB)
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(10 * 1024 * 1024);

			try {
				// 실제 업로드 부분(이부분에서 파일이 생성된다)
				List<FileItem> items = upload.parseRequest(request);

				Iterator iter = items.iterator(); // Iterator 사용
				System.out.println("들어옴1");
				System.out.println("items size: " + items.size());

				while (iter.hasNext()) {
					System.out.println("들어옴2");
					// 파일을 가져온다
					FileItem fileItem = (FileItem) iter.next();

					// 업로드된 파일이 text 형태인 경우
					if (fileItem.isFormField()) {
						out.println("폼 파라미터: " + fileItem.getFieldName() + "=" + fileItem.getString("euc=kr") + "<br>");
					}
					// 업로드된 파일이 file 형태인 경우
					else {
						// size>0이면 업로드 성공
						if (fileItem.getSize() > 0) {
							String fieldName = fileItem.getFieldName();
							String fileName = fileItem.getName();
							String contentType = fileItem.getContentType();
							boolean isInMemory = fileItem.isInMemory();
							long sizeInBytes = fileItem.getSize();
							out.println("파일 [fieldName] : " + fieldName + "<br/>");
							out.println("파일 [fileName] : " + fileName + "<br/>");
							out.println("파일 [contentType] : " + contentType + "<br/>");
							out.println("파일 [isInMemory] : " + isInMemory + "<br/>");
							out.println("파일 [sizeInBytes] : " + sizeInBytes + "<br/>");

							// 실제 디렉토리에 fileName으로 카피 된다.
							File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
							fileItem.write(uploadedFile);
							fileItem.delete(); // 카피 완료후 temp폴더의 temp파일을 제거
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			out.println("인코딩 타입이 multipart/form=data 가 아님.");
		}
	}

	/** request msg 출력 */
	public void requestMsgLog(HttpServletRequest request) {

		/* server가 받은 request 시작줄 정보 */
		System.out.println("==================시작줄==================");
		System.out.println("Request Method: " + request.getMethod());
		System.out.println("Request RequestURI: " + request.getRequestURI());
		System.out.println("Request Protocol: " + request.getProtocol());

		/* server가 받은 request 헤더 정보 */
		/* server가 받은 기본적인 request header msg 정보 */
		System.out.println("==================헤더====================");
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

		System.out.println("================엔터티 본문================");
		System.out.println("__sequence: " + request.getParameter("__sequence"));
		System.out.println("uploadFilename: " + request.getParameter("uploadFilename"));
		System.out.println("Content-Disposition: " + request.getParameter("Content-Disposition"));
		System.out.println("===========================================");
	}
}
