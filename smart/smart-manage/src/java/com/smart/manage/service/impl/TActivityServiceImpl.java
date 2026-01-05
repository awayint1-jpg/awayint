package com.smart.manage.service.impl;

import com.smart.manage.domain.TActivity;
import com.smart.manage.mapper.TActivityMapper;
import com.smart.manage.service.ITActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * 活动信息Service业务层处理
 *
 * @author smart
 * @date 2026-01-05
 */
@Service
public class TActivityServiceImpl implements ITActivityService {

    @Autowired
    private TActivityMapper activityMapper;

    @Override
    public TActivity selectTActivityByActiveId(Long activeId) {
        return activityMapper.selectTActivityByActiveId(activeId);
    }

    @Override
    public List<TActivity> selectTActivityList(TActivity tActivity) {
        return activityMapper.selectTActivityList(tActivity);
    }

    @Override
    public int insertTActivity(TActivity tActivity) {
        // 1. 时间基础校验
        validateActivityTime(tActivity);

        // 2. 设置默认状态为草稿
        tActivity.setStatus("DRAFT");
        // 3. 设置逻辑删除默认值
        tActivity.setDeleted(0);

        return activityMapper.insertTActivity(tActivity);
    }

    @Override
    public int updateTActivity(TActivity tActivity) {
        // 1. 校验是否为草稿状态
        if (!isDraftStatus(tActivity.getActiveId())) {
            throw new IllegalArgumentException("仅草稿状态的活动可修改");
        }

        // 2. 时间基础校验
        validateActivityTime(tActivity);

        return activityMapper.updateTActivity(tActivity);
    }

    @Override
    public int logicDeleteTActivityByActiveIds(Long[] activeIds) {
        // 批量删除前校验每个活动是否为草稿
        for (Long activeId : activeIds) {
            if (!isDraftStatus(activeId)) {
                throw new IllegalArgumentException("仅草稿状态的活动可删除，活动ID：" + activeId);
            }
        }
        return activityMapper.logicDeleteTActivityByActiveIds(activeIds);
    }

    @Override
    public int logicDeleteTActivityByActiveId(Long activeId) {
        if (!isDraftStatus(activeId)) {
            throw new IllegalArgumentException("仅草稿状态的活动可删除");
        }
        return activityMapper.logicDeleteTActivityByActiveId(activeId);
    }

    @Override
    public int deleteTActivityByActiveIds(Long[] activeIds) {
        return activityMapper.deleteTActivityByActiveIds(activeIds);
    }

    @Override
    public int deleteTActivityByActiveId(Long activeId) {
        return activityMapper.deleteTActivityByActiveId(activeId);
    }

    @Override
    public boolean validateAddress(String address) {
        // 示例：简单校验地址非空，可根据业务扩展
        return !ObjectUtils.isEmpty(address);
    }

    @Override
    public boolean isDraftStatus(Long activeId) {
        TActivity activity = selectTActivityByActiveId(activeId);
        if (activity == null) {
            throw new IllegalArgumentException("活动不存在，ID：" + activeId);
        }
        return "DRAFT".equals(activity.getStatus());
    }

    /**
     * 活动时间统一校验（开始/结束时间不早于当前，结束时间≥开始时间）
     * @param tActivity 活动信息
     */
    private void validateActivityTime(TActivity tActivity) {
        Date now = new Date();
        Date startTime = tActivity.getActiveStartTime();
        Date endTime = tActivity.getActiveEndTime();

        // 1. 校验开始时间非空
        if (startTime == null) {
            throw new IllegalArgumentException("活动开始时间不能为空");
        }
        // 2. 校验结束时间非空
        if (endTime == null) {
            throw new IllegalArgumentException("活动结束时间不能为空");
        }
        // 3. 校验开始时间不早于当前时间
        if (startTime.before(now)) {
            throw new IllegalArgumentException("活动开始时间不能早于当前时间");
        }
        // 4. 校验结束时间不早于当前时间
        if (endTime.before(now)) {
            throw new IllegalArgumentException("活动结束时间不能早于当前时间");
        }
        // 5. 校验结束时间不早于开始时间
        if (endTime.before(startTime)) {
            throw new IllegalArgumentException("活动结束时间不能早于开始时间");
        }
    }
}