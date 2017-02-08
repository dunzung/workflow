package com.jund.workflow.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jund.workflow.Constants;
import com.jund.workflow.entity.dto.ActivityDto;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;


public class ActivityUtil {

    /**
     * 获取未审批过节点
     *
     * @param currentActivity
     * @return
     */
    public static List<ActivityDto> getNextActivities(ActivityImpl currentActivity) {
        /**
         * 获取节点路由
         */
        List<PvmTransition> transitions = currentActivity.getOutgoingTransitions();
        /**
         * 封装流转节点
         */
        List<ActivityDto> dtoes = new ArrayList<ActivityDto>();
        for (PvmTransition transition : transitions) {
            ActivityDto dto = new ActivityDto();
            /**
             * 活动节点ID
             */
            PvmActivity activity = transition.getDestination();
            /**
             * 活动节点名称
             */
            String activityName = (String) activity.getProperty("name");
            dto.setActivityName(activityName);
            dto.setActivityCode(transition.getDestination().getId());
            /**
             * 活动节点类型
             */
            String taskType = (String) transition.getDestination().getProperty("type");
            dto.setActivityType(taskType);
            dto.setProDefId(activity.getProcessDefinition().getId());
            dtoes.add(dto);
        }
        return dtoes;
    }

    public static List<ActivityDto> getActivitiesById(String[] activityIds, List<ActivityImpl> activities) {
    	/**
         * 封装流转节点
         */
        Set<String> dtoSet = new HashSet<String>(Arrays.asList(activityIds));
        List<ActivityDto> dtoes = new ArrayList<ActivityDto>();
        for (ActivityImpl activity : activities) {
            if ( ! dtoSet.contains(activity.getId())) continue;
            ActivityDto dto = new ActivityDto();
            /**
             * 活动节点名称
             */
            String name = (String)activity.getProperty("name");
            dto.setActivityName(StringUtils.isBlank(name) ? activity.getId() : name);
            dto.setActivityCode(activity.getId());
            dto.setActivityType((String)activity.getProperty("type"));
            dto.setProDefId(activity.getProcessDefinition().getId());
            dtoes.add(dto);
            dtoSet.add(dto.getActivityCode());
        }
        return dtoes;
	}

    /**
     * 清空指定活动节点流向
     *
     * @param activity
     * @return
     */
    public static List<PvmTransition> clearTransitions(ActivityImpl activity) {
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        return oriPvmTransitionList;
    }

    /**
     * @param activity
     * @param oriPvmTransitionList
     */
    public static void setTransitions(ActivityImpl activity, List<PvmTransition> oriPvmTransitionList) {
        List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
        //清空现有流向
        pvmTransitionList.clear();
        //添加指定流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 返回指定节点与结束节点之间的�?有Task类型节点
     *
     * @param activity 流程节点
     * @return Map<String, Activity>
     */
    public static Map<String, ActivityImpl> betweenEndTaskActivity(ActivityImpl activity) {
        if (activity == null)
            return Collections.emptyMap();

        Map<String, ActivityImpl> betweenNames = new HashMap<String, ActivityImpl>();
        Set<String> beforeNames = new HashSet<String>();
        betweenEndTaskActivityImpl(activity, betweenNames, beforeNames);
        return betweenNames;
    }

    private static void betweenEndTaskActivityImpl(ActivityImpl activity, Map<String, ActivityImpl> betweenNames, Set<String> beforeNames) {
        Object type = activity.getProperty("type");
        if (Constants.ActivityNode.END.equals(type)) {
            return;
        }
        Object activityName = activity.getProperty("name");
        if (beforeNames.contains(activityName)) {
            return;
        }
        beforeNames.add(activityName.toString());
        if (Constants.ActivityNode.TASK.equals(type)) {
            betweenNames.put(activityName.toString(), activity);
        }
        List<? extends PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        for (PvmTransition transition : outgoingTransitions) {
            ActivityImpl destination = (ActivityImpl) transition.getDestination();
            betweenEndTaskActivityImpl(destination, betweenNames, beforeNames);
        }
    }

    /**
     * 返回当前节点流向的下�?个节�?, 多条流向将返回多个节�?
     *
     * @param activity 流程节点
     */
    public static Map<String, ActivityImpl> nextActivity(ActivityImpl activity) {
        if (activity == null)
            return Collections.emptyMap();

        Map<String, ActivityImpl> nextActivitys = new HashMap<String, ActivityImpl>();
        Set<String> beforeNames = new HashSet<String>();
        nextActivityImpl(activity, nextActivitys, beforeNames);

        return nextActivitys;
    }

    private static void nextActivityImpl(ActivityImpl activity, Map<String, ActivityImpl> nextActivitys, Set<String> beforeNames) {
        String currType = (String) activity.getProperty("type");
        if (Constants.ActivityNode.END.equals(currType)) {
            return;
        }

        String activityName = (String) activity.getProperty("name");
        if (beforeNames.contains(activityName))
            return;

        beforeNames.add(activityName);
        if (Constants.ActivityNode.DECISION.equals(currType)) {
            nextActivitys.put(activityName, activity);
            List<? extends PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
            for (PvmTransition transition : outgoingTransitions) {
                PvmActivity destination = transition.getDestination();
                nextActivityImpl((ActivityImpl) destination, nextActivitys, beforeNames);
            }
        } else if (Constants.ActivityNode.TASK.equals(currType) || Constants.ActivityNode.END.equals(currType)) {
            nextActivitys.put(activityName, activity);
        } else {
            List<? extends PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
            for (PvmTransition transition : outgoingTransitions) {
                PvmActivity destination = transition.getDestination();
                nextActivityImpl((ActivityImpl) destination, nextActivitys, beforeNames);
            }
        }

        List<? extends PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
        for (PvmTransition transition : outgoingTransitions) {
            PvmActivity destination = transition.getDestination();
            String name = (String) destination.getProperty("name");
            String type = (String) destination.getProperty("type");

            if (Constants.ActivityNode.DECISION.equals(type)) {
                nextActivitys.put(name, (ActivityImpl) destination);
                nextActivityImpl((ActivityImpl) destination, nextActivitys, beforeNames);
            } else if (Constants.ActivityNode.TASK.equals(type) || Constants.ActivityNode.END.equals(type)) {
                nextActivitys.put(name, (ActivityImpl) destination);
            } else {
                nextActivityImpl((ActivityImpl) destination, nextActivitys, beforeNames);
            }
        }
    }

    public static ActivityImpl getActivityByProDef(ProcessDefinition procDef, String taskCode) {
        if (StringUtils.isBlank(taskCode) || procDef == null) return null;
        List<ActivityImpl> activits = ((ProcessDefinitionEntity) procDef).getActivities();
        ActivityImpl activity = null;
        for (ActivityImpl activityImpl : activits) {
            if (taskCode.equals(activityImpl.getId())) {
                activity = activityImpl;
                break;
            }
        }
        return activity;
    }

}
