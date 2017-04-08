package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PrintHttpRequestHeaders
 */
@WebServlet("/PrintHttpRequestHeaders")
public class PrintHttpRequestHeaders extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PrintHttpRequestHeaders() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		/* server가 받은 기본적인 request header msg 정보 */
		out.println("Request Method: " + request.getMethod());
		out.println("Request URI: " + request.getRequestURI());
		out.println("Request Protocol: " + request.getProtocol());
		out.println("Request ServerPort: " + request.getServerPort());
		out.println("Request ContentType: " + request.getContentType());
		out.println("Request ContentLength: " + request.getContentLength());
		out.println("Request ContextPath: " + request.getContextPath());
		out.println("-------------------------------");
		out.println("Request LocalAddr: " + request.getLocalAddr());
		out.println("Request LocalName: " + request.getLocalName());
		out.println("Request LocalPort: " + request.getLocalPort());
		out.println("-------------------------------");
		out.println("Request RemoteAddr: " + request.getRemoteAddr());
		out.println("Request RemoteHost: " + request.getRemoteHost());
		out.println("Request RemotePort: " + request.getRemotePort());
		out.println("Request RemoteUser: " + request.getRemoteUser());
		out.println("===============================");

		/* server가 받은 기본적인 request header msg 정보 */
		Enumeration headerNames = request.getHeaderNames();

		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();

			out.println(headerName + ": " + request.getHeader(headerName));
		}

		/* server가 받은 추가적인 request body msg 정보 */
		out.println("===============================");
		out.println("username: " + request.getParameter("username"));
		out.println("Passwd: " + request.getParameter("Passwd"));
	}
}
