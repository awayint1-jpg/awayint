import request from '@/utils/request'

// 查询活动报名与打卡记录列表
export function listActiveRegistration(query) {
  return request({
    url: '/system/ActiveRegistration/list',
    method: 'get',
    params: query
  })
}

// 查询活动报名与打卡记录详细
export function getActiveRegistration(regId) {
  return request({
    url: '/system/ActiveRegistration/' + regId,
    method: 'get'
  })
}

// 新增活动报名与打卡记录
export function addActiveRegistration(data) {
  return request({
    url: '/system/ActiveRegistration',
    method: 'post',
    data: data
  })
}

// 修改活动报名与打卡记录
export function updateActiveRegistration(data) {
  return request({
    url: '/system/ActiveRegistration',
    method: 'put',
    data: data
  })
}

// 删除活动报名与打卡记录
export function delActiveRegistration(regId) {
  return request({
    url: '/system/ActiveRegistration/' + regId,
    method: 'delete'
  })
}
