<template>
  <div>
    <div style="width: 1000px; heigth: 500px; margin-left: 100px">
      <div>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="createClass"
          style="float: right"
          >创建班级</el-button
        >
      </div>
      <br />
      <div style="margin-top: 15px">
        <el-table :data="classData" stripe style="width: 100%" border>
          <el-table-column type="index" width="50"> </el-table-column>
          <el-table-column
            prop="className"
            label="班级名称"
            width="180"
          ></el-table-column>
          <el-table-column prop="id" label="班级ID" hidden> </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="selectClassStudents(scope.$index, scope.row)"
                >查看班级学生</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="insertClassStudents(scope.$index, scope.row)"
                >添加班级学生</el-button
              >
            </template>
          </el-table-column>
          <el-table-column fixed="right">
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
              >
            </template>
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

    <!-- 创建班级 -->
    <div>
      <el-dialog
        title="创建班级"
        :visible.sync="createClassFormVisible"
        width="500px"
      >
        <el-form :model="createClassFormModel">
          <el-form-item label="班级名称" :label-width="formLabelWidth">
            <el-input
              v-model="createClassFormModel.className"
              autocomplete="off"
              placeholder="电信18-5班"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="createClassFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="createClassForm">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 班级学生信息 -->
    <div>
      <el-dialog
        title="班级学生信息"
        :visible.sync="selectStudentsVisible"
        width="1000px"
      >
        <el-table :data="studentData">
          <el-table-column
            property="stuId"
            label="学号"
            width="120"
          ></el-table-column>
          <el-table-column
            property="stuName"
            label="姓名"
            width="100"
          ></el-table-column>
          <el-table-column
            property="stuPhone"
            label="手机"
            width="150"
          ></el-table-column>
          <el-table-column
            property="stuQq"
            label="QQ"
            width="150"
          ></el-table-column>
          <el-table-column
            property="stuMail"
            label="邮箱"
            width="150"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="stuHandleEdit(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="stuHandleDelete(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="width: auto">
          <el-pagination
            @size-change="stuHandleSizeChange"
            @current-change="stuHandleCurrentChange"
            :current-page="stuCurrentPage"
            :page-sizes="stuSizes"
            :page-size="stuSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="stuTotal"
          >
          </el-pagination>
        </div>
      </el-dialog>
    </div>

    <!-- 添加学生 -->
    <div>
      <el-dialog
        :title="insertStuToClass"
        :visible.sync="insertStudentsToClassVisible"
        width="500px"
      >
        <el-form
          :model="insertStudentsToClassModel"
          ref="insertStudentsToClassModel"
          label-width="100px"
        >
          <el-form-item
            v-for="(studentId, index) in insertStudentsToClassModel.studentIds"
            :prop="'studentIds.' + index + '.value'"
            label="学生学号"
            :rules="[
              { required: true, message: '请输入学号', trigger: 'blur' },
              { min: 8, max: 8, message: '请输入正确的学号', trigger: 'blur' },
            ]"
            :key="studentId.key"
          >
            <el-input v-model="studentId.value"></el-input>
            <el-button
              @click.prevent="removeStudentId(studentId)"
              style="margin-left: 5px"
              >删除</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitInsertStudentForm('insertStudentsToClassModel')"
              >提交</el-button
            >
            <el-button @click="addStudentId">新增学生</el-button>
            <el-button
              @click="resetInsertStudentForm('insertStudentsToClassModel')"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
        <!-- <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
  </div> -->
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "TeaClass",
  data() {
    return {
      // 添加学生到班级模块
      insertStuToClass: "",
      insertStuClassId: "",
      insertStudentsToClassVisible: false,
      insertStudentsToClassModel: {
        studentIds: [
          {
            value: "",
          },
        ],
      },

      // 查询班级学生模块
      currentClassId: "",
      studentData: [],
      selectStudentsVisible: false,
      stuCurrentPage: 1,
      stuSizes: [5, 10, 15, 20, 25],
      stuSize: 5,
      stuTotal: 100,

      // 查询班级模块
      classData: [],
      createClassFormVisible: false,
      formLabelWidth: "120px",
      createClassFormModel: {
        className: "",
      },
      currentPage: 1,
      sizes: [5, 10, 15, 20, 25],
      size: 5,
      total: 100,
    };
  },
  methods: {
    submitInsertStudentForm(insertStudentsToClassModel) {
      this.$refs[insertStudentsToClassModel].validate((valid) => {
        if (valid) {
          this.$confirm("确认将学生添加到此班级?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              let url = "classId=" + this.insertStuClassId;
              this.insertStudentsToClassModel.studentIds.forEach(
                (studentId) => {
                  url += "&students=" + studentId.value;
                }
              );
              this.$post("/teacher/insertStudent?" + url).then((res) => {
                this.insertStudentsToClassModel.studentIds = [{ value: "" }];
                this.insertStudentsToClassVisible = false;
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消添加",
              });
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetInsertStudentForm(insertStudentsToClassModel) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$refs[insertStudentsToClassModel].resetFields();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消重置",
          });
        });
    },
    removeStudentId(item) {
      let index = this.insertStudentsToClassModel.studentIds.indexOf(item);
      this.insertStudentsToClassModel.studentIds.splice(index, 1);
    },
    addStudentId() {
      this.insertStudentsToClassModel.studentIds.push({
        value: "",
        key: Date.now(),
      });
    },
    insertClassStudents(index, oneClass) {
      this.insertStuToClass = "添加学生到班级" + oneClass.className;
      this.insertStuClassId = oneClass.id;
      this.insertStudentsToClassVisible = true;
    },

    // 查询班级学生
    initClassStudents() {
      let url = "currentPage=" + this.stuCurrentPage + "&size=" + this.stuSize;
      this.$get(
        "/teacher/listStudents/" + this.currentClassId + "?" + url
      ).then((res) => {
        this.studentData = res.data;
        this.stuTotal = res.total;
      });
    },
    stuHandleEdit(index, oneStu) {
      this.$confirm("此操作将初始化学生账号密码为学号, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$post(
            "/teacher/updateStuById/" + oneStu.stuId
          ).then((res) => {});
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消初始化",
          });
        });
    },
    stuHandleDelete(index, oneStu) {
      this.$confirm("此操作将删除该学生用户包括其作业, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$delete("/teacher/deleteStudent/" + oneStu.stuId).then((res) => {
            this.initClassStudents();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    stuHandleCurrentChange(val) {
      this.stuCurrentPage = val;
      this.initClassStudents();
    },
    stuHandleSizeChange(val) {
      this.stuSize = val;
      this.initClassStudents();
    },
    selectClassStudents(index, oneClass) {
      this.currentClassId = oneClass.id;
      this.initClassStudents();
      this.selectStudentsVisible = true;
    },

    handleEdit(index, oneClass) {
      this.$prompt("请输入新的班级名称", "功能为开放", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "功能未开放",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    handleDelete(index, oneClass) {
      this.$confirm("此操作将永久删除该班级包括其学生, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$delete("/teacher/deleteClass/" + oneClass.id).then((res) => {});
          this.initClasses();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handleSizeChange(val) {
      this.size = val;
      this.initClasses();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initClasses();
    },

    createClass() {
      this.createClassFormVisible = true;
    },
    createClassForm() {
      this.$post(
        "/teacher/createClass/" + this.createClassFormModel.className
      ).then((res) => {
        this.initClasses();
      });
      this.createClassFormVisible = false;
      this.createClassFormModel.className = "";
    },

    initClasses() {
      let url = "?currentPage=" + this.currentPage + "&size=" + this.size;
      this.$get("/teacher/listClasses" + url).then((res) => {
        if (res) {
          this.classData = res.data;
          this.total = res.total;
          // this.$store.commit("initClass", res.data);
        }
      });
    },
  },
  computed: {},
  created() {
    this.initClasses();
  },
};
</script>

<style scoped>
</style>