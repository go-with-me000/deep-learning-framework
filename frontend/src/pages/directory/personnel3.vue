
<template>
  <div>
    <div class="Table">
      <div class="action" style="position:relative;margin-bottom:0px;top:-15px;">
        <Row type="flex" justify="start" class="code-row-bg">
          <Col span="4" style="background:#fff">
            <Select
              v-model="collegeName"
              style="width:100%;color:#000 !important"
              placeholder="请选择学院"
            >
              <Option v-for="item in academyList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </Col>
          <Col span="16" style="position:relative;left:15px;"></Col>
          <Col span="4" style="position:relative;left:0px;text-align:right">
            <Button
              type="success"
              @click="handleOnClickRefresh()"
              style="margin-right:0px;position:relative;left:0px"
            >刷新</Button>
            <Button
              type="info"
              style="margin-right:0px;position:relative;left:0px"
              @click="addControl=true"
            >增加学院管理员</Button>
          </Col>
        </Row>
      </div>
      <div class="tablemain">
        <Table border ref="selection" :columns="columnsc" :data="datac" stripe :loading="loading"></Table>
        <Row type="flex" justify="space-between" class="code-row-bg">
          <Col span="2"></Col>
          <Col span="18" style="text-align:center;">
            <Page
              :total="totalRecord"
              show-elevator
              style="margin-bottom:40px;padding-top:10px;margin-bottom:10px;"
              @on-change="cutaway"
              :page-size="10"
              :current="page"
            />
          </Col>
          <Col span="2"></Col>
        </Row>
      </div>
    </div>
    <Modal v-model="deleteControl" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>此管理员删除后，其数据将不能保留</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="deleteControls(theIndex)">删除</Button>
      </div>
    </Modal>

    <Modal v-model="addControl" width="25" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>增加管理员</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="form" :model="forms" :label-width="100">
          <FormItem label="学号/工号">
            <Input v-model="forms.userId" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" @click="addControls()" style="margin-right:1vh;">增加</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="addControl = false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>

    <Modal v-model="dialog" width="20" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>此任务删除后，其数据将不能保留</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="deleteProject(projectIndex)">删除</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      totalRecord: 0,
      page: 1,
      forms: {
        userId: ""
      },
      academyList: [
        "全部",
        "理学院",
        "文学院",
        "法学院",
        "外国语学院",
        "社会学院",
        "马克思主义学院",
        "新闻传播学院",
        "计算机工程与科学学院",
        "机电工程与自动化学院",
        "通信与信息工程学院",
        "环境与化学工程学院",
        "材料科学与工程学院",
        "材料基因组工程研究院",
        "中欧工程技术学院",
        "土木工程系",
        "力学所",
        "纳米科学与技术中心",
        "经济学院",
        "管理学院",
        "图书情报档案系",
        "悉尼工商学院",
        "MBA中心",
        "医学院",
        "上海电影学院",
        "上海美术学院",
        "上海研究院",
        "生命科学学院",
        "音乐学院",
        "数码艺术学院",
        "上海温哥华电影学院",
        "社区学院",
        "社会科学学部",
        "钱伟长学院",
        "体育学院",
        "人才学院",
        "微电子中心",
        "国际教育学院"
      ],
      collegeName: "",
      addControl: false,
      deleteControl: false,
      row: "",
      xiangqing: false,
      loading: false,
      projectIndex: "",
      projectKind: "12",
      datac: [],
      columnsc: [
        {
          title: "序号",
          width: 70,
          key: "index",
          align: "center"
        }, {
          title: "学号/工号",
          key: "userId",
          align: "center",
          width: 250
        },
        {
          title: "姓名",
          key: "name",
          align: "center",
          width: 200
        },
       
        {
          title: "所属学院",
          key: "college_name",
          align: "center"
        },
        {
          title: "操作",
          slot: "action",
          width: 180,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      this.theIndex = params.index;
                      this.deleteControl = true;
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
      theIndex: -1,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
      dialogFormVisible4: false,
      dialogFormVisible5: false,
      dialog: false,
      addmodal: false,
      form1: {
        accent: ""
      },
      form2: {
        projectName: "",
        maxAccentNum: "",
        dateb: "",
        timeb: "",
        dateo: "",
        timeo: "",
        method: "",
        introuction: ""
      },
      projectInfo: {
        id: -1,
        projectName: "",
        maxAccentNum: "",
        startTime: "",
        endTime: "",
        method: "",
        introuction: "",
        submit: true
      },
      form3: {
        newProjectName: "",
        oldProjectName: ""
      },
      checkControl: false
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    cutaway(index) {
      this.page = index;
      this.loadData();
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert("submit!");
          this.form1.accent = "";
          this.dialogFormVisible1 = false;
          this.dialogFormVisible2 = false;
        } else {
          this.form1.accent = "";
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleOnClickRefresh() {
      this.loadData();
    },

    deleteControls(index) {
      let userId = this.datac[index].userId;
      let URL = `${apiPath}/admin/deleteCollegeAd`;

      axios({
        url: URL,
        method: "get",
        headers: {
          operatorId: userId
        }
      })
        .then(res => {
          this.deleteControl = false;
          if (res.data.code === "SUCCESS") {
            this.loadData();

            this.$Notice.success({ title: `删除成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    addControls() {
      let URL = `${apiPath}/admin/addCollegeAd`;
      axios({
        url: URL,
        method: "get",
        headers: {
          operatorId: this.forms.userId
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData();

            this.$Notice.success({ title: `增加学院管理员成功` });
            this.addControl = false;
          } else {
            this.$Notice.warning({
              title: `出错，提示：${res.data.message}`
            });
            this.addControl = false;
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
          //  this.addControl = false
        });
    },

    loadData() {
      let URL = `${apiPath}/admin/collegeAdmin`;
      let projectName = localStorage.getItem("projectName");
      if (this.collegeName == "全部") this.collegeName = "";
      this.loading = true;
      axios({
        url: URL,
        method: "post",
        data: {
          collegeName: this.collegeName,
          page: this.page
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            if (res.data.data != null) {
              this.totalRecord = res.data.data.total;
              this.datac = res.data.data.collegeAdminDetails;
              for (let i = 0; i < this.datac.length; i++) {
                this.datac[i].index = i + 1 + (this.page - 1) * 10;
              }
            } else {
              this.datac = [];
              this.totalRecord = 0;
            }
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
          this.loading = false;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  watch: {
    addControl(val, oldVal) {
      if (!val) {
        this.forms.userId = "";
      }
    },

    collegeName(val, oldVal) {
      this.page = 1;
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
@import "card";
</style>
<style scoped>
.expand-row {
  margin-bottom: 16px;
}
.expand-key {
  font-weight: lighter;
}
.expand-value {
  /* font-size:22px; */
  font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif;
  /* font-weight: bold; */
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
  font-size: 22px;
  font-weight: bolder;
  position: relative;
  float: right;
  right: 12%;
  /* top:1px; */
}
</style>