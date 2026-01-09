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
 * 活动报名与打卡记录服务实现
 *
 * @author smart
 * @date 2026-01-07
 */
@Service
public class ActiveRegistrationServiceImpl implements IActiveRegistrationService {

    @Autowired
    private ActiveRegistrationMapper mapper;

    /**
     * 批量删除报名记录
     *
     * @param regIds 需删除的记录主键数组
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveRegistrationByRegIds(Long[] regIds) {
        return mapper.deleteActiveRegistrationByRegIds(regIds);
    }

    /**
     * 删除单条报名记录
     *
     * @param regId 记录主键ID
     * @return 删除操作影响的行数
     */
    @Override
    public int deleteActiveRegistrationByRegId(Long regId) {
        return mapper.deleteActiveRegistrationByRegId(regId);
    }

    /**
     * 新增报名记录
     *
     * @param registration 报名记录实体
     * @return 插入操作影响的行数
     */
    @Override
    public int insertActiveRegistration(ActiveRegistration registration) {
        registration.setCreateTime(DateUtils.getNowDate());
        registration.setUserId(SecurityUtils.getUserId());
        return mapper.insertActiveRegistration(registration);
    }

    /**
     * 更新报名记录信息
     *
     * @param registration 报名记录实体
     * @return 更新操作影响的行数
     */
    @Override
    public int updateActiveRegistration(ActiveRegistration registration) {
        return mapper.updateActiveRegistration(registration);
    }

    /**
     * 根据记录ID查询详细信息
     *
     * @param regId 记录主键ID
     * @return 报名记录实体
     */
    @Override
    public ActiveRegistration selectActiveRegistrationByRegId(Long regId) {
        return mapper.selectActiveRegistrationByRegId(regId);
    }

    /**
     * 条件查询报名记录列表
     *
     * @param registration 查询条件实体
     * @return 报名记录集合
     */
    @Override
    public List<ActiveRegistration> selectActiveRegistrationList(ActiveRegistration registration) {
        return mapper.selectActiveRegistrationList(registration);
    }
}