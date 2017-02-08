package com.jund.workflow;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jund.workflow.entity.dto.ProcessDefinitionDto;
import com.jund.workflow.entity.dto.ProcessModelDto;
import com.jund.workflow.util.ModelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 流程定义Controller
 *
 * @Author Created by killer_duan
 * @Since 2015/6/29
 */
@Controller
@RequestMapping("/workflow/definition")
public class ProcessDefinitionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionController.class);

    @RequestMapping("/index")
    public String index() {
        return "workflow/definition/definition_index";
    }

    /**
     * 流程设计器
     *
     * @return
     */
    @RequestMapping("/designer")
    public ModelAndView designer(ProcessModelDto dto) {
        ModelAndView modelAndView = new ModelAndView("workflow/definition/definition_designer");
        modelAndView.addObject("modelId", dto.getModelId());
        return modelAndView;
    }

    /**
     * 流程模型设计器元素构件
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/designer/stencilset")
    @ResponseBody
    public String stencilset() throws IOException {
        InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("/workflow/stencilset.json");
        return IOUtils.toString(stencilsetStream, "utf-8");
    }


    /**
     * 流程模型详细数据
     *
     * @param processDefinitionId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail/{processDefinitionId}")
    @ResponseBody
    public ObjectNode detail(@PathVariable String processDefinitionId) {
        ObjectNode modelNode = null;
        ObjectMapper objectMapper = ModelUtil.objectMapper();
//        try {
//            ProcessDefinitionDto dto = processDefinitionService.findProcessDefinitionBy(processDefinitionId);
//            modelNode = (!StringUtils.isEmpty(dto.getMetaInfo())) ? objectMapper.createObjectNode() : (ObjectNode) objectMapper.readTree(dto.getMetaInfo());
//            modelNode.put("modelId", dto.getModelId());
//            modelNode.put("name", dto.getModelName());
//            modelNode.put("description", dto.getDescription());
//            byte[] treeNode = processDefinitionService.getModelEditorSource(dto.getModelId());
//            modelNode.put("model", objectMapper.readTree(new String(treeNode, "utf-8")));
//        } catch (Exception e) {
        //  LOGGER.error("查询详细失败", e.getMessage());  // }
        modelNode = objectMapper.createObjectNode();
        modelNode.put("modelId", Constants.MetaModel.DEFAULT_MODEL_ID);
        modelNode.put("name", "");
        modelNode.put("description", "");
        modelNode.put("model", ModelUtil.getDefualtEditInfo());
        return modelNode;
    }

}
