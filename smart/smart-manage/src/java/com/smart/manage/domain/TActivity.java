package com.smart.manage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.smart.common.annotation.Excel;
import com.smart.common.core.domain.BaseEntity;

/**
 * 活动信息对象 t_activity
 *
 * @author smart
 * @date 2026-01-05
 */
public class TActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 活动id */
    private Long activeId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    @NotBlank(message = "活动标题不能为空")
    @Size(max = 100, message = "活动标题长度不能超过100个字符")
    private String activeTitle;

    /** 活动类型 */
    @Excel(name = "活动类型")
    private String actvieType;

    /** 活动人数 */
    @Excel(name = "活动人数")
    @Min(value = 1, message = "活动人数最少不能少于1人")
    @Max(value = 1000, message = "活动人数最多不能超过1000人")
    private Long fullNum;

    /** 活动地址 */
    @Excel(name = "活动地址")
    private String address;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activeStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activeEndTime;

    /** 活动内容 */
    @Excel(name = "活动内容")
    private String activeContent;

    /** 活动描述 */
    @Excel(name = "活动描述")
    private String description;

    // ========== 新增字段 ==========
    /** 活动状态（DRAFT-草稿，PUBLISHED-已发布，FINISHED-已结束） */
    @Excel(name = "活动状态")
    private String status;

    /** 逻辑删除标记（0-未删除，1-已删除） */
    private Integer deleted;

    // ========== 原有 getter/setter 保持不变 ==========
    public void setActiveId(Long activeId) { this.activeId = activeId; }
    public Long getActiveId() { return activeId; }
    public void setActiveTitle(String activeTitle) { this.activeTitle = activeTitle; }
    public String getActiveTitle() { return activeTitle; }
    public void setActvieType(String actvieType) { this.actvieType = actvieType; }
    public String getActvieType() { return actvieType; }
    public void setFullNum(Long fullNum) { this.fullNum = fullNum; }
    public Long getFullNum() { return fullNum; }
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return address; }
    public void setActiveStartTime(Date activeStartTime) { this.activeStartTime = activeStartTime; }
    public Date getActiveStartTime() { return activeStartTime; }
    public void setActiveEndTime(Date activeEndTime) { this.activeEndTime = activeEndTime; }
    public Date getActiveEndTime() { return activeEndTime; }
    public void setActiveContent(String activeContent) { this.activeContent = activeContent; }
    public String getActiveContent() { return activeContent; }
    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    // ========== 新增字段的 getter/setter ==========
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("activeId", getActiveId())
                .append("activeTitle", getActiveTitle())
                .append("actvieType", getActvieType())
                .append("fullNum", getFullNum())
                .append("address", getAddress())
                .append("activeStartTime", getActiveStartTime())
                .append("activeEndTime", getActiveEndTime())
                .append("activeContent", getActiveContent())
                .append("description", getDescription())
                .append("status", getStatus()) // 新增
                .append("deleted", getDeleted()) // 新增
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}