package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveAuditLog;

/**
 * 活动审批流水记录Service接口
 * 
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveAuditLogService 
{
    /**
     * 查询活动审批流水记录
     * 
     * @param logId 活动审批流水记录主键
     * @return 活动审批流水记录
     */
    public ActiveAuditLog selectActiveAuditLogByLogId(Long logId);

    /**
     * 查询活动审批流水记录列表
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 活动审批流水记录集合
     */
    public List<ActiveAuditLog> selectActiveAuditLogList(ActiveAuditLog activeAuditLog);

    /**
     * 新增活动审批流水记录
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 结果
     */
    public int insertActiveAuditLog(ActiveAuditLog activeAuditLog);

    /**
     * 修改活动审批流水记录
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 结果
     */
    public int updateActiveAuditLog(ActiveAuditLog activeAuditLog);

    /**
     * 批量删除活动审批流水记录
     * 
     * @param logIds 需要删除的活动审批流水记录主键集合
     * @return 结果
     */
    public int deleteActiveAuditLogByLogIds(Long[] logIds);

    /**
     * 删除活动审批流水记录信息
     * 
     * @param logId 活动审批流水记录主键
     * @return 结果
     */
    public int deleteActiveAuditLogByLogId(Long logId);
}
