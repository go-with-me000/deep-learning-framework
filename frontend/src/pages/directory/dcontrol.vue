
<template>
  <div>
    <div class="Table">
     
      <div class="action" style="position:relative;margin-bottom:0px;top:-15px;">
       
        <Row type="flex" justify="space-between" class="code-row-bg" >
          <Col span="18">
            <Row type="flex" justify="start" class="code-row-bg">
              <Col span="6" style="text-align:right">
                <Input v-model="searchProject" placeholder="请输入项目名称查询" clearable />
              </Col>

             
              <Col span="3.5" style="position:relative;left:15px;right:15px;">
                <Select v-model="votingMode" style="width:100%;background:#fff" placeholder="投票模式">
                  <Option
                    v-for="item in votingList"
                    :value="item.value"
                    :key="item.value"
                  >{{ item.label }}</Option>
                </Select>
              </Col>

              <Col span="5.5" style="position:relative;left:15px;">
                 <Select
                  v-model="state"
                  style="width:100%;color:#000 !important;position:relative;left:15px;right:15px;background:#fff"
                  placeholder="选择状态"
                >
                  <Option
                    v-for="item in stateList"
                    :value="item.value"
                    :key="item.value"
                  >{{ item.label }}</Option>
                </Select>
              </Col>
            </Row>
          </Col>
           <Col span="6" style="text-align:right;">
            <Row type="flex" justify="end" class="code-row-bg">
              <Col span="18">
                <Button
                  type="success"
                  @click="handleOnClickRefresh()"
                  style="margin-right:0px;position:relative;left:0px"
                >刷新</Button>
                <Button
                  type="info"
                  style="margin-right:0px;position:relative;left:0px"
                  @click="addProject()"
                >增加项目</Button>
              </Col>
            </Row>
          </Col>
          
        </Row>
      </div>

      <div class="tablemain">
        <Table
          border
          ref="selection"
          :columns="columns"
          :data="data"
          stripe
          :loading="loading"
          @on-row-click="toggleTableExpand"
        ></Table>
        <Row type="flex" justify="space-between" class="code-row-bg">
          <Col span="2"></Col>
          <Col span="18" style="text-align:center;">
            <Page
              :total="totalRecord"
              :current="page"
              show-elevator
              style="margin-bottom:40px;padding-top:10px;margin-bottom:10px;"
              @on-change="cutaway"
              :page-size="10"
            />
          </Col>
          <Col span="2"></Col>
        </Row>
        <!-- <div class="bottomButton">
          <div style="text-align:center">
            <Button
              type="info"
              size="large"
              ghost
              @click="handleOnClickRefresh()"
              style="margin-right:0px;position:relative;left:-20px"
            >刷新</Button>
            <Button type="success" size="large" ghost @click="addProject()">增加项目</Button>
          </div>
          <div class="bottom1"></div>
        </div>-->
      </div>
    </div>

    <Modal title="项目详情" v-model="xiangqing" width="550" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">项目详情</span>
      </p>
      <div style="width:100%;position:relative;text-align:left">
        <Row style="text-align:left">
          <Col span="24" :bordered="false">
            <Card>
             
              <Row class="expand-row">
                <Col span="12">
                  <span class="expand-key">已投人数:</span>
                  <span class="expand-value">{{ row.havevotednumber }}</span>
                </Col>
                <Col span="12">
                  <span class="expand-key">至多可选:</span>
                  <span class="expand-value">{{ row.electornumber }}</span>
                </Col>
              </Row>
              <Divider class="spider"></Divider>
              <Row class="expand-row">
                <Col span="12">
                  <span class="expand-key">计票员数:</span>
                  <span class="expand-value">{{ row.countPeople }}</span>
                </Col>
                <Col span="12">
                  <span class="expand-key">投票员数:</span>
                  <span class="expand-value">{{ row.haverightman }}</span>
                </Col>
              </Row>
              <Divider class="spider"></Divider>
              <Row class="expand-row">
                <Col span="12">
                  <span class="expand-key">开始时间:</span>
                  <span class="expand-value">{{ row.starttime }}</span>
                </Col>

                <Col span="12">
                  <span class="expand-key">截止时间:</span>
                  <span class="expand-value">{{ row.endtime }}</span>
                </Col>
              </Row>
              <Divider class="spider"></Divider>

              <Row class="expand-row">
                <Col span="24">
                  <span class="expand-key">
                    项目简介:
                    <br />
                    <br />
                  </span>
                  <span
                    class="expand-value"
                  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ row.description }}</span>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" long @click="xiangqing=false">确定</Button>
      </div>
    </Modal>
    <Modal title="增加投票项目" v-model="addmodal" width="450" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">增加投票项目</span>
      </p>
      <div style="width:100%;position:relative">
        <Form ref="projectInfo" :model="projectInfo" :label-width="100">
          <FormItem label="项目名称">
            <Input v-model="projectInfo.projectName" placeholder="请输入项目名称" />
          </FormItem>
          <FormItem label="至多赞成数">
            <Input v-model="projectInfo.maxAccentNum" placeholder="请输入至多赞成数" />
          </FormItem>
         
          <FormItem label="投票方式">
            <RadioGroup v-model="projectInfo.method">
              <Radio label="线上投票">线上投票</Radio>
              <Radio label="线下投票">线下投票</Radio>
            </RadioGroup>
          </FormItem>

          <FormItem label="开始时间">
            <DatePicker
              type="datetime"
              placeholder="选择时间"
              :value="projectInfo.startTime"
              @on-change="projectInfo.startTime=$event"
              format="yyyy-MM-dd HH:mm:ss"
            ></DatePicker>
          </FormItem>
          <FormItem label="结束时间">
            <DatePicker
              type="datetime"
              placeholder="选择时间"
              :value="projectInfo.endTime"
              @on-change="projectInfo.endTime=$event"
              format="yyyy-MM-dd HH:mm:ss"
            ></DatePicker>
          </FormItem>

          <FormItem label="项目简介">
            <Input
              v-model="projectInfo.introuction"
              type="textarea"
              :autosize="{minRows: 3,maxRows: 5}"
              placeholder="请写下对项目的介绍"
            />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="createNewProject2()"
          style="margin-right:1vh;"
          :disabled="projectInfo.submit"
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

    <Modal v-model="dialogFormVisible5" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>更改项目</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="form3" :model="form3" :label-width="100">
          <FormItem label="项目名称">
            <Input v-model="projectInfo.projectName" />
          </FormItem>
          <FormItem label="至多可选" v-if="theApply">
            <Input v-model="projectInfo.maxAccentNum" placeholder="请输入至多赞成数" />
          </FormItem>
          <FormItem label="结束时间">
            <DatePicker
              type="datetime"
              placeholder="选择时间"
              :value="projectInfo.endTime"
              @on-change="projectInfo.endTime=$event"
              format="yyyy-MM-dd HH:mm:ss"
            ></DatePicker>
          </FormItem>
          <FormItem label="项目简介">
            <Input
              v-model="projectInfo.introuction"
              type="textarea"
              :autosize="{minRows: 3,maxRows: 5}"
              placeholder="请写下对项目的介绍"
            />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="dialogFormVisible5=false;changeProjectName()"
          style="margin-right:1vh;"
        >更改</Button>
        <Button type="primary" size="large" ghost @click="cancelName()" style="margin-right:0vh;">取消</Button>
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
      votingMode: "",
      votingList: [
        {
          value:0,
          label:"全部",
        },
        {
          value: 1,
          label: "线上"
        },
        {
          value: 2,
          label: "线下"
        },
       
      ],
       state:"",
       theApply:false,
    stateList: [
        {
          value:-2,
          label:"全部",
        },
        {
          value: 0,
          label: "未开始"
        },
        {
          value: 1,
          label: "进行中"
        },
        {
          value: -1,
          label: "已结束"
        }
      ],
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
      academyChoose: "",
      searchProject: "",
      forms: {
        userId: ""
      },
      addControl: false,
      deleteControl: false,
      row: "",
      totalRecord: 0,
      page: 1,
      xiangqing: false,
      loading: false,
      projectIndex: "",
      projectKind: "12",
      datac: [],
      columnsc: [
        {
          type: "index",
          title: "序号",
          width: 70
          // align:center
        },
        {
          title: "姓名",
          key: "name"
        },
        {
          title: "学号/工号",
          key: "userId"
        },
        {
          title: "Action",
          slot: "action",
          width: 80,
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
      columns: [
        {
          title: "序号",
          width: 70,
          key: "index",
          align: "center"
        },
        {
          title: "项目名称",
          key: "votesystemname",
          align: "center"
          // width: 120
        },
        {
          title: "投票模式",
          key: "votingmode",
          align: "center"
        },
        {
          title: "候选人数",
          key: "candidatenumber",
          align: "center"
        },
        {
          title: "至多可选",
          key: "electornumber",
          align: "center"
        },
        {
          title: "项目状态",
          key: "applystatus",
          width: 160,
          align: "center",
          render: (h, params) => {
            const row = params.row;
            const color =
              row.applystatus == 0
                ? "primary"
                : row.applystatus == 1
                ? "success"
                : "error";
            const text =
              row.applystatus == 0
                ? "未开始"
                : row.applystatus == 1
                ? "进行中"
                : "已结束";

            return h(
              "Tag",
              {
                props: {
                  type: "dot",
                  color: color
                }
              },
              text
            );
          }
        },

        {
          title: "Action",
          slot: "action",
          width: 180,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true
                  },
                  style: {
                    marginRight: "1px"
                  },
                  on: {
                    click: () => {
                      localStorage.setItem(
                        "projectName",
                        this.data[params.index].votesystemname
                      );
                      localStorage.setItem(
                        "haverightman",
                        this.data[params.index].haverightman
                      );
                      localStorage.setItem(
                        "havevotednumber",
                        this.data[params.index].havevotednumber
                      );
                      localStorage.setItem(
                        "projectId",
                        this.data[params.index].id
                      );
                      localStorage.setItem(
                        "votingmode",
                        this.data[params.index].votingmode
                      );
                      let projectName = this.data[params.index].votesystemname;
                      this.$router.push({
                        name: "scontrol2",
                        params: {
                          // haverightman: this.data[params.index].haverightman,
                          // havevotednumber: this.data[params.index]
                          //   .havevotednumber,
                          // votingmode: this.data[params.index].votingmode
                        }
                      });
                    }
                  }
                },
                "管理"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "success",
                    size: "small",
                    ghost: true,
                    //  disabled:
                    //   this.data[params.index].applystatus == 0 ? false : true
                  },
                  style: {
                    marginRight: "1px"
                  },
                  on: {
                    click: () => {
                       if(this.data[params.index].applystatus == 0){
                        this.theApply=true;
                      }else{
                        this.theApply=false;
                      }
                      this.projectInfo.projectName = this.data[
                        params.index
                      ].votesystemname;
                      this.projectInfo.endTime = this.data[
                        params.index
                      ].endtime;
                      this.projectInfo.maxAccentNum = this.data[
                        params.index
                      ].electornumber;
                      this.projectInfo.introuction = this.data[
                        params.index
                      ].description;
                      this.projectInfo.id = this.data[params.index].id;
                      this.dialogFormVisible5 = true;
                    }
                  }
                },
                "修改"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small",
                    ghost: true,
                    //  disabled:
                    //   this.data[params.index].applystatus != 1 ? false : true
                  },
                  style: {
                    marginRight: "1px"
                  },
                  on: {
                    click: () => {
                      this.projectIndex = params.index;
                      this.dialog = true;
                      // this.deleteProject(params.index)
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
    addProject() {
      // this.dialogFormVisible3 = true
      this.addmodal = true;
    },
    handleCommand(command) {
      if (command == "addCount") {
        this.dialogFormVisible1 = true;
      } else if (command == "delCount") {
        this.dialogFormVisible2 = true;
      } else if (command == "addProject") {
        this.dialogFormVisible3 = true;
      } else {
        alert("删除项目");
      }
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
    loadData() {
      let URL = `${apiPath}/collegeAdmin/voteList`;
      let username = localStorage.getItem("username");
      let type = localStorage.getItem("type");
      this.loading = true;
      let votingMode = this.votingMode!=0?this.votingMode:"";
      let applystatus = this.state != -2?this.state :"";
      axios({
        url: URL,
        method: "POST",
        data: {
          page: this.page,
          searchProject: this.searchProject,
          applystatus:applystatus,
          votingMode: votingMode
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loading = false;
            if (res.data.data != 0) {
              this.totalRecord = res.data.data.number;
              this.data = res.data.data.voteSystemList;
              for (let i = 0; i < this.data.length; i++) {
                
                this.data[i].index = i + 1 + (this.page - 1) * 10;
              }
            }else{
              this.totalRecord=0;
              this.data=[];
            }
          } else {
            this.loading = false;
            this.$Notice.warning({ title: `${res.data.message}` });
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
   
    cancelName() {
      this.dialogFormVisible5 = false;
    },
    cutaway(index) {
      this.page = index;
      this.loadData();
    },
    changeProjectName() {
      let id = this.projectInfo.id;
      let projectName = this.projectInfo.projectName;
      let maxAccentNum = this.projectInfo.maxAccentNum;

      let endTime = this.projectInfo.endTime;
      let description = this.projectInfo.introuction;

      axios({
        url: `${apiPath}/admin/changeName`,
        method: "post",
        data: {
          id: id,
          projectName: projectName,
          maxAccentNum: maxAccentNum,
          endTime: endTime,
          description: description
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData();

            this.$Notice.success({ title: `修改成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    deleteProject(index) {
      let projectName = this.data[index].votesystemname;
      let id = this.data[index].id;
      let URL = `${apiPath}/admin/deleteProject`;
      this.loading = true;
      this.dialog = false;
      axios({
        url: URL,
        method: "post",
        data: {
          projectId: id
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loading = false;
            this.loadData();
            this.projectKind--;
            this.$Notice.success({ title: `删除成功` });
          } else {
            this.loading = false;
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },

    createNewProject() {
      // let URL=`${apiPath}/`
      let projectName = this.form2.projectName;
      let maxAccentNum = this.form2.maxAccentNum;
      let dateb = this.form2.dateb;
      let dateo = this.form2.dateo;
      let timeb = this.form2.timeb;
      let timeo = this.form2.timeo;
      let description = this.form2.introuction;
      let method = this.form2.method;

      if (method == "线上投票") {
        method = 1;
      } else {
        method = 2;
      }
      axios({
        url: `${apiPath}/admin/addOne`,
        method: "post",
        data: {
          projectname: projectName,
          electorNum: maxAccentNum,
          votingmode: method,
          startDate: dateb,
          endDate: dateo,
          startTime: timeb,
          endTime: timeo,
          description: description
        }
      })
        .then(res => {
          this.spin = false;
          if (res.data.code === "SUCCESS") {
            this.handleOnClickRefresh();

            this.$Notice.success({ title: "添加成功！" });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },

    createNewProject2() {
      // let URL=`${apiPath}/`
      let projectName = this.projectInfo.projectName;
      let maxAccentNum = this.projectInfo.maxAccentNum;
      let startTime = this.projectInfo.startTime;
      let endTime = this.projectInfo.endTime;
      let description = this.projectInfo.introuction;
      let method = this.projectInfo.method;
      let college = localStorage.getItem("collegeName")
      if (method == "线上投票") {
        method = 1;
      } else {
        method = 2;
      }
      axios({
        url: `${apiPath}/admin/addOne`,
        method: "post",
        data: {
          projectname: projectName,
          electorNum: maxAccentNum,
          votingmode: method,
          startTime: startTime,
          endTime: endTime,
          description: description,
          college: college
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.addmodal = false;
            this.handleOnClickRefresh();
            this.$Notice.success({ title: "添加成功！" });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    toggleTableExpand(row, index) {
      if (!this.dialogFormVisible5 && !this.dialog) {
        this.xiangqing = true;
        this.row = row;
      }
    }
  },
  watch: {
    searchProject() {
      this.page = 1;
      this.loadData();
    },
    votingMode(){
      this.page = 1;
      this.loadData();
    },
  
    addControl(val, oldVal) {
      if (!val) {
        this.forms.userId = "";
      }
    },
    
    addmodal(val, oldVal) {
      if (!val) {
        this.projectInfo.projectName = "";
        this.projectInfo.maxAccentNum = "";
        this.projectInfo.method = "";
        this.projectInfo.startTime = "";
        this.projectInfo.endTime = "";
        this.projectInfo.introuction = "";
        this.projectInfo.submit = false;
      }
    },
    dialogFormVisible5(val, oldVal) {
      if (!val) {
        this.projectInfo.projectName = "";
        this.projectInfo.maxAccentNum = "";
        this.projectInfo.method = "";
        this.projectInfo.startTime = "";
        this.projectInfo.endTime = "";
        this.projectInfo.introuction = "";
        this.projectInfo.id = -1;
      }
    },
    state(){
      this.page = 1;
      this.loadData();
    },
    projectInfo: {
      handler() {
        if (
          this.projectInfo.projectName != "" &&
          this.projectInfo.maxAccentNum != "" &&
          this.projectInfo.method != "" &&
          this.projectInfo.startTime != "" &&
          this.projectInfo.endTime != "" &&
          this.projectInfo.introuction != ""
        ) {
          this.projectInfo.submit = false;
        } else {
          this.projectInfo.submit = true;
        }
      },
      deep: true
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
  /* font-weight: lighter; */
  color: #8b8b8b;
}
.expand-value {
  /* font-size:22px; */
  /* font-family: "Gill Sans", "Gill Sans MT", Calibri, "Trebuchet MS", sans-serif; */
  /* font-weight: bold; */
  color:rgb(53, 52, 52);
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