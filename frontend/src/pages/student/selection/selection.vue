<template>
  <div>
    <!-- <Row type="flex" justify="space-around" class="code-row-bg">
      <Col span="13">
        <Tabs
          type="card"
          :value="theTabs"
          style="padding-left:5%;margin-top:2%"
          size="small"
          @on-click="tabClick"
        >
          <TabPane label="已选" name="courses" style="background:#fff">
            <Table stripe :columns="columns" :data="data" max-height="573">
              <template slot-scope="{ row }" slot="course">
                <div style="margin-top:20px;margin-bottom:20px">
                  <p style="font-size:15px">
                    {{row.courseName}}
                    <Tag type="border" style="border-radius:5px;float:right;">{{row.credit}}</Tag>
                  </p>

                  <p style="font-size:12px;color:#000">({{row.courseNum}})</p>
                </div>
              </template>
              <template slot-scope="{ row }" slot="teacher">
                <div style="margin-top:20px;margin-bottom:20px">
                  <p style="font-size:15px">{{row.teacherName}}</p>

                  <p style="font-size:12px;color:#000">({{row.teacherNum}})</p>
                </div>
              </template>

              <template slot-scope="{ row, index }" slot="action">
                <Button size="large" type="error" ghost @click="deleteCourse(row)">退课</Button>
              </template>
            </Table>
          </TabPane>
          <TabPane label="检索课程" name="choose">
            <div id="choose-box">
              <Row type="flex" justify="space-between" class="code-row-bg">
                <Col span="24">
                  <Row type="flex" justify="start" class="code-row-bg">
                    <Col span="6" style="text-align:right">
                      <Input v-model="theCourse.courseNum" placeholder="请输入课程号" clearable />
                    </Col>

                    <Col span="6" style="position:relative;background:#fff" offset="2">
                      <Input v-model="theCourse.courseName" placeholder="请输入课名" clearable />
                    </Col>
                    <Col span="6" style="position:relative;" offset="2">
                      <Input v-model="theCourse.credit" placeholder="学分数" clearable />
                    </Col>
                  </Row>
                  <Row type="flex" justify="start" class="code-row-bg" style="margin-top:10px">
                    <Col span="7" style="text-align:right">
                      <Input v-model="theCourse.teacherNum" placeholder="请输入教师号" clearable />
                    </Col>

                    <Col span="7" style="position:relative;background:#fff;left:15px;">
                      <Input v-model="theCourse.teacherName" placeholder="请输入教师名" clearable />
                    </Col>
                    <Col span="7" offset="2" style="margin-top:5px">
                      筛除冲突：
                      <i-switch v-model="switch1" style="margin-right: -5px"></i-switch>
                    </Col>
                  </Row>
                </Col>
              </Row>
            </div>
            <div>
              <Table stripe :columns="columns2" :data="data2" max-height="500">
                <template slot-scope="{ row }" slot="course">
                  <div style="margin-top:20px;margin-bottom:20px">
                    <p style="font-size:15px">
                      {{row.courseName}}
                      <Tag type="border" style="border-radius:5px;float:right;">{{row.credit}}</Tag>
                    </p>

                    <p style="font-size:12px;color:#000">({{row.courseNum}})</p>
                  </div>
                </template>
                <template slot-scope="{ row }" slot="teacher">
                  <div style="margin-top:20px;margin-bottom:20px">
                    <p style="font-size:15px">{{row.teacherName}}</p>

                    <p style="font-size:12px;color:#000">({{row.teacherNum}})</p>
                  </div>
                </template>

                <template slot-scope="{ row, index }" slot="action">
                  <div v-if="switch1 && row.legal==false">
                    <Button
                      icon="md-add"
                      type="primary"
                      size="medium"
                      style="margin-right: 5px"
                      @click="classSelection(row)"
                      disabled
                    >选课</Button>
                  </div>
                  <div v-else>
                    <Button
                      icon="md-add"
                      type="primary"
                      size="medium"
                      style="margin-right: 5px"
                      @click="classSelection(row)"
                    >选课</Button>
                  </div>
                </template>
              </Table>
            </div>
          </TabPane>
        </Tabs>
      </Col>
      <Col span="10">
        <div id="coursesTable"></div>
      </Col>
    </Row> -->
  </div>
</template>
<script>
</script>
<script>
// import { Timetables } from "./Timetables.min.js";
export default {
  data() {
    return {
      columns2: [
        {
          title: "课程",
          slot: "course",
          align: "center"
        },
        {
          title: "教师",
          slot: "teacher",
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
                  fontSize: "10px"
                }
              },
              params.row.allTime
            );
          }
        },
        {
          title: "选择",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      data2: [],
      columns: [
        {
          title: "课程",
          slot: "course",
          align: "center"
        },
        {
          title: "教师",
          slot: "teacher",
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
                  fontSize: "10px"
                }
              },
              params.row.allTime
            );
          }
        },
        {
          title: "选择",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      data: [],
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
      Timetable: "",
      theCourse: {
        courseNum: "",
        courseName: "",
        teacherNum: "",
        teacherName: "",
        credit: ""
      },
      searchProject: "",
      switch1: "",
      theTabs: "courses",
      courseList_redis: [
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""]
      ],
      courseList: [
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""],
        ["", "", "", "", "", "", "", "", "", "", "", "", ""]
      ],
      studentNum: "",
      week: ["一", "二", "三", "四", "五"],
      courseType: [
        [{ index: "1", name: "8:00" }, 1],
        [{ index: "2", name: "8:55" }, 1],
        [{ index: "3", name: "10:00" }, 1],
        [{ index: "4", name: "10:55" }, 1],
        [{ index: "5", name: "12:10" }, 1],
        [{ index: "6", name: "13:05" }, 1],
        [{ index: "7", name: "14:10" }, 1],
        [{ index: "8", name: "15:05" }, 1],
        [{ index: "9", name: "16:00" }, 1],
        [{ index: "10", name: "16:55" }, 1],
        [{ index: "11", name: "18:00" }, 1],
        [{ index: "12", name: "18:55" }, 1],
        [{ index: "13", name: "19:50" }, 1]
      ],
      noweid: "",
      nowSemester: ""
    };
  },
  methods: {
    classSelection(row) {
      let oid = row.oid;
      for (let i = 0; i < this.data.length; i++) {
        if (this.data[i].oid == oid) {
          this.$Notice.warning({ title: `已选此课` });
          return;
        }
      }
      for (let i = 0; i < this.data.length; i++) {
        if (this.data[i].time == row.time) {
          this.$Notice.warning({ title: `时间冲突` });
          return;
        }
      }

      let URL = `${apiPath}/classselection/chooseCourse`;

      axios({
        url: URL,
        method: "get",
        params: {
          oid: row.oid,
          studentNum: this.studentNum,
          eid: this.noweid
        }
      })
        .then(res => {
          if (this.switch1) {
            this.chooseCourse2();
          } else {
            this.chooseCourse();
          }
          this.loadData();
          this.$Notice.success({ title: `选课成功` });
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },

    change(status) {
      this.$Message.info("开关状态：" + status);
    },
    tabClick(name) {
      if (name == "courses") {
        this.theTabs = "courses";
        this.loadData();
      } else if (name == "choose") {
        if (this.switch1) {
          this.chooseCourse2();
        } else {
          this.chooseCourse();
        }
        this.theTabs = "choose";
      }
    },
    // excel(index) {
    //   this.Timetable = new Timetables({
    //     el: "#coursesTable",
    //     timetables: this.courseList,
    //     week: this.week,
    //     timetableType: this.courseType,

    //     gridOnClick: function(e) {
    //       // alert(
    //       //   e.name +
    //       //     "  " +
    //       //     e.week +
    //       //     ", 第" +
    //       //     e.index +
    //       //     "节课, 课长" +
    //       //     e.length +
    //       //     "节"
    //       // );
    //     },
    //     setOption: function(e) {},
    //     styles: {
    //       Gheight: 50
    //     }
    //   });
    // },
    getEid() {
      let URL = `${apiPath}/classselection/getMaxeid`;

      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          if (res.data.length == 0) {
            this.noweid = 1;
          } else {
            this.noweid = res.data[res.data.length - 1].eid + 1;
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    chooseCourse() {
      let URL = `${apiPath}/classselection/consult`;

      axios({
        url: URL,
        method: "get",
        params: {
          courseNum: this.theCourse.courseNum,
          courseName: this.theCourse.courseName,
          teacherNum: this.theCourse.teacherNum,
          teacherName: this.theCourse.teacherName,
          credit: this.theCourse.credit,
          semester:this.nowSemester
        }
      })
        .then(res => {
          this.data2 = res.data;
          for (let i = 0; i < this.data2.length; i++) {
            let week = Math.floor(this.data2[i].time / 13);
            this.data2[i].section = (this.data2[i].time % 13) + 1;
            let weeks = this.weekList[week].label;
            this.data2[i].week = weeks;
            this.data2[i].allTime = weeks + " " + this.data2[i].section;
          }
          this.getEid();
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    chooseCourse2() {
      let URL = `${apiPath}/classselection/consult`;

      axios({
        url: URL,
        method: "get",
        params: {
          courseNum: this.theCourse.courseNum,
          courseName: this.theCourse.courseName,
          teacherNum: this.theCourse.teacherNum,
          teacherName: this.theCourse.teacherName,
          credit: this.theCourse.credit,
          semester:this.nowSemester
        }
      })
        .then(res => {
          this.data2 = res.data;
          for (let i = 0; i < this.data2.length; i++) {
            let week = Math.floor(this.data2[i].time / 13);
            this.data2[i].section = (this.data2[i].time % 13) + 1;
            let weeks = this.weekList[week].label;
            this.data2[i].week = weeks;
            this.data2[i].allTime = weeks + " " + this.data2[i].section;
          }
          for (let i = 0; i < this.data2.length; i++) {
            this.data2[i].legal = true;
            for (let j = 0; j < this.data.length; j++) {
              if (this.data[j].time == this.data2[i].time) {
                this.data2[i].legal = false;
                break;
              }
            }
          }
          this.getEid();
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    deleteCourse(row) {
      let URL = `${apiPath}/classselection/delete`;

      axios({
        url: URL,
        method: "get",
        params: {
          eid: row.eid
        }
      })
        .then(res => {
          let rew = Math.floor(row.time / 13);
          let col = row.time % 13;

          this.courseList[rew][col] = "";
          this.loadData();
          this.getEid();
          this.$Notice.success({ title: `退课成功` });
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadData() {
      let URL = `${apiPath}/classselection/already`;
      axios({
        url: URL,
        method: "get",
        params: {
          studentNum: this.studentNum,
          semester:this.nowSemester
        }
      })
        .then(res => {
          this.data = res.data;
          this.courseList = this.courseList_redis;

          for (let i = 0; i < this.data.length; i++) {
            let week = Math.floor(this.data[i].time / 13);
            this.data[i].section = (this.data[i].time % 13) + 1;
            let weeks = this.weekList[week].label;
            this.data[i].week = weeks;
            this.data[i].allTime = weeks + " " + this.data[i].section;
          }
          for (let i = 0; i < this.data.length; i++) {
            let row = Math.floor(this.data[i].time / 13);
            let col = this.data[i].time % 13;

            this.courseList[row][col] = this.data[i].courseName;
          }
          // this.Timetable.setOption({
          //   timetables: this.courseList,
          //   week: this.week,
          //   timetableType: this.courseType,
          //   styles: {
          //     Gheight: 50
          //   }
          // });
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    getSemester() {
      let URL = `${apiPath}/nowSemester`;

      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.nowSemester = res.data.semester;

          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  mounted() {
    this.studentNum = localStorage.getItem("userId");

    this.getSemester();
    this.excel(0);
  },
  watch: {
    theCourse: {
      handler() {
        if (this.switch1) {
          this.chooseCourse2();
        } else {
          this.chooseCourse();
        }
      },
      deep: true
    },
    switch1(val, oldVal) {
      if (val) {
        this.chooseCourse2();
      } else {
        this.chooseCourse();
      }
    }
  }
};
</script>

<style lang="scss">
@import "selection";
</style>
<style>
.ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab-active {
  border-color: #fff;
  border: 0px none #ffffff;
}
.ivu-tabs.ivu-tabs-card > .ivu-tabs-bar .ivu-tabs-tab {
  border: 0px none #ffffff;
}
.ivu-tabs .ivu-tabs-card {
}
.ivu-tabs-nav-scroll {
  background: #f9fafc;
}
.ivu-tabs {
  background: #fff;
  padding-left: 0px !important;
}
.ivu-table th {
  background: #f9fafc;
}
</style>