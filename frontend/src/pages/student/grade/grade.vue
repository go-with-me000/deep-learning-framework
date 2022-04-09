
<template>
  <div>
    <div class="Table">
      <div class="action" style="position:relative;margin-bottom:0px;top:-15px;">
        <Row type="flex" justify="space-between" class="code-row-bg">
          <Col span="18">
            <Row type="flex" justify="start" class="code-row-bg">
              <Col span="8" style="position:relative;left:0px;right:15px;">
                <Select v-model="semester" style="width:100%;background:#fff" placeholder="选择查询学期">
                  <Option
                    v-for="item in semesterList"
                    :value="item.semester"
                    :key="item.semester"
                  >{{ item.semester }}</Option>
                </Select>
              </Col>

              <!-- <Col span="5" offset="0">
                <Button type="primary" style="margin-right:0px;position:relative;left:15px">查询</Button>
              </Col> -->
            </Row>
          </Col>
        </Row>
      </div>

      <div class="tablemain">
        <Table border ref="selection" :columns="columns" :data="data" stripe :loading="loading"></Table>
        <div class="bottomButton">
          <div style="text-align:center">
            <Row
              style="position:relative;top:10px;font-size:16px;font-weight:bold;padding-bottom:30px"
            >
              <Col span="12">总计学分：{{allCredit}}</Col>
              <Col span="12">平均成绩：{{jd}}</Col>
            </Row>
          </div>
        </div>
        
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      allCredit:0,
      jd:0,
      semesterList: [],
      semester:'',
      studentNum:'',
      columns: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "课程编号",
          key: "courseNum",
          align: "center"
        },
        {
          title: "课程名",
          key: "courseName",
          align: "center"
        },
        {
          title: "学分",
          key: "credit",
          align: "center"
        },
        {
          title: "成绩",
          key: "score",
          sortable: true,
          align: "center"
        }
      ],
      data: [
      ],
    };
  },
  mounted() {
    this.studentNum=localStorage.getItem("userId");
    this.loadSemester();
  },
  methods: {
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
    loadData() {
      let URL = `${apiPath}/gradeconsult`;
     this.loading=true;
     axios({
        url: URL,
        method: "get",
        params:{
          studentNum:this.studentNum,
          semester:this.semester
        }
      })
        .then(res => {
          this.loading = false;
          console.log(res)
          this.data = res.data;
          let nums = 0;
          for(let i = 0; i<this.data.length;i++){
            this.allCredit+=this.data[i].credit;
            nums+=this.data[i].score;
          }
          this.jd = Math.floor(nums/this.data.length);
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },

   
  },
  watch: {
    semester(val,oldVal){
      this.loadData();
    }
  }
};
</script>

<style scoped>
.bottomButton {
  margin-top: 10px;
}
.bottom {
  margin-left: 39%;
  /* background-color: antiquewhite; */
}
.bottom1 {
  padding-top: 5px;
  margin-top: 5px;
}
.pagetitle {
  height: 5vh;
  left: 4%;
  position: relative;
  /* background-color: blueviolet; */
}
.Table {
  position: relative;
  width: 96%;
  margin-top: 3%;
  left: 2%;
}
.intru {
  position: relative;
  top: 25%;
  float: left;
  left: 10px;
}
.tablemain {
  /* background-color: rgba(102, 146, 191, 0.44); */

  border: solid 1px rgba(102, 146, 191, 0.68);

  border-radius: 10px;

  -moz-box-shadow: 2px 2px 1px #585858;
  -webkit-box-shadow: 2px 2px 1px #666565;
  box-shadow: 1px 1px 8px #7c7e7e;
}
.func {
  position: relative;

  right: 9%;
  float: right;
  top: 15%;
  /* background-color: #585858; */
}
.imgs {
  position: relative;
  left: 10%;
  width: 80%;
}
.autos {
  /* background-color: #7c7e7e; */
  position: relative;
  left: 31%;

  margin-top: 2vh;
}
</style>
<style lang="scss">
.ivu-form .ivu-form-item-label {
  text-align: right;
  vertical-align: middle;
  float: left;
  font-size: 15px;
  font-weight: bold;
  color: #495060;
  line-height: 1;
  padding: 10px 12px 10px 0;
  box-sizing: border-box;
}
.ivu-modal-wrap {
  // margin-top: -15%;
}
.smallModal {
  .ivu-modal-wrap {
    margin-top: -8%;
  }
}
</style>
<style lang="scss" scoped>
</style>
<style scoped>
.expand-row {
  margin-bottom: 16px;
}
.expand-key {
  /* font-weight: lighter; */
  color: #8b8b8b;
}
.expand-value {
  /* font-size:22px; */
  /* font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif; */
  /* font-weight: bold; */
  color: rgb(53, 52, 52);
}
.spider {
  font-weight: bold;
}
.stitle {
  margin-left: 3px;
  margin-bottom: -8px;

  font-size: 22px;
  font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
  font-weight: bolder;
}
#method {
  font-size: 16px;
  font-weight: bold;
  position: relative;
  float: right;
  right: 2%;
  top: 5px;
}
</style>