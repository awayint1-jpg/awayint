import request from '@/utils/request'

// 查询社区活动信息列表
export function listActiveInfo(query) {
  return request({
    url: '/system/ActiveInfo/list',
    method: 'get',
    params: query
  })
}

// 查询社区活动信息详细
export function getActiveInfo(activityId) {
  return request({
    url: '/system/ActiveInfo/' + activityId,
    method: 'get'
  })
}

// 新增社区活动信息
export function addActiveInfo(data) {
  return request({
    url: '/system/ActiveInfo',
    method: 'post',
    data: data
  })
}

// 修改社区活动信息
export function updateActiveInfo(data) {
  return request({
    url: '/system/ActiveInfo',
    method: 'put',
    data: data
  })
}

// 删除社区活动信息
export function delActiveInfo(activityId) {
  return request({
    url: '/system/ActiveInfo/' + activityId,
    method: 'delete'
  })
}

// 提交活动审批
export function submitActiveInfo(data) {
  return request({
    url: '/system/ActiveInfo/submit',
    method: 'post',
    data: data
  })
}

// 更新活动状态（推进活动进度）
export function updateActiveStatus(data) {
  return request({
    url: '/system/ActiveInfo/updateStatus',
    method: 'put',
    data: data
  })
}
