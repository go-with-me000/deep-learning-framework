<template>
  <div style="height:100%">
    <Tabs :value="theTabs" style="padding-left:5%;margin-top:2%" size="small" @on-click="tabClick">
      <TabPane label="候选人" name="candidate">
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
      <TabPane label="计票员" name="counter">
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
      <TabPane label="投票员" name="voter" v-if="votingmode=='线上投票'">
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

    <Modal v-model="dialog1" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>此计票员删除后，将没有权限为此系统计票</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button
          type="error"
          size="large"
          long
          @click="dialog1 = false;deleteCount(formDelete.count)"
        >删除</Button>
      </div>
    </Modal>
    <Modal v-model="dialog2" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>
          删除所有
          <span v-if="deleteIndex==0">候选人</span>
          <span v-else-if="deleteIndex==1">计票员</span>
          <span v-else>投票员</span>,其数据将不会保留
        </p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="dialog2 = false;deleteAll()">删除</Button>
      </div>
    </Modal>
    <Modal v-model="addVoter" width="25" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>增加投票员</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="voter" :model="voter" :label-width="100">
          <FormItem label="工号/学号">
            <Input v-model="voter.accent" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" @click="addVoters()" style="margin-right:1vh;">增加</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="addVoter = false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>
    <Modal v-model="dialogFormVisible1" width="25" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>增加计票员</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="form1" :model="form1" :label-width="100">
          <FormItem label="工号/学号">
            <Input v-model="form1.accent" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" @click="addCount()" style="margin-right:1vh;">增加</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="dialogFormVisible1 = false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>

    <Modal v-model="dialogFormVisible5" width="25" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">上传投票员名单</span>
      </p>
      <div style="width:100%;position:relative">
        <div style="position:relative;width:100%;right:0%">
          <Upload
            ref="upload"
            type="drag"
            :on-success="handleSuccess"
            :before-upload="handleBeforeUpload"
            :headers="Headers"
            :action="fileURL"
          >
            <div style="padding: 10px 0">
              <Icon type="ios-cloud-upload" size="30" style="color: #3399ff"></Icon>
              <p>
                请选择你要上传的文件
                <br />（Excel格式）
              </p>
            </div>
          </Upload>
        </div>
      </div>

      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          ghost
          @click="dialogFormVisible5 = false"
          style="margin-right:1vh;"
        >取消</Button>
        <a href="..\..\..\static/excel/simple.xlsx" rel="external nofollow" download="simple">
          <Button type="primary" size="large" style="margin-right:1vh;">下载模板</Button>
        </a>
      </div>
    </Modal>

    <Modal v-model="deleteDialog" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>此候选人删除后，其数据将不能保留</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button
          type="error"
          size="large"
          long
          @click="deleteCandidate(formDelete.candidate);deleteDialog = false"
        >删除</Button>
      </div>
    </Modal>
    <Modal v-model="deleteVoter" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>删除确认</span>
      </p>
      <div style="text-align:center">
        <p>此投票员被删除后，将无权限投票</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续删除？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="deleteVoters()">删除</Button>
      </div>
    </Modal>
    <Modal v-model="clearDialog" width="20%" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#f60;text-align:center">
        <Icon type="ios-information-circle"></Icon>
        <span>置零确认</span>
      </p>
      <div style="text-align:center">
        <p>此操作后，数据将全部清空</p>
        <p style="font-weight:bold;margin-top:10px;">是否继续？</p>
      </div>
      <div slot="footer">
        <Button type="error" size="large" long @click="clearAll();clearDialog = false">置零</Button>
      </div>
    </Modal>
    <Modal v-model="addCandidates" width="25" :styles="{top: '300px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>增加候选人</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="form" :model="form" :label-width="100">
          <FormItem label="候选人姓名">
            <Input v-model="form.name" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="addCandidates = false;addCandidate()"
          style="margin-right:1vh;"
        >增加</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="addCandidates = false"
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
      printObj: {
        id: "printDiv",
        popTitle: "上海大学投票项目：" + localStorage.getItem("projectName"),
        // extraCss: "https://www.google.com,https://www.google.com",
        extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>'
      },
      fits: ["cover"],
      url: "@/common/images/warning.jpg",
      fileURL: `${apiPath}/file/upload`,
      Headers: {
        Authorization: localStorage.getItem("token")
      },
      voter: {
        accent: ""
      },
      projectName: "",
      formDelete: {
        count: "",
        candidate: ""
      },
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
      addCandidates: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogTableVisible3: false,
      dialogFormVisible4: false,
      dialogFormVisible5: false,
      dialog1: false,
      dialog2: false,
      deleteDialog: false,
      clearDialog: false,
      addVoter: false,
      checkVoter: false,
      deleteVoter: false,
      deleteIndex: -1,
      form: {
        name: ""
      },
      searchCandidate: "",
      searchCounter: "",
      searchVoter: "",

      form1: {
        accent: ""
      },
      username: "",
      projectName: "",
      projectId: -1,
      loading: false,
      tab: -1,
      theTabs: "candidate",
      page: 1,
      totalVoter: 0,
      address: "",
      votingmode: "",
      haverightman: "",
      havevotednumber: ""
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
    searchCounters() {
      let URL = `${apiPath}/admin/findTeller`;
      let name = this.searchCounter;
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
            this.datac = res.data.data.tellerMsgList;
            this.$Notice.success({ title: `搜索成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错：${err}` });
        });
    },
    searchVoters() {
      let URL = `${apiPath}/admin/findVoter`;
      let name = this.searchVoter;
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
            this.datav = res.data.data.votersList;
            this.$Notice.success({ title: `搜索成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错：${err}` });
        });
    },
    addCount() {
      let URL = `${apiPath}/admin/changeCountMan`;
      let projectName = localStorage.getItem("projectName");
 let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "POST",
        data: {
          projectId: projectId,

          userId: this.form1.accent,
          flag: 1
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData();

            this.$Notice.success({ title: `增加计票员成功` });
            this.dialogFormVisible1 = false;
          } else {
            this.$Notice.warning({
              title: `出错，提示：${res.data.message}`
            });
            this.form1.accent = "";
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
          this.form1.accent = "";
        });
    },
    addVoters() {
      let URL = `${apiPath}/admin/addVoteMan`;
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "POST",
        data: {
          projectId: projectId,
          manId: this.voter.accent
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData2();
            this.$Notice.success({ title: `增加投票员成功` });
            this.addVoter = false;
          } else {
            this.$Notice.warning({
              title: `出错，提示：${res.data.message}`
            });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    cutaway1(index) {
      this.page1 = index;
    },
    cutaway2(index) {
      this.page2 = index;
      this.loadData();
    },
    cutaway3(index) {
      this.page3 = index;
      this.loadData2();
    },

    upMove(index) {
      if (index == 0) {
        this.$alert("此候选人已至最高序列", "错误", {
          confirmButtonText: "确定",
          callback: action => {
            this.$message({
              type: "info",
              message: `action: ${action}`
            });
          }
        });
      } else {
        let mainCandidate = this.data[index].candidatename;
        let viceCandidate = this.data[--index].candidatename;
        let projectName = localStorage.getItem("projectName");
           let projectId = localStorage.getItem("projectId");
        let URL = `${apiPath}/admin/move`;
        axios({
          url: URL,
          method: "POST",
          data: {
            projectId:projectId,
            name1: mainCandidate,
            name2: viceCandidate
          }
        })
          .then(res => {
            if (res.data.code === "SUCCESS") {
              this.loadData();
              this.$Notice.success({ title: `上移成功` });
            } else {
              this.$Notice.warning({
                title: `出错，提示：${res.data.message}`
              });
            }
          })
          .catch(err => {
            this.$Notice.warning({ title: `出错，提示：${err}` });
          });
      }
    },
    downMove(index) {
      if (index == this.data.length - 1) {
        this.$alert("此候选人已至最低序列", "错误", {
          confirmButtonText: "确定",
          callback: action => {
            this.$message({
              type: "info",
              message: `action: ${action}`
            });
          }
        });
      } else {
        let mainCandidate = this.data[index].candidatename;
        let viceCandidate = this.data[++index].candidatename;
        let projectName = localStorage.getItem("projectName");
        let projectId = localStorage.getItem("projectId");
        let URL = `${apiPath}/admin/move`;
        axios({
          url: URL,
          method: "POST",
          data: {
            // username: this.username,
           projectId:projectId,

            name1: mainCandidate,
            name2: viceCandidate
          }
        })
          .then(res => {
            if (res.data.code === "SUCCESS") {
              this.loadData();
              this.$Notice.success({ title: `下移成功` });
            } else {
              this.$Notice.warning({
                title: `出错，提示：${res.data.message}`
              });
            }
          })
          .catch(err => {
            this.$Notice.warning({ title: `出错，提示：${err}` });
          });
      }
    },
    deleteCandidate(index) {
      let projectName = localStorage.getItem("projectName");
      let candidateName = this.data[index].candidatename;
      let URL = `${apiPath}/admin/deleteOneCandidate`;
let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "POST",
        data: {
          // username: this.username,
          projectId:projectId,

          candidateName: candidateName
        }
      })
        .then(res => {
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
    deleteAll() {
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      let URL1 = `${apiPath}/admin/candidates/${projectId}`;
      let URL2 = `${apiPath}/admin/tellers/${projectId}`;
      let URL3 = `${apiPath}/admin/voters/${projectId}`;
      var URL = "";
      if (this.deleteIndex == 0) {
        URL = URL1;
      } else if (this.deleteIndex == 1) {
        URL = URL2;
      } else {
        URL = URL3;
      }
      axios({
        url: URL,
        method: 'GET'
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            if (this.deleteIndex == 2) {
              this.loadData2();
            } else {
              this.loadData();
            }

            this.$Notice.success({ title: `删除成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    addCandidate() {
      let URL = `${apiPath}/admin/addOneCandidate`;
      let projectname = localStorage.getItem("projectName");
let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "post",
        data: {
          // username: this.username,
          projectId:projectId,
          newname: this.form.name
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.$Notice.success({ title: `增加成功` });
            this.loadData();
            this.form.name = "";
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
            this.form.name = "";
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    clearAll() {
      let URL = `${apiPath}/admin/setAllZero`;
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      axios({
        url: URL,
        method: "POST",
        data: {
          projectId:projectId,
          flag: 0
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData();
            this.$Notice.success({ title: `置零成功` });
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    deleteCount(index) {
      let countName = this.datac[index].countName;
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      let URL = `${apiPath}/admin/deleteCountMan`;
      // let projectName = localStorage.getItem('projectName')
      let userId = this.datac[index].userId;
      axios({
        url: URL,
        method: "post",
        data: {
          userId: userId,
          projectId:projectId
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            this.loadData();
            this.$Notice.success({ title: `删除成功` });
          } else {
            this.$Notice.warning({ title: `错误，提示：${message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
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
    loadData2() {
      let URL = `${apiPath}/admin/getAllVoters`;
      let projectName = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      this.loading = true;
      axios({
        url: URL,
        method: "get",
        params: {
          projectId: projectId,
          page: this.page3,
          search: this.searchVoter
        }
      })
        .then(res => {
          this.loading = false;
          if (res.data.code === "SUCCESS") {
            // this.totalRecord3 = res.data
            this.datav.splice(0, this.datav.length);
            if (res.data.data.votersList != null) {
              this.totalVoter = res.data.data.total;
              this.datav = res.data.data.votersList;
              for (let i = 0; i < this.datav.length; i++) {
                this.datav[i].index = i + 1 + (this.page3 - 1) * 10;
              }
            }else{
              this.datav = [];
              this.totalVoter=0;
            }
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    finalUpload() {
      let URL = `${apiPath}/admin/test`;
      let path = this.address;
      let projectId = localStorage.getItem("projectId");

      axios({
        url: URL,
        method: "get",
        params: {
          projectId: projectId,
          path: path
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            if (res.data.data != null) {
            }
          } else {
            this.$Notice.warning({ title: `出错，提示：${res.data.message}` });
          }
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    handleSuccess(res, file) {
      this.address = res.message;
      this.finalUpload();
      this.$Notice.success({
        title: "上传成功",
        desc: "投票员正在导入,请稍等片刻，再刷新页面"
      });
      this.dialogFormVisible5 = false;
    },
    handleBeforeUpload(file) {
      this.file = file;
      const extension = file.name.split(".")[1] === "xls";
      const extension2 = file.name.split(".")[1] === "xlsx";
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!extension && !extension2) {
        this.$message.warning("上传模板只能是 xls、xlsx格式!");
        return false;
      }
      if (!isLt2M) {
        this.$message.warning("上传模板大小不能超过 5MB!");
        return false;
      }
    }
  },
  mounted() {
    this.loadData();
    this.projectId = localStorage.getItem("projectId");
    let projectName = localStorage.getItem("projectName");
    this.projectName = localStorage.getItem("projectName");
    // this.votingmode = this.$route.params.votingmode;

    // this.haverightman = this.$route.params.haverightman;
    // this.havevotednumber = this.$route.params.havevotednumber;
    this.haverightman = localStorage.getItem("haverightman");
    this.havevotednumber = localStorage.getItem("havevotednumber");
    this.votingmode = localStorage.getItem("votingmode");
  },

  computed: {
    candidateNum() {
      return this.data.length;
    }
  },
  watch: {
    addCandidates(val, oldVal) {
      if (!val) {
        this.form.name = "";
      }
    },
    dialogFormVisible5(val, oldVal) {
      if (!val) {
        this.file = "";
      }
    },
    dialogFormVisible1(val, olaVal) {
      if (!val) {
        this.form1.accent = "";
      }
    },
    addVoter(val, olaVal) {
      if (!val) {
        this.voter.accent = "";
      }
    },
    checkVoter(val, oldVal) {
      if (val) {
        this.loadData2();
      }
    },
    searchCandidate(val, oldVal) {
      if (val != "") {
        this.searchCandidates();
      } else {
        this.loadData();
      }
    },
    searchCounter(val, oldVal) {
      this.page2 = 1;
      this.loadData();
    },
    searchVoter(val, oldVal) {
      this.page3 = 1;
      this.loadData2();
    },
    page1() {
      this.loadData();
    }
    // page2() {
    //   this.loadData();
    // },
    // page3() {
    //   this.loadData2();
    // }
  }
};
</script>

<style scoped>
.action {
  height: 7vh;
  left: 0%;
  width: 92%;
  margin-top: 0%;
  position: relative;
  /* background-color: blueviolet; */
}
.Table {
  width: 92%;
  /* height: 65vh; */
  position: relative;
  /* left: 5%; */
  /* top: -2%; */
  /* background-color: aquamarine; */
}
.tablemain {
  border-radius: 10px;
  -moz-box-shadow: 2px 2px 1px #585858;
  -webkit-box-shadow: 2px 2px 1px #666565;
  box-shadow: 1px 1px 4px #7c7e7e;
}
.demo-upload-list {
  display: inline-block;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 60px;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
}
.demo-upload-list img {
  width: 100%;
  height: 100%;
}
.demo-upload-list-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}
.demo-upload-list:hover .demo-upload-list-cover {
  display: block;
}
.demo-upload-list-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}
.bottomButton {
  margin-top: 10px;
  margin-bottom: 10px;
  /* height: 7vh; */
}
</style>
<style>
.ivu-tabs-mini .ivu-tabs-tab {
  font-size: 20px;
}
</style>
