package com.jund.workflow.entity.dto;

/**
 * Created by killer_duan on 2015/10/20.
 */
public class NodeVariableDto {

    private String uuid;

    /**
     * 流程定义ID
     */
    private String procDefId;

    private String nodeId;                //节点编号，英�?******************

    private String nodeName;            //节点名称，中文，人工录入

    private String activityName;        //活动的名称，自动取流程定义的活动名称，下拉列表�?�择

    private String activityType;        //活动类型，系统后台自动赋�?

    private String isBegin;            //是否是启动流程的节点，自动赋�?

    private String listenerStart;        //�?始事件监�?

    private String listenerEnd;            //结束事件监听，目前只使用结束事件监听

    private String statusDictValue;    //当前任务节点审批通过后，状�?�标识的值，如开始节点�?�常状�?�标识变为审批中2，审批�?�过节点变成生效1

    /***************代办属�??****************/
    private String approveType;        //1提交人，2提交人所在机构某角色�?3角色�?4某机构某角色�?5指定用户�?6提交人上级机构的某角�?

    private String approveOrganCode;    //隶属机构或部�?, 多个机构逗号分隔

    private String approveRoleCode;        //预审批角�?, 多个角色逗号分隔

    private String approveUserName;    //预审批人, 多个用户逗号分隔

    private String assignNextApproverAble;  //审批时指定下�?任务的执行人

    private String consignAble;            //该任务是否允许委�?

    private String rollbackRule;            //回�??规则

    /**
     * 会签类型
     */
    private String signType;

    private ProcessModelDto modelDto;

    /**
     * 投票规则
     */
    private String voteType;

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getIsBegin() {
        return isBegin;
    }

    public void setIsBegin(String isBegin) {
        this.isBegin = isBegin;
    }

    public String getListenerStart() {
        return listenerStart;
    }

    public void setListenerStart(String listenerStart) {
        this.listenerStart = listenerStart;
    }

    public String getListenerEnd() {
        return listenerEnd;
    }

    public void setListenerEnd(String listenerEnd) {
        this.listenerEnd = listenerEnd;
    }

    public String getStatusDictValue() {
        return statusDictValue;
    }

    public void setStatusDictValue(String statusDictValue) {
        this.statusDictValue = statusDictValue;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveOrganCode() {
        return approveOrganCode;
    }

    public void setApproveOrganCode(String approveOrganCode) {
        this.approveOrganCode = approveOrganCode;
    }

    public String getApproveRoleCode() {
        return approveRoleCode;
    }

    public void setApproveRoleCode(String approveRoleCode) {
        this.approveRoleCode = approveRoleCode;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getAssignNextApproverAble() {
        return assignNextApproverAble;
    }

    public void setAssignNextApproverAble(String assignNextApproverAble) {
        this.assignNextApproverAble = assignNextApproverAble;
    }

    public String getConsignAble() {
        return consignAble;
    }

    public void setConsignAble(String consignAble) {
        this.consignAble = consignAble;
    }

    public String getRollbackRule() {
        return rollbackRule;
    }

    public void setRollbackRule(String rollbackRule) {
        this.rollbackRule = rollbackRule;
    }

    public ProcessModelDto getModelDto() {
        return modelDto;
    }

    public void setModelDto(ProcessModelDto modelDto) {
        this.modelDto = modelDto;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
