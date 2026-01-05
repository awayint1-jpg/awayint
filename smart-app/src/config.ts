interface Agreement {
  title: string
  url: string
}

interface AppInfo {
  name: string
  version: string
  logo: string
  site_url: string
  agreements: Agreement[]
}

interface Config {
  baseUrl: string
  appInfo: AppInfo
}

// 应用全局配置
const config: Config = {
  // baseUrl: 'https://vue.smart.vip/prod-api',
  baseUrl: 'http://localhost:8080',
  // 应用信息
  appInfo: {
    // 应用名称
    name: "smart-app",
    // 应用版本
    version: "1.1.0",
    // 应用logo
    logo: "/static/logo.png",
    // 官方网站
    site_url: "http://smart.vip",
    // 政策协议
    agreements: [{
        title: "隐私政策",
        url: "https://smart.vip/protocol.html"
      },
      {
        title: "用户服务协议",
        url: "https://smart.vip/protocol.html"
      }
    ]
  }
}
export default config

