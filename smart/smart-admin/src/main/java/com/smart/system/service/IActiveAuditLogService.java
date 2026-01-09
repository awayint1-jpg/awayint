package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveAuditLog;

/**
 * 活动审批流水记录服务层接口
 *
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveAuditLogService {

    /**
     * 新增审批流水记录
     *
     * @param auditLog 审批流水记录实体
     * @return 插入操作影响的行数
     */
    public int insertActiveAuditLog(ActiveAuditLog auditLog);

    /**
     * 更新审批流水记录信息
     *
     * @param auditLog 审批流水记录实体
     * @return 更新操作影响的行数
     */
    public int updateActiveAuditLog(ActiveAuditLog auditLog);

    /**
     * 批量删除审批流水记录
     *
     * @param logIds 需要删除的记录主键数组
     * @return 删除操作影响的行数
     */
    public int deleteActiveAuditLogByLogIds(Long[] logIds);

    /**
     * 删除单条审批流水记录
     *
     * @param logId 记录主键ID
     * @return 删除操作影响的行数
     */
    public int deleteActiveAuditLogByLogId(Long logId);

    /**
     * 根据主键查询审批流水记录详情
     *
     * @param logId 记录主键ID
     * @return 审批流水记录实体
     */
    public ActiveAuditLog selectActiveAuditLogByLogId(Long logId);

    /**
     * 条件查询审批流水记录列表
     *
     * @param auditLog 查询条件实体
     * @return 审批流水记录集合
     */
    public List<ActiveAuditLog> selectActiveAuditLogList(ActiveAuditLog auditLog);
}