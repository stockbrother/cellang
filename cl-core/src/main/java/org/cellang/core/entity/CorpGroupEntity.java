package org.cellang.core.entity;

public class CorpGroupEntity extends EntityObject {

	public static String tableName = "corp_group";

	private String groupId;
	
	private String groupType;
	
	private String groupDate;
	
	public String getGroupDate() {
		return groupDate;
	}

	public void setGroupDate(String groupDate) {
		this.groupDate = groupDate;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
