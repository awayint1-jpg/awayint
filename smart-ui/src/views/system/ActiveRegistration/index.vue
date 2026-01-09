<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联活动ID" prop="activityId">
        <el-input
          v-model="queryParams.activityId"
          placeholder="请输入关联活动ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报名用户ID(关联sys_user)" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入报名用户ID(关联sys_user)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报名人姓名(冗余字段，方便快速查询)" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入报名人姓名(冗余字段，方便快速查询)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号(冗余字段)" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入手机号(冗余字段)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打卡照片路径(OSS或本地路径)" prop="checkInPhoto">
        <el-input
          v-model="queryParams.checkInPhoto"
          placeholder="请输入打卡照片路径(OSS或本地路径)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打卡时间" prop="checkInTime">
        <el-date-picker clearable
          v-model="queryParams.checkInTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择打卡时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:ActiveRegistration:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:ActiveRegistration:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:ActiveRegistration:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:ActiveRegistration:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ActiveRegistrationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报名主键ID" align="center" prop="regId" />
      <el-table-column label="关联活动ID" align="center" prop="activityId" />
      <el-table-column label="报名用户ID(关联sys_user)" align="center" prop="userId" />
      <el-table-column label="报名人姓名(冗余字段，方便快速查询)" align="center" prop="userName" />
      <el-table-column label="手机号(冗余字段)" align="center" prop="phonenumber" />
      <el-table-column label="打卡状态(0未打卡 1已打卡)" align="center" prop="checkInStatus" />
      <el-table-column label="打卡照片路径(OSS或本地路径)" align="center" prop="checkInPhoto" />
      <el-table-column label="打卡时间" align="center" prop="checkInTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkInTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:ActiveRegistration:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:ActiveRegistration:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改活动报名与打卡记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联活动ID" prop="activityId">
          <el-input v-model="form.activityId" placeholder="请输入关联活动ID" />
        </el-form-item>
        <el-form-item label="报名用户ID(关联sys_user)" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入报名用户ID(关联sys_user)" />
        </el-form-item>
        <el-form-item label="报名人姓名(冗余字段，方便快速查询)" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入报名人姓名(冗余字段，方便快速查询)" />
        </el-form-item>
        <el-form-item label="手机号(冗余字段)" prop="phonenumber">
          <el-input v-model="form.phonenumber" placeholder="请输入手机号(冗余字段)" />
        </el-form-item>
        <el-form-item label="打卡照片路径(OSS或本地路径)" prop="checkInPhoto">
          <el-input v-model="form.checkInPhoto" placeholder="请输入打卡照片路径(OSS或本地路径)" />
        </el-form-item>
        <el-form-item label="打卡时间" prop="checkInTime">
          <el-date-picker clearable
            v-model="form.checkInTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择打卡时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listActiveRegistration, getActiveRegistration, delActiveRegistration, addActiveRegistration, updateActiveRegistration } from "@/api/system/ActiveRegistration"

export default {
  name: "ActiveRegistration",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 活动报名与打卡记录表格数据
      ActiveRegistrationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityId: null,
        userId: null,
        userName: null,
        phonenumber: null,
        checkInStatus: null,
        checkInPhoto: null,
        checkInTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activityId: [
          { required: true, message: "关联活动ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "报名用户ID(关联sys_user)不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询活动报名与打卡记录列表 */
    getList() {
      this.loading = true
      listActiveRegistration(this.queryParams).then(response => {
        this.ActiveRegistrationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        regId: null,
        activityId: null,
        userId: null,
        userName: null,
        phonenumber: null,
        checkInStatus: null,
        checkInPhoto: null,
        checkInTime: null,
        createTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.regId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加活动报名与打卡记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const regId = row.regId || this.ids
      getActiveRegistration(regId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改活动报名与打卡记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.regId != null) {
            updateActiveRegistration(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addActiveRegistration(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const regIds = row.regId || this.ids
      this.$modal.confirm('是否确认删除活动报名与打卡记录编号为"' + regIds + '"的数据项？').then(function() {
        return delActiveRegistration(regIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/ActiveRegistration/export', {
        ...this.queryParams
      }, `ActiveRegistration_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
