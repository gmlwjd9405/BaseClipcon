package client;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This program demonstrates a usage of the MultipartUtility class.
 *
 * @author www.codejava.net
 *
 */
public class MultipartFileUploader3 {
	public static void main(String[] args) {
		System.out.println("<MultipartFileUploader3>");
		String charset = "UTF-8";
		// File uploadFile2 = new
		// File("C:\\Users\\Administrator\\Desktop\\aaaa.jpeg");
		File uploadFile1 = new File("C:\\Users\\Administrator\\Desktop\\hello.zip");
		String requestURL = "http://localhost:8080/TomcatServer/UploadServlet";

		try {
			MultipartUtility2 multipart = new MultipartUtility2(requestURL, charset);
			multipart.addHeaderField("User-Agent", "Heeee");

			/**
			 * __sequence contains the name of the called sequence within the
			 * specified project (in the requestURL)
			 */
			multipart.addFormField("__sequence", "uploadFile");

			/**
			 * uploadFilename is the name of the sequence input variable in the
			 * called project
			 *
			 * the value is the name that will be given to the file
			 */
			multipart.addFormField("uploadFilename", "hello.zip");
			// multipart.addFormField("uploadFilename", uploadFile1.getName());

			// not needed but helpful in logs
			multipart.addFilePart("uploadFilename", uploadFile1);
			// multipart.addFilePart("uploadFilename", uploadFile2);

			List<String> response = multipart.finish();

			System.out.println("SERVER REPLIED:");

			for (String line : response) {
				System.out.println(line);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
