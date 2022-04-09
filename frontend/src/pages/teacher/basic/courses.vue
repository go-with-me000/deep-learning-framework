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
        </div>
      </div>
    </div>

    <Modal title="增加课程" v-model="addmodal" width="450" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">增加课程</span>
      </p>
      <div style="width:100%;position:relative">
        <Form ref="theCourse" :model="theCourse" :label-width="100">
          <FormItem label="课程名称">
            <Input v-model="theCourse.courseName" placeholder="请输入课程名称" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theCourse.academy" style="width:100%" placeholder="请选择课程所属学院">
              <Option
                v-for="item in academyList"
                :value="item.yxh"
                :key="item.yxh"
              >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="学分">
            <Select v-model="theCourse.credit" style="width:100%" placeholder="请选择学分">
              <Option v-for="item in creditList" :value="item" :key="item">{{ item }}</Option>
            </Select>
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
    <Modal v-model="changemodal" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>更改课程</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="theCourse" :model="theCourse" :label-width="100">
          <FormItem label="课程名称">
            <Input v-model="theCourse.courseName" placeholder="请输入课程名称" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theCourse.academy" style="width:100%" placeholder="请选择课程所属学院">
              <Option
                v-for="item in academyList"
                :value="item.yxh"
                :key="item.yxh"
              >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="学分">
            <Select v-model="theCourse.credit" style="width:100%" placeholder="请选择学分">
              <Option v-for="item in creditList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="changeCourse(courseIndex)"
          :disabled="theCourse.submit"
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
      deletemodal: false,
      addmodal: false,
      courseIndex: -1,
      theCourse: {
        courseName: "",
        courseNum: "",
        credit: "",
        academy: "",
        submit: true
      },
      academyList: [],
      creditList: [1, 2, 3, 4, 5, 6],
      totalRecord: 0,
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
          title: "院系",
          key: "academyName",
          align: "center"
        },
        {
          title: "学分",
          key: "credit",
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
              params.row.credit
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
                  props: {
                    type: "success",

                    ghost: true
                  },
                  style: {
                    marginRight: "3px"
                  },
                  on: {
                    click: () => {
                      this.courseIndex=params.index
                      this.theCourse.courseName = this.data[
                        params.index
                      ].courseName;
                      this.theCourse.courseNum = this.data[
                        params.index
                      ].courseNum;
                      this.theCourse.credit = this.data[params.index].credit;
                      this.theCourse.academy = this.data[params.index].yxh;
                      this.changemodal = true;
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
      this.loading = true;
      this.loadData();
      this.loading = false;
    },

    create() {
      let URL = `${apiPath}/classstarts/add`;
      let num = this.data[this.data.length - 1].courseNum + 1;

      axios({
        url: URL,
        method: "get",
        params: {
          courseNum: num,
          courseName: this.theCourse.courseName,
          credit: this.theCourse.credit,
          yxh: this.theCourse.academy
        }
      })
        .then(res => {
          this.loadData();
        })
        .catch(err => {
          console.log(err)
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
      this.addmodal = false;
    },
    deleteCourse(courseIndex) {
      let URL = `${apiPath}/classstarts/delete`;
      
      axios({
        url: URL,
        method: "get",
        params: {
          courseNum: this.data[this.courseIndex].courseNum
        }
      })
        .then(res => {
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `有人选择了这门课` });
        });
      this.deletemodal = false;
    },
    changeCourse(courseIndex) {
      let URL = `${apiPath}/classstarts/update`;
      console.log(this.data)
      console.log(courseIndex)
      axios({
        url: URL,
        method: "get",
        params: {
          courseNum: this.data[this.courseIndex].courseNum,
          courseName: this.theCourse.courseName,
          credit: this.theCourse.credit,
          yxh: this.theCourse.academy
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

    loadData() {
      let URL = `${apiPath}/classstarts/consult`;
      this.loading = true;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.loading = false;
          console.log(res);
          this.data = res.data;
        })
        .catch(err => {
          this.loading = false;
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadDataAcademy() {
      let URL = `${apiPath}/departments`;

      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.academyList = res.data;
          console.log(res.data);
        })
        .catch(err => {
          console.log(err);
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  mounted() {
    this.loadData();
    this.loadDataAcademy();
  },

  computed: {},
  watch: {
    addmodal(val, oldVal) {
      if (!val) {
        this.theCourse.courseName = "";

        this.theCourse.credit = "";
        this.theCourse.academy = "";
        this.theCourse.submit = false;
      }
    },
    changemodal(val, oldVal) {
      if (!val) {
        this.theCourse.courseName = "";

        this.theCourse.credit = "";
        this.theCourse.academy = "";
        this.theCourse.submit = false;
      }
    },
    theCourse: {
      handler() {
        if (
          this.theCourse.courseName != "" &&
          this.theCourse.academy != "" &&
          this.theCourse.credit != ""
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
@import "basic";
</style>
