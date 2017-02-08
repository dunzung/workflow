package com.jund.workflow.entity.dto;

import java.util.Date;

/**
 * 流程定义模型
 * Created by killer_duan on 2015/9/14.
 */
public class ProcessModelDto {

    private String modelId;

    private String modelName;

    private String modelVersion;

    /**
     * 生效标志
     */
    private String status;

    private String json_xml;

    private String svg_xml;

    private String metaInfo;

    private Date createdTm;

    private String description;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getJson_xml() {
        return json_xml;
    }

    public void setJson_xml(String json_xml) {
        this.json_xml = json_xml;
    }

    public String getSvg_xml() {
        return svg_xml;
    }

    public void setSvg_xml(String svg_xml) {
        this.svg_xml = svg_xml;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTm() {
        return createdTm;
    }

    public void setCreatedTm(Date createdTm) {
        this.createdTm = createdTm;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
