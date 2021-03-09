<template>
  <div>
    <div style="width: 1000px; heigth: 500px; margin-left: 100px">
      <div style="margin-top: 10px" class="select-div">
        <el-input
          placeholder="请输入ID"
          v-model="selectId"
          class="input-with-select"
          size="small"
        >
          <el-select
            v-model="select"
            slot="prepend"
            placeholder="请选择"
            style="width: 90px"
          >
            <el-option label=" 所有" value="1"></el-option>
            <el-option label=" 教师" value="2"></el-option>
            <el-option label=" 学生" value="3"></el-option>
          </el-select>
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="selectById"
          ></el-button>
        </el-input>
        <el-button
          type="primary"
          style="margin-left: 580px"
          icon="el-icon-plus"
          size="small"
          @click="insertTeacher"
          >添加教师</el-button
        >
      </div>
      <br />
      <div>
        <el-table
          size="small"
          :data="userData"
          border
          style="width: 100%"
          height="500px"
          stripe
        >
          <el-table-column prop="username" label="用户ID" width="200">
          </el-table-column>
          <el-table-column prop="userRole" label="用户角色" width="200">
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="200">
          </el-table-column>
          <el-table-column prop="createTime" label="更新时间" width="200">
          </el-table-column>
          <el-table-column fixed="right" label="操作" style="width: auto">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >删除</el-button
              > </template
            >>
          </el-table-column>
        </el-table>
      </div>
      <div class="block" style="width: auto">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="sizes"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 添加教师弹框 -->
    <div>
      <el-dialog
        title="添加教师"
        :visible.sync="insertTeacherVersibe"
        width="400px"
      >
        <el-form
          :model="insertTeacherModel"
          ref="insertTeacherModel"
          :rules="insertTeacherRules"
          size="small"
        >
          <el-form-item
            label="教师工号"
            :label-width="formLabelWidth"
            prop="teacherId"
          >
            <el-input
              v-model="insertTeacherModel.teacherId"
              autocomplete="off"
              size="small"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="insertTeacherVersibe = false" size="small"
            >取 消</el-button
          >
          <el-button
            type="primary"
            @click="insertTeacherForm('insertTeacherModel')"
            size="small"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdmUser",
  data() {
    return {
      insertTeacherVersibe: false,
      formLabelWidth: "100px",
      insertTeacherModel: {
        teacherId: "",
      },
      insertTeacherRules: {
        teacherId: [
          { required: true, message: "请输入老师工号", trigger: "blur" },
          { min: 8, max: 8, message: "工号长度有误", trigger: "blur" },
        ],
      },
      selectId: "",
      select: "1",
      userData: [],
      currentPage: 1,
      sizes: [10, 20, 30, 40, 50],
      size: 10,
      total: 100,
    };
  },
  methods: {
    selectById() {
      this.initUsers();
    },
    insertTeacher() {
      this.insertTeacherVersibe = true;
    },
    insertTeacherForm(insertTeacherModel) {
      this.$refs[insertTeacherModel].validate((valid) => {
        if (valid) {
          this.$post("/admin/insert/" + this.insertTeacherModel.teacherId).then(
            (res) => {
              this.initUsers();
            }
          );
          this.insertTeacherVersibe = false;
          this.insertTeacherModel.teacherId = "";
        } else {
          this.insertTeacherModel.teacherId = "";
          this.insertTeacherVersibe = false;
        }
      });
    },
    handleSizeChange(val) {
      this.size = val;
      this.initUsers();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initUsers();
    },
    handleEdit(index, data) {
      this.$confirm("此操作将初始化用户密码, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$put("/admin/init/" + data.username).then((res) => {});
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消初始化",
          });
        });
    },
    handleDelete(index, data) {
      this.$confirm(
        "此操作将永久删除该教师包括其学生和班级, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          if (data.userRoleId == 3) {
            this.$message({
              type: "info",
              message: "只能删除教师",
            });
          } else if (data.userRoleId == 2) {
            this.$delete("/admin/delete/" + data.username).then((res) => {
              this.initUsers();
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    initUsers() {
      let url =
        "?currentPage=" +
        this.currentPage +
        "&size=" +
        this.size +
        "&userId=" +
        this.selectId;
      this.$get("/admin/list" + url).then((res) => {
        if (res) {
          this.userData = res.data;
          this.total = res.total;
          this.selectId = "";
        }
      });
    },
  },
  created() {
    this.initUsers();
  },
};
</script>

<style scoped>
.el-select .el-input {
  width: 100px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.input-with-select {
  width: 300px;
}
</style>