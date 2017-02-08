package com.jund.workflow.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jund.workflow.Constants;
import com.jund.workflow.entity.dto.ProcessDefinitionDto;
import com.jund.workflow.entity.dto.ProcessModelDto;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipInputStream;

/**
 * Created by killer_duan on 2015/10/19.
 */
public class ModelUtil extends NodeVariableUtil {

    private static final String TAG_EX_MODEL = "ex_model";

    private static final String TAG_EX_METAINFO = "metainfo";

    private static final String _JSON_SUFFIX = ".json";

    public static ObjectNode getDefualtEditInfo() {
        return getEditInfo(null);
    }

    public static ObjectNode getEditInfo(ObjectMapper objectMapper) {
        if (StringUtils.isEmpty(objectMapper)) {
            objectMapper = objectMapper();
        }

        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");

        ObjectNode designerNode = objectMapper.createObjectNode();
        designerNode.put("id", "canvas");
        designerNode.put("resourceId", "canvas");
        designerNode.put("stencilset", stencilSetNode);
        return designerNode;
    }

    public static void updateProcessDefinition(ObjectNode metaNode, ProcessDefinitionDto dto) {
        if (!StringUtils.isEmpty(dto.getDefineCode())) {
            NodeVariableUtil.updateDefinitionKey(metaNode, dto.getDefineCode());
        }
        if (StringUtils.isEmpty(dto.getModelName())) {
            NodeVariableUtil.updateDefinitionName(metaNode, dto.getDefineName());
        } else {
            NodeVariableUtil.updateDefinitionName(metaNode, dto.getModelName());
        }
    }

    public static ObjectNode addMetaInfo(ObjectMapper objectMapper, String modelName, Integer version, String descr) {
        if (StringUtils.isEmpty(objectMapper)) {
            objectMapper = new ObjectMapper();
        }
        ObjectNode modelNode = objectMapper.createObjectNode();
        updateMetaInfo(modelNode, modelName, version, descr);
        return modelNode;
    }

    public static void updateMetaInfo(ObjectNode metaNode, String modelName, Integer version, String descr) {
        metaNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
        metaNode.put(ModelDataJsonConstants.MODEL_REVISION, version);
        metaNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, descr);
    }

    public static ObjectNode getMetaNode(byte[] metaBytes) throws IOException {
        return (ObjectNode) new ObjectMapper().readTree(metaBytes);
    }

    public static ObjectNode getMetaNode(ZipInputStream barinput) throws IOException {
        byte[] metaBytes = IOUtils.toByteArray(new InputStreamReader(barinput), Constants.Charset.UTF8);
        return getMetaNode(metaBytes);
    }

    public static byte[] getBpmnMeta(ObjectNode metaNode) {
        BpmnModel bpmnModel = getBpmnJsonConverter().convertToBpmnModel(metaNode);
        byte[] bpmnMeta = getBpmnXMLConverter().convertToXML(bpmnModel);
        return bpmnMeta;
    }

    public static BpmnModel getBpmnModel(ObjectNode metaNode) {
        return getBpmnJsonConverter().convertToBpmnModel(metaNode);
    }

    public static BpmnModel getBpmnModel(byte[] bytes) throws IOException {
        ObjectNode metaNode = getMetaNode(bytes);
        return getBpmnJsonConverter().convertToBpmnModel(metaNode);
    }

    public static void addModel(Model copyModel, String key, String modelName, ObjectNode metaInfo, String tenantId) {
        copyModel.setKey(key);
        updateModel(copyModel, modelName, metaInfo, tenantId);
    }

    public static void updateModel(Model copyModel, String modelName, ObjectNode metaInfo, String tenantId) {
        copyModel.setName(modelName);
        if (null != metaInfo) {
            copyModel.setMetaInfo(metaInfo.toString());
        }
        copyModel.setTenantId(tenantId);
    }

    public static void addModelVariables(ObjectNode metaNode, ProcessModelDto dto) throws Exception {
        if (StringUtils.isEmpty(dto)) {
            throw new Exception("");
        }
        ObjectNode modelNode = metaNode.putObject(TAG_EX_MODEL);
        modelNode.put(ModelDataJsonConstants.MODEL_ID, NodeVariableUtil.hasVariableEmpty(dto.getModelId()));
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, NodeVariableUtil.hasVariableEmpty(dto.getModelName()));
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, NodeVariableUtil.hasVariableEmpty(dto.getModelVersion()));
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, NodeVariableUtil.hasVariableEmpty(dto.getDescription()));
        modelNode.put(TAG_EX_METAINFO, NodeVariableUtil.hasVariableEmpty(dto.getMetaInfo()));
    }

    public static Model getModelVariables(ObjectNode nodesVariables, Model model) throws Exception {
        if (StringUtils.isEmpty(nodesVariables) || StringUtils.isEmpty(nodesVariables.get(TAG_EX_MODEL))) {
            throw new Exception("");
        }
        JsonNode modelNode = nodesVariables.get(TAG_EX_MODEL);
        model.setName(NodeVariableUtil.hasVariableEmpty(modelNode.get(ModelDataJsonConstants.MODEL_NAME)));
        model.setMetaInfo(NodeVariableUtil.hasVariableEmpty(modelNode.get(TAG_EX_METAINFO)));
        model.setTenantId("userId");
        model.setVersion(Constants.DEFAULT_VERSION);
        if (!StringUtils.isEmpty(modelNode.get(ModelDataJsonConstants.MODEL_REVISION))) {
            model.setVersion(Integer.parseInt(modelNode.get(ModelDataJsonConstants.MODEL_REVISION).textValue()));
        }
        return model;
    }

    public static void removeExModelTag(ObjectNode nodesVariables) {
        if (nodesVariables.has(TAG_EX_MODEL)) {
            nodesVariables.remove(TAG_EX_MODEL);
        }
    }

    public static String deleteSpecialChar(String svgstr) {
        if (svgstr.contains("url(\"")) {
            svgstr = svgstr.replaceAll("url\\(\"", "url(");
        }
        if (svgstr.contains("start\")")) {
            svgstr = svgstr.replaceAll("start\"\\)", "start)");
        }
        if (svgstr.contains("end\")")) {
            svgstr = svgstr.replaceAll("end\"\\)", "end)");
        }
        return svgstr;
    }

    public static BpmnJsonConverter getBpmnJsonConverter() {
        return new BpmnJsonConverter();
    }

    public static BpmnXMLConverter getBpmnXMLConverter() {
        return new BpmnXMLConverter();
    }

    public static ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
