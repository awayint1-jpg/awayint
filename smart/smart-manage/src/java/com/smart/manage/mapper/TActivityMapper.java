package com.smart.manage.mapper;

import java.util.List;
import com.smart.manage.domain.TActivity;

/**
 * 活动信息Mapper接口
 * 
 * @author smart
 * @date 2026-01-05
 */
public interface TActivityMapper 
{
    /**
     * 查询活动信息
     * 
     * @param activeId 活动信息主键
     * @return 活动信息
     */
    public TActivity selectTActivityByActiveId(Long activeId);

    /**
     * 查询活动信息列表
     * 
     * @param tActivity 活动信息
     * @return 活动信息集合
     */
    public List<TActivity> selectTActivityList(TActivity tActivity);

    /**
     * 新增活动信息
     * 
     * @param tActivity 活动信息
     * @return 结果
     */
    public int insertTActivity(TActivity tActivity);

    /**
     * 修改活动信息
     * 
     * @param tActivity 活动信息
     * @return 结果
     */
    public int updateTActivity(TActivity tActivity);

    /**
     * 删除活动信息
     * 
     * @param activeId 活动信息主键
     * @return 结果
     */
    public int deleteTActivityByActiveId(Long activeId);

    /**
     * 批量删除活动信息
     * 
     * @param activeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTActivityByActiveIds(Long[] activeIds);

    int logicDeleteTActivityByActiveIds(Long[] activeIds);

    int logicDeleteTActivityByActiveId(Long activeId);
}
