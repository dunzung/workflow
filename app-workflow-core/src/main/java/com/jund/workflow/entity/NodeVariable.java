package com.jund.workflow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程节点扩展参数
 * Created by killer_duan on 2015/10/20.
 */
@Entity
@Table(name = "ACT_EX_NODE_VARIABLE")
public class NodeVariable implements Serializable {

	private static final long serialVersionUID = -7772392509763368721L;
	
	private String uuid;
    /**
     * 流程模型ID
     */
    private String modelId;
    /**
     * 流程定义ID
     */
    private String procDefId;
    private String nodeId;                //节点编号，英文******************
    private String nodeName;            //节点名称，中文，人工录入

    private String activityName;        //活动的名称，自动取流程定义的活动名称，下拉列表选择
    private String activityType;        //活动类型，系统后台自动赋值

    private String isBegin;            //是否是启动流程的节点，自动赋值

    private String listenerStart;        //开始事件监听
    private String listenerEnd;            //结束事件监听，目前只使用结束事件监听

    private String statusDictValue;    //当前任务节点审批通过后，状态标识的值，如开始节点通常状态标识变为审批中2，审批通过节点变成生效1

    //代办属性
    private String approveType;        //1提交人，2提交人所在机构某角色，3角色，4某机构某角色，5指定用户，6提交人上级机构的某角色
    private String approveOrganCode;    //隶属机构或部门, 多个机构逗号分隔
    private String approveRoleCode;        //预审批角色, 多个角色逗号分隔
    private String approveUserName;    //预审批人, 多个用户逗号分隔

    private String assignNextApproverAble;  //审批时指定下一任务的执行人
    private String consignAble;            //该任务是否允许改派
    private String rollbackRule;            //回退规则
    /**
     * 投票规则
     */
    private String voteType;
    /**
     * 会签类型
     */
    private String signType;

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "UUID", unique = true, nullable = false, length = 40)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name = "NODEID_", nullable = true, length = 40)
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Column(name = "MODEL_ID", nullable = true, length = 40)
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Column(name = "NODENAME_", nullable = true, length = 60)
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Column(name = "ACTIVITYNAME_", nullable = true, length = 40)
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Column(name = "ACTIVITYTYPE_", nullable = true, length = 40)
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @Column(name = "ISBEGIN_", nullable = true, length = 40)
    public String getIsBegin() {
        return isBegin;
    }

    public void setIsBegin(String isBegin) {
        this.isBegin = isBegin;
    }

    @Column(name = "LISTENERSTART", nullable = true, length = 200)
    public String getListenerStart() {
        return listenerStart;
    }

    public void setListenerStart(String listenerStart) {
        this.listenerStart = listenerStart;
    }

    @Column(name = "LISTENEREND", nullable = true, length = 200)
    public String getListenerEnd() {
        return listenerEnd;
    }

    public void setListenerEnd(String listenerEnd) {
        this.listenerEnd = listenerEnd;
    }

    @Column(name = "STATUSDICTVALUE_", nullable = true, length = 40)
    public String getStatusDictValue() {
        return statusDictValue;
    }

    public void setStatusDictValue(String statusDictValue) {
        this.statusDictValue = statusDictValue;
    }

    @Column(name = "APPROVETYPE_", nullable = true, length = 2)
    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    @Column(name = "APPROVEUSERNAME_", nullable = true, length = 1024)
    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    @Column(name = "ASSIGNNEXTAPPROVERABLE_", nullable = true, length = 2)
    public String getAssignNextApproverAble() {
        return assignNextApproverAble;
    }

    public void setAssignNextApproverAble(String assignNextApproverAble) {
        this.assignNextApproverAble = assignNextApproverAble;
    }

    @Column(name = "CONSIGNABLE_", nullable = true, length = 2)
    public String getConsignAble() {
        return consignAble;
    }

    public void setConsignAble(String consignAble) {
        this.consignAble = consignAble;
    }

    @Column(name = "SIGN_TYPE", nullable = true, length = 10)
    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    @Column(name = "APPROVEORGANCODE_", nullable = true, length = 1024)
    public String getApproveOrganCode() {
        return approveOrganCode;
    }

    public void setApproveOrganCode(String approveOrganCode) {
        this.approveOrganCode = approveOrganCode;
    }

    @Column(name = "APPROVEROLECODE_", nullable = true, length = 1024)
    public String getApproveRoleCode() {
        return approveRoleCode;
    }

    public void setApproveRoleCode(String approveRoleCode) {
        this.approveRoleCode = approveRoleCode;
    }

    @Column(name = "ROLLBACKRULE_", nullable = true, length = 40)
    public String getRollbackRule() {
        return rollbackRule;
    }

    public void setRollbackRule(String rollbackRule) {
        this.rollbackRule = rollbackRule;
    }

    @Column(name = "DEFINE_ID", nullable = true, length = 40)
    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    @Column(name = "VOTE_TYPE", nullable = true, length = 2)
    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (this.uuid == null) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "NodeVariable [uuid=" + this.uuid + "]";
    }


}
