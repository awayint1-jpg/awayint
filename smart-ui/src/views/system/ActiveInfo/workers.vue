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
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="草稿" value="0"/>
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增活动</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

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
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="info">草稿</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" type="warning">待审批</el-tag>
          <el-tag v-else-if="scope.row.status === '2'" type="success">已通过</el-tag>
          <el-tag v-else-if="scope.row.status === '3'" type="danger">已驳回</el-tag>
          <el-tag v-else-if="scope.row.status === '4'">进行中</el-tag>
          <el-tag v-else-if="scope.row.status === '5'" type="info">已结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="350" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="['0','3'].includes(scope.row.status)" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-if="['0','3'].includes(scope.row.status)" size="mini" type="text" icon="el-icon-upload2" @click="handleSubmit(scope.row)">提交审批</el-button>
          <el-button v-if="scope.row.status === '0'" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button v-if="scope.row.status === '2'" size="mini" type="text" icon="el-icon-video-play" @click="handleStartActivity(scope.row)" style="color: #67C23A">开始活动</el-button>
          <el-button v-if="scope.row.status === '4'" size="mini" type="text" icon="el-icon-circle-check" @click="handleEndActivity(scope.row)" style="color: #909399">结束活动</el-button>
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button v-if="['2','4','5'].includes(scope.row.status)" size="mini" type="text" icon="el-icon-user" @click="handleViewRegistrations(scope.row)">报名名单</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 添加或修改活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入活动标题" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="活动类型" prop="activityType">
          <el-select v-model="form.activityType" placeholder="请选择或输入自定义类型" filterable allow-create style="width: 100%">
            <el-option v-for="dict in dict.type.sys_act_type" :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="活动地点" prop="address">
          <el-select v-model="form.address" placeholder="请选择或输入自定义地点" filterable allow-create style="width: 100%">
            <el-option v-for="dict in dict.type.sys_act_local" :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="活动时间" required>
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-date-picker v-model="form.startTime" type="datetime" placeholder="开始时间" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%"/>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="text-align: center">至</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-date-picker v-model="form.endTime" type="datetime" placeholder="结束时间" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%"/>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="人数上限" prop="maxPeople">
          <el-input-number v-model="form.maxPeople" :min="1" :max="1000" style="width: 100%"/>
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="4" placeholder="请输入活动描述"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

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
          <el-tag v-if="viewForm.status === '0'" type="info">草稿</el-tag>
          <el-tag v-else-if="viewForm.status === '1'" type="warning">待审批</el-tag>
          <el-tag v-else-if="viewForm.status === '2'" type="success">已通过</el-tag>
          <el-tag v-else-if="viewForm.status === '3'" type="danger">已驳回</el-tag>
          <el-tag v-else-if="viewForm.status === '4'">进行中</el-tag>
          <el-tag v-else-if="viewForm.status === '5'" type="info">已结束</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建人">{{ viewForm.createBy }}</el-descriptions-item>
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
import { listActiveInfo, getActiveInfo, delActiveInfo, addActiveInfo, updateActiveInfo, submitActiveInfo, updateActiveStatus } from "@/api/system/ActiveInfo"
import { listActiveAuditLog } from "@/api/system/ActiveAuditLog"
import { listActiveRegistration } from "@/api/system/ActiveRegistration"

export default {
  name: "WorkersActiveInfo",
  dicts: ['sys_act_type', 'sys_act_local'],
  data() {
    const validateTime = (rule, value, callback) => {
      if (!this.form.startTime) {
        callback(new Error('请选择开始时间'))
      } else if (!this.form.endTime) {
        callback(new Error('请选择结束时间'))
      } else if (new Date(this.form.startTime) < new Date()) {
        callback(new Error('活动开始时间不能早于当前时间'))
      } else if (new Date(this.form.endTime) <= new Date(this.form.startTime)) {
        callback(new Error('结束时间必须晚于开始时间'))
      } else {
        callback()
      }
    }
    
    return {
      loading: true,
      showSearch: true,
      total: 0,
      activeList: [],
      title: "",
      open: false,
      viewOpen: false,
      registrationOpen: false,
      registrationLoading: false,
      registrationList: [],
      auditLogs: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        activityType: null,
        status: null
      },
      form: {},
      viewForm: {},
      rules: {
        title: [{ required: true, message: "活动标题不能为空", trigger: "blur" }],
        activityType: [{ required: true, message: "活动类型不能为空", trigger: "change" }],
        address: [{ required: true, message: "活动地点不能为空", trigger: "blur" }],
        startTime: [{ required: true, validator: validateTime, trigger: "change" }],
        endTime: [{ required: true, validator: validateTime, trigger: "change" }],
        maxPeople: [{ required: true, message: "人数上限不能为空", trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
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
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        activityId: null,
        title: null,
        activityType: null,
        address: null,
        startTime: null,
        endTime: null,
        maxPeople: 50,
        description: null,
        status: '0',
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增活动"
    },
    handleUpdate(row) {
      this.reset()
      getActiveInfo(row.activityId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改活动"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.activityId != null) {
            updateActiveInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addActiveInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除该活动？').then(() => {
        return delActiveInfo(row.activityId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleSubmit(row) {
      this.$modal.confirm('是否确认提交该活动进行审批？').then(() => {
        return submitActiveInfo({
          activityId: row.activityId
        })
      }).then(() => {
        this.$modal.msgSuccess("提交成功")
        this.getList()
      }).catch(() => {})
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
