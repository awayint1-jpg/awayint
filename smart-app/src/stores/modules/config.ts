import { defineStore } from 'pinia'
import { ref } from 'vue'

// 定义 Store
export const useConfigStore = defineStore(
  'config',
  () => {
    // 配置内容：用于替换 getApp().globalData!.config = config
    const config = ref()

    // 更新配置信息
    const setConfig = (val: any) => {
      config.value = val
    }

    // 记得 return
    return {
      config,
      setConfig
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
