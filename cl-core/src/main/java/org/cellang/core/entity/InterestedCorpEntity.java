package org.cellang.core.entity;

public class InterestedCorpEntity extends EntityObject {
	
	public static String tableName = "interested_corp";
	
	private String corpId;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}
