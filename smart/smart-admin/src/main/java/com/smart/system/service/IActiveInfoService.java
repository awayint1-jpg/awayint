package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveInfo;

/**
 * 社区活动信息业务服务接口
 *
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveInfoService {

    /**
     * 批量删除活动信息记录
     *
     * @param activityIds 需要删除的活动主键数组
     * @return 删除操作影响的行数
     */
    public int deleteActiveInfoByActivityIds(Long[] activityIds);

    /**
     * 删除单条活动信息记录
     *
     * @param activityId 活动主键ID
     * @return 删除操作影响的行数
     */
    public int deleteActiveInfoByActivityId(Long activityId);

    /**
     * 新增活动信息记录
     *
     * @param info 活动信息实体
     * @return 插入操作影响的行数
     */
    public int insertActiveInfo(ActiveInfo info);

    /**
     * 更新活动信息
     *
     * @param info 活动信息实体（包含更新数据）
     * @return 更新操作影响的行数
     */
    public int updateActiveInfo(ActiveInfo info);

    /**
     * 根据活动ID查询详细信息
     *
     * @param activityId 活动主键ID
     * @return 活动信息实体
     */
    public ActiveInfo selectActiveInfoByActivityId(Long activityId);

    /**
     * 条件查询活动信息列表
     *
     * @param info 查询条件实体
     * @return 活动信息集合
     */
    public List<ActiveInfo> selectActiveInfoList(ActiveInfo info);
}