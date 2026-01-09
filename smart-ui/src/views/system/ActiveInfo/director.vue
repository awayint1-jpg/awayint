<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动标题" prop="title">
        <el-input v-model="queryParams.title" placeholder="请输入活动标题" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="待审批" value="1"/>
          <el-option label="已通过" value="2"/>
          <el-option label="已驳回" value="3"/>
          <el-option label="进行中" value="4"/>
          <el-option label="已结束" value="5"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="待审批" name="1">
        <el-badge :value="pendingCount" class="item" :hidden="pendingCount === 0"/>
      </el-tab-pane>
      <el-tab-pane label="已审批" name="approved"/>
      <el-tab-pane label="全部活动" name="all"/>
    </el-tabs>

    <el-table v-loading="loading" :data="activeList">
      <el-table-column label="活动标题" align="center" prop="title" :show-overflow-tooltip="true"/>
      <el-table-column label="活动类型" align="center" prop="activityType" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_act_type" :value="scope.row.activityType"/>
        </template>
      </el-table-column>
      <el-table-column label="活动地点" align="center" prop="address" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_act_local" :value="scope.row.address"/>
        </template>
      </el-table-column>
      <el-table-column label="活动时间" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          <br/>
          <span>至 {{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="人数上限" align="center" prop="maxPeople" width="100"/>
      <el-table-column label="申请人" align="center" prop="createBy" width="100"/>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '1'" type="warning">待审批</el-tag>
          <el-tag v-else-if="scope.row.status === '2'" type="success">已通过</el-tag>
          <el-tag v-else-if="scope.row.status === '3'" type="danger">已驳回</el-tag>
          <el-tag v-else-if="scope.row.status === '4'">进行中</el-tag>
          <el-tag v-else-if="scope.row.status === '5'" type="info">已结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="320" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看详情</el-button>
          <el-button v-if="scope.row.status === '1'" size="mini" type="text" icon="el-icon-check" @click="handleApprove(scope.row)">审批</el-button>
          <el-button v-if="scope.row.status === '2'" size="mini" type="text" icon="el-icon-video-play" @click="handleStartActivity(scope.row)" style="color: #67C23A">开始活动</el-button>
          <el-button v-if="scope.row.status === '4'" size="mini" type="text" icon="el-icon-circle-check" @click="handleEndActivity(scope.row)" style="color: #909399">结束活动</el-button>
          <el-button v-if="['2','4','5'].includes(scope.row.status)" size="mini" type="text" icon="el-icon-user" @click="handleViewRegistrations(scope.row)">报名名单</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 查看活动详情对话框 -->
    <el-dialog title="活动详情" :visible.sync="viewOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="活动标题">{{ viewForm.title }}</el-descriptions-item>
        <el-descriptions-item label="活动类型">
          <dict-tag :options="dict.type.sys_act_type" :value="viewForm.activityType"/>
        </el-descriptions-item>
        <el-descriptions-item label="活动地点">
          <dict-tag :options="dict.type.sys_act_local" :value="viewForm.address"/>
        </el-descriptions-item>
        <el-descriptions-item label="人数上限">{{ viewForm.maxPeople }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ parseTime(viewForm.startTime) }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ parseTime(viewForm.endTime) }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="viewForm.status === '1'" type="warning">待审批</el-tag>
          <el-tag v-else-if="viewForm.status === '2'" type="success">已通过</el-tag>
          <el-tag v-else-if="viewForm.status === '3'" type="danger">已驳回</el-tag>
          <el-tag v-else-if="viewForm.status === '4'">进行中</el-tag>
          <el-tag v-else-if="viewForm.status === '5'" type="info">已结束</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="申请人">{{ viewForm.createBy }}</el-descriptions-item>
        <el-descriptions-item label="申请时间">{{ parseTime(viewForm.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="活动描述" :span="2">{{ viewForm.description }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ viewForm.remark }}</el-descriptions-item>
      </el-descriptions>
      
      <!-- 审批记录 -->
      <el-divider content-position="left">审批记录</el-divider>
      <el-timeline>
        <el-timeline-item v-for="log in auditLogs" :key="log.logId" :timestamp="parseTime(log.operTime)" placement="top">
          <el-card>
            <h4>{{ log.operName }} - {{ getOperTypeName(log.operType) }}</h4>
            <p>{{ log.operContent }}</p>
            <p v-if="log.auditRemark" style="color: #909399">备注：{{ log.auditRemark }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-dialog>

    <!-- 审批对话框 -->
    <el-dialog title="活动审批" :visible.sync="approveOpen" width="600px" append-to-body>
      <el-form ref="approveForm" :model="approveForm" :rules="approveRules" label-width="100px">
        <el-form-item label="活动标题">
          <span>{{ approveForm.title }}</span>
        </el-form-item>
        <el-form-item label="审批结果" prop="approveResult">
          <el-radio-group v-model="approveForm.approveResult">
            <el-radio label="2">通过</el-radio>
            <el-radio label="3">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审批意见" prop="auditRemark">
          <el-input v-model="approveForm.auditRemark" type="textarea" :rows="4" placeholder="请输入审批意见或驳回原因" maxlength="500" show-word-limit/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitApprove">确 定</el-button>
        <el-button @click="approveOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 报名名单对话框 -->
    <el-dialog title="报名名单" :visible.sync="registrationOpen" width="900px" append-to-body>
      <el-table :data="registrationList" v-loading="registrationLoading">
        <el-table-column label="序号" type="index" width="50"/>
        <el-table-column label="姓名" prop="userName" width="120"/>
        <el-table-column label="手机号" prop="phonenumber" width="120"/>
        <el-table-column label="报名时间" prop="createTime" width="180"/>
        <el-table-column label="打卡状态" width="100">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.checkInStatus === '0'" type="info">未打卡</el-tag>
            <el-tag v-else type="success">已打卡</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="打卡时间" prop="checkInTime" width="180"/>
        <el-table-column label="打卡照片">
          <template slot-scope="scope">
            <image-preview v-if="scope.row.checkInPhoto" :src="scope.row.checkInPhoto" :width="60" :height="60"/>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import { listActiveInfo, getActiveInfo, updateActiveInfo, updateActiveStatus } from "@/api/system/ActiveInfo"
import { listActiveAuditLog, addActiveAuditLog } from "@/api/system/ActiveAuditLog"
import { listActiveRegistration } from "@/api/system/ActiveRegistration"

export default {
  name: "DirectorActiveInfo",
  dicts: ['sys_act_type', 'sys_act_local'],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      activeList: [],
      activeTab: '1',
      pendingCount: 0,
      viewOpen: false,
      approveOpen: false,
      registrationOpen: false,
      registrationLoading: false,
      registrationList: [],
      auditLogs: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        status: '1'
      },
      viewForm: {},
      approveForm: {},
      approveRules: {
        approveResult: [{ required: true, message: "请选择审批结果", trigger: "change" }],
        auditRemark: [{ required: true, message: "请输入审批意见", trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
    this.getPendingCount()
  },
  methods: {
    getList() {
      this.loading = true
      listActiveInfo(this.queryParams).then(response => {
        this.activeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getPendingCount() {
      listActiveInfo({ status: '1', pageNum: 1, pageSize: 1 }).then(response => {
        this.pendingCount = response.total
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
      if (tab.name === '1') {
        this.queryParams.status = '1'
      } else if (tab.name === 'approved') {
        this.queryParams.status = '2,3'
      } else {
        this.queryParams.status = null
      }
      this.handleQuery()
    },
    handleView(row) {
      getActiveInfo(row.activityId).then(response => {
        this.viewForm = response.data
        this.viewOpen = true
        this.loadAuditLogs(row.activityId)
      })
    },
    loadAuditLogs(activityId) {
      listActiveAuditLog({ activityId: activityId }).then(response => {
        this.auditLogs = response.rows
      })
    },
    handleApprove(row) {
      this.approveForm = {
        activityId: row.activityId,
        title: row.title,
        approveResult: null,
        auditRemark: null
      }
      this.approveOpen = true
    },
    submitApprove() {
      this.$refs["approveForm"].validate(valid => {
        if (valid) {
          const oldStatus = this.activeList.find(item => item.activityId === this.approveForm.activityId).status
          const newStatus = this.approveForm.approveResult
          const statusMap = { '1': '待审批', '2': '已通过', '3': '已驳回' }
          
          // 更新活动状态
          updateActiveInfo({
            activityId: this.approveForm.activityId,
            status: newStatus
          }).then(() => {
            // 记录审批日志
            return addActiveAuditLog({
              activityId: this.approveForm.activityId,
              operType: newStatus,
              operContent: `状态由${statusMap[oldStatus]}变更为${statusMap[newStatus]}`,
              auditRemark: this.approveForm.auditRemark,
              operTime: new Date()
            })
          }).then(() => {
            this.$modal.msgSuccess("审批成功")
            this.approveOpen = false
            this.getList()
            this.getPendingCount()
          })
        }
      })
    },
    handleViewRegistrations(row) {
      this.registrationOpen = true
      this.registrationLoading = true
      listActiveRegistration({ activityId: row.activityId }).then(response => {
        this.registrationList = response.rows
        this.registrationLoading = false
      })
    },
    getOperTypeName(type) {
      const map = { '1': '提交', '2': '通过', '3': '驳回', '4': '开始', '5': '结束' }
      return map[type] || type
    },
    handleStartActivity(row) {
      this.$modal.confirm('确认开始该活动吗？活动开始后居民可以打卡。').then(() => {
        return updateActiveStatus({
          activityId: row.activityId,
          status: '4'
        })
      }).then(() => {
        this.$modal.msgSuccess("活动已开始")
        this.getList()
        this.getPendingCount()
      }).catch(() => {})
    },
    handleEndActivity(row) {
      this.$modal.confirm('确认结束该活动吗？活动结束后将不能再打卡。').then(() => {
        return updateActiveStatus({
          activityId: row.activityId,
          status: '5'
        })
      }).then(() => {
        this.$modal.msgSuccess("活动已结束")
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>
