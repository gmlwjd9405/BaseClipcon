package server;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Contents {
	public static String TYPE_STRING = "STRING";
	public static String TYPE_IMAGE = "IMAGE";
	public static String TYPE_FILE = "FILE";

	private String contentsType;
	private long contentsSize;
	private String folderName = null; //파일이 포함된 폴더 이름

	// 그룹내의 각 Data를 구분하는 고유키값
	private static int contentsPKValue = 0;
	public String contentsPKName;

	private String uploadUserName;
	private String uploadTime;
	
	//String Type: String값, File Type: FileOriginName(폴더에 포함되어 있는 경우는 folder1/fileOriginName)
	private String contentsValue;

	/** 생성 시 고유키값을 할당한다. */
	public Contents() {
		this.contentsPKName = Integer.toString(++contentsPKValue);
		System.out.println("Contents 생성자 호출, ++contentsPKValue");
	}
	
	/** 다수의 파일을 전송했을 때 하나의 폴더에 저장 */
	public void setFolderName(String folderName){
		this.folderName = folderName;
	}
}
