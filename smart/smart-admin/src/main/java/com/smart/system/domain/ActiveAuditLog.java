package com.smart.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.smart.common.annotation.Excel;
import com.smart.common.core.domain.BaseEntity;
/**
 * 活动审批操作日志实体
 *
 * @author smart
 * @date 2026-01-07
 */
public class ActiveAuditLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 操作类型(1提交 2通过 3驳回) */
    @Excel(name = "操作类型(1提交 2通过 3驳回)")
    private String operType;

    /** 关联活动ID */
    @Excel(name = "关联活动ID")
    private Long activityId;

    /** 操作人姓名(取当前登录用户) */
    @Excel(name = "操作人姓名(取当前登录用户)")
    private String operName;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operTime;

    /** 日志主键ID */
    private Long logId;

    /** 审批备注/驳回原因 */
    @Excel(name = "审批备注/驳回原因")
    private String auditRemark;

    /** 操作内容(例: 状态由待审批变更为已驳回) */
    @Excel(name = "操作内容(例: 状态由待审批变更为已驳回)")
    private String operContent;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperContent() {
        return operContent;
    }

    public void setOperContent(String operContent) {
        this.operContent = operContent;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("operType", getOperType())
                .append("activityId", getActivityId())
                .append("operName", getOperName())
                .append("operTime", getOperTime())
                .append("logId", getLogId())
                .append("auditRemark", getAuditRemark())
                .append("operContent", getOperContent())
                .toString();
    }
}