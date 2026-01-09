package com.smart.system.mapper;

import java.util.List;
import com.smart.system.domain.ActiveAuditLog;
/**
 * 活动审批操作日志数据访问接口
 *
 * @author smart
 * @date 2026-01-07
 */
public interface ActiveAuditLogMapper {

    /**
     * 新增审批日志记录
     *
     * @param log 审批日志实体
     * @return 插入结果（影响行数）
     */
    public int insertActiveAuditLog(ActiveAuditLog log);

    /**
     * 更新审批日志信息
     *
     * @param log 审批日志实体
     * @return 更新结果（影响行数）
     */
    public int updateActiveAuditLog(ActiveAuditLog log);

    /**
     * 根据日志ID删除单条记录
     *
     * @param logId 日志主键ID
     * @return 删除结果（影响行数）
     */
    public int deleteActiveAuditLogByLogId(Long logId);

    /**
     * 批量删除审批日志记录
     *
     * @param logIds 需要删除的日志ID数组
     * @return 删除结果（影响行数）
     */
    public int deleteActiveAuditLogByLogIds(Long[] logIds);

    /**
     * 根据日志ID查询详细信息
     *
     * @param logId 日志主键ID
     * @return 审批日志实体
     */
    public ActiveAuditLog selectActiveAuditLogByLogId(Long logId);

    /**
     * 条件查询审批日志列表
     *
     * @param log 查询条件实体
     * @return 审批日志集合
     */
    public List<ActiveAuditLog> selectActiveAuditLogList(ActiveAuditLog log);
}