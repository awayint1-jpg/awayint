import { getInfo, login, logout } from '@/api/login'
import config from '@/config'
import { getToken, removeToken, setToken } from '@/utils/auth'
import constant from '@/utils/constant'
import storage from '@/utils/storage'
const baseUrl = config.baseUrl
import { defineStore } from 'pinia'
import { ref } from 'vue'

import profileJpg from '@/static/images/profile.jpg'

// 定义 Store
export const useUserStore = defineStore(
  'user',
  () => {
    // 令牌
    const token = ref(getToken())
    const name = ref(storage.get(constant.name))
    const avatar = ref(storage.get(constant.avatar))
    const roles = ref(storage.get(constant.roles))
    const permissions = ref(storage.get(constant.permissions))

    const SET_TOKEN = (tokenVal: any) => {
      token.value = tokenVal
    }
    const SET_NAME = (nameVal: any) => {
      name.value = nameVal
      // 这个 storage 可能用不着了
      storage.set(constant.name, nameVal)
    }
    const SET_AVATAR = (avatarVal: any) => {
      avatar.value = avatarVal
      storage.set(constant.avatar, avatarVal)
    }
    const SET_ROLES = (rolesVal: any) => {
      roles.value = rolesVal
      storage.set(constant.roles, rolesVal)
    }
    const SET_PERMISSIONS = (permissionsVal: any) => {
      permissions.value = permissionsVal
      storage.set(constant.permissions, permissionsVal)
    }

    // 登录
    const Login = (userInfo: any) => {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise<void>((resolve, reject) => {
        login(username, password, code, uuid).then((res: any) => {
          setToken(res.token)
          SET_TOKEN(res.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }

    // 获取用户信息
    const GetInfo = () => {
      return new Promise((resolve, reject) => {
        getInfo().then((res: any) => {
          const user = res.user
          // require("@/static/images/profile.jpg") 这是 vue2 的写法
          const avatar = (user == null || user.avatar == "" || user.avatar == null) ?
            profileJpg :
            baseUrl + user.avatar
          const username = (user == null || user.userName == "" || user.userName == null) ? "" : user.userName
          if (res.roles && res.roles.length > 0) {
            SET_ROLES(res.roles)
            SET_PERMISSIONS(res.permissions)
          } else {
            SET_ROLES(['ROLE_DEFAULT'])
          }
          SET_NAME(username)
          SET_AVATAR(avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    }

    // 退出系统
    const LogOut = () => {
      return new Promise<void>((resolve, reject) => {
        // logout(state.token).then(() => {
        logout().then(() => {
          SET_TOKEN('')
          SET_ROLES([])
          SET_PERMISSIONS([])
          removeToken()
          storage.clean()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }

    // 记得 return
    return {
      token,
      name,
      avatar,
      roles,
      permissions,
      SET_TOKEN,
      SET_NAME,
      SET_AVATAR,
      SET_ROLES,
      SET_PERMISSIONS,
      Login,
      GetInfo,
      LogOut
    }
  },
  // TODO: 持久化
  {
    // 网页端配置
    // persiste: true
    // 小程序端配置
    persist: {
      storage: {
        getItem(key) {
          return uni.getStorageSync(key)
        },
        setItem(key, value) {
          uni.setStorageSync(key, value)
        },
      },
    },
  },
)
