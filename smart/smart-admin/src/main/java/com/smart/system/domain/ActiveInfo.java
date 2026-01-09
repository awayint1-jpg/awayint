package com.smart.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.smart.common.annotation.Excel;
import com.smart.common.core.domain.BaseEntity;
/**
 * 社区活动信息实体类
 *
 * @author smart
 * @date 2026-01-07
 */
public class ActiveInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 活动标题 */
    @Excel(name = "活动标题")
    private String title;

    /** 活动类型(直接存字符串，支持自定义) */
    @Excel(name = "活动类型(直接存字符串，支持自定义)")
    private String activityType;

    /** 活动地点(直接存字符串，支持自定义) */
    @Excel(name = "活动地点(直接存字符串，支持自定义)")
    private String address;

    /** 活动开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 活动结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 人数上限(1-1000) */
    @Excel(name = "人数上限(1-1000)")
    private Long maxPeople;

    /** 活动描述 */
    @Excel(name = "活动描述")
    private String description;

    /** 状态(0草稿 1待审批 2已通过 3已驳回 4进行中 5已结束) */
    @Excel(name = "状态(0草稿 1待审批 2已通过 3已驳回 4进行中 5已结束)")
    private String status;

    /** 状态数组（用于多状态查询） */
    private String[] statusArray;

    /** 活动主键ID */
    private Long activityId;

    /** 删除标志(0代表存在 2代表删除) */
    private String delFlag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Long maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(String[] statusArray) {
        this.statusArray = statusArray;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("title", getTitle())
                .append("activityType", getActivityType())
                .append("address", getAddress())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("maxPeople", getMaxPeople())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("activityId", getActivityId())
                .append("delFlag", getDelFlag())
                .append("statusArray", getStatusArray())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}