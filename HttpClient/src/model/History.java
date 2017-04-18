package model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class History {
	private String groupPK;
	private Map<String, Contents> contentsMap;

	public History(String groupPK) {
		this.groupPK = groupPK;
	}

	/** ���ο� �����Ͱ� ���ε�Ǹ� �����丮�� add */
	public void addContents(Contents contents) {
		contentsMap.put(contents.getContentsPKName(), contents);
	}

	/** Data�� �����ϴ� ����Ű���� ��ġ�ϴ� Contents�� return */
	public Contents getContentsByPK(String contentsPKName) {
		return contentsMap.get(contentsPKName);
	}
}
