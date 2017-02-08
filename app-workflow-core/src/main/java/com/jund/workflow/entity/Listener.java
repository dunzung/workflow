package com.jund.workflow.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 监听器实�?
 */
@Entity
@Table(name = "ACT_EX_LISTENER")
public class Listener implements Serializable {

    private String uuid;        //主键
    private String name;        //监听器中文名�?
    private String remark;        //描述
    private String type;        //类型  �?始�?�结�?
    private String className;    //实现类完整名�?

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

    @Column(name = "NAME_", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "REMARK_", nullable = true, length = 40)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "TYPE_", nullable = true, length = 1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "CLASS_NAME", nullable = true, length = 225)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        return result;
    }

    /**
     * @see Object#equals(Object)
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
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Listener [uuid=" + this.uuid + "]";
    }


}
