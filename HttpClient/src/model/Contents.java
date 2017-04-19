package model;

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

	// 그룹내의 각 Data를 구분하는 고유키값
	private int contentsPKValue = 0;
	public String contentsPKName;

	private String uploadUserName;
	private String uploadTime;
	
	// String,Imgae Type: null, File Type: FileOriginName
	private String fileOriginName;

	/** 생성 시 서버로부터 받은 고유키값을 할당한다. */
	public Contents(String contentsPKName) {
		this.contentsPKName = contentsPKName;
		this.contentsPKValue = Integer.parseInt(contentsPKName);
	}
}
