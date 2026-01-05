/**
 * 添加拦截器：
 *  拦截 request 请求
 *  拦截 uploadFile 文件上传
 *
 * TODO:
 *  1. 非 http 开头需要拼接地址
 *  2. 请求超时
 *  3. 添加小程序端请求头标识
 *  4. 添加 token 请求头标识
 *
 */

import { useMemberStore } from '@/stores'

const baseURL = 'https://pcapi-xiaotuxian-front-devtest.itheima.net'

// 添加拦截器
const httpInterceptor = {
  // 拦截前触发
  invoke(options: UniNamespace.RequestOptions) {
    // 1. 非 http 开头需要拼接地址
    if (!options.url.startsWith('http')) {
      options.url = baseURL + options.url
    }
    // 2. 请求超时， 默认 60s, 修改为 10s 100s(接口速度太慢了)
    options.timeout = 100000
    // 3. 添加小程序端请求头标识
    options.header = {
      // 保留原本的请求头
      ...options.header,
      'source-client': 'miniapp',
    }
    // 4. 添加 token 请求头标识
    const memberStore = useMemberStore()
    const token = memberStore.profile?.token
    if (token) {
      options.header.Authorization = token
    }
  },
}
// 拦截 request 请求
// uni.addInterceptor('request', httpInterceptor)
// 拦截 uploadFile 文件上传
// uni.addInterceptor('uploadFile', httpInterceptor)

interface Data<T> {
  code: string
  message: string
  result: T
}

/**
 * 请求函数
 */
// 添加类型支持
export const http = <T>(options: UniApp.RequestOptions) => {
  // 返回 peomise 对象
  return new Promise<Data<T>>((resolve, reject) => {
    uni.request({
      ...options,
      // 请求成功
      success(res) {
        // 状态码 2xx, axios 就是这样设计的
        if (res.statusCode >= 200 && res.statusCode < 300) {
          // 提取核心数据 res.data
          resolve(res.data as Data<T>)
        } else if (res.statusCode == 401) {
          // 401错误 -> 清理用户信息, 跳转登录页
          const memberStore = useMemberStore()
          memberStore.clearProfile()
          uni.navigateTo({ url: '/pages/login/login' })
          // 回传 res
          reject(res)
        } else {
          // 其他错误 -> 根据后端错误信息轻提示
          uni.showToast({
            icon: 'none',
            title: (res.data as Data<T>).message || '请求错误',
          })
          reject(res)
        }
      },
      // 响应失败
      fail(err) {
        // 网络没了
        uni.showToast({
          icon: 'none',
          title: '网络错误，换个网络试试',
        })
        reject(err)
      },
    })
  })
}
