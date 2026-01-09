package com.smart.system.mapper;

import java.util.List;
import com.smart.system.domain.ActiveRegistration;
/**
 * 活动报名与打卡记录数据访问接口
 *
 * @author smart
 * @date 2026-01-07
 */
public interface ActiveRegistrationMapper {

    /**
     * 新增报名记录
     *
     * @param registration 报名记录实体
     * @return 插入操作影响的行数
     */
    public int insertActiveRegistration(ActiveRegistration registration);

    /**
     * 更新报名信息
     *
     * @param registration 报名记录实体（包含更新数据）
     * @return 更新操作影响的行数
     */
    public int updateActiveRegistration(ActiveRegistration registration);

    /**
     * 根据记录ID删除单条报名记录
     *
     * @param id 报名记录主键ID
     * @return 删除操作影响的行数
     */
    public int deleteActiveRegistrationByRegId(Long id);

    /**
     * 批量删除报名记录
     *
     * @param ids 需要删除的报名记录ID数组
     * @return 删除操作影响的行数
     */
    public int deleteActiveRegistrationByRegIds(Long[] ids);

    /**
     * 根据记录ID查询报名详情
     *
     * @param id 报名记录主键ID
     * @return 报名记录实体
     */
    public ActiveRegistration selectActiveRegistrationByRegId(Long id);

    /**
     * 条件查询报名记录列表
     *
     * @param registration 查询条件实体
     * @return 报名记录集合
     */
    public List<ActiveRegistration> selectActiveRegistrationList(ActiveRegistration registration);
}