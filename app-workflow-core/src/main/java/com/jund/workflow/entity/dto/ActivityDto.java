package com.jund.workflow.entity.dto;

import java.io.Serializable;

/**
 * 活动节点定义
 */
public class ActivityDto implements Serializable{

	private static final long serialVersionUID = 3984393348377490576L;

    /**
     * 活动节点名称
     */
    private String activityName;
    
    /**
     * 活动节点编码
     */
    private String activityCode;
    
    /**
     * 活动节点类型
     */
    private String activityType;
    
    /**
     * 流程定义id
     */
    private String proDefId;

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getProDefId() {
		return proDefId;
	}

	public void setProDefId(String proDefId) {
		this.proDefId = proDefId;
	}

	@Override
	public String toString() {
		return "ActivityDto [activityName=" + activityName + ", activityCode=" + activityCode + ", activityType=" + activityType + "]";
	}

}
