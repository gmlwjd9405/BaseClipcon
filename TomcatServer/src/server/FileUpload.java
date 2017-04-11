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
	// ���ε� ������ ������ ��ġ
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
		// System.out.println("����1");
		// System.out.println(iterator.toString());
		// System.out.println(iterator.hasNext());
		//
		// System.out.println("����2");
		// FileItem item = (FileItem) iterator.next();
		// System.out.println("����3");
		// if (!item.isFormField()) {
		// System.out.println("����4");
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
		//// System.out.println("����4");
		//// FileItem item = (FileItem) iterator.next();
		//// System.out.println("����2");
		//// if (!item.isFormField()) {
		//// System.out.println("����3");
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
		// System.out.println("����");
		// } catch (Exception e) {
		// }
		// }

		/* 5. */
		// //ServletFileUpload ������Ʈ�� ����
		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(factory);
		//
		// //���ε��� ���� ���ذ��� ����
		// factory.setSizeThreshold(1024);
		// upload.setSizeMax(=1);
		// //upload.setHeaderEncoding(encoding);
		//
		// try{
		// System.out.println("����1");
		// //���ϵ�����(FileItem ��ü)�� ����ϰ�, List ������Ʈ�ν� ����
		// List list = upload.parseRequest(request);
		// System.out.println("list size: " + list.size());
		//
		// //���ϵ�����(FileItem ��ü)�� ������� ó��
		// Iterator iterator = list.iterator();
		// while(iterator.hasNext()){
		// FileItem fileitem = (FileItem)iterator.next();
		//
		// //���ϵ������� ���, if ���η� �̵�
		// if(!(fileitem.isFormField())){
		// //���ϵ������� ���ϸ��� ���
		// String fileName = fileitem.getName();
		// if((fileitem != null) && (!fileitem.equals(""))){
		// //PATH���� ������ ���ϸ��� ���
		// fileName = (new File(fileName)).getName();
		// //���ϵ����͸� ������ ���Ͽ� ����.
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

		// multipart�� ���۵Ǿ��°��� üũ
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		// multipart�� ���� �Ǿ��� ���
		if (isMultipart) {
			// ���ε� �� ������ �ӽ� ���� ������ ����
			File temporaryDir = new File("C:\\Users\\Administrator\\Desktop\\heeuploads");

			// ������ ��ü ��θ� �������� upload��� ������ ����� �ű�� tmp�� ������ ���۵� ������ upload ������
			// ī�� �Ѵ�.
			// String realDir =
			// config.getServletContext().getRealPath("/upload/");

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 1MB ����: �޸𸮿��� �ٷ� ���, 1MB �̻�: temporaryDir ��� ������ �̵�
			factory.setSizeThreshold(1 * 1024 * 1024);
			factory.setRepository(temporaryDir);

			// ���� �����ܰ� �ƴ� �����ܰ迴��. �ִ� ���� ũ��(10MB)
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(10 * 1024 * 1024);

			try {
				// ���� ���ε� �κ�(�̺κп��� ������ �����ȴ�)
				List<FileItem> items = upload.parseRequest(request);

				Iterator iter = items.iterator(); // Iterator ���
				System.out.println("����1");
				System.out.println("items size: " + items.size());

				while (iter.hasNext()) {
					System.out.println("����2");
					// ������ �����´�
					FileItem fileItem = (FileItem) iter.next();

					// ���ε�� ������ text ������ ���
					if (fileItem.isFormField()) {
						out.println("�� �Ķ����: " + fileItem.getFieldName() + "=" + fileItem.getString("euc=kr") + "<br>");
					}
					// ���ε�� ������ file ������ ���
					else {
						// size>0�̸� ���ε� ����
						if (fileItem.getSize() > 0) {
							String fieldName = fileItem.getFieldName();
							String fileName = fileItem.getName();
							String contentType = fileItem.getContentType();
							boolean isInMemory = fileItem.isInMemory();
							long sizeInBytes = fileItem.getSize();
							out.println("���� [fieldName] : " + fieldName + "<br/>");
							out.println("���� [fileName] : " + fileName + "<br/>");
							out.println("���� [contentType] : " + contentType + "<br/>");
							out.println("���� [isInMemory] : " + isInMemory + "<br/>");
							out.println("���� [sizeInBytes] : " + sizeInBytes + "<br/>");

							// ���� ���丮�� fileName���� ī�� �ȴ�.
							File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
							fileItem.write(uploadedFile);
							fileItem.delete(); // ī�� �Ϸ��� temp������ temp������ ����
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			out.println("���ڵ� Ÿ���� multipart/form=data �� �ƴ�.");
		}
	}

	/** request msg ��� */
	public void requestMsgLog(HttpServletRequest request) {

		/* server�� ���� request ������ ���� */
		System.out.println("==================������==================");
		System.out.println("Request Method: " + request.getMethod());
		System.out.println("Request RequestURI: " + request.getRequestURI());
		System.out.println("Request Protocol: " + request.getProtocol());
		
		/* server�� ���� request ��� ���� */
		/* server�� ���� �⺻���� request header msg ���� */
		System.out.println("==================���====================");
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

		System.out.println("================����Ƽ ����================");
		System.out.println("description: " + request.getParameter("description"));
		System.out.println("keywords: " + request.getParameter("keywords"));
		System.out.println("===========================================");
	}
}
