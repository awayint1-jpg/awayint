package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveRegistration;

/**
 * 活动报名与打卡记录Service接口
 * 
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveRegistrationService 
{
    /**
     * 查询活动报名与打卡记录
     * 
     * @param regId 活动报名与打卡记录主键
     * @return 活动报名与打卡记录
     */
    public ActiveRegistration selectActiveRegistrationByRegId(Long regId);

    /**
     * 查询活动报名与打卡记录列表
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 活动报名与打卡记录集合
     */
    public List<ActiveRegistration> selectActiveRegistrationList(ActiveRegistration activeRegistration);

    /**
     * 新增活动报名与打卡记录
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 结果
     */
    public int insertActiveRegistration(ActiveRegistration activeRegistration);

    /**
     * 修改活动报名与打卡记录
     * 
     * @param activeRegistration 活动报名与打卡记录
     * @return 结果
     */
    public int updateActiveRegistration(ActiveRegistration activeRegistration);

    /**
     * 批量删除活动报名与打卡记录
     * 
     * @param regIds 需要删除的活动报名与打卡记录主键集合
     * @return 结果
     */
    public int deleteActiveRegistrationByRegIds(Long[] regIds);

    /**
     * 删除活动报名与打卡记录信息
     * 
     * @param regId 活动报名与打卡记录主键
     * @return 结果
     */
    public int deleteActiveRegistrationByRegId(Long regId);
}
