import request from '@/utils/request'

// 查询活动审批流水记录列表
export function listActiveAuditLog(query) {
  return request({
    url: '/system/ActiveAuditLog/list',
    method: 'get',
    params: query
  })
}

// 查询活动审批流水记录详细
export function getActiveAuditLog(logId) {
  return request({
    url: '/system/ActiveAuditLog/' + logId,
    method: 'get'
  })
}

// 新增活动审批流水记录
export function addActiveAuditLog(data) {
  return request({
    url: '/system/ActiveAuditLog',
    method: 'post',
    data: data
  })
}

// 修改活动审批流水记录
export function updateActiveAuditLog(data) {
  return request({
    url: '/system/ActiveAuditLog',
    method: 'put',
    data: data
  })
}

// 删除活动审批流水记录
export function delActiveAuditLog(logId) {
  return request({
    url: '/system/ActiveAuditLog/' + logId,
    method: 'delete'
  })
}
