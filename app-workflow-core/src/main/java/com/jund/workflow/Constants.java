package com.jund.workflow;


public final class Constants {

	public static final String PREFIX = "_wfl_"; 
    public final static String PARAM_PROCESS_STATUS = PREFIX + "processStatus";
    public static final String PARAM_TASK_RESULT_FLAG = PREFIX + "resultFlag";        //审批结果参数�?
    public static final String PARAM_TASK_COMMENT = "taskComment";            //审批结果参数�?
    public static final String PARAM_PROCESS_APPLY_USER = PREFIX + "applyUser";        //流程发起�?
    public static final String PARAM_PROCESS_APPLY_USER_REALNAME = PREFIX + "applyUser_realName";        //流程发起�?
    public static final String PARAM_TASK_APPROVER = PREFIX + "taskApprover";            //审批�?
    public static final String PARAM_NEXT_TASK_CANDIDATES = PREFIX + "next_task_candidates";    //下一节点审批候�?�人
    public static final String PARAM_TASK_CANDIDATES = PREFIX + "task_candidates";            //当前节点审批候�?�人
    public static final String PARAM_PROCESS_RUN_ROUTE = PREFIX + "process_run_route";    //下一节点审批候�?�人

    public static final String PARAM_SIGN_ASSIGNEES = PREFIX + "sign_assignees";        //会签人员变量
    public static final String PARAM_SIGN_COMPLETED_CONDITION = PREFIX + "sign_completed_condition";        //会签完成条件
    public static final String PARAM_SIGN_VARIABLE = PREFIX + "sign_variable";        //会签变量
    public static final String PARAM_SIGN_NR_OF_INSTANCES = "nrOfInstances";        //会签实例总数
    public static final String PARAM_SIGN_NR_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";        //会签已经完成的实例�?�数
    public static final String PARAM_SIGN_NR_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";        //会签还没完成的实例�?�数
    public static final String PARAM_SIGN_NR_OF_PASS_INSTANCES = PREFIX + "nrOfPassInstances";        //会签任务通过�?

    public static final String SIGN_TASK_STATUS_SUCCESS = "1";        //会签节点状�?�：成功
    public static final String SIGN_TASK_STATUS_FAILEUR = "2";        //会签节点状�?�：失败
    public static final String SIGN_TASK_STATUS_RUNNING = "3";        //会签节点状�?�：尚在运行�?

    public static final String SIGN_TYPE_PARALLEL = "Parallel";        //会签类型，是

    public static final String NODE_ROLLBACK_RULE_ORIGINAL = "0";   //会�??给原办理�?
    public static final String NODE_ROLLBACK_RULE_ALL = "1";        //回�??原映射所有审批人

    public static final String TASK_RESULT_YES = "Y";    //审批通过
    public static final String TASK_RESULT_NO = "N";        //审批未�?�过

    public static final String TASK_DELETE_REASON_DELETE = "deleted";        //关闭
    public static final String TASK_DELETE_REASON_COMPLETED = "completed";  //完成

    public static final String TASK_STATUS_RUNNING = "1";    //运行�?
    public static final String TASK_STATUS_FINISH = "2";        //已结�?

    public static final String NODE_CONSIGNABLE = "1";            //允许委派
    public static final String NODE_UNCONSIGN = "0";                //不允许委�?

    public static final String MODEL_DEPLOY = "1";        //发布

    /**
     * 挂起
     */
    public static final String SUSPEND = "2";
    /**
     * �?�?
     */
    public static final String ACTIVE = "1";

    public static final Integer DEFAULT_VERSION = 1;

    public final static class BizParam {
        public static final String BIZ_URL = "bizUrl";

        /**
         * 流程业务主键
         */
        public static final String PROCESS_BIZ_KEY = "process_biz_key";

        /**
         * 任务业务主键
         */
        public static final String TASK_BIZ_KEY = "task_biz_key";
    }

    public final static class MetaModel {
        public static final String BPMN_JSON = ".bpmn20.json";
        public static final String BPMN_XML = ".bpmn20.xml";
        public static final String DEFAULT_MODEL_ID = "1000";
    }

    /**
     * 会签投票规则
     */
    public final static class VoteRule {
        /**
         * �?票否决制
         */
        public static final String ONE_DENY = "0";
        /**
         * �?票�?�过�?
         */
        public static final String ONE_AGREE = "1";
        /**
         * 半数通过�?
         */
        public static final String PER_50_AGREE = "2";
        /**
         * 60%通过�?
         */
        public static final String PER_60_AGREE = "3";
    }

    public final static class Variable {
        /**
         * 任务级业务主�?
         */
        public static final String TASK_BUSINESS_KEY = "task_business_key";
        /**
         * 流程级业务主�?
         */
        public static final String PROCESS_BUSINESS_KEY = "process_business_key";
        /**
         * 是否通过
         */
        public static final String HAS_AGREE = "hasAgree";
        /**
         * 复位发起关联：流程实例ID
         */
        public static final String PROC_PREV_INST_ID = "prevInstID";
    }

    public final static class Charset {
        public static final String UTF8 = "utf-8";
    }

    /**
     * 流程实例状�??
     */
    public final static class ProcessInstStatus {
        public static final String RUNNING = "1";    //运行�?
        public static final String FINISH = "2";        //已结�?
        public static final String CANCELLED = "3";        //已终�?
        public static final String ERROR = "4";        //错误
        public static final String SUSPEND = "5";        //挂起
    }

    /**
     * 流程定义状�??
     */
    public final static class ProcessDefStatus {
        /**
         * 启用
         */
        public static final String ENABLE = "1";
        /**
         * 锁定
         */
        public static final String LOCK = "2";
    }

    public final static class ActivityNode {
        public static final String START = "startEvent";
        public static final String END = "endEvent";
        public static final String TASK = "userTask";
        public static final String DECISION = "exclusiveGateway";
    }

    /**
     * 审批意见类型
     *
     * @author tanghier_tang
     */
    public final static class Comment {
        public static final String TYPE_EVENT = "event";
        public static final String TYPE_COMMENT = "comment";
    }

    /**
     * 任务事件类型
     *
     * @author tanghier_tang
     */
    public final static class TaskListener {

        public static final String EVENTNAME_CREATE = "create";

        public static final String EVENTNAME_ASSIGNMENT = "assignment";

        public static final String EVENTNAME_COMPLETE = "complete";

        public static final String EVENTNAME_DELETE = "delete";

        public static final String ASSIGNMENT_LISTENER = "com.vprisk.workflow.core.activiti.listener.ProcessAssignmentListener";

        public static final String TASK_START_LISTENER = "com.vprisk.workflow.core.activiti.listener.TaskStartListener";

        public static final String TASK_END_LISTENER = "com.vprisk.workflow.core.activiti.listener.TaskEndListener";
    }

    /**
     * 执行事件类型
     *
     * @author tanghier_tang
     */
    public final static class ExecutionListener {
        public static final String EVENTNAME_START = "start";
        public static final String EVENTNAME_END = "end";
        public static final String EVENTNAME_TAKE = "take";

        public static final String EXECUTION_END_LISTENER = "com.vprisk.workflow.core.activiti.listener.GatewayEndListener";
    }

    /**
     * 映射审批类型
     */
    public final static class ApproveType {
        public static final String APPLY_USER = "1";            //提交�?
        public static final String APPLY_ORG_ROLE = "2";        //提交人所在机构某角色
        public static final String ROLE = "3";                  //角色
        public static final String ORG_ROLE = "4";              //某机构某角色
        public static final String USER = "5";                  //指定用户
        public static final String APPLY_PORG_ROLE = "6";        //提交人上级机构的某角�?
        public static final String APPLY_ORG_PORG_ROLE = "7";    //提交人所在机构或者上级机构的某角�?
    }

    public final static class Tags {
        public static final String ID = "overrideid";

        public static final String STENCIL = "stencil";

        public static final String STENCIL_ID = "id";

        public static final String NAME = "name";

        public static final String DOCUMENTATION = "documentation";

        public static final String EX_NODES = "ex_nodes";

        public static final String NODE_ID = "nodeid";

        public static final String NODE_NAME = "nodename";

        /**
         * 投票规则
         */
        public static final String VOTE_TYPE = "votetype";

        /**
         * 会签类型
         */
        public static final String SIGN_TYPE = "multiinstance_type";

        /**
         * 流程定义ID
         */
        public static final String PID = "process_id";

        public static final String PROPS = "properties";

        public static final String CHILD_SHAPES = "childShapes";
        /**
         * 回�??规则
         */
        public static final String BACKRULE = "rollbackrule";
        /**
         * 审核类型
         */
        public static final String APPROVETYPE = "approvetype";
        /**
         * 是否委派
         */
        public static final String CONSIGNABLE = "consignable";
        /**
         * 机构
         */
        public static final String APPROVE_ORGANCODE = "approveorgancode";
        /**
         * 角色
         */
        public static final String APPROVE_ROLECODE = "approverolecode";
        /**
         * 用户
         */
        public static final String APPROVE_USERID = "approveuserid";

        public static final String GATEWAY_START_LISTENER = "gateway_start_listener";

        public static final String GATEWAY_END_LISTENER = "gateway_end_listener";

        public static final String STARTNONE_START_LISTENER = "startnone_start_listener";

        public static final String ENDNONE_END_LISTENER = "endnone_end_listener";

        public static final String TASK_START_LISTENER = "task_start_listener";

        public static final String TASK_END_LISTENER = "task_end_listener";

        public static final String TASK_LISTENER = "tasklisteners";

        public static final String TASK_LISTENER_ITEMS = "taskListeners";

        public static final String EXECUTION_LISTENER = "executionlisteners";

        public static final String EXECUTION_LISTENER_ITEMS = "executionListeners";

        public static final class Listener {

            public static final String EVENT = "event";

            public static final String CLASSNAME = "className";

            public static final String IMPLEMENTENTATION = "implementation";

            public static final String EXPRESSION = "expression";

            public static final String DELEGATE_EXPRESSSION = "delegateExpression";

            public static final String FIELDS = "fields";
        }
    }

    /**
     * 标签内容�?
     */
    public static final class TagVal {
        /**
         * StartNoneEvent
         */
        public static final String START_NONE_STENCIL_ID = "StartNoneEvent";

        /**
         * EndNoneEvent
         */
        public static final String END_NONE_STENCIL_ID = "EndNoneEvent";

        /**
         * UserTask
         */
        public static final String TASK_STENCIL_ID = "UserTask";

        /**
         * SequenceFlow
         */
        public static final String SEQUENCEFLOW_STENCIL_ID = "SequenceFlow";
        /**
         * ExclusiveGateway
         */
        public static final String GATEWAY_TENCIL_ID = "ExclusiveGateway";
    }

    public class Errors {
        public static final String MULTI_INSTANCE_MISSING_COLLECTION = "activiti-multi-instance-missing-collection";
    }

}
