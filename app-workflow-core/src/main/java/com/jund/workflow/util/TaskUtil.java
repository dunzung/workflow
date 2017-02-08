package com.jund.workflow.util;

import com.jund.workflow.Constants;
import com.jund.workflow.entity.Listener;
import org.activiti.engine.delegate.DelegateExecution;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by killer_duan on 2015/11/12.
 */
public class TaskUtil {

    /**
     * 会签任务
     *
     * @param execution
     * @return
     */
    public static boolean hasSignTask(DelegateExecution execution) {
        return execution.hasVariable(Constants.PARAM_SIGN_COMPLETED_CONDITION);
    }

    /**
     * 设置任务监听�?
     *
     * @param properties
     * @param startTaskListener
     * @param endTaskListener
     */
    public static void registerTaskListner(ObjectNode properties, Listener startTaskListener, Listener endTaskListener) {
        /**
         * 创建监听器节�?
         */
        ArrayNode newListenerItems = ModelUtil.objectMapper().createArrayNode();
        /**
         * 设置默认的任务监听器
         */
        ListenerUtil.setDefaultTaskStartAndEndListener(newListenerItems);
        /**
         * �?始监听器
         */
        if (null != startTaskListener) {
            ListenerUtil.buildTaskListener(newListenerItems, startTaskListener.getClassName(), "", startTaskListener.getType(), "", "", null);
        }
        /**
         * 结束监听�?
         */
        if (null != endTaskListener) {
            ListenerUtil.buildTaskListener(newListenerItems, endTaskListener.getClassName(), "", endTaskListener.getType(), "", "", null);
        }
        /**
         * 任务选择分配监听�?
         */
        ListenerUtil.buildTaskListener(newListenerItems, Constants.TaskListener.ASSIGNMENT_LISTENER, "", Constants.TaskListener.EVENTNAME_CREATE, "", "", null);
        /**
         * 创建监听器节�?
         */
        ObjectNode newListeners = ModelUtil.objectMapper().createObjectNode();
        newListeners.put(Constants.Tags.TASK_LISTENER_ITEMS, newListenerItems);
        properties.put(Constants.Tags.TASK_LISTENER, newListeners);
    }

    /**
     * 设置流程级监听器
     *
     * @param properties
     * @param startExecutionListener
     * @param endExecutionListener
     */
    public static void registerExecutionListener(ObjectNode properties, Listener startExecutionListener, Listener endExecutionListener) {
        /**
         * 创建监听器节�?
         */
        ArrayNode newListenerItems = ModelUtil.objectMapper().createArrayNode();
        /**
         * 注册自定义决策器
         */
        registerExecutionListener(properties, newListenerItems, startExecutionListener, endExecutionListener);
    }

    /**
     * 创建流程监听�?
     *
     * @param properties
     * @param newListenerItems
     * @param startExecutionListener
     * @param endExecutionListener
     */
    public static void registerExecutionListener(ObjectNode properties, ArrayNode newListenerItems, Listener startExecutionListener, Listener endExecutionListener) {
        /**
         * 创建监听器节�?
         */
        if (null == newListenerItems) {
            newListenerItems = ModelUtil.objectMapper().createArrayNode();
        }
        /**
         * �?始监听器
         */
        if (null != startExecutionListener) {
            ListenerUtil.buildExecutionListener(newListenerItems, startExecutionListener.getClassName(), "", startExecutionListener.getType(), "", "", null);
        }
        /**
         * 结束监听�?
         */
        if (null != endExecutionListener) {
            ListenerUtil.buildExecutionListener(newListenerItems, endExecutionListener.getClassName(), "", endExecutionListener.getType(), "", "", null);
        }
        /**
         * 创建监听器节�?
         */
        ObjectNode newListeners = ModelUtil.objectMapper().createObjectNode();
        newListeners.put(Constants.Tags.EXECUTION_LISTENER_ITEMS, newListenerItems);
        properties.put(Constants.Tags.EXECUTION_LISTENER, newListeners);
    }

    /**
     * 会签规则判断
     *
     * @param signRule   会签规则
     * @param allTaskNum �?有任务数
     * @param passNum    任务通过�?
     * @param finishNum  任务完成�?
     * @return
     */
    public static String hasSignTaskPass(String signRule, int allTaskNum, int passNum, int finishNum) {
        /**
         * �?票否决制
         */
        if (Constants.VoteRule.ONE_DENY.equals(signRule)) {
            return SignVoteRule.doOneDeny(allTaskNum, finishNum, passNum);
        }
        /**
         * �?票�?�过�?
         */
        if (Constants.VoteRule.ONE_AGREE.equals(signRule)) {
            return SignVoteRule.doOneAgree(allTaskNum, finishNum, passNum);
        }
        /**
         * 半数通过�?
         */
        if (Constants.VoteRule.PER_50_AGREE.equals(signRule)) {
            return SignVoteRule.doPerAgree(allTaskNum, finishNum, passNum, 0.5f);
        }
        /**
         * 60%通过�?
         */
        if (Constants.VoteRule.PER_60_AGREE.equals(signRule)) {
            return SignVoteRule.doPerAgree(allTaskNum, finishNum, passNum, 0.6f);
        }
        return Constants.SIGN_TASK_STATUS_RUNNING;
    }

    /**
     * 会签投票规则
     */
    private static class SignVoteRule {
        /**
         * �?票否决制
         */
        public static String doOneDeny(int allTaskNum, int finishNum, int passNum) {
            if (SignVoteRule.hasOneDeny(finishNum, passNum)) {
                return Constants.SIGN_TASK_STATUS_FAILEUR;
            }
            if (finishNum == allTaskNum) {
                return Constants.SIGN_TASK_STATUS_SUCCESS;
            }
            return Constants.SIGN_TASK_STATUS_RUNNING;
        }

        /**
         * �?票�?�过�?
         */
        public static String doOneAgree(int allTaskNum, int finishNum, int passNum) {
            if (SignVoteRule.hasOneAgree(passNum)) {
                return Constants.SIGN_TASK_STATUS_SUCCESS;
            }
            if (finishNum == allTaskNum) {
                return Constants.SIGN_TASK_STATUS_FAILEUR;
            }
            return Constants.SIGN_TASK_STATUS_RUNNING;
        }

        /**
         * 百分比�?�过�?
         */
        public static String doPerAgree(int allTaskNum, int finishNum, int passNum, float ratio) {
            if (SignVoteRule.hasMorePerAgree(allTaskNum, passNum, ratio)) {
                return Constants.SIGN_TASK_STATUS_SUCCESS;
            }
            int failNum = finishNum - passNum;
            if (SignVoteRule.hasMorePerDeny(allTaskNum, failNum, 1 - ratio)) {
                return Constants.SIGN_TASK_STATUS_FAILEUR;
            }
            return Constants.SIGN_TASK_STATUS_RUNNING;
        }

        public static boolean hasOneDeny(int finishNum, int passNum) {
            return passNum < finishNum;
        }

        public static boolean hasOneAgree(int passNum) {
            return passNum >= 1;
        }

        public static boolean hasMorePerAgree(int allTaskNum, int passNum, float ratio) {
            return (float) passNum / (float) allTaskNum >= ratio;
        }

        public static boolean hasMorePerDeny(int allTaskNum, int failNum, float ratio) {
            return (float) failNum / (float) allTaskNum > ratio;
        }
    }
}
