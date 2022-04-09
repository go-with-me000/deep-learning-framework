<template>
  <div style="height:100%">
    <Tabs :value="theTabs" style="padding-left:5%;margin-top:2%" size="small" @on-click="tabClick">
      <TabPane label="课程管理" name="courses">
        <div class="action">
          <Row type="flex" class="code-row-bg" style="position:relative;">
            <Col span="5">
              <Input v-model="searchCandidate" placeholder="请输入名字查询" clearable />
            </Col>
            <Col span="6">
              <!-- <Button type="success" style="margin-left:1%" @click="addCandidates=true">增加候选人</Button> -->

              <Button type="info" @click="handleOnClickRefresh()" style="margin-left:2%">刷新</Button>
              <Button v-print="printObj" type="info">打印</Button>
            </Col>
            <Col span="13" style="text-align:right">
              <div>
                <Button type="success" style="margin-left:1%" @click="addCandidates=true">增加候选人</Button>
                <!-- <Button v-print="printObj" type="info">打印</Button>

                <Button type="info" @click="handleOnClickRefresh()">刷新</Button> -->
                <Button type="warning" @click="clearDialog = true" style="margin-left:0%">置零</Button>
                <Button type="error" @click="deleteIndex=0;dialog2=true">全部删除</Button>
              </div>
            </Col>
          </Row>
        </div>
        <div style="margin-right:0%;">
          <div class="Table" id="printDiv">
            <div class="tablemain">
              <Table ref="selection" :columns="columns" :data="data" stripe :loading="loading"></Table>
              <div class="bottomButton">
                <div style="text-align:center">
                  <Row
                    style="position:relative;top:10px;font-size:16px;font-weight:bold;padding-bottom:30px"
                  >
                    <Col span="12">已投人数：{{havevotednumber}}</Col>
                    <Col span="12">总投人数：{{haverightman}}</Col>
                  </Row>
                  <!-- <Divider style="position:relative;margin-bottom:1px"></Divider>
                  <Row type="flex" justify="space-between" class="code-row-bg">
                    <Col span="2"></Col>
                    <Col span="18" style="text-align:center;">
                      <Page
                        :total="totalRecord1"
                        show-elevator
                        style="margin-bottom:40px;padding-top:10px;margin-bottom:10px;"
                        @on-change="cutaway1"
                        :page-size="10"
                      />
                    </Col>
                    <Col span="2"></Col>
                  </Row>-->
                </div>
              </div>
            </div>
          </div>
        </div>
      </TabPane>
      <TabPane label="教师管理" name="teachers">
        <div class="action">
          <Row
            type="flex"
            justify="start"
            class="code-row-bg"
            style="position:relative;margin-top:0%;"
          >
            <Col span="5">
              <Input v-model="searchCounter" placeholder="请输入名字查询" clearable />
            </Col>
            <Col span="3">
              <!-- <Button type="success" @click="dialogFormVisible1=true" style="margin-left:1%">增加计票员</Button> -->
              <Button type="info" @click="handleOnClickRefresh()" style="margin-left:3%">刷新</Button>
            </Col>
            <Col span="16" style="text-align:right">
              <Button type="success" @click="dialogFormVisible1=true" style="margin-left:1%">增加计票员</Button>
              <!-- <Button type="info" @click="handleOnClickRefresh()">刷新</Button> -->
              <Button type="error" @click="deleteIndex=1;dialog2=true">全部删除</Button>
            </Col>
          </Row>
        </div>
        <div class="Table">
          <div class="tablemain">
            <Table stripe :columns="columnsc" :data="datac" :loading="loading"></Table>

            <Row type="flex" justify="space-between" class="code-row-bg">
              <Col span="2"></Col>
              <Col span="18" style="text-align:center;">
                <Page
                  :total="totalRecord2"
                  show-elevator
                  style="margin-bottom:40px;padding-top:10px;margin-bottom:10px;"
                  @on-change="cutaway2"
                  :page-size="10"
                />
              </Col>
              <Col span="2"></Col>
            </Row>
          </div>
        </div>
      </TabPane>
      <TabPane label="学生管理" name="students">
        <div class="action">
          <Row
            type="flex"
            justify="start"
            class="code-row-bg"
            style="position:relative;margin-top:0%;"
          >
            <Col span="5">
              <Input v-model="searchVoter" placeholder="请输入名字查询" clearable />
            </Col>
            <Col span="3">
              <!-- <Button type="success" @click="addVoter=true" style="margin-left:1%">增加投票员</Button>
              <Button type="success" @click="dialogFormVisible5=true">导入投票员</Button>-->
              <Button type="info" @click="handleOnClickRefresh()" style="margin-left:3%">刷新</Button>
            </Col>
            <Col span="16" style="text-align:right">
              <Button type="success" @click="addVoter=true" style="margin-left:1%">增加投票员</Button>
              <Button type="warning" @click="dialogFormVisible5=true">导入投票员</Button>

              <Button type="error" @click="deleteIndex=2;dialog2=true">全部删除</Button>
            </Col>
          </Row>
        </div>
        <div class="Table">
          <div class="tablemain">
            <Table stripe :columns="columnsv" :data="datav" :loading="loading"></Table>
            <Row type="flex" justify="space-between" class="code-row-bg">
              <Col span="2"></Col>
              <Col span="18" style="text-align:center;">
                <Page
                  :total="totalVoter"
                  show-elevator
                  style="margin-bottom:40px;padding-top:10px;margin-bottom:10px;"
                  @on-change="cutaway3"
                  :page-size="10"
                />
              </Col>
              <Col span="2"></Col>
            </Row>
          </div>
        </div>
      </TabPane>
    </Tabs>

   
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
    
      columnsc: [
        {
          title: "序号",
          width: 70,
          key: "index",
          align: "center"
        },
        {
          title: "姓名",
          key: "name",
          align: "center"
        },
        {
          title: "学号/工号",
          key: "userId",
          align: "center"
        },
        {
          title: "Action",
          slot: "action",
          width: 120,
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
                      this.formDelete.count = params.index;
                      this.dialog1 = true;
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      columnsv: [
        {
          title: "序号",
          width: 70,
          key: "index",
          align: "center"
        },
        {
          title: "姓名",
          key: "username",
          align: "center"
        },
        {
          title: "学号/工号",
          key: "userId",
          align: "center"
        },
        {
          title: "投票状态",
          key: "isvote",
          width: 160,
          align: "center",
          render: (h, params) => {
            const row = params.row;
            const color = row.isvote === 0 ? "warning" : "success";
            const text = row.isvote === 0 ? "未投" : "已投";

            return h("div", [
              h(
                "Tag",
                {
                  props: {
                    type: "dot",
                    color: color
                  }
                },
                text
              )
            ]);
          }
        },
        {
          title: "Action",
          slot: "action",
          width: 120,
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
                      this.deleteVoter = true;
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      totalRecord1: 0,
      totalRecord2: 0,
      totalRecord3: 0,
      page1: 1,
      page2: 1,
      page3: 1,
      datac: [],
      datav: [],
      columns: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "姓名",
          key: "candidatename"
        },
        {
          title: "赞成数",
          key: "votenumber",
          sortable: true
        },
        {
          title: "反对数",
          key: "voteagainstnumber",
          sortable: true
        },
        {
          title: "弃权数",
          key: "voteabondnumber",
          sortable: true
        },
        {
          title: "Action",
          slot: "action",
          // width: 190,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "success",
                    size: "small",
                    ghost: true
                  },
                  style: {
                    marginRight: "3px"
                  },
                  on: {
                    click: () => {
                      this.upMove(params.index);
                    }
                  }
                },
                "上移"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true
                  },
                  style: {
                    marginRight: "3px"
                  },
                  on: {
                    click: () => {
                      this.downMove(params.index);
                    }
                  }
                },
                "下移"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small",
                    ghost: true
                  },
                  on: {
                    click: () => {
                      this.formDelete.candidate = params.index;
                      this.deleteDialog = true;
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
     
     
      searchCandidate: "",
      searchCounter: "",
      searchVoter: "",

    
      theTabs: "candidate",
      page: 1,
     
    };
  },
  methods: {
    tabClick(name) {
      if (name == "candidate") {
        this.tab = 1;
        this.theTabs = "candidate";
        this.loadData();
      } else if (name == "counter") {
        this.tab = 2;
        this.theTabs = "counter";
        this.loadData();
      } else {
        this.tab = 3;
        this.theTabs = "voter";
        this.loadData2();
      }
    },
    handleOnClickRefresh() {
      if (this.tab == 3) {
        this.loadData2();
      } else {
        this.loadData();
      }
    },
    searchCandidates() {
      let URL = `${apiPath}/admin/findByName`;
      let name = this.searchCandidate;
      let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "get",
        params: {
          projectId: projectId,
          name: name
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.data = res.data.data.candidateMsgList;
            for (let i = 0; i < res.data.data.candidateMsgList.length; i++) {
              this.data[i].votenumber = res.data.data.candidateMsgList[i].agree;
              this.data[i].voteagainstnumber =
                res.data.data.candidateMsgList[i].against;
              this.data[i].voteabondnumber =
                res.data.data.candidateMsgList[i].abond;
              this.data[i].candidatename =
                res.data.data.candidateMsgList[i].name;
            }

            this.$Notice.success({ title: `搜索成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错：${err}` });
        });
    },
    cutaway(index) {
      this.page = index;
      this.loadData2();
    },
    
  
    deleteVoters() {
      let projectName = localStorage.getItem("projectName");
      let URL = `${apiPath}/admin/deleteOneVote`;
      let userId = this.datav[this.theIndex].userId;
      let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "get",
        params: {
          votemanId: userId,
          projectId: projectId
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData2();
            this.$Notice.success({ title: `删除成功` });
            this.deleteVoter = false;
          } else {
            this.$Notice.warning({ title: `错误，提示：${message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadData() {
      let URL = `${apiPath}/user/adminShow`;
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      this.projectName = projectName;
      this.myHeaders = localStorage.getItem("token");
      this.loading = true;
      axios({
        url: URL,
        method: "post",
        data: {
          projectId: projectId,
          search: this.searchCounter,
          page2: this.page2
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            // this.totalRecord1 = res.data.data.total_candidate;
            this.totalRecord2 = res.data.data.total_voter;
            this.data.splice(0, this.data.length);
            this.datac.splice(0, this.datac.length);
            if (res.data.data != null) {
              this.data = res.data.data.showlist;
              this.datac = res.data.data.countList;
              for (let i = 0; i < this.datac.length; i++) {
                this.datac[i].index = i + 1 + (this.page2 - 1) * 10;
              }
            }else{
              this.datac = [];
              this.data = [];
              this.totalRecord2=0;
            }
            this.loading = false;
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
   
  },
  mounted() {
   
  },

  computed: {
    
  },
  watch: {
   
   
  }
};
</script>

<style scoped lang ='scss'>
@import "basic"
</style>
