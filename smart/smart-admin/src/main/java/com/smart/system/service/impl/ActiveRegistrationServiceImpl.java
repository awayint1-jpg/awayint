package com.smart.system.service.impl;

import java.util.List;
import com.smart.common.utils.DateUtils;
import com.smart.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smart.system.mapper.ActiveRegistrationMapper;
import com.smart.system.domain.ActiveRegistration;
import com.smart.system.service.IActiveRegistrationService;

/**
 * 活动报名与打卡记录Service业务层处理
 * 
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveRegistrationServiceImpl implements IActiveRegistrationService 
{
    @Autowired
    private ActiveRegistrationMapper activeRegistrationMapper;

    /**
     * 查询活动报名与打卡记录
     * 
     * @param regId 活动报名与打卡记录主键
     * @return 活动报名与打卡记录
     */
    @Override
    public ActiveRegistration selectActiveRegistrationByRegId(Long regId)
    {
        return activeRegistrationMapper.selectActiveRegistrationByRegId(regId);
    }

    /**
     * 查询活动报名与打卡记录列表
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 活动报名与打卡记录
     */
    @Override
    public List<ActiveRegistration> selectActiveRegistrationList(ActiveRegistration activeRegistration)
    {
        return activeRegistrationMapper.selectActiveRegistrationList(activeRegistration);
    }

    /**
     * 新增活动报名与打卡记录
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 结果
     */
    @Override
    public int insertActiveRegistration(ActiveRegistration activeRegistration)
    {
        activeRegistration.setCreateTime(DateUtils.getNowDate());
        activeRegistration.setUserId(SecurityUtils.getUserId());

        return activeRegistrationMapper.insertActiveRegistration(activeRegistration);
    }

    /**
     * 修改活动报名与打卡记录
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 结果
     */
    @Override
    public int updateActiveRegistration(ActiveRegistration activeRegistration)
    {
        return activeRegistrationMapper.updateActiveRegistration(activeRegistration);
    }

    /**
     * 批量删除活动报名与打卡记录
     * 
     * @param regIds 需要删除的活动报名与打卡记录主键
     * @return 结果
     */
    @Override
    public int deleteActiveRegistrationByRegIds(Long[] regIds)
    {
        return activeRegistrationMapper.deleteActiveRegistrationByRegIds(regIds);
    }

    /**
     * 删除活动报名与打卡记录信息
     * 
     * @param regId 活动报名与打卡记录主键
     * @return 结果
     */
    @Override
    public int deleteActiveRegistrationByRegId(Long regId)
    {
        return activeRegistrationMapper.deleteActiveRegistrationByRegId(regId);
    }
}
