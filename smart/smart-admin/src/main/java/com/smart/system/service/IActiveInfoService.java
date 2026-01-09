package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveInfo;

/**
 * 社区活动信息Service接口
 * 
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveInfoService 
{
    /**
     * 查询社区活动信息
     * 
     * @param activityId 社区活动信息主键
     * @return 社区活动信息
     */
    public ActiveInfo selectActiveInfoByActivityId(Long activityId);

    /**
     * 查询社区活动信息列表
     * 
     * @param ActiveInfo 社区活动信息
     * @return 社区活动信息集合
     */
    public List<ActiveInfo> selectActiveInfoList(ActiveInfo ActiveInfo);

    /**
     * 新增社区活动信息
     * 
     * @param ActiveInfo 社区活动信息
     * @return 结果
     */
    public int insertActiveInfo(ActiveInfo ActiveInfo);

    /**
     * 修改社区活动信息
     * 
     * @param ActiveInfo 社区活动信息
     * @return 结果
     */
    public int updateActiveInfo(ActiveInfo ActiveInfo);

    /**
     * 批量删除社区活动信息
     * 
     * @param activityIds 需要删除的社区活动信息主键集合
     * @return 结果
     */
    public int deleteActiveInfoByActivityIds(Long[] activityIds);

    /**
     * 删除社区活动信息信息
     * 
     * @param activityId 社区活动信息主键
     * @return 结果
     */
    public int deleteActiveInfoByActivityId(Long activityId);
}
