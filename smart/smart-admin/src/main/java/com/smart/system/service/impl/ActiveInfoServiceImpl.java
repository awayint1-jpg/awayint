package com.smart.system.service.impl;

import java.util.List;
import com.smart.common.utils.DateUtils;
import com.smart.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smart.system.mapper.ActiveInfoMapper;
import com.smart.system.domain.ActiveInfo;
import com.smart.system.service.IActiveInfoService;

/**
 * 社区活动信息Service业务层处理
 * 
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveInfoServiceImpl implements IActiveInfoService 
{
    @Autowired
    private ActiveInfoMapper activeInfoMapper;

    /**
     * 查询社区活动信息
     * 
     * @param activityId 社区活动信息主键
     * @return 社区活动信息
     */
    @Override
    public ActiveInfo selectActiveInfoByActivityId(Long activityId)
    {
        return activeInfoMapper.selectActiveInfoByActivityId(activityId);
    }

    /**
     * 查询社区活动信息列表
     * 
     * @param ActiveInfo 社区活动信息
     * @return 社区活动信息
     */
    @Override
    public List<ActiveInfo> selectActiveInfoList(ActiveInfo ActiveInfo)
    {
        return activeInfoMapper.selectActiveInfoList(ActiveInfo);
    }

    /**
     * 新增社区活动信息
     * 
     * @param ActiveInfo 社区活动信息
     * @return 结果
     */
    @Override
    public int insertActiveInfo(ActiveInfo ActiveInfo)
    {
        ActiveInfo.setCreateTime(DateUtils.getNowDate());
        ActiveInfo.setCreateBy(SecurityUtils.getUserId().toString());
        return activeInfoMapper.insertActiveInfo(ActiveInfo);
    }

    /**
     * 修改社区活动信息
     * 
     * @param ActiveInfo 社区活动信息
     * @return 结果
     */
    @Override
    public int updateActiveInfo(ActiveInfo ActiveInfo)
    {
        ActiveInfo.setUpdateTime(DateUtils.getNowDate());
        return activeInfoMapper.updateActiveInfo(ActiveInfo);
    }

    /**
     * 批量删除社区活动信息
     * 
     * @param activityIds 需要删除的社区活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActiveInfoByActivityIds(Long[] activityIds)
    {
        return activeInfoMapper.deleteActiveInfoByActivityIds(activityIds);
    }

    /**
     * 删除社区活动信息信息
     * 
     * @param activityId 社区活动信息主键
     * @return 结果
     */
    @Override
    public int deleteActiveInfoByActivityId(Long activityId)
    {
        return activeInfoMapper.deleteActiveInfoByActivityId(activityId);
    }
}
