<template>
  <div style="height:100%">
    <div class="action">
      <Row type="flex" class="code-row-bg" style="position:relative;margin-top:15px;">
        <Col span="5"></Col>
        <Col span="6"></Col>
        <Col span="13" style="text-align:right">
          <div>
            <Button
              type="info"
              @click="handleOnClickRefresh()"
              style="margin-left:2%"
              size="large"
            >刷新</Button>
            <Button type="success" size="large" @click="addmodal=true">增加课程</Button>
          </div>
        </Col>
      </Row>
    </div>
    <div style="margin-right:0%;">
      <div class="Table" id="printDiv">
        <div class="tablemain">
          <Table ref="selection" :columns="columns" :data="data" stripe :loading="loading"></Table>
          <Divider style="margin-top:0px"></Divider>
        </div>
      </div>
    </div>

    <Modal title="增加课程" v-model="addmodal" width="450" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">增加课程</span>
      </p>
      <div style="width:100%;position:relative">
        <Form ref="theCourse" :model="theCourse" :label-width="100">
          <FormItem label="课程号">
            <Select v-model="theCourse.courseNum" style="width:100%" placeholder="请选择课程">
              <Option
                v-for="item in courseList"
                :value="item.courseNum"
                :key="item.courseNum"
              >{{ item.courseName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="教师号">
            <Select v-model="theCourse.teacherNum" style="width:100%" placeholder="请选择老师">
              <Option
                v-for="item in teacherList"
                :value="item.teacherNum"
                :key="item.teacherNum"
              >{{ item.teacherName }}</Option>
            </Select>
          </FormItem>
          <!-- <FormItem label="学期">
            <Select v-model="theCourse.semester" style="width:100%" placeholder="请选择课程的学期">
              <Option
                v-for="item in semesterList"
                :value="item.semester"
                :key="item.semester"
              >{{ item.semester }}</Option>
            </Select>
          </FormItem> -->
          <FormItem label="上课时间">
            <Row>
              <Col span="11">
                <Select v-model="theCourse.week" style="width:100%" placeholder="星期">
                  <Option
                    v-for="item in weekList"
                    :value="item.value"
                    :key="item.value"
                  >{{ item.label }}</Option>
                </Select>
              </Col>
              <Col span="2" style="text-align: center">-</Col>
              <Col span="11">
                <Select v-model="theCourse.section" style="width:100%" placeholder="时间">
                  <Option v-for="item in sectionList" :value="item" :key="item">{{ item }}</Option>
                </Select>
              </Col>
            </Row>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="create()"
          style="margin-right:1vh;"
          :disabled="theCourse.submit"
        >立即创建</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="addmodal=false"
          style="margin-right:1vh;"
        >取消</Button>
      </div>
    </Modal>
    <Modal v-model="deletemodal" width="20" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>删除后，其数据将不能保留</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="deleteCourse(courseIndex)">删除</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      deletemodal: false,
      addmodal: false,
      courseIndex: -1,
      weekList: [
        {
          label: "一",
          value: 1
        },
        {
          label: "二",
          value: 2
        },
        {
          label: "三",
          value: 3
        },
        {
          label: "四",
          value: 4
        },
        {
          label: "五",
          value: 5
        }
      ],
      sectionList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13],
      semesterList: [],
      theCourse: {
        teacherName: "",
        teacherNum: "",
        courseNum: "",
        courseName: "",
        week: "",
        section: "",
        allTime: "",
        time: "",
        credit: "",
        academy: "",
        submit: true
      },
      academyList: [],
      legal: false,
      legal2:false,
      creditList: [1, 2, 3, 4, 5, 6],
      teacherList: [],
      courseList: [],
      columns: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "课程号",
          key: "courseNum",
          align: "center"
        },
        {
          title: "课程名",
          key: "courseName",
          align: "center"
        },
        {
          title: "教师号",
          key: "teacherNum",
          align: "center"
        },
        {
          title: "教师名",
          key: "teacherName",
          align: "center"
        },
        {
          title: "学期",
          key: "semester",
          align: "center"
        },
        {
          title: "上课时间",
          key: "allTime",
          align: "center",
          render: (h, params) => {
            return h(
              "Tag",
              {
                props: {
                  size: "large"
                },
                style: {
                  fontSize: "15px"
                }
              },
              params.row.allTime
            );
          }
        },
      

        {
          title: "操作",
          slot: "action",
          // width: 190,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  style: {
                    marginRight: "3px"
                  },
                  props: {
                    type: "info",

                    ghost: true
                  },
                  on: {
                    click: () => {
                      localStorage.setItem(
                        "oid",
                        this.data[params.index].oid
                      );
                      console.log(this.data)
                      this.$router.push({ path: "/control/scourse" });
                    }
                  }
                },
                "详情"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",

                    ghost: true
                  },
                  on: {
                    click: () => {
                      this.courseIndex = params.index;
                      this.deletemodal = true;
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      data: [],

      page: 1
    };
  },
  methods: {
    handleOnClickRefresh() {
      this.loadData();
    },

    cutaway(index) {
      this.page = index;
    },
    create() {
      this.legals();
      this.legals2();
      if (!this.legal) {
        this.$Notice.warning({ title: `已有该课程` });
        
        return;
      }
      if (!this.legal2) {
        this.$Notice.warning({ title: `该老师该时间已有课` });
        
        return;
      }
      let nowSemester='';
      for(let i = 0; i<this.semesterList.length;i++){
        if(this.semesterList[i].already==1){
          nowSemester=this.semesterList[i].semester;
        }
      }
      let URL = `${apiPath}/classcreated/add`;
      let id = this.data[this.data.length - 1].oid + 1;
      let theTime = (this.theCourse.week - 1) * 13 + this.theCourse.section - 1;
      console.log(theTime);
      axios({
        url: URL,
        method: "get",
        params: {
          oid: id,
          courseNum: this.theCourse.courseNum,
          teacherNum: this.theCourse.teacherNum,
          time: theTime,
          semester: nowSemester
        }
      })
        .then(res => {
          this.loadData();
          this.$Notice.success({ title: `创建成功` });
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
      this.addmodal = false;
    },
    deleteCourse(courseIndex) {
      let URL = `${apiPath}/classcreated/delete`;

      axios({
        url: URL,
        method: "get",
        params: {
          oid: this.data[courseIndex].oid
        }
      })
        .then(res => {
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `该门课有学生` });
        });
        this.deletemodal = false;
    },
    legals() {
      for (let i = 0; i < this.data.length; i++) {
        if (
          this.theCourse.courseNum == this.data[i].courseNum &&
          this.theCourse.teacherNum == this.data[i].teacherNum &&
          this.theCourse.semester == this.data[i].semester
        ) {
          this.legal = false;
          return;
        }
      }
      this.legal = true;
    },
    legals2(){
      let theTime = (this.theCourse.week - 1) * 13 + this.theCourse.section - 1;
      for (let i = 0; i < this.data.length; i++) {
        if (
          
          this.theCourse.teacherNum == this.data[i].teacherNum &&
          theTime == this.data[i].time &&
          this.theCourse.semester == this.data[i].semester
        ) {
          this.legal2 = false;
          return;
        }
      }
      this.legal2 = true;
    },

    loadData() {
      this.loadCourse();
      this.loadSemester();
      this.loadTeacher();
      this.loadCoursecreated();
      console.log(this.teacherList);
    },
    loadCourse() {
      let URL = `${apiPath}/classcreated/courseNum`;
      this.loading = true;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.loading = false;
          this.courseList = res.data;
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadSemester() {
      let URL = `${apiPath}/semesters`;
      this.loading = true;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.loading = false;
          this.semesterList = res.data;
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadTeacher() {
      let URL = `${apiPath}/classcreated/teacherNum`;
      this.loading = true;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.loading = false;
          this.teacherList = res.data;
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadCoursecreated() {
      let URL = `${apiPath}/classcreated/consult`;
      this.loading = true;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.loading = false;
          this.data = res.data;
          for (let i = 0; i < this.data.length; i++) {
            let week = Math.floor(this.data[i].time / 13);
            this.data[i].section = (this.data[i].time % 13) + 1;
            let weeks = this.weekList[week].label;
            this.data[i].week = weeks;
            this.data[i].allTime = weeks + "         " + this.data[i].section;
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  mounted() {
    this.loadData();
  },

  computed: {},
  watch: {
    addmodal(val, oldVal) {
      if (!val) {
        this.theCourse.teacherNum = "";
        this.theCourse.teacherName = "";
        this.theCourse.courseNum = "";
        this.theCourse.semester = "";
        this.theCourse.time = "";
        this.theCourse.credit = "";
        this.theCourse.academy = "";
        this.theCourse.week = "";
        this.theCourse.section = "";
        this.theCourse.submit = false;
      }
    },

    theCourse: {
      handler() {
        if (
          this.theCourse.teacherNum != "" &&
          this.theCourse.courseNum != "" &&
          this.theCourse.week != "" &&
          this.theCourse.section != "" &&
          this.theCourse.semester != ""
        ) {
          this.theCourse.submit = false;
        } else {
          this.theCourse.submit = true;
        }
      },
      deep: true
    }
  }
};
</script>

<style scoped lang ='scss'>
@import "courses";
</style>
