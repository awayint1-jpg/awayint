<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>智慧后台管理框架</h2>
        <p>
          一直想做一款后台管理系统，看了很多优秀的开源项目但是发现没有合适自己的。于是利用空闲休息时间开始自己写一套后台系统。如此有了智慧管理系统，她可以用于所有的Web应用程序，如网站管理后台，网站会员中心，CMS，CRM，OA等等，当然，您也可以对她进行深度定制，以做出更强系统。所有前端后台代码封装过后十分精简易上手，出错概率低。同时支持移动客户端访问。系统会陆续更新一些实用功能。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-tag type="danger">&yen;免费开源</el-tag>
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row>
      <el-col >
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v3.9.1 - 2025-12-18">
              <ol>
                <li>支持防盗链功能</li>
                <li>菜单导航设置支持纯顶部</li>
                <li>使用yauaa代替bitwalker</li>
                <li>用户头像更换后移除旧头像文件</li>
                <li>支持Excel导出对象的多个子列表</li>
                <li>升级oshi到最新版本6.9.1</li>
                <li>升级druid到最新版本1.2.27</li>
                <li>升级fastjson到最新版2.0.60</li>
                <li>升级spring-security到5.7.14</li>
                <li>升级tomcat到最新版本9.0.112</li>
                <li>升级commons.io到最新版本2.21.0</li>
                <li>用户导入添加验证提示</li>
                <li>显示列信息支持对象格式</li>
                <li>忽略用户密码字段的JSON序列化</li>
                <li>网页标题设置新增SET_TITLE方法</li>
                <li>自动识别json对象白名单配置范围缩小</li>
                <li>登录/注册页面底部版权信息修改为读取配置</li>
                <li>修复用户归属部门无法修改为空问题</li>
                <li>修复固定头部时出现的导航栏偏移问题</li>
                <li>修复v3时间控件between选择后清空报错问题</li>
                <li>修复comboReadDict属性下多个sheet出现的报错</li>
                <li>修复表单构建移除所有控件后切换路由回来空白问题</li>
                <li>优化布局设置显示</li>
                <li>优化字典组件值宽松匹配</li>
                <li>优化获取字典类型值的方法</li>
                <li>优化生成代码下载的zip文件名</li>
                <li>优化日志记录参数拼装提升效率</li>
                <li>优化导入文件检查标题行不能为空</li>
                <li>优化表单构建关闭页签销毁复制插件</li>
                <li>优化Excel统计行数值的单元格样式显示</li>
                <li>优化数据权限控制逻辑，放开permission限制</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.9.0 - 2025-05-28">
              <ol>
                <li>优化菜单搜索查询页</li>
                <li>导航栏显示昵称&设置</li>
                <li>菜单管理新增路由名称</li>
                <li>添加底部版权信息&开关</li>
                <li>分配角色禁用不允许勾选</li>
                <li>Excel导入导出支持多图片</li>
                <li>添加页签图标显示开关功能</li>
                <li>上传组件新增拖动排序属性</li>
                <li>显隐列组件支持全选/全不选</li>
                <li>初始密码支持自定义修改策略</li>
                <li>账号密码支持自定义更新周期</li>
                <li>代码生成列表支持按时间排序</li>
                <li>支持富文本复制粘贴图片上传至url</li>
                <li>支持文件&图片组件自定义地址&参数</li>
                <li>升级tomcat到最新版本9.0.105</li>
                <li>升级oshi到最新版本6.8.1</li>
                <li>升级fastjson到最新版2.0.57</li>
                <li>升级commons.io到最新版本2.19.0</li>
                <li>package.json移除runjs依赖</li>
                <li>package.json移除eslint依赖</li>
                <li>package.json移除vue-meta依赖</li>
                <li>修复代码生成主子表校验必填失效问题</li>
                <li>优化前端树结构性能问题</li>
                <li>优化前端处理路由函数代码</li>
                <li>优化文件上传组件新增类型</li>
                <li>优化顶部菜单搜索栏为多层级显示</li>
                <li>优化文件&图片上传组件新增disabled属性</li>
                <li>优化空指针异常时无法获取错误信息问题</li>
                <li>优化定时任务字符包含多个括号导致数据错误</li>
                <li>优化登录&注册页表头使用VUE_APP_TITLE配置值</li>
                <li>优化导出Excel日期格式双击离开后与设定的格式不一致问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.9 - 2024-12-30">
              <ol>
                <li>用户管理支持分栏拖动</li>
                <li>修改主题样式本地读取</li>
                <li>用户头像http(s)链接支持</li>
                <li>用户管理过滤掉已禁用部门</li>
                <li>支持自定义显示Excel属性列</li>
                <li>操作日志记录DELETE请求参数</li>
                <li>白名单支持对通配符路径匹配</li>
                <li>校检文件名是否包含特殊字符</li>
                <li>代码生成创建表屏蔽违规的字符</li>
                <li>菜单面包屑导航支持多层级显示</li>
                <li>Excel注解支持wrapText是否允许内容换行</li>
                <li>代码生成新增配置是否允许文件覆盖到本地</li>
                <li>修复角色禁用权限不失效问题</li>
                <li>修复代码生成上级菜单显示问题</li>
                <li>修复导出子列表对象只能在最后的问题</li>
                <li>修复TopNav无法正确获取active的问题</li>
                <li>修复默认关闭Tags-Views内链页面打不开</li>
                <li>升级oshi到最新版本6.6.5</li>
                <li>升级tomcat到最新版本9.0.96</li>
                <li>升级fastjson到最新版2.0.53</li>
                <li>升级logback到最新版本1.2.13</li>
                <li>升级spring-framework到最新版本5.3.39</li>
                <li>升级quill到最新版本2.0.2</li>
                <li>升级axios到最新版本0.28.1</li>
                <li>优化身份证脱敏正则</li>
                <li>优化权限更新后同步缓存</li>
                <li>优化查询时间范围日期格式</li>
                <li>优化参数键值更换为多行文本</li>
                <li>优化导入带标题文件关闭清理</li>
                <li>优化上传图片带域名不增加前缀</li>
                <li>优化特殊字符密码修改失败问题</li>
                <li>优化无用户编号不校验数据权限</li>
                <li>优化TopNav内链菜单点击没有高亮</li>
                <li>优化菜单管理切换Mini布局错乱问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.8 - 2024-06-30">
              <ol>
                <li>菜单管理新增路由名称</li>
                <li>新增数据脱敏过滤注解</li>
                <li>用户密码新增非法字符验证</li>
                <li>限制用户操作数据权限范围</li>
                <li>代码生成新增创建表结构功能</li>
                <li>定时任务白名单配置范围缩小</li>
                <li>优化代码生成主子表关联查询方式</li>
                <li>Excel注解新增属性comboReadDict</li>
                <li>Excel注解ColumnType类型新增文本</li>
                <li>新增国际化资源文件配置</li>
                <li>升级oshi到最新版本6.6.1</li>
                <li>升级druid到最新版本1.2.23</li>
                <li>升级core-js到最新版本3.37.1</li>
                <li>更新HttpUtils中的User-Agent</li>
                <li>更新compressionPlugin到6.1.2以兼容node18+</li>
                <li>升级spring-security到安全版本，防止漏洞风险</li>
                <li>升级spring-framework到安全版本，防止漏洞风险</li>
                <li>优化自定义XSS注解匹配方式</li>
                <li>优化缓存监控键名列表排序显示</li>
                <li>优化定时任务日志默认按时间排序</li>
                <li>优化默认文件大小超过2G无效的问题</li>
                <li>优化查表特殊字符使用反斜杠进行转义</li>
                <li>优化定时任务cron表达式小时配置显示错误问题</li>
                <li>优化多个自定数据权限使用in查询,避免多次拼接</li>
                <li>优化导入Excel时设置dictType属性重复查缓存问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.9.1"
    }
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

