<template>
  <div>
    <div style="width: 1000px; heigth: 500px; margin-left: 100px">
      <div>
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="createWork"
          style="float: right"
          >布置作业</el-button
        >
      </div>
      <br />
      <div style="margin-top: 15px">
        <el-table :data="classWorkData" stripe style="width: 100%" border>
          <el-table-column type="index" width="30"> </el-table-column>
          <el-table-column
            prop="classTea.className"
            label="班级名称"
            width="120"
          ></el-table-column>
          <el-table-column
            prop="teaWork.workTitle"
            label="作业标题"
            width="120"
          ></el-table-column>
          <el-table-column
            prop="teaWork.workDescribe"
            label="作业描述"
            width="120"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="创建日期"
            width="110"
          ></el-table-column>
          <el-table-column
            prop="endTime"
            label="截止日期"
            width="110"
          ></el-table-column>
          <el-table-column width="250">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="selectClassWork(scope.$index, scope.row)"
                >查看作业情况</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="getCheckResult(scope.$index, scope.row)"
                >查看查重结果</el-button
              >
            </template>
          </el-table-column>
          <el-table-column width="150">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEditClassWork(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDeleteClassWork(scope.$index, scope.row)"
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

    <!-- 修改作业截止日期弹出框 -->
    <div>
      <el-dialog
        title="修改作业截止日期"
        :visible.sync="updateClassWorkEndTimeVisible"
        width="500px"
      >
        <el-form>
          <el-form-item label="截至日期" required>
            <div class="block">
              <el-date-picker
                v-model="newEndTime"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateClassWorkEndTimeVisible = false"
            >取 消</el-button
          >
          <el-button type="primary" @click="checkUpdateClassWorkEndTime()"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>

    <!-- 布置作业模块 -->
    <div>
      <el-dialog title="布置作业" :visible.sync="createWorkFormVisible">
        <el-form
          :model="createWorkFormModel"
          :rules="createWorkFormRules"
          ref="createWorkFormModel"
          label-width="100px"
        >
          <el-form-item label="作业标题" prop="workTitle">
            <el-select
              v-model="createWorkFormModel.workTitle"
              clearable
              placeholder="请选择作业标题"
              @change="changWorkDescribe(createWorkFormModel.workTitle)"
            >
              <el-option
                v-for="item in workTitleOptions"
                :key="item.workId"
                :label="item.workTitle"
                :value="item.workTitle"
              >
                <span style="float: left">{{ item.workTitle }}</span>
                <el-button
                  class="el-icon-close"
                  style="float: right; margin-top: 4px"
                  circle
                  size="mini"
                  @click="deleteWorkTitle(item.workId)"
                ></el-button>
              </el-option>
            </el-select>
            <el-button
              icon="el-icon-plus"
              @click="createWorkTitle"
              style="margin-left: 5px"
              size="medium"
              >添加作业</el-button
            >
            <!-- <el-input
              v-model="createWorkFormModel.workTitle"
              placeholder="数据结构第一次作业"
            ></el-input> -->
          </el-form-item>
          <el-form-item label="作业描述" prop="workDescribe">
            <el-input
              v-model="createWorkFormModel.workDescribe"
              maxlength="100"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item label="选择班级" prop="workClasses">
            <el-checkbox-group v-model="createWorkFormModel.workClasses">
              <el-checkbox
                v-for="(workClass, index) in workClass"
                :key="index"
                :label="workClass.className"
                name="workClasses"
              ></el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="截至日期" required prop="workDateTime">
            <div class="block">
              <el-date-picker
                v-model="createWorkFormModel.workDateTime"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitCreateWorkForm('createWorkFormModel')"
              >布置</el-button
            >
            <el-button @click="resetCreateWorkForm('createWorkFormModel')"
              >取消</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

    <!-- 查询班级作业完成情况模块 -->
    <div>
      <el-dialog
        title="班级学生作业情况"
        :visible.sync="classStudentWorksVisible"
      >
        <el-table :data="classStudentWorksData">
          <el-table-column
            property="stuId"
            label="学号"
            width="120"
          ></el-table-column>
          <el-table-column
            property="workName"
            label="源代码"
            width="150"
          ></el-table-column>
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-button @click="downCode(scope.row)" type="text" size="small"
                >下载</el-button
              >
              <!-- <el-button
                @click="inlineCode(scope.row)"
                type="text"
                size="small"
                disabled
                >查看</el-button
              > -->
            </template>
          </el-table-column>
          <el-table-column
            property="pdfName"
            label="PDF"
            width="150"
          ></el-table-column>
          <el-table-column label="操作" width="90">
            <template slot-scope="scope">
              <el-button @click="downPdf(scope.row)" type="text" size="small"
                >下载</el-button
              >
              <!-- <el-button
                @click="inlinePdf(scope.row)"
                type="text"
                size="small"
                disabled
                >查看</el-button -->
            </template>
          </el-table-column>
          <el-table-column
            property="isCommit"
            label="提交情况"
          ></el-table-column>
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

    <!-- 查询查重结果 -->
    <div>
      <el-dialog title="作业查重情况" :visible.sync="checkResultVisible">
        <el-table :data="checkResultData" stripe>
          <el-table-column
            property="stuWorkFirstName"
            label="源代码一"
            width="250"
          ></el-table-column>
          <el-table-column
            property="stuWorkSecondName"
            label="源代码二"
            width="250"
          ></el-table-column>
          <el-table-column
            property="workResult"
            label="查重结果"
            width="150"
          ></el-table-column>
        </el-table>
        <div class="block" style="width: auto">
          <el-pagination
            @size-change="resHandleSizeChange"
            @current-change="resHandleCurrentChange"
            :current-page="resCurrentPage"
            :page-sizes="resSizes"
            :page-size="resSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="resTotal"
          >
          </el-pagination>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "TeaWork",
  data() {
    return {
      // 查重结果模块
      checkResultVisible: false,
      checkResultData: [],
      currentCheckResultWorkId: "",
      resCurrentPage: 1,
      resSizes: [5, 10, 15, 20, 30],
      resSize: 5,
      resTotal: 100,
      // 修改作业截止日期
      updateClassWorkEndTimeClassWorkId: "",
      newEndTime: "",
      updateClassWorkEndTimeVisible: false,
      // 查询班级学生作业情况模块
      currentClassId: "",
      currentWorkId: "",
      stuCurrentPage: 1,
      stuSizes: [5, 10, 15, 20, 30],
      stuSize: 5,
      stuTotal: 100,
      classStudentWorksVisible: false,
      classStudentWorksData: [],
      // 查询班级作业模块
      classWorkData: [],
      currentPage: 1,
      sizes: [5, 10, 15],
      size: 5,
      total: 100,
      // 布置作业模块
      workTitleOptions: [],
      workClass: [],
      createWorkFormRules: {
        workTitle: [
          { required: true, message: "请选择作业标题", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "长度在 3 到 15 个字符",
            trigger: "blur",
          },
        ],
        workDescribe: [],
        workDateTime: [
          {
            type: "date",
            required: true,
            message: "请选择截止时间",
            trigger: "change",
          },
        ],
        workClasses: [
          {
            type: "array",
            required: true,
            message: "请至少选择一个班级",
            trigger: "change",
          },
        ],
      },
      createWorkFormModel: {
        workTitle: "",
        workDescribe: "",
        workDateTime: "",
        workClasses: [],
      },
      createWorkFormVisible: false,
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "明天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周后",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
    };
  },
  methods: {
    // 查重结果模块
    initCheckResult() {
      let url =
        "currentPage=" +
        this.resCurrentPage +
        "&size=" +
        this.resSize +
        "&workId=" +
        this.currentCheckResultWorkId;
      this.$get("/teacher/checkResult?" + url).then((res) => {
        this.checkResultData = res.data;
        this.resTotal = res.total;
      });
    },
    resHandleSizeChange(val) {
      this.resSize = val;
      this.initCheckResult();
    },
    resHandleCurrentChange(val) {
      this.resCurrentPage = val;
      this.initCheckResult();
    },
    // 修改作业截止日期
    checkUpdateClassWorkEndTime() {
      // updateClassWorkEndTimeClassWorkId
      //  newEndTime: ""
      let date = this.newEndTime;
      function p(s) {
        return s < 10 ? "0" + s : s;
      }
      let endTime =
        date.getFullYear() +
        "-" +
        p(date.getMonth() + 1) +
        "-" +
        p(date.getDate()) +
        " " +
        p(date.getHours()) +
        ":" +
        p(date.getMinutes()) +
        ":" +
        p(date.getSeconds());
      let url =
        "classWorkId=" +
        this.updateClassWorkEndTimeClassWorkId +
        "&newEndTime=" +
        endTime;
      this.$put("/teacher/updateWorkEnd?" + url).then((res) => {
        this.endTime = "";
        this.initClassWorks();
        this.updateClassWorkEndTimeVisible = false;
      });
    },
    // 查询班级学生作业情况模块
    initClassStudentWorks() {
      let url =
        "currentPage=" +
        this.stuCurrentPage +
        "&size=" +
        this.stuSize +
        "&classId=" +
        this.currentClassId +
        "&workId=" +
        this.currentWorkId;
      this.$get("/teacher/getClassStudentWorks?" + url).then((res) => {
        this.classStudentWorksData = res.data;
        this.stuTotal = res.total;
      });
    },
    stuHandleSizeChange(val) {
      this.stuSize = val;
      this.initClassStudentWorks();
    },
    stuHandleCurrentChange(val) {
      this.stuCurrentPage = val;
      this.initClassStudentWorks();
    },
    // 查询班级作业模块
    inlineCode(row) {},
    downCode(row) {
      let flag = 1;
      this.downStuWork(row.id, flag);
    },
    inlinePdf(row) {},
    downPdf(row) {
      let flag = 2;
      this.downStuWork(row.id, flag);
    },
    downStuWork(stuWorkId, flag) {
      let url = "stuWorkId=" + stuWorkId + "&flag=" + flag;
      this.$download("/teacher/download?" + url).then((res) => {});
    },
    handleSizeChange(val) {
      this.size = val;
      this.initClassWorks();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initClassWorks();
    },
    selectClassWork(index, row) {
      this.currentClassId = row.classId;
      this.currentWorkId = row.workId;
      this.initClassStudentWorks();
      this.classStudentWorksVisible = true;
    },
    getCheckResult(index, row) {
      this.checkResultVisible = true;
      this.currentCheckResultWorkId = row.workId;
      this.initCheckResult();
    },
    handleEditClassWork(index, row) {
      this.updateClassWorkEndTimeClassWorkId = row.id;
      this.updateClassWorkEndTimeVisible = true;
    },
    handleDeleteClassWork(index, row) {
      this.$confirm("此操作将永久删除该班级作业, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$delete("/teacher/deleteWorkByClass/" + row.id).then((res) => {
            this.initClassWorks();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    initClassWorks() {
      let url = "currentPage=" + this.currentPage + "&size=" + this.size;
      this.$get("/teacher/getAllWorks?" + url).then((res) => {
        this.classWorkData = res.data;
        this.total = res.total;
      });
    },
    // 布置作业模块
    initClass() {
      this.$get("/teacher/getClasses").then((res) => {
        this.workClass = res;
      });
    },
    deleteWorkTitle(workId) {
      this.$confirm("确定删除该作业包括其已经提交的内容?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$delete("/teacher/deleteWorkById/" + workId).then((res) => {
            this.createWorkFormModel.workTitle = "";
            this.createWorkFormModel.workDescribe = "";
            this.getWorkTitles();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    changWorkDescribe(workTitle) {
      this.workTitleOptions.forEach((workOption) => {
        if (workOption.workTitle == workTitle) {
          this.createWorkFormModel.workDescribe = workOption.workDescribe;
        }
      });
    },
    getWorkTitles() {
      this.$get("/teacher/listWorkTitles").then((res) => {
        this.workTitleOptions = res;
      });
    },
    createWorkTitle() {
      this.$prompt("请输入作业标题", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          this.$post("/teacher/createWorkTitle/" + value).then((res) => {
            this.getWorkTitles();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    submitCreateWorkForm(createWorkFormModel) {
      this.$refs[createWorkFormModel].validate((valid) => {
        if (valid) {
          this.checkSubmitCreateWork(createWorkFormModel);
        } else {
          this.$message("布置失败");
          return false;
        }
      });
    },
    checkSubmitCreateWork(createWorkFormModel) {
      // 此弹框不会终端程序运行,弹出后程序会继续运行,在选择确定会再运行.then中的程序,所以不能实现多次弹框
      this.$confirm("确定向班级布置作业", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 标准时间格式转化为 "yyyy-MM-dd HH:mm:ss"
          // 后端再根据 yyyy-MM-dd HH:mm:ss 转为日期类型
          let date = this.createWorkFormModel.workDateTime;
          function p(s) {
            return s < 10 ? "0" + s : s;
          }
          let endTime =
            date.getFullYear() +
            "-" +
            p(date.getMonth() + 1) +
            "-" +
            p(date.getDate()) +
            " " +
            p(date.getHours()) +
            ":" +
            p(date.getMinutes()) +
            ":" +
            p(date.getSeconds());
          // 补全url
          let url =
            "workTitle=" +
            this.createWorkFormModel.workTitle +
            "&endTime=" +
            endTime +
            "&workDescribe=" +
            this.createWorkFormModel.workDescribe;
          // console.log(this.createWorkFormModel.workClasses);
          // 判断某班级是否已经布置过
          let flag = false;
          this.createWorkFormModel.workClasses.forEach((workClass) => {
            // 循环班级数据得到班级id
            this.workClass.forEach((getClass) => {
              // 相等时说明该班级被选中
              if (getClass.className == workClass) {
                // 循环判断班级作业数据
                this.classWorkData.forEach((classWork) => {
                  // 相等说明该班级已经布置过该作业了
                  if (
                    classWork.classId == getClass.id &&
                    classWork.teaWork.workTitle ==
                      this.createWorkFormModel.workTitle
                  ) {
                    flag = true;
                  }
                });
              }
              url += "&classIds=" + getClass.id;
            });
          });
          // 存在布置过的作业,询问是否重新布置,不能实现对每个班级的判断,原因在于弹框原理
          if (flag) {
            this.$confirm("存在班级已经布置过该作业了, 是否重新布置?", "提示", {
              confirmButtonText: "是",
              cancelButtonText: "否",
              type: "warning",
            })
              .then(() => {
                this.$post("/teacher/dispose?" + url).then((res) => {
                  this.createWorkFormVisible = false;
                  this.$refs[createWorkFormModel].resetFields();
                  this.initClassWorks();
                });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消布置",
                });
              });
          }
          if (!flag) {
            this.$post("/teacher/dispose?" + url).then((res) => {
              this.createWorkFormVisible = false;
              this.$refs[createWorkFormModel].resetFields();
              this.initClassWorks();
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消布置",
          });
        });
    },
    resetCreateWorkForm(createWorkFormModel) {
      this.createWorkFormVisible = false;
      this.$refs[createWorkFormModel].resetFields();
    },
    createWork() {
      this.createWorkFormVisible = true;
    },
  },
  computed: {},
  created() {
    this.initClass();
    this.getWorkTitles();
    this.initClassWorks();
  },
};
</script>

<style scoped>
</style>