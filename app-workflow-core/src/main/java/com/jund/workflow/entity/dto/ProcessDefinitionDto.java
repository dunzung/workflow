package com.jund.workflow.entity.dto;

import java.util.Date;

/**
 * 流程定义
 * Created by killer_duan on 2015/9/14.
 */
public class ProcessDefinitionDto extends ProcessModelDto {

    /**
     * 主键
     */
    private String defineId;

    /**
     * 流程定义名称
     */
    private String defineName;

    /**
     * 流程定义编号
     */
    private String defineCode;

    /**
     * 流程定义版本
     */
    private String defineVersion;

    /**
     * 生效标志
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdTm;

    public String getDefineId() {
        return defineId;
    }

    public void setDefineId(String defineId) {
        this.defineId = defineId;
    }

    public String getDefineName() {
        return defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }

    public String getDefineCode() {
        return defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getDefineVersion() {
        return defineVersion;
    }

    public void setDefineVersion(String defineVersion) {
        this.defineVersion = defineVersion;
    }

    public Date getCreatedTm() {
        return createdTm;
    }

    public void setCreatedTm(Date createdTm) {
        this.createdTm = createdTm;
    }
}
