package client;

import static java.lang.System.currentTimeMillis;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.URLConnection.guessContentTypeFromName;
import static java.text.MessageFormat.format;
import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class MultipartFileUploader3 {
	public static String SERVER_URI = "http://localhost:8080/TomcatServer";
	public static String REQUEST_SERVER_SERVLET = "/FileUpload";
	
    private static final Logger log = getLogger(MultipartFileUploader3.class.getName());

    private static final String CRLF = "\r\n";
    private static final String CHARSET = "UTF-8";

    private static final int CONNECT_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 10000;

    private final HttpURLConnection connection;
    private final OutputStream outputStream;
    private final PrintWriter writer;
    private final String boundary;

    private final URL url;
    private final long start;
    
    public static void main(String[] args) {
    	
         
	}
    
	public void fileUpload() {
		try {
			File file = new File("C:\\Users\\Administrator\\Desktop\\ccccc.txt");
			URL url = new URL(SERVER_URI + REQUEST_SERVER_SERVLET);
			URLConnection httpConn = url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("Content-type", "application/octet-stream");
			httpConn.setRequestProperty("Content-Length", String.valueOf(file.length()));

			OutputStream out = httpConn.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int readcount = 0;
			
			while ((readcount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readcount);
			}

			//
			// MultipartUtility http = new MultipartUtility();
			//// http.addFormField("someField", "someValue");
			//// http.addFormField("someButton", "Submit");
			//
			// http.addFilePart("someFile", uploadFile);
			// final byte[] bytes = http.finish();
			// try (final OutputStream os = new FileOutputStream("ccccc.txt") {
			// os.write(bytes);
			// }
			// } catch (IOException e) {
			// e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public MultipartFileUploader3(final URL url) throws IOException {
        start = currentTimeMillis();
        this.url = url;

        boundary = "---------------------------" + currentTimeMillis();

        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", CHARSET);
        connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + boundary);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        outputStream = connection.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET),true);
    }

    public void addFormField(final String name, final String value) {
        writer.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"").append(name)
                .append("\"").append(CRLF)
                .append("Content-Type: text/plain; charset=").append(CHARSET)
                .append(CRLF).append(CRLF).append(value).append(CRLF);
    }

    public void addFilePart(final String fieldName, final File uploadFile)
            throws IOException {
        final String fileName = uploadFile.getName();
        writer.append("--").append(boundary).append(CRLF)
                .append("Content-Disposition: form-data; name=\"")
                .append(fieldName).append("\"; filename=\"").append(fileName)
                .append("\"").append(CRLF).append("Content-Type: ")
                .append(guessContentTypeFromName(fileName)).append(CRLF)
                .append("Content-Transfer-Encoding: binary").append(CRLF)
                .append(CRLF);

        writer.flush();
        outputStream.flush();
        try (final FileInputStream inputStream = new FileInputStream(uploadFile);) {
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }

        writer.append(CRLF);
    }

    public void addHeaderField(String name, String value) {
        writer.append(name).append(": ").append(value).append(CRLF);
    }

    public byte[] finish() throws IOException {
        writer.append(CRLF).append("--").append(boundary).append("--")
                .append(CRLF);
        writer.close();

        final int status = connection.getResponseCode();
        if (status != HTTP_OK) {
            throw new IOException(format("{0} failed with HTTP status: {1}",
                    url, status));
        }

        try (final InputStream is = connection.getInputStream()) {
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                bytes.write(buffer, 0, bytesRead);
            }

            log.log(INFO,
                    format("{0} took {4} ms", url,
                            (currentTimeMillis() - start)));
            return bytes.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
}