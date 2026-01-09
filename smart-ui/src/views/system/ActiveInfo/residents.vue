<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入活动标题" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="活动类型" prop="activityType">
        <el-select v-model="queryParams.activityType" placeholder="请选择活动类型" clearable>
          <el-option v-for="dict in dict.type.sys_act_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="可报名活动" name="2"/>
      <el-tab-pane label="我的报名" name="my"/>
      <el-tab-pane label="进行中" name="4"/>
      <el-tab-pane label="已结束" name="5"/>
    </el-tabs>

    <el-row :gutter="20">
      <el-col :span="8" v-for="item in activeList" :key="item.activityId">
        <el-card class="activity-card" shadow="hover">
          <div class="card-header">
            <span class="activity-title">{{ item.title }}</span>
            <el-tag size="small" :type="getStatusType(item.status)">{{ getStatusText(item.status) }}</el-tag>
          </div>
          <div class="card-content">
            <p><i class="el-icon-location-outline"></i> <dict-tag :options="dict.type.sys_act_local" :value="item.address"/></p>
            <p><i class="el-icon-time"></i> {{ parseTime(item.startTime, '{y}-{m}-{d} {h}:{i}') }}</p>
            <p><i class="el-icon-user"></i> 人数上限：{{ item.maxPeople }}人</p>
            <p><i class="el-icon-tickets"></i> <dict-tag :options="dict.type.sys_act_type" :value="item.activityType"/></p>
            <p class="description">{{ item.description }}</p>
          </div>
          <div class="card-footer">
            <el-button size="small" type="primary" plain @click="handleViewDetail(item)">查看详情</el-button>
            <el-button v-if="item.status === '2' && !item.isRegistered" size="small" type="success" @click="handleRegister(item)">立即报名</el-button>
            <el-button v-if="item.isRegistered && item.status === '4' && item.checkInStatus === '0'" size="small" type="warning" @click="handleCheckIn(item)">活动打卡</el-button>
            <el-tag v-if="item.isRegistered && item.checkInStatus === '1'" size="small" type="success">已打卡</el-tag>
            <el-tag v-else-if="item.isRegistered" size="small" type="info">已报名</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 活动详情对话框 -->
    <el-dialog title="活动详情" :visible.sync="detailOpen" width="700px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="活动标题" :span="2">{{ detailForm.title }}</el-descriptions-item>
        <el-descriptions-item label="活动类型">
          <dict-tag :options="dict.type.sys_act_type" :value="detailForm.activityType"/>
        </el-descriptions-item>
        <el-descriptions-item label="活动地点">
          <dict-tag :options="dict.type.sys_act_local" :value="detailForm.address"/>
        </el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ parseTime(detailForm.startTime) }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ parseTime(detailForm.endTime) }}</el-descriptions-item>
        <el-descriptions-item label="人数上限">{{ detailForm.maxPeople }}人</el-descriptions-item>
        <el-descriptions-item label="当前报名">{{ detailForm.registeredCount || 0 }}人</el-descriptions-item>
        <el-descriptions-item label="活动描述" :span="2">{{ detailForm.description }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="detailForm.status === '2' && !detailForm.isRegistered" type="primary" @click="handleRegister(detailForm)">立即报名</el-button>
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 打卡对话框 -->
    <el-dialog title="活动打卡" :visible.sync="checkInOpen" width="500px" append-to-body>
      <el-form ref="checkInForm" :model="checkInForm" :rules="checkInRules" label-width="100px">
        <el-form-item label="活动名称">
          <span>{{ checkInForm.activityTitle }}</span>
        </el-form-item>
        <el-form-item label="打卡照片" prop="checkInPhoto">
          <image-upload v-model="checkInForm.checkInPhoto" :limit="1" :fileSize="2"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCheckIn">确 定</el-button>
        <el-button @click="checkInOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listActiveInfo, getActiveInfo } from "@/api/system/ActiveInfo"
import { listActiveRegistration, addActiveRegistration, updateActiveRegistration } from "@/api/system/ActiveRegistration"

export default {
  name: "ResidentsActiveInfo",
  dicts: ['sys_act_type', 'sys_act_local'],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      activeList: [],
      activeTab: '2',
      detailOpen: false,
      checkInOpen: false,
      queryParams: {
        pageNum: 1,
        pageSize: 9,
        title: null,
        activityType: null,
        status: '2'
      },
      detailForm: {},
      checkInForm: {},
      checkInRules: {
        checkInPhoto: [{ required: true, message: "请上传打卡照片", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      const params = { ...this.queryParams }
      
      if (this.activeTab === 'my') {
        // 查询我的报名
        this.getMyRegistrations()
        return
      }
      
      listActiveInfo(params).then(response => {
        this.activeList = response.rows
        this.total = response.total
        this.loading = false
        
        // 检查用户是否已报名
        this.checkRegistrationStatus()
      })
    },
    getMyRegistrations() {
      // 获取当前用户的报名记录
      listActiveRegistration({ 
        userId: this.$store.state.user.userId,
        pageNum: this.queryParams.pageNum,
        pageSize: this.queryParams.pageSize
      }).then(response => {
        if (response.rows && response.rows.length > 0) {
          // 为每个报名记录查询活动详情
          const promises = response.rows.map(reg => 
            getActiveInfo(reg.activityId).then(res => ({
              ...res.data,
              isRegistered: true,
              checkInStatus: reg.checkInStatus,
              regId: reg.regId
            }))
          )
          
          Promise.all(promises).then(activities => {
            this.activeList = activities
            this.total = response.total
            this.loading = false
          }).catch(() => {
            this.activeList = []
            this.total = 0
            this.loading = false
          })
        } else {
          this.activeList = []
          this.total = 0
          this.loading = false
        }
      })
    },
    checkRegistrationStatus() {
      const activityIds = this.activeList.map(item => item.activityId)
      if (activityIds.length === 0) return
      
      listActiveRegistration({ 
        userId: this.$store.state.user.userId
      }).then(response => {
        const registrations = response.rows || []
        const registeredActivityIds = registrations.map(r => r.activityId)
        
        // 如果是可报名列表，过滤掉已报名的活动
        if (this.activeTab === '2') {
          this.activeList = this.activeList.filter(activity => 
            !registeredActivityIds.includes(activity.activityId)
          )
        } else {
          // 其他列表标记报名状态
          this.activeList = this.activeList.map(activity => {
            const registration = registrations.find(r => r.activityId === activity.activityId)
            return {
              ...activity,
              isRegistered: !!registration,
              checkInStatus: registration ? registration.checkInStatus : '0',
              regId: registration ? registration.regId : null
            }
          })
        }
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleTabClick(tab) {
      if (tab.name === 'my') {
        this.queryParams.status = null
      } else {
        this.queryParams.status = tab.name
      }
      this.handleQuery()
    },
    handleViewDetail(row) {
      getActiveInfo(row.activityId).then(response => {
        this.detailForm = {
          ...response.data,
          isRegistered: row.isRegistered
        }
        this.detailOpen = true
        
        // 获取报名人数
        listActiveRegistration({ activityId: row.activityId }).then(res => {
          this.detailForm.registeredCount = res.total
        })
      })
    },
    handleRegister(row) {
      this.$modal.confirm('确认报名参加该活动？').then(() => {
        const user = this.$store.state.user
        return addActiveRegistration({
          activityId: row.activityId,
          userId: user.userId,
          userName: user.userName,
          phonenumber: user.phonenumber,
          checkInStatus: '0'
        })
      }).then(() => {
        this.$modal.msgSuccess("报名成功")
        this.detailOpen = false
        this.getList()
      }).catch(() => {})
    },
    handleCheckIn(row) {
      this.checkInForm = {
        regId: row.regId,
        activityId: row.activityId,
        activityTitle: row.title,
        checkInPhoto: ''
      }
      this.checkInOpen = true
    },
    submitCheckIn() {
      this.$refs["checkInForm"].validate(valid => {
        if (valid) {
          updateActiveRegistration({
            regId: this.checkInForm.regId,
            checkInStatus: '1',
            checkInPhoto: this.checkInForm.checkInPhoto,
            checkInTime: new Date()
          }).then(() => {
            this.$modal.msgSuccess("打卡成功")
            this.checkInOpen = false
            this.getList()
          })
        }
      })
    },
    getStatusType(status) {
      const map = {
        '2': 'success',
        '4': '',
        '5': 'info'
      }
      return map[status] || 'info'
    },
    getStatusText(status) {
      const map = {
        '2': '可报名',
        '4': '进行中',
        '5': '已结束'
      }
      return map[status] || ''
    }
  }
}
</script>

<style scoped>
.activity-card {
  margin-bottom: 20px;
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.activity-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.card-content {
  margin-bottom: 15px;
}

.card-content p {
  margin: 8px 0;
  color: #606266;
  font-size: 14px;
}

.card-content i {
  margin-right: 5px;
  color: #909399;
}

.description {
  color: #909399;
  font-size: 13px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
