<template>
  <div>
    <div class="action">
      <Row
        type="flex"
        justify="start"
        class="code-row-bg"
        style="position:relative;margin-top:4%;margin-bottom:-10px"
      >
        <Col span="14">
          <Input v-model="searchCandidate" placeholder="请输入名字查询" clearable style="width:50%" />
       </Col>
        <Col span="10" style="text-align:right">
          <Button v-print="printObj" type="success" style="margin-left:1%">打印</Button>
          <Button type="info" @click="handleOnClickRefresh()" style="margin-left:1%">刷新</Button>
        </Col>
      </Row>
    </div>
    <div class="Table" id="printDiv">
      <div class="tablemain">
        <Table border ref="selection" :columns="columns4" :data="data" stripe :loading="loading"></Table>
        <div class="bottomButton">
          <div style="text-align:center">
            <Row style="position:relative;top:10px;font-size:16px;font-weight:bold;">
              <Col span="12">已投人数：{{havevotednumber}}</Col>
              <Col span="12">总投人数：{{haverightman}}</Col>
            </Row>
            <Divider style="position:relative;margin-bottom:1px"></Divider>
           
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
      totalRecord:0,
     
      printObj: {
        id: "printDiv",
        popTitle: "上海大学投票项目：" + localStorage.getItem("projectName"),
        // extraCss: "https://www.google.com,https://www.google.com",
        extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>'
      },
      columns4: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "姓名",
          key: "candidatename",
          align: "center"
        },
        {
          title: "赞成数",
          key: "votenumber",
          sortable: true,
          align: "center"
        },
        {
          title: "反对数",
          key: "voteagainstnumber",
          sortable: true,
          align: "center"
        },
        {
          title: "弃权数",
          key: "voteabondnumber",
          sortable: true,
          align: "center"
        }
      ],
      data: [],
      loading: false,
      searchCandidate: "",
      haverightman: "",
      havevotednumber: ""
    };
  },
  mounted() {
    this.loadData();
    this.haverightman = this.$route.params.haverightman;
    this.havevotednumber = this.$route.params.havevotednumber;
  },
  methods: {
   
    searchCandidates() {
      let URL = `${apiPath}/user/findCandidate`;
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
            // this.data=[];
            // this.data.splice(0,this.data.length);
            if (res.data.data.candidateMsgList != null) {
              this.data = res.data.data.candidateMsgList;
              for (let i = 0; i < res.data.data.candidateMsgList.length; i++) {
                this.data[i].votenumber =
                  res.data.data.candidateMsgList[i].agree;
                this.data[i].voteagainstnumber =
                  res.data.data.candidateMsgList[i].against;
                this.data[i].voteabondnumber =
                  res.data.data.candidateMsgList[i].abond;
                this.data[i].candidatename =
                  res.data.data.candidateMsgList[i].name;
              }
            } else {
              this.data = [];
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

    loadData() {
      let URL = `${apiPath}/user/showOnlineCount`;
      let projectname = localStorage.getItem("projectName");
      let projectId = localStorage.getItem("projectId");
      this.loading = true;
      axios({
        url: URL,
        method: "post",
        data: {
          projectId: projectId,
         
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            if (res.data.data != null) {
              this.data = res.data.data.candidateMessageList;
              this.totalRecord = res.data.data.total;
            }else{
              this.data=[];
              this.totalRecord=0;
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
    handleOnClickRefresh() {
      this.loadData();
    }
  },
  watch: {
    searchCandidate(val, oldVal) {
      if (val != "") {
        this.searchCandidates();
      } else {
        this.loadData();
      }
    },
   
    
  }
};
</script>

<style scoped>
.action {
  height: 1vh;
  left: 5%;
  width: 90%;
  margin-top: 1%;
  position: relative;
  /* background-color: blueviolet; */
}
.bottomButton {
  margin-top: 10px;
  margin-bottom: 10px;
  /* height: 7vh; */
}
.Table {
  width: 90%;
  /* height: 65vh; */
  top: 50px;
  position: relative;
  left: 5%;
  /* background-color: aquamarine; */
}
.tablemain {
  position: relative;
  border-radius: 10px;
  -moz-box-shadow: 2px 2px 1px #585858;
  -webkit-box-shadow: 2px 2px 1px #666565;
  box-shadow: 1px 1px 4px #7c7e7e;
}
#printDiv {
}
</style>
