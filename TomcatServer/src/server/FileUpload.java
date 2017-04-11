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
@WebServlet("/FileUpload")
@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 업로드 파일을 저장할 위치
	private final String UPLOAD_DIRECTORY = "C:\\Users\\Administrator\\Desktop\\heeuploads";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUpload() {
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

		/* 1 */
		//response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// // Retrieves <input type="file" name="file">
		// Part filePart = request.getPart("file");
		// System.out.println("filePart: " + filePart.toString());
		//
		// // MSIE fix.
		// String submittedFileName = filePart.getName();
		// System.out.println("submittedFileName: " + submittedFileName);
		//// String fileName =
		// Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		//
		// InputStream fileContent = filePart.getInputStream();
		// ... (do your job here)

		/* 2 */
		// Check that we have a file upload request
		// boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// HashMap dataMap = new HashMap();
		// File tempFile = null;
		//
		// if (!isMultipart) {
		// throw new Exception("Form type is not multipart");
		//
		// // Create a factory for disk=based file items
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		//
		// // Set factory constraints
		// factory.setRepository(TempDirectory);
		//
		// // Create a new file upload handler
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// // Parse the request
		// List<FileItem> items = upload.parseRequest(request);
		//
		// // Process the uploaded items
		// for (FileItem item : items) {
		// String fieldName = item.getFieldName();
		//
		// if (item.isFormField()) { // processFormField
		// dataMap.put(fieldName, item.getString());
		// } else {
		// if (item.getSize() > 0)
		// tempFile = item;
		// }
		// }
		//
		// // Create file object for upload
		// File uploadFile = new File(savePath + "/" + saveFileName);
		//
		// // Save file object
		// if (tempFile.getSize() > 0) {
		// tempFile.write(uploadFile);
		// tempFile.delete();
		// }
		// }

		/* 3 */
		// process only if its multipart content
		// if (ServletFileUpload.isMultipartContent(request)) {
		// try {
		// List<FileItem> multiparts = new ServletFileUpload(new
		// DiskFileItemFactory()).parseRequest(request);
		// System.out.println("11111111111111111111111111111");
		//
		// for (FileItem item : multiparts) {
		// System.out.println("22222222222222222222222222");
		// if (!item.isFormField()) {
		// String name = new File(item.getName()).getName();
		// System.out.println("Filename: " + name);
		// item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
		// }
		// }
		// System.out.println("333333333333333333333333333");
		//
		// // File uploaded successfully
		// request.setAttribute("message", "File Uploaded Successfully");
		// System.out.println("444444444444444444444444444444");
		// } catch (Exception ex) {
		// request.setAttribute("message", "File Upload Failed due to " + ex);
		// }
		//
		// } else {
		// request.setAttribute("message", "Sorry this Servlet only handles file
		// upload request");
		// }
		//
		// request.getRequestDispatcher("/result.jsp").forward(request,
		// response);

		/* 4. */

		// boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// System.out.println("isMultipart: " + isMultipart);
		//
		// if (isMultipart) {
		// FileItemFactory factory = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// try {
		// List items = upload.parseRequest(request);
		// System.out.println("items size: " + items.size());
		// Iterator iterator = items.iterator();
		// System.out.println("들어옴1");
		// System.out.println(iterator.toString());
		// System.out.println(iterator.hasNext());
		//
		// System.out.println("들어옴2");
		// FileItem item = (FileItem) iterator.next();
		// System.out.println("들어옴3");
		// if (!item.isFormField()) {
		// System.out.println("들어옴4");
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
		//
		//// while (iterator.hasNext()) {
		//// System.out.println("들어옴4");
		//// FileItem item = (FileItem) iterator.next();
		//// System.out.println("들어옴2");
		//// if (!item.isFormField()) {
		//// System.out.println("들어옴3");
		//// String fileName = item.getName();
		////
		//// File path = new File(UPLOAD_DIRECTORY);
		//// if (!path.exists()) {
		//// boolean status = path.mkdirs();
		//// }
		////
		//// File uploadedFile = new File(path + "\\" + fileName);
		//// item.write(uploadedFile);
		//// }
		//// }
		// System.out.println("나옴");
		// } catch (Exception e) {
		// }
		// }

		/* 5. */
		// //ServletFileUpload 오브젝트를 생성
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// //업로드할 때의 기준값을 설정
		// factory.setSizeThreshold(1024);
		// upload.setSizeMax(=1);
		// //upload.setHeaderEncoding(encoding);
		//
		// try{
		// System.out.println("들어옴1");
		// //파일데이터(FileItem 객체)를 취득하고, List 오브젝트로써 리턴
		// List list = upload.parseRequest(request);
		// System.out.println("list size: " + list.size());
		//
		// //파일데이터(FileItem 객체)를 순서대로 처리
		// Iterator iterator = list.iterator();
		// while(iterator.hasNext()){
		// FileItem fileitem = (FileItem)iterator.next();
		//
		// //파일데이터의 경우, if 내부로 이동
		// if(!(fileitem.isFormField())){
		// //파일데이터의 파일명을 취득
		// String fileName = fileitem.getName();
		// if((fileitem != null) && (!fileitem.equals(""))){
		// //PATH명을 제외한 파일명만을 취득
		// fileName = (new File(fileName)).getName();
		// //파일데이터를 지정된 파일에 쓴다.
		// fileitem.write(new File(UPLOAD_DIRECTORY + "/" + fileName));
		// }
		// }
		// }
		// }catch (FileUploadException e) {
		// e.printStackTrace();
		// }catch (Exception e) {
		// e.printStackTrace();
		// }

		/* 6. */

		requestMsgLog(request);

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
		System.out.println("description: " + request.getParameter("description"));
		System.out.println("keywords: " + request.getParameter("keywords"));
		System.out.println("===========================================");
	}
}
