import tab from './tab'
import auth from './auth'
import modal from './modal'
import type { App } from 'vue'

export default {
  install(app: App) {
    // 页签操作 vue2 的注册方法
    // Vue.prototype.$tab = tab
    // // 认证对象
    // Vue.prototype.$auth = auth
    // // 模态框对象
    // Vue.prototype.$modal = modal
    // 页签操作
    app.config.globalProperties.$tab = tab
    // 认证对象
    app.config.globalProperties.$auth = auth
    // 模态框对象
    app.config.globalProperties.$modal = modal
  }
}
