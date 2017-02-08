package com.jund.workflow.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jund.workflow.Constants;
import com.jund.workflow.entity.NodeVariable;
import com.jund.workflow.entity.dto.NodeVariableDto;
import com.jund.workflow.entity.dto.ProcessModelDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 流程扩展节点
 * <p/>
 * Created by killer_duan on 2015/10/19.
 */
public class NodeVariableUtil {
    /**
     * 获取扩展属�??
     *
     * @param metaNode
     * @return
     */
    public static List<NodeVariable> reverseMetaNode(ObjectNode metaNode, String modelId) {
        return reverseMetaNode(metaNode, modelId, null);
    }

    public static List<NodeVariable> reverseMetaNode(byte[] metaBytes, String modelId) {
        try {
            ObjectNode metaNode = ModelUtil.getMetaNode(metaBytes);
            return reverseMetaNode(metaNode, modelId, null);
        } catch (Exception e) {
            //throw new RMPRuntimeException("流程定义元数据解析异常！");
        }
        return  null;
    }

    /**
     * 获取扩展属�??
     *
     * @param metaNode
     * @param modelId
     * @param procDefId
     * @return
     */
    public static List<NodeVariable> reverseMetaNode(ObjectNode metaNode, String modelId, String procDefId) {
        List<NodeVariable> variables = new ArrayList<NodeVariable>();

        validateVariable(metaNode);

        Iterator<JsonNode> iterator = getChildShapes(metaNode).iterator();
        for (; iterator.hasNext(); ) {
            JsonNode elementNode = iterator.next();
            JsonNode properties = getProperties(elementNode);

            NodeVariable variable = new NodeVariable();
            variable.setNodeId(getId(properties));
            variable.setNodeName(getName(properties));
            variable.setApproveOrganCode(getApproveOrganCode(properties));
            variable.setApproveRoleCode(getApproveRoleCode(properties));
            variable.setApproveUserName(getApproveUserid(properties));
            variable.setRollbackRule(getRollbackRule(properties));
            variable.setApproveType(getApproveType(properties));
            variable.setConsignAble(getConsignAble(properties));
            if (NodeVariableUtil.hasStencilNode(elementNode, Constants.TagVal.GATEWAY_TENCIL_ID)) {
                variable.setListenerStart(getGatewayListenerStart(properties));
                variable.setListenerEnd(getGatewayListenerEnd(properties));
            }
            if (NodeVariableUtil.hasStencilNode(elementNode, Constants.TagVal.TASK_STENCIL_ID)) {
                variable.setListenerStart(getTaskListenerStart(properties));
                variable.setListenerEnd(getTaskListenerEnd(properties));
            }
            if (NodeVariableUtil.hasStencilNode(elementNode, Constants.TagVal.END_NONE_STENCIL_ID)
                    || NodeVariableUtil.hasStencilNode(elementNode, Constants.TagVal.START_NONE_STENCIL_ID)) {
                variable.setListenerStart(getStartNoneListenerStart(properties));
                variable.setListenerEnd(getEndNoneListenerEnd(properties));
            }

            variable.setVoteType(getVoteType(properties));
            variable.setSignType(getSignType(properties));
            variable.setModelId(modelId);
            variable.setProcDefId(procDefId);

            variables.add(variable);
        }

        return variables;
    }

    public static void addNodeVariables(ObjectNode metaNode, List<NodeVariableDto> variables) {
        if (CollectionUtils.isNotEmpty(variables)) {
            List<ObjectNode> nodes = new ArrayList<ObjectNode>();
            for (NodeVariableDto dto : variables) {
                ObjectNode variableNode = new ObjectMapper().createObjectNode();
                variableNode.put(Constants.Tags.BACKRULE, hasVariableEmpty(dto.getRollbackRule()));
                variableNode.put(Constants.Tags.APPROVE_ORGANCODE, hasVariableEmpty(dto.getApproveOrganCode()));
                variableNode.put(Constants.Tags.APPROVE_USERID, hasVariableEmpty(dto.getApproveUserName()));
                variableNode.put(Constants.Tags.CONSIGNABLE, hasVariableEmpty(dto.getConsignAble()));
                variableNode.put(Constants.Tags.APPROVETYPE, hasVariableEmpty(dto.getApproveType()));

//                if (NodeVariableUtil.hasStencilNode(properties, Constants.TagVal.GATEWAY_TENCIL_ID)) {
//                    variable.setListenerStart(getGatewayListenerStart(properties));
//                    variable.setListenerEnd(getGatewayListenerEnd(properties));
//                }
//                if (NodeVariableUtil.hasStencilNode(properties, Constants.TagVal.TASK_STENCIL_ID)) {
//                    variableNode.put(Constants.Tags.TASK_START_LISTENER, hasVariableEmpty(dto.getListenerStart()));
//                    variableNode.put(Constants.Tags.TASK_END_LISTENER, hasVariableEmpty(dto.getListenerEnd()));
//                }
//                if (NodeVariableUtil.hasStencilNode(properties, Constants.TagVal.END_NONE_STENCIL_ID)
//                        || NodeVariableUtil.hasStencilNode(properties, Constants.TagVal.START_NONE_STENCIL_ID)) {
//                    variable.setListenerStart(getStartNoneListenerStart(properties));
//                    variable.setListenerEnd(getEndNoneListenerEnd(properties));
//                }

                variableNode.put(Constants.Tags.TASK_START_LISTENER, hasVariableEmpty(dto.getListenerStart()));
                variableNode.put(Constants.Tags.TASK_END_LISTENER, hasVariableEmpty(dto.getListenerEnd()));

                variableNode.put(Constants.Tags.NODE_ID, hasVariableEmpty(dto.getNodeId()));
                variableNode.put(Constants.Tags.SIGN_TYPE, hasVariableEmpty(dto.getSignType()));
                variableNode.put(Constants.Tags.VOTE_TYPE, hasVariableEmpty(dto.getVoteType()));
                variableNode.put(Constants.Tags.NODE_NAME, hasVariableEmpty(dto.getNodeName()));
                nodes.add(variableNode);
            }
            metaNode.putArray(Constants.Tags.EX_NODES).addAll(nodes);
        }
    }

    public static List<NodeVariableDto> getNodeVariables(List<NodeVariable> variables, ProcessModelDto modelDto) {
        List<NodeVariableDto> nodes = new ArrayList<NodeVariableDto>();
        if (CollectionUtils.isNotEmpty(variables)) {
            for (NodeVariable entity : variables) {
               // NodeVariableDto dto = new NodeVariableConverter(modelDto).convertDto(entity);
               // nodes.add(dto);
            }
        }
        return nodes;
    }

    public static void removeExNodesTag(ObjectNode nodesVariables) {
        if (nodesVariables.has(Constants.Tags.EX_NODES)) {
            nodesVariables.remove(Constants.Tags.EX_NODES);
        }
    }

    public static String getId(JsonNode properties) {
        return (properties.has(Constants.Tags.ID)) ? properties.get(Constants.Tags.ID).textValue() : "";
    }

    public static String getName(JsonNode properties) {
        return (properties.has(Constants.Tags.NAME)) ? properties.get(Constants.Tags.NAME).textValue() : "";
    }

    public static String getDefinitionName(ObjectNode metaNode) {
        return metaNode.get(Constants.Tags.PROPS).get(Constants.Tags.NAME).textValue();
    }


    public static String getDefinitionKey(ObjectNode metaNode) {
        return metaNode.get(Constants.Tags.PROPS).get(Constants.Tags.PID).textValue();
    }

    public static void updateDefinitionKey(ObjectNode metaNode, String defineCode) {
        ObjectNode properties = (ObjectNode) metaNode.get(Constants.Tags.PROPS);
        properties.put(Constants.Tags.PID, defineCode);
    }

    public static void updateDefinitionName(ObjectNode metaNode, String defineName) {
        ObjectNode properties = (ObjectNode) metaNode.get(Constants.Tags.PROPS);
        properties.put(Constants.Tags.NAME, defineName);
    }

//    public static void updateDefinitionDocumentation(ObjectNode metaNode, String documentation) {
//        ObjectNode properties = (ObjectNode) metaNode.get(Constants.Tags.PROPS);
//        properties.put(Constants.Tags.DOCUMENTATION, documentation);
//    }
//
//    public static String getDefinitionDocumentation(JsonNode properties) {
//        ObjectNode properties = (ObjectNode) metaNode.get(Constants.Tags.PROPS);
//        return (properties.has(Constants.Tags.DOCUMENTATION)) ? properties.get(Constants.Tags.DOCUMENTATION).textValue() : "";
//    }

    public static JsonNode getProperties(JsonNode targetNode) {
        return targetNode.get(Constants.Tags.PROPS);
    }

    public static void setProperties(JsonNode targetNode) {
        targetNode.get(Constants.Tags.PROPS);
    }

    public static JsonNode getChildShapes(ObjectNode metaNode) {
        return metaNode.get(Constants.Tags.CHILD_SHAPES);
    }

    public static JsonNode getStencil(JsonNode taskNode) {
        return taskNode.get(Constants.Tags.STENCIL);
    }

    public static String getRollbackRule(JsonNode properties) {
        return (properties.has(Constants.Tags.BACKRULE)) ? properties.get(Constants.Tags.BACKRULE).textValue() : "";
    }

    public static String getConsignAble(JsonNode properties) {
        return (properties.has(Constants.Tags.CONSIGNABLE)) ? properties.get(Constants.Tags.CONSIGNABLE).textValue() : "";
    }

    public static String getApproveType(JsonNode properties) {
        return (properties.has(Constants.Tags.APPROVETYPE)) ? properties.get(Constants.Tags.APPROVETYPE).textValue() : "";
    }

    public static String getStartNoneListenerStart(JsonNode properties) {
        return (properties.has(Constants.Tags.STARTNONE_START_LISTENER)) ? properties.get(Constants.Tags.STARTNONE_START_LISTENER).textValue() : "";
    }

    public static String getEndNoneListenerEnd(JsonNode properties) {
        return (properties.has(Constants.Tags.ENDNONE_END_LISTENER)) ? properties.get(Constants.Tags.ENDNONE_END_LISTENER).textValue() : "";
    }

    public static String getTaskListenerStart(JsonNode properties) {
        return (properties.has(Constants.Tags.TASK_START_LISTENER)) ? properties.get(Constants.Tags.TASK_START_LISTENER).textValue() : "";
    }

    public static String getTaskListenerEnd(JsonNode properties) {
        return (properties.has(Constants.Tags.TASK_END_LISTENER)) ? properties.get(Constants.Tags.TASK_END_LISTENER).textValue() : "";
    }

    public static String getGatewayListenerStart(JsonNode properties) {
        return (properties.has(Constants.Tags.GATEWAY_START_LISTENER)) ? properties.get(Constants.Tags.GATEWAY_START_LISTENER).textValue() : "";
    }

    public static String getGatewayListenerEnd(JsonNode properties) {
        return (properties.has(Constants.Tags.GATEWAY_END_LISTENER)) ? properties.get(Constants.Tags.GATEWAY_END_LISTENER).textValue() : "";
    }

    public static String getApproveOrganCode(JsonNode properties) {
        return (properties.has(Constants.Tags.APPROVE_ORGANCODE)) ? properties.get(Constants.Tags.APPROVE_ORGANCODE).textValue() : "";
    }

    public static String getApproveRoleCode(JsonNode properties) {
        return (properties.has(Constants.Tags.APPROVE_ROLECODE)) ? properties.get(Constants.Tags.APPROVE_ROLECODE).textValue() : "";
    }

    private static String getVoteType(JsonNode properties) {
        return (properties.has(Constants.Tags.VOTE_TYPE)) ? properties.get(Constants.Tags.VOTE_TYPE).textValue() : "";
    }

    private static String getSignType(JsonNode properties) {
        return (properties.has(Constants.Tags.SIGN_TYPE)) ? properties.get(Constants.Tags.SIGN_TYPE).textValue() : "";
    }

    public static String getApproveUserid(JsonNode properties) {
        return (properties.has(Constants.Tags.APPROVE_USERID)) ? properties.get(Constants.Tags.APPROVE_USERID).textValue() : "";
    }

    public static String hasVariableEmpty(Object target) {
        if (target instanceof JsonNode) {
            return (StringUtils.isEmpty(target)) ? "" : ((JsonNode) target).textValue();
        }
        return (StringUtils.isEmpty(target)) ? "" : target.toString();
    }

    public static void validateVariable(ObjectNode metaNode) {
        if (StringUtils.isEmpty(metaNode)) {
            //throw new RMPRuntimeException("获取节点属�?�数据失败！");
        }

        String definitionKey = getDefinitionKey(metaNode);
        if (StringUtils.isEmpty(definitionKey)) {
            //throw new RMPRuntimeException("获取流程定义编号为空�?");
        }

        String definitionName = getDefinitionName(metaNode);
        if (StringUtils.isEmpty(definitionName)) {
          //  throw new RMPRuntimeException("获取流程定义名称为空�?");
        }

        JsonNode childShapes = getChildShapes(metaNode);
        if (StringUtils.isEmpty(childShapes) || childShapes.size() == 0) {
          //  throw new RMPRuntimeException("节点属�?�数据为空！");
        }
    }

    public static boolean hasStencilNode(JsonNode elementNode, String stencilName) {
        Object stencil_id = getStencil(elementNode).get("id").asText();
        if (null == stencil_id || null == stencilName) {
            return false;
        }
        return stencil_id.toString().equals(stencilName);
    }
}
