package com.smart.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smart.system.mapper.ActiveAuditLogMapper;
import com.smart.system.domain.ActiveAuditLog;
import com.smart.system.service.IActiveAuditLogService;

/**
 * 活动审批流水记录Service业务层处理
 * 
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveAuditLogServiceImpl implements IActiveAuditLogService 
{
    @Autowired
    private ActiveAuditLogMapper activeAuditLogMapper;

    /**
     * 查询活动审批流水记录
     * 
     * @param logId 活动审批流水记录主键
     * @return 活动审批流水记录
     */
    @Override
    public ActiveAuditLog selectActiveAuditLogByLogId(Long logId)
    {
        return activeAuditLogMapper.selectActiveAuditLogByLogId(logId);
    }

    /**
     * 查询活动审批流水记录列表
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 活动审批流水记录
     */
    @Override
    public List<ActiveAuditLog> selectActiveAuditLogList(ActiveAuditLog activeAuditLog)
    {
        return activeAuditLogMapper.selectActiveAuditLogList(activeAuditLog);
    }

    /**
     * 新增活动审批流水记录
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 结果
     */
    @Override
    public int insertActiveAuditLog(ActiveAuditLog activeAuditLog)
    {
        return activeAuditLogMapper.insertActiveAuditLog(activeAuditLog);
    }

    /**
     * 修改活动审批流水记录
     * 
     * @param activeAuditLog 活动审批流水记录
     * @return 结果
     */
    @Override
    public int updateActiveAuditLog(ActiveAuditLog activeAuditLog)
    {
        return activeAuditLogMapper.updateActiveAuditLog(activeAuditLog);
    }

    /**
     * 批量删除活动审批流水记录
     * 
     * @param logIds 需要删除的活动审批流水记录主键
     * @return 结果
     */
    @Override
    public int deleteActiveAuditLogByLogIds(Long[] logIds)
    {
        return activeAuditLogMapper.deleteActiveAuditLogByLogIds(logIds);
    }

    /**
     * 删除活动审批流水记录信息
     * 
     * @param logId 活动审批流水记录主键
     * @return 结果
     */
    @Override
    public int deleteActiveAuditLogByLogId(Long logId)
    {
        return activeAuditLogMapper.deleteActiveAuditLogByLogId(logId);
    }
}
