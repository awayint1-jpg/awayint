import { createSSRApp } from 'vue'
import pinia from './stores'
import plugins from './plugins' // plugins
import './permission' // permission


// Vue.config.productionTip = false
// Vue.prototype.$store = store

import App from './App.vue'
export function createApp() {
  const app = createSSRApp(App)

  app.use(pinia)
  app.use(plugins)
  return {
    app,
  }
}
