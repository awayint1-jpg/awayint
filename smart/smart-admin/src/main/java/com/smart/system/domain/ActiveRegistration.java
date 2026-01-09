package com.smart.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.smart.common.annotation.Excel;
import com.smart.common.core.domain.BaseEntity;
/**
 * 活动报名与打卡记录实体
 *
 * @author smart
 * @date 2026-01-07
 */
public class ActiveRegistration extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 打卡状态(0未打卡 1已打卡) */
    @Excel(name = "打卡状态(0未打卡 1已打卡)")
    private String checkInStatus;

    /** 关联活动ID */
    @Excel(name = "关联活动ID")
    private Long activityId;

    /** 报名人姓名(冗余字段，方便快速查询) */
    @Excel(name = "报名人姓名(冗余字段，方便快速查询)")
    private String userName;

    /** 打卡照片路径(OSS或本地路径) */
    @Excel(name = "打卡照片路径(OSS或本地路径)")
    private String checkInPhoto;

    /** 报名用户ID(关联sys_user) */
    @Excel(name = "报名用户ID(关联sys_user)")
    private Long userId;

    /** 手机号(冗余字段) */
    @Excel(name = "手机号(冗余字段)")
    private String phonenumber;

    /** 打卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打卡时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkInTime;

    /** 报名主键ID */
    private Long regId;

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckInPhoto() {
        return checkInPhoto;
    }

    public void setCheckInPhoto(String checkInPhoto) {
        this.checkInPhoto = checkInPhoto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("checkInStatus", getCheckInStatus())
                .append("activityId", getActivityId())
                .append("userName", getUserName())
                .append("checkInPhoto", getCheckInPhoto())
                .append("userId", getUserId())
                .append("phonenumber", getPhonenumber())
                .append("checkInTime", getCheckInTime())
                .append("regId", getRegId())
                .append("createTime", getCreateTime())
                .toString();
    }
}