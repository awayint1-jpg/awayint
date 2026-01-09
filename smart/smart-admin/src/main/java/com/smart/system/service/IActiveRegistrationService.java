package com.smart.system.service;

import java.util.List;
import com.smart.system.domain.ActiveRegistration;

/**
 * 活动报名与打卡记录业务服务接口
 *
 * @author smart
 * @date 2026-01-07
 */
public interface IActiveRegistrationService {

    /**
     * 批量删除报名记录
     *
     * @param regIds 需要删除的记录主键数组
     * @return 删除操作影响的行数
     */
    public int deleteActiveRegistrationByRegIds(Long[] regIds);

    /**
     * 删除单条报名记录
     *
     * @param regId 记录主键ID
     * @return 删除操作影响的行数
     */
    public int deleteActiveRegistrationByRegId(Long regId);

    /**
     * 新增报名记录
     *
     * @param activeRegistration 报名记录实体
     * @return 插入操作影响的行数
     */
    public int insertActiveRegistration(ActiveRegistration activeRegistration);

    /**
     * 更新报名信息
     *
     * @param activeRegistration 报名记录实体
     * @return 更新操作影响的行数
     */
    public int updateActiveRegistration(ActiveRegistration activeRegistration);

    /**
     * 根据记录ID查询详细信息
     *
     * @param regId 记录主键ID
     * @return 报名记录实体
     */
    public ActiveRegistration selectActiveRegistrationByRegId(Long regId);

    /**
     * 条件查询报名记录列表
     *
     * @param activeRegistration 查询条件实体
     * @return 报名记录集合
     */
    public List<ActiveRegistration> selectActiveRegistrationList(ActiveRegistration activeRegistration);
}