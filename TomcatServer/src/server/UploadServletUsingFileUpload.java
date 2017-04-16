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
	// ���ε� ������ ������ ��ġ
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
		// //���ε��� ���� ���ذ��� ����
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
		System.out.println("__sequence: " + request.getParameter("__sequence"));
		System.out.println("uploadFilename: " + request.getParameter("uploadFilename"));
		System.out.println("Content-Disposition: " + request.getParameter("Content-Disposition"));
		System.out.println("===========================================");
	}
}
