package com.jinghai.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 学年表
 * @TableName jh_school_session
 */
@TableName(value ="jh_school_session")
@Data
public class JhSchoolSession implements Serializable {
    /**
     * 学年名称
     */
    @TableField(value = "school_session_name")
    private String schoolSessionName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        JhSchoolSession other = (JhSchoolSession) that;
        return (this.getSchoolSessionName() == null ? other.getSchoolSessionName() == null : this.getSchoolSessionName().equals(other.getSchoolSessionName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSchoolSessionName() == null) ? 0 : getSchoolSessionName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", school_session_name=").append(schoolSessionName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
