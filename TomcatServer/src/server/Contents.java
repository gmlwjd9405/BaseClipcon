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
	private String folderName = null; //������ ���Ե� ���� �̸�

	// �׷쳻�� �� Data�� �����ϴ� ����Ű��
	private static int contentsPKValue = 0;
	public String contentsPKName;

	private String uploadUserName;
	private String uploadTime;
	
	//String Type: String��, File Type: FileOriginName(������ ���ԵǾ� �ִ� ���� folder1/fileOriginName)
	private String contentsValue;

	/** ���� �� ����Ű���� �Ҵ��Ѵ�. */
	public Contents() {
		this.contentsPKName = Integer.toString(++contentsPKValue);
		System.out.println("Contents ������ ȣ��, ++contentsPKValue");
	}
	
	/** �ټ��� ������ �������� �� �ϳ��� ������ ���� */
	public void setFolderName(String folderName){
		this.folderName = folderName;
	}
}
