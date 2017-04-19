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

	// �׷쳻�� �� Data�� �����ϴ� ����Ű��
	private int contentsPKValue = 0;
	public String contentsPKName;

	private String uploadUserName;
	private String uploadTime;
	
	// String,Imgae Type: null, File Type: FileOriginName
	private String fileOriginName;

	/** ���� �� �����κ��� ���� ����Ű���� �Ҵ��Ѵ�. */
	public Contents(String contentsPKName) {
		this.contentsPKName = contentsPKName;
		this.contentsPKValue = Integer.parseInt(contentsPKName);
	}
}
