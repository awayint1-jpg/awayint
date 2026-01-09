package com.smart.system.controller;

import java.util.List;

import com.smart.system.domain.ActiveInfo;
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
import com.smart.system.domain.ActiveRegistration;
import com.smart.system.service.IActiveRegistrationService;
import com.smart.common.utils.poi.ExcelUtil;
import com.smart.common.core.page.TableDataInfo;

/**
 * 活动报名与打卡记录管理控制器
 *
 * @author smart
 * @date 2026-01-07
 */
@RestController
@RequestMapping("/system/ActiveRegistration")
public class ActiveRegistrationController extends BaseController {

    @Autowired
    private IActiveRegistrationService registrationService;

    @Autowired
    private com.smart.system.service.IActiveInfoService activityService;

    /**
     * 获取报名记录详情
     */
    @GetMapping(value = "/{regId}")
    public AjaxResult getDetail(@PathVariable("regId") Long regId) {
        return success(registrationService.selectActiveRegistrationByRegId(regId));
    }

    /**
     * 查询报名记录列表
     */
    @GetMapping("/list")
    public TableDataInfo queryList(ActiveRegistration registration) {
        startPage();
        List<ActiveRegistration> dataList = registrationService.selectActiveRegistrationList(registration);
        return getDataTable(dataList);
    }

    /**
     * 新增活动报名
     */
    @Log(title = "活动报名与打卡记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult createRegistration(@RequestBody ActiveRegistration registration) {
        // 验证活动有效性
        ActiveInfo activity = activityService.selectActiveInfoByActivityId(registration.getActivityId());
        if (activity == null) {
            return error("活动不存在");
        }
        if (!"2".equals(activity.getStatus())) {
            return error("该活动暂不可报名");
        }

        // 检查重复报名
        ActiveRegistration checkParam = new ActiveRegistration();
        checkParam.setActivityId(registration.getActivityId());
        checkParam.setUserId(registration.getUserId());
        List<ActiveRegistration> existingRecords = registrationService.selectActiveRegistrationList(checkParam);
        if (existingRecords != null && !existingRecords.isEmpty()) {
            return error("您已报名该活动，请勿重复报名");
        }

        // 检查报名人数限制
        ActiveRegistration countParam = new ActiveRegistration();
        countParam.setActivityId(registration.getActivityId());
        List<ActiveRegistration> allRegistrations = registrationService.selectActiveRegistrationList(countParam);
        if (allRegistrations.size() >= activity.getMaxPeople()) {
            return error("报名人数已满");
        }

        int result = registrationService.insertActiveRegistration(registration);
        return toAjax(result);
    }

    /**
     * 更新报名信息
     */
    @Log(title = "活动报名与打卡记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateRegistration(@RequestBody ActiveRegistration registration) {
        int result = registrationService.updateActiveRegistration(registration);
        return toAjax(result);
    }

    /**
     * 批量删除报名记录
     */
    @Log(title = "活动报名与打卡记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{regIds}")
    public AjaxResult batchDelete(@PathVariable Long[] regIds) {
        int result = registrationService.deleteActiveRegistrationByRegIds(regIds);
        return toAjax(result);
    }

    /**
     * 导出报名数据
     */
    @Log(title = "活动报名与打卡记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void exportData(HttpServletResponse response, ActiveRegistration registration) {
        List<ActiveRegistration> dataList = registrationService.selectActiveRegistrationList(registration);
        ExcelUtil<ActiveRegistration> excelUtil = new ExcelUtil<>(ActiveRegistration.class);
        excelUtil.exportExcel(response, dataList, "活动报名记录数据");
    }
}