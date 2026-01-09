package com.smart.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smart.system.mapper.ActiveAuditLogMapper;
import com.smart.system.domain.ActiveAuditLog;
import com.smart.system.service.IActiveAuditLogService;

/**
 * 活动审批日志业务处理服务实现
 *
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveAuditLogServiceImpl implements IActiveAuditLogService {

    @Autowired
    private ActiveAuditLogMapper logMapper;

    /**
     * 批量删除审批日志记录
     *
     * @param logIds 需要删除的日志主键数组
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveAuditLogByLogIds(Long[] logIds) {
        return logMapper.deleteActiveAuditLogByLogIds(logIds);
    }

    /**
     * 删除单条审批日志记录
     *
     * @param logId 日志主键ID
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveAuditLogByLogId(Long logId) {
        return logMapper.deleteActiveAuditLogByLogId(logId);
    }

    /**
     * 新增审批日志记录
     *
     * @param log 审批日志实体
     * @return 插入操作影响的行数
     */
    @Override
    public int insertActiveAuditLog(ActiveAuditLog log) {
        return logMapper.insertActiveAuditLog(log);
    }

    /**
     * 更新审批日志信息
     *
     * @param log 审批日志实体
     * @return 更新操作影响的行数
     */
    @Override
    public int updateActiveAuditLog(ActiveAuditLog log) {
        return logMapper.updateActiveAuditLog(log);
    }

    /**
     * 根据日志ID查询详细信息
     *
     * @param logId 日志主键ID
     * @return 审批日志实体
     */
    @Override
    public ActiveAuditLog selectActiveAuditLogByLogId(Long logId) {
        return logMapper.selectActiveAuditLogByLogId(logId);
    }

    /**
     * 条件查询审批日志列表
     *
     * @param log 查询条件实体
     * @return 审批日志集合
     */
    @Override
    public List<ActiveAuditLog> selectActiveAuditLogList(ActiveAuditLog log) {
        return logMapper.selectActiveAuditLogList(log);
    }
}