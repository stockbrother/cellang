package org.cellang.core.entity;

public class GroupCorpEntity extends EntityObject {
	
	public static String tableName = "group_corp";
	
	private String corpId;
	
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}
