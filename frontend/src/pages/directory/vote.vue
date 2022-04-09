<template>
  <div>
    <!-- <div class="pagetitle">
      <div class="intru">现在一共有11种投票项目：</div>
    </div>-->

    <div class="Table">
      <div class="tablemain">
        <Table
          border
          ref="selection"
          :columns="columns4"
          :data="data"
          height="500"
          stripe
          :loading="loading"
        ></Table>
         <Row type="flex" justify="space-between" class="code-row-bg">
          <Col span="2"></Col>
          <Col span="18" style="text-align:center;">
            <Page
              :total="totalRecord"
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
          </div>
          <div class="bottom1"></div>
        </div>-->
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
      page:1,
      loading: false,
      columns4: [
        {
          
          title: "序号",
          width: 70,
          key:'index',
          align: "center"
        },
        {
          title: "项目名称",
          key: "votesystemname",
          align: "center"
          // width: 120
        },
        {
          title: "候选人数",
          key: "candidatenumber",
          width: 93,
          align: "center"
        },
        {
          title: "至多可选",
          key: "electornumber",
          width: 95,
          align: "center"
        },

        {
          title: "开始日期",
          key: "starttime",
          align: "center"
          
        },
        {
          title: "截止日期",
          key: "endtime",
          align: "center"
        },
         {
          title: "项目状态",
          key: "applystatus",
          width: 129,
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
          title: "投票状态",
          key: "isvote",
          width: 118,
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
          width: 100,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                     disabled:
                      this.data[params.index].applystatus == 1&&this.data[params.index].isvote==0? false : true
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      localStorage.setItem(
                        "projectName",
                        this.data[params.index].votesystemname
                      );
                      localStorage.setItem(
                        "projectId",
                        this.data[params.index].id
                      );
                      if (this.data[params.index].isvote == 0) {
                        this.$router.push({
                          path: `specifics/vote`
                        });
                      } else {
                        this.$Message.warning("您已为该项目投过票");
                      }
                    }
                  }
                },
                "投票"
              )
            ]);
          }
        }
      ],
      data: []
    };
  },
  methods: {
     cutaway(index) {
      this.page = index;
    },
    loadData() {
      let URL = `${apiPath}/user/votemenu`;
      this.loading = true;
      axios({
        url: URL,
        method: "get",
        params:{
          page:this.page
        }
      })
        .then(res => {
          if (res.data.code === "SUCCESS") {
            if (res.data.data != null) {
              this.totalRecord = res.data.data.total;
              this.data = res.data.data.ordinaryShowList;
               for (let i = 0; i < this.data.length; i++) {
                this.data[i].index = i + 1 + (this.page - 1) * 10;
              }
            }

            this.loading = false;
          } else if (res.data.code === "FAILED") {
            this.loading = false;
            this.$Notice.warning({ title: `暂无项目` });
          } else {
            this.loading = false;
            this.$Notice.warning({ title: `出错，提示：${err}` });
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
  mounted() {
    this.loadData();
  },
  watch:{
    page(){
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
  margin-left: 44%;
  /* background-color: antiquewhite; */
}
.bottom1 {
  padding-top: 5px;
  margin-top: 5px;
}
.pagetitle {
  height: 10vh;
  left: 4%;
  position: relative;
  /* background-color: blueviolet; */
}
.Table {
  width: 90%;
  height: 65vh;
  position: absolute;
  left: 5%;
  top: 6%;
  /* background-color: aquamarine; */
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
</style>
