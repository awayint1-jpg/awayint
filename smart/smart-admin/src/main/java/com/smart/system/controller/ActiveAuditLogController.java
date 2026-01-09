package com.smart.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smart.common.annotation.Log;
import com.smart.common.core.controller.BaseController;
import com.smart.common.core.domain.AjaxResult;
import com.smart.common.enums.BusinessType;
import com.smart.system.domain.ActiveAuditLog;
import com.smart.system.service.IActiveAuditLogService;
import com.smart.common.utils.poi.ExcelUtil;
import com.smart.common.core.page.TableDataInfo;

/**
 * 活动审批日志记录控制器
 *
 * @author smart
 * @date 2026-01-07
 */
@RestController
@RequestMapping("/system/ActiveAuditLog")
public class ActiveAuditLogController extends BaseController {

    @Autowired
    private IActiveAuditLogService logService;

    /**
     * 获取审批日志详情
     */
    @GetMapping(value = "/{logId}")
    public AjaxResult getDetail(@PathVariable("logId") Long logId) {
        return success(logService.selectActiveAuditLogByLogId(logId));
    }

    /**
     * 查询审批日志列表
     */
    @GetMapping("/list")
    public TableDataInfo getList(ActiveAuditLog log) {
        startPage();
        List<ActiveAuditLog> dataList = logService.selectActiveAuditLogList(log);
        return getDataTable(dataList);
    }

    /**
     * 新增审批日志记录
     */
    @Log(title = "活动审批流水记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@RequestBody ActiveAuditLog log) {
        // 自动设置操作人为当前登录用户
        log.setOperName(getUsername());
        if (log.getOperTime() == null) {
            log.setOperTime(new java.util.Date());
        }
        return toAjax(logService.insertActiveAuditLog(log));
    }

    /**
     * 更新审批日志信息
     */
    @Log(title = "活动审批流水记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@RequestBody ActiveAuditLog log) {
        return toAjax(logService.updateActiveAuditLog(log));
    }

    /**
     * 批量删除审批日志
     */
    @Log(title = "活动审批流水记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult batchDelete(@PathVariable Long[] logIds) {
        return toAjax(logService.deleteActiveAuditLogByLogIds(logIds));
    }

    /**
     * 导出审批日志数据
     */
    @Log(title = "活动审批流水记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void exportData(HttpServletResponse response, ActiveAuditLog log) {
        List<ActiveAuditLog> dataList = logService.selectActiveAuditLogList(log);
        ExcelUtil<ActiveAuditLog> excelUtil = new ExcelUtil<>(ActiveAuditLog.class);
        excelUtil.exportExcel(response, dataList, "活动审批日志数据");
    }
}