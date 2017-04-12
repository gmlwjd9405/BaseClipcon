package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(location = "C:\\Users\\Administrator\\Desktop\\heeuploads", maxFileSize = 1024 * 1024 * 10, fileSizeThreshold = 1024
		* 1024, maxRequestSize = 1024 * 1024 * 20)

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestMsgLog(request);
		System.out.println(request.getParameter("name")); // This doesn't work
		
		String fileName = "hello.zip";
		Part filePart = null;
		
		for (Part part : request.getParts()) {
			System.out.println(part);
			for (String headerName : part.getHeaderNames()) {
				System.out.println(headerName);
				System.out.println("-");
				System.out.println(part.getHeader(headerName));
				// To find out file name, parse header value of
				// content-disposition
				// e.g. form-data; name="file"; filename=""
			}
			// Get a normal parameter
			if (part.getName().equals("name")) {
				String paramValue = getStringFromStream(part.getInputStream());
				System.out.println("1111111111111");
				System.out.println(paramValue);
				fileName = paramValue;
			}
			if (part.getName().equals("uploadFilename")) {
				filePart = part; // Absolute path doesn't work.
				System.out.println("2222222222222");
				System.out.println(part.toString());
				System.out.println(part.getName());
			}
		}
		filePart.write(fileName);
		//response.sendRedirect("index.jsp");
	}

	public String getStringFromStream(InputStream stream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
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
		return sb.toString();
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
