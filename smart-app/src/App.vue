<script setup lang="ts">
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'
import config from './config';
import { getToken } from './utils/auth';
import { useConfigStore } from '@/stores';

// 初始化应用
const initApp = () => {
  // 初始化应用配置
  initConfig()
  // 检查用户登录状态
  //#ifdef H5
  checkLogin()
  //#endif
}

const initConfig = () => {
  // todo: 这个要换成 pina
  useConfigStore().setConfig(config)
}

// 跳转列表会刷新
const checkLogin = () => {
  if (!getToken()) {
    uni.reLaunch({ url: '/pages/login' })
  }
}
onLaunch(() => {
  console.log('App Launch')
  initApp()
})
onShow(() => {
  console.log('App Show')
})
onHide(() => {
  console.log('App Hide')
})
</script>

<style lang="scss">
// 字体图标: font-family: "erabbit" !important; 会导致智慧的图标无法显示
// @import '@/styles/fonts.scss';
@import '@/static/scss/index.scss';

view,
navigator,
input,
scroll-view {
  box-sizing: border-box;
}

button::after {
  border: none;
}

swiper,
scroll-view {
  flex: 1;
  height: 100%;
  overflow: hidden;
}

image {
  width: 100%;
  height: 100%;
  vertical-align: middle;
}

// 两行省略
.ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
