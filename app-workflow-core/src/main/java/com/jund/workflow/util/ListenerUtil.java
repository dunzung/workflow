package com.jund.workflow.util;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jund.workflow.Constants;

/**
 * Created by killer_duan on 2015/11/9.
 */
public final class ListenerUtil {

    public static void buildTaskListener(ArrayNode newListeners, String className, String implementation, String eventType, String delegateExpression, String expression, ObjectNode fields) {
        buildListener(newListeners, className, implementation, eventType, delegateExpression, expression, fields);
    }

    public static void buildExecutionListener(ArrayNode newListeners, String className, String implementation, String eventType, String delegateExpression, String expression, ObjectNode fields) {
        buildListener(newListeners, className, implementation, eventType, delegateExpression, expression, fields);
    }

    public static void buildListener(ArrayNode newListeners, String className, String implementation, String eventType, String delegateExpression, String expression, ObjectNode fields) {
        ObjectNode listenerNode = ModelUtil.objectMapper().createObjectNode();
        listenerNode.put(Constants.Tags.Listener.CLASSNAME, className);
        listenerNode.put(Constants.Tags.Listener.IMPLEMENTENTATION, implementation);
        listenerNode.put(Constants.Tags.Listener.EVENT, eventType);
        listenerNode.put(Constants.Tags.Listener.DELEGATE_EXPRESSSION, delegateExpression);
        listenerNode.put(Constants.Tags.Listener.EXPRESSION, expression);
        if (null != fields) {
            listenerNode.put(Constants.Tags.Listener.FIELDS, fields);
        }
        newListeners.add(listenerNode);
    }


    /**
     * 设置默认的任务监听器
     *
     * @param newListeners
     */
    public static void setDefaultTaskStartAndEndListener(ArrayNode newListeners) {
        /**
         * 任务�?始监听器
         */
        buildTaskListener(newListeners, Constants.TaskListener.TASK_START_LISTENER, Constants.TaskListener.TASK_START_LISTENER, Constants.TaskListener.EVENTNAME_CREATE, "", "", null);
        /**
         * 结束�?始监听器
         */
        buildTaskListener(newListeners, Constants.TaskListener.TASK_END_LISTENER, Constants.TaskListener.TASK_END_LISTENER, Constants.TaskListener.EVENTNAME_COMPLETE, "", "", null);
    }

    /**
     * 设置默认的决策监听器
     */
    public static void setDefaultGatewayStartAndEndListener(ArrayNode newListeners) {
        /**
         * 决策结束监听�?
         */
        buildExecutionListener(newListeners, Constants.ExecutionListener.EXECUTION_END_LISTENER, Constants.ExecutionListener.EXECUTION_END_LISTENER, Constants.ExecutionListener.EVENTNAME_END, "", "", null);
    }
}
