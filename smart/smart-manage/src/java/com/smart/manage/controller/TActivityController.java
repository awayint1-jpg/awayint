package com.smart.manage.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import com.smart.manage.domain.TActivity;
import com.smart.manage.service.ITActivityService;
import com.smart.common.utils.poi.ExcelUtil;
import com.smart.common.core.page.TableDataInfo;

/**
 * 活动信息Controller
 *
 * @author smart
 * @date 2026-01-05
 */
@RestController
@RequestMapping("/manage/activity")
public class TActivityController extends BaseController
{
    @Autowired
    private ITActivityService tActivityService;

    /**
     * 查询活动信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(TActivity tActivity)
    {
        startPage();
        List<TActivity> list = tActivityService.selectTActivityList(tActivity);
        return getDataTable(list);
    }

    /**
     * 导出活动信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:export')")
    @Log(title = "活动信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TActivity tActivity)
    {
        List<TActivity> list = tActivityService.selectTActivityList(tActivity);
        ExcelUtil<TActivity> util = new ExcelUtil<TActivity>(TActivity.class);
        util.exportExcel(response, list, "活动信息数据");
    }

    /**
     * 获取活动信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:query')")
    @GetMapping(value = "/{activeId}")
    public AjaxResult getInfo(@PathVariable("activeId") Long activeId)
    {
        return success(tActivityService.selectTActivityByActiveId(activeId));
    }

    /**
     * 新增活动信息
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:add')")
    @Log(title = "活动信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody TActivity tActivity)  // 添加@Valid触发校验
    {
        return toAjax(tActivityService.insertTActivity(tActivity));
    }

    /**
     * 修改活动信息
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:edit')")
    @Log(title = "活动信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody TActivity tActivity)  // 添加@Valid触发校验
    {
        return toAjax(tActivityService.updateTActivity(tActivity));
    }

    /**
     * 删除活动信息
     */
    @PreAuthorize("@ss.hasPermi('manage:activity:remove')")
    @Log(title = "活动信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activeIds}")
    public AjaxResult remove(@PathVariable Long[] activeIds)
    {
        return toAjax(tActivityService.deleteTActivityByActiveIds(activeIds));
    }
}