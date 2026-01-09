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
 * 社区活动信息业务处理服务实现
 *
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveInfoServiceImpl implements IActiveInfoService {

    @Autowired
    private ActiveInfoMapper mapper;

    /**
     * 批量删除活动信息记录
     *
     * @param activityIds 需要删除的活动主键数组
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveInfoByActivityIds(Long[] activityIds) {
        return mapper.deleteActiveInfoByActivityIds(activityIds);
    }

    /**
     * 删除单条活动信息记录
     *
     * @param activityId 活动主键ID
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveInfoByActivityId(Long activityId) {
        return mapper.deleteActiveInfoByActivityId(activityId);
    }

    /**
     * 新增活动信息记录
     *
     * @param info 活动信息实体
     * @return 插入操作影响的行数
     */
    @Override
    public int insertActiveInfo(ActiveInfo info) {
        info.setCreateTime(DateUtils.getNowDate());
        info.setCreateBy(SecurityUtils.getUserId().toString());
        return mapper.insertActiveInfo(info);
    }

    /**
     * 更新活动信息
     *
     * @param info 活动信息实体
     * @return 更新操作影响的行数
     */
    @Override
    public int updateActiveInfo(ActiveInfo info) {
        info.setUpdateTime(DateUtils.getNowDate());
        return mapper.updateActiveInfo(info);
    }

    /**
     * 根据活动ID查询详细信息
     *
     * @param activityId 活动主键ID
     * @return 活动信息实体
     */
    @Override
    public ActiveInfo selectActiveInfoByActivityId(Long activityId) {
        return mapper.selectActiveInfoByActivityId(activityId);
    }

    /**
     * 条件查询活动信息列表
     *
     * @param info 查询条件实体
     * @return 活动信息集合
     */
    @Override
    public List<ActiveInfo> selectActiveInfoList(ActiveInfo info) {
        return mapper.selectActiveInfoList(info);
    }
}