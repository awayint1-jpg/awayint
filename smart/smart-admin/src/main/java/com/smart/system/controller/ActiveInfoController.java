package com.smart.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.smart.common.utils.SecurityUtils;
import com.smart.system.domain.ActiveInfo;
import com.smart.system.service.IActiveInfoService;
import com.smart.common.utils.poi.ExcelUtil;
import com.smart.common.core.page.TableDataInfo;
/**
 * 社区活动信息Controller
 *
 * @author smart
 * @date 2026-01-07
 */
@RestController
@RequestMapping("/system/ActiveInfo")
public class ActiveInfoController extends BaseController {

    @Autowired
    private IActiveInfoService infoService;

    @Autowired
    private com.smart.system.service.IActiveAuditLogService logService;

    /**
     * 获取活动详细信息
     */
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId) {
        return success(infoService.selectActiveInfoByActivityId(activityId));
    }

    /**
     * 查询活动信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ActiveInfo info) {
        startPage();

        // 社区工作人员权限过滤
        if (SecurityUtils.hasRole("workers")) {
            info.setCreateBy(getUserId().toString());
        }

        // 处理多状态查询
        String status = info.getStatus();
        if (status != null && status.contains(",")) {
            String[] statusArr = status.split(",");
            info.setStatusArray(statusArr);
            info.setStatus(null);
        }

        List<ActiveInfo> dataList = infoService.selectActiveInfoList(info);
        return getDataTable(dataList);
    }

    /**
     * 导出活动信息
     */
    @Log(title = "社区活动信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActiveInfo info) {
        List<ActiveInfo> dataList = infoService.selectActiveInfoList(info);
        ExcelUtil<ActiveInfo> excelUtil = new ExcelUtil<>(ActiveInfo.class);
        excelUtil.exportExcel(response, dataList, "社区活动数据");
    }

    /**
     * 新增活动信息
     */
    @Log(title = "社区活动信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActiveInfo info) {
        int result = infoService.insertActiveInfo(info);
        return toAjax(result);
    }

    /**
     * 更新活动执行状态
     */
    @Log(title = "更新活动状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody ActiveInfo info) {
        ActiveInfo existing = infoService.selectActiveInfoByActivityId(info.getActivityId());
        if (existing == null) {
            return error("活动不存在");
        }

        String currentStatus = existing.getStatus();
        String targetStatus = info.getStatus();
        boolean validTransition = false;

        // 状态流转验证
        if ("4".equals(targetStatus) && "2".equals(currentStatus)) {
            validTransition = true;
        } else if ("5".equals(targetStatus) && "4".equals(currentStatus)) {
            validTransition = true;
        }

        if (!validTransition) {
            return error("状态流转规则：已通过→进行中，进行中→已结束");
        }

        ActiveInfo statusUpdate = new ActiveInfo();
        statusUpdate.setActivityId(info.getActivityId());
        statusUpdate.setStatus(targetStatus);
        int updateResult = infoService.updateActiveInfo(statusUpdate);

        // 记录状态变更日志
        if (updateResult > 0) {
            com.smart.system.domain.ActiveAuditLog log = new com.smart.system.domain.ActiveAuditLog();
            log.setActivityId(info.getActivityId());
            log.setOperName(getUsername());
            log.setOperType(targetStatus);

            String fromStatus = "2".equals(currentStatus) ? "已通过" : "进行中";
            String toStatus = "4".equals(targetStatus) ? "进行中" : "已结束";
            log.setOperContent("状态由" + fromStatus + "变更为" + toStatus);
            log.setOperTime(new java.util.Date());

            logService.insertActiveAuditLog(log);
        }

        return toAjax(updateResult);
    }

    /**
     * 提交活动审批
     */
    @Log(title = "提交活动审批", businessType = BusinessType.UPDATE)
    @PostMapping("/submit")
    public AjaxResult submit(@RequestBody ActiveInfo info) {
        ActiveInfo existing = infoService.selectActiveInfoByActivityId(info.getActivityId());
        if (existing == null) {
            return error("活动不存在");
        }

        String currentStatus = existing.getStatus();
        if (!"0".equals(currentStatus) && !"3".equals(currentStatus)) {
            return error("只能提交草稿或已驳回状态的活动");
        }

        ActiveInfo statusUpdate = new ActiveInfo();
        statusUpdate.setActivityId(info.getActivityId());
        statusUpdate.setStatus("1");
        int result = infoService.updateActiveInfo(statusUpdate);

        if (result > 0) {
            com.smart.system.domain.ActiveAuditLog log = new com.smart.system.domain.ActiveAuditLog();
            log.setActivityId(info.getActivityId());
            log.setOperName(getUsername());
            log.setOperType("1");
            String statusDesc = "0".equals(currentStatus) ? "草稿" : "已驳回";
            log.setOperContent("状态由" + statusDesc + "变更为待审批");
            log.setOperTime(new java.util.Date());
            logService.insertActiveAuditLog(log);
        }

        return toAjax(result);
    }

    /**
     * 审批活动
     */
    @Log(title = "审批活动", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    public AjaxResult approve(@RequestBody ActiveInfo info) {
        ActiveInfo existing = infoService.selectActiveInfoByActivityId(info.getActivityId());
        if (existing == null) {
            return error("活动不存在");
        }
        if (!"1".equals(existing.getStatus())) {
            return error("只能审批待审批状态的活动");
        }
        if (!"2".equals(info.getStatus()) && !"3".equals(info.getStatus())) {
            return error("审批结果只能是通过或驳回");
        }
        return toAjax(infoService.updateActiveInfo(info));
    }

    /**
     * 修改活动信息
     */
    @Log(title = "社区活动信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActiveInfo info) {
        return toAjax(infoService.updateActiveInfo(info));
    }

    /**
     * 删除活动信息
     */
    @Log(title = "社区活动信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds) {
        for (Long id : activityIds) {
            ActiveInfo item = infoService.selectActiveInfoByActivityId(id);
            if (item != null && !"0".equals(item.getStatus())) {
                return error("只能删除草稿状态的活动");
            }
        }
        return toAjax(infoService.deleteActiveInfoByActivityIds(activityIds));
    }
}