<template>
  <div style="height:100%">
    <div class="action"></div>
    <Row type="flex" justify="space-around" class="code-row-bg">
      <Col span="4" offset="0">
        <Card title="基本信息" icon="ios-bookmarks-outline" :padding="0" shadow>
          <CellGroup>
            <Cell :title="theCourse.courseName" :label="theCourse.courseNum" extra="课程信息" />
            <Cell :title="theCourse.teacherName" :label="theCourse.teacherNum" extra="教师信息" />
            
            <Cell :title="theCourse.allTime" extra="上课时间" />
            <Cell :title="studentLength" extra="选课人数" />
           
          </CellGroup>
        </Card>
      </Col>
      <Col span="18">
        <div class="Table" id="printDiv">
          <div class="tablemain">
            <Table ref="selection" :columns="columns" :data="data" stripe :loading="loading"></Table>
            
          </div>
        </div>
      </Col>
    </Row>

    <Modal v-model="changemodal" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>修改成绩</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="theStudent" :model="theStudent" :label-width="100">
          <FormItem label="分数">
            <Input v-model="theStudent.score" placeholder="请输入分数" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="changeCourse(studentIndex)"
          :disabled="theStudent.submit"
          style="margin-right:1vh;"
        >更改</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="changemodal=false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      changemodal: false,
      oid:"",
      studentLength:0,
      theCourse: {
        teacherNum: "123",
        teacherName: "陈科宇",
        courseNum: "123123",
        courseName: "编译原理",
        time: "五 1-2",
        credit: "6",
        academy: "计算机学院"
      },
      studentIndex: -1,
      theStudent: {
        name: "",
        num: "",
        score: "",
        submit: true
      },

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
      columns: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "学号",
          key: "studentNum",
          align: "center"
        },
        {
          title: "学生名字",
          key: "studentName",
          align: "center"
        },

        {
          title: "分数",
          key: "score",
          align: "center",
        
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
                  props: {
                    type: "warning",

                    ghost: true
                  },
                  style: {
                    marginRight: "3px"
                  },
                  on: {
                    click: () => {
                      this.studentIndex = params.index
                      this.theStudent.score = this.data[params.index].score;
                      this.changemodal = true;
                    }
                  }
                },
                "修改"
              )
            ]);
          }
        }
      ],
      data: [
       
      ],

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

    changeCourse(studentIndex) {
       let URL = `${apiPath}/scoreupdate`;
     console.log(this.score)
      axios({
        url: URL,
        method: "get",
        params:{
          eid:this.data[this.studentIndex].eid,
          score:this.theStudent.score
        }
      })
        .then(res => {
         
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });

      this.changemodal = false;
    },

    loadCourse() {
      let URL = `${apiPath}/oneCourse`;
     
      axios({
        url: URL,
        method: "get",
        params:{
          oid:this.oid
        }
      })
        .then(res => {
         
          this.theCourse=res.data;
           let week = Math.floor(this.theCourse.time / 13);
            this.theCourse.section = (this.theCourse.time % 13) + 1;
            let weeks = this.weekList[week].label;
            this.theCourse.week = weeks;
            this.theCourse.allTime = weeks + " " + this.theCourse.section;
        
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });


    },
    loadData() {
      let URL = `${apiPath}/grade`;
      this.loading = true;
      
      axios({
        url: URL,
        method: "get",
        params:{
          oid:this.oid
        }
      })
        .then(res => {
          console.log(res)
          this.data=res.data;
          this.loading=false;
          this.studentLength = this.data.length;
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `没有选课学生` });
        });


    }
  },
  mounted() {
    this.oid = localStorage.getItem("oid")
    this.loadCourse();
    this.loadData();

  },

  computed: {},
  watch: {
    changemodal(val, oldVal) {
      if (!val) {
        this.theStudent.score = "";
        this.theStudent.submit = false;
      }
    },

    theStudent: {
      handler() {
        if (this.theStudent.score != "") {
          this.theStudent.submit = false;
        } else {
          this.theStudent.submit = true;
        }
      },
      deep: true
    }
  }
};
</script>

<style scoped lang ='scss'>
@import "scourse";
</style>
