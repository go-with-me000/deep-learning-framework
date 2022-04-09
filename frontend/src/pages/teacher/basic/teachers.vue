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
            <Button type="success" size="large" @click="addmodal=true">增加老师</Button>
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

    <Modal title="增加老师" v-model="addmodal" width="450" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">增加老师</span>
      </p>
      <div style="width:100%;position:relative">
        <Form ref="theTeacher" :model="theTeacher" :label-width="100">
          
          <FormItem label="老师名字">
            <Input v-model="theTeacher.teacherName" placeholder="请输入老师名字" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theTeacher.yxh" style="width:100%" placeholder="请选择老师所属学院">
              <Option
                    v-for="item in academyList"
                    :value="item.yxh"
                    :key="item.yxh"
                  >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="性别">
            <Select v-model="theTeacher.sex" style="width:100%" placeholder="请选择性别">
              <Option v-for="item in sexList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </FormItem>
          <FormItem label="年龄">
            <Input v-model="theTeacher.age" placeholder="请输入老师年龄" />
          </FormItem>
         
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="create()"
          style="margin-right:1vh;"
          :disabled="theTeacher.submit"
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
        <Button type="error" size="large" long @click="deleteteacher(teacherIndex)">删除</Button>
      </div>
    </Modal>
    <Modal v-model="changemodal" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>更改老师</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="theTeacher" :model="theTeacher" :label-width="100">
          
          <FormItem label="老师名字">
            <Input v-model="theTeacher.teacherName" placeholder="请输入老师名字" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theTeacher.yxh" style="width:100%" placeholder="请选择老师所属学院">
              <Option
                    v-for="item in academyList"
                    :value="item.yxh"
                    :key="item.yxh"
                  >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="性别">
            <Select v-model="theTeacher.sex" style="width:100%" placeholder="请选择性别">
              <Option v-for="item in sexList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </FormItem>
          <FormItem label="年龄">
            <Input v-model="theTeacher.age" placeholder="请输入老师年龄" />
          </FormItem>
        
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="changeteacher(teacherIndex)"
          :disabled="theTeacher.submit"
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
      teacherIndex: -1,
      theTeacher: {
        teacherName: "",
        teacherNum: "",
        age:'',
        sex: "",
        academy: "",
        submit: true,
       
      },
      academyList: [
       
      ],
      sexList: ["男", "女"],
    
      columns: [
        {
          type: "index",
          title: "序号",
          width: 70,
          align: "center"
        },
        {
          title: "老师号",
          key: "teacherNum",
          align: "center"
        },
        {
          title: "老师名",
          key: "teacherName",
          align: "center"
        },
        {
          title: "院系",
          key: "academyName",
          align: "center"
        },
        {
          title: "年龄",
          key: "age",
          align: "center"
        },
        {
          title: "性别",
          key: "sex",
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
              params.row.sex
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
                      this.teacherIndex = params.index
                      this.theTeacher.teacherName = this.data[
                        params.index
                      ].teacherName;
                      this.theTeacher.teacherNum = this.data[params.index].teacherNum;
                      this.theTeacher.sex = this.data[params.index].sex;
                      this.theTeacher.yxh = this.data[params.index].yxh;
                      this.theTeacher.password = this.data[params.index].password;
                      this.theTeacher.age = this.data[params.index].age;
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
                      this.teacherIndex = params.index;
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
      data: [
       
      ],

      page: 1
    };
  },
  methods: {
    handleOnClickRefresh() {
      this.loadData();
    },
loadDataAcademy(){
       let URL = `${apiPath}/departments`;
      
      axios({
        url: URL,
        method: "get",
       
      })
        .then(res => {
          this.academyList = res.data;
        })
        .catch(err => {
          console.log(err)
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    cutaway(index) {
      this.page = index;
    },
    create() {
       let URL = `${apiPath}/teacher/add`;
      let num = this.data[this.data.length-1].teacherNum+1;

      axios({
        url: URL,
        method: "get",
        params:{
          teacherNum:num,
          teacherName:this.theTeacher.teacherName,
          sex:this.theTeacher.sex,
          password: this.theTeacher.password,
          yxh:this.theTeacher.yxh,
          age:this.theTeacher.age
        }
       
      })
        .then(res => {
          this.data=null;
          this.loadData();
        })
        .catch(err => {
          console.log(err)
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
      this.addmodal = false;
    },
    deleteteacher(teacherIndex) {
        let URL = `${apiPath}/teacher/delete`;
  console.log(this.data[this.theTeacherIndex])
      axios({
        url: URL,
        method: "get",
        params:{
          teacherNum: this.data[this.teacherIndex].teacherNum
        }
       
      })
        .then(res => {
         this.loadData()
        })
        .catch(err => {
          this.$Notice.warning({ title: `该老师有开课` });
        });
      this.deletemodal = false;
    },
    changeteacher(teacherIndex) {
       let URL = `${apiPath}/teacher/update`;
      console.log(this.data)
      console.log(this.teacherIndex)
      axios({
        url: URL,
        method: "get",
        params:{
          teacherNum:this.data[this.teacherIndex].teacherNum,
          teacherName:this.theTeacher.teacherName,
          sex:this.theTeacher.sex,
          password: this.theTeacher.password,
          yxh:this.theTeacher.yxh,
          age:this.theTeacher.age
        }
       
      })
        .then(res => {
          // this.data=;
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
      this.changemodal = false;
    },

    loadData() {
      let URL = `${apiPath}/teacher/consult`;
      this.loading = true;
      axios({
        url: URL,
        method: "get",
        
      })
        .then(res => {
          this.loading=false;
          console.log(res)
          this.data = res.data;
        })
        .catch(err => {
          this.loading = false;
          console.log(err)
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  mounted() {
    this.loadDataAcademy();
    this.loadData();
  },

  computed: {},
  watch: {
    addmodal(val, oldVal) {
      if (!val) {
        this.theTeacher.teacherName = "";
        this.theTeacher.teacherNum = "";
        this.theTeacher.sex = "";
        this.theTeacher.age = "";
        this.theTeacher.yxh = "";
        this.theTeacher.submit = false;
      }
    },
    changemodal(val, oldVal) {
      if (!val) {
        this.theTeacher.teacherName = "";
        this.theTeacher.teacherNum = "";
        this.theTeacher.sex = "";
        this.theTeacher.age = "";
        this.theTeacher.yxh = "";
        this.theTeacher.submit = false;
      }
    },
    theTeacher: {
      handler() {
        if (
          this.theTeacher.teacherName != "" &&
         
          this.theTeacher.yxh != "" &&
          this.theTeacher.age != "" &&
          this.theTeacher.sex != ""
        ) {
          this.theTeacher.submit = false;
        } else {
          this.theTeacher.submit = true;
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
