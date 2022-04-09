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
            <Button type="success" size="large" @click="addmodal=true">增加学生</Button>
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

    <Modal title="增加学生" v-model="addmodal" width="450" :styles="{top: '80px'}">
      <p slot="header" style="color:#000;text-align:center;height:30px;">
        <span style="position:relative;font-size:24px;font-weight:bold;top:20%">增加学生</span>
      </p>
      <div style="width:100%;position:relative">
        <Form ref="theStudent" :model="theStudent" :label-width="100">
          
          <FormItem label="学生名字">
            <Input v-model="theStudent.studentName" placeholder="请输入学生名字" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theStudent.yxh" style="width:100%" placeholder="请选择学生所属学院">
              <Option
                    v-for="item in academyList"
                    :value="item.yxh"
                    :key="item.yxh"
                  >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="性别">
            <Select v-model="theStudent.sex" style="width:100%" placeholder="请选择性别">
              <Option v-for="item in sexList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </FormItem>
          <FormItem label="年龄">
            <Input v-model="theStudent.age" placeholder="请输入学生年龄" />
          </FormItem>
          <FormItem label="学生密码">
            <Input v-model="theStudent.password" placeholder="请输入学生密码" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="create()"
          style="margin-right:1vh;"
          :disabled="theStudent.submit"
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
        <Button type="error" size="large" long @click="deleteStudent(studentIndex)">删除</Button>
      </div>
    </Modal>
    <Modal v-model="changemodal" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>更改学生</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="theStudent" :model="theStudent" :label-width="100">
          
          <FormItem label="学生名字">
            <Input v-model="theStudent.studentName" placeholder="请输入学生名字" />
          </FormItem>
          <FormItem label="所属学院">
            <Select v-model="theStudent.yxh" style="width:100%" placeholder="请选择学生所属学院">
              <Option
                    v-for="item in academyList"
                    :value="item.yxh"
                    :key="item.yxh"
                  >{{ item.academyName }}</Option>
            </Select>
          </FormItem>
          <FormItem label="性别">
            <Select v-model="theStudent.sex" style="width:100%" placeholder="请选择性别">
              <Option v-for="item in sexList" :value="item" :key="item">{{ item }}</Option>
            </Select>
          </FormItem>
          <FormItem label="年龄">
            <Input v-model="theStudent.age" placeholder="请输入学生年龄" />
          </FormItem>
          <FormItem label="学生密码">
            <Input v-model="theStudent.password" placeholder="请输入学生密码" />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="changeStudent(studentIndex)"
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
      deletemodal: false,
      addmodal: false,
      studentIndex: -1,
      theStudent: {
        studentName: "",
        studentNum: "",
        age:'',
        sex: "",
        academy: "",
        submit: true,
        password: ""
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
          title: "学生号",
          key: "studentNum",
          align: "center"
        },
        {
          title: "学生名",
          key: "studentName",
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
          title: "密码",
          key: "password",
          align: "center"
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
                      this.studentIndex = params.index
                      this.theStudent.studentName = this.data[
                        params.index
                      ].studentName;
                      this.theStudent.studentNum = this.data[params.index].studentNum;
                      this.theStudent.sex = this.data[params.index].sex;
                      this.theStudent.yxh = this.data[params.index].yxh;
                      this.theStudent.password = this.data[params.index].password;
                      this.theStudent.age = this.data[params.index].age;
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
                      this.studentIndex = params.index;
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
       let URL = `${apiPath}/student/add`;
       let num;
       if(this.data.length==0){
         num = 18120190;
       }else{
         num = this.data[this.data.length-1].studentNum+1;
       }
     
      
      axios({
        url: URL,
        method: "get",
        params:{
          studentNum:num,
          studentName:this.theStudent.studentName,
          sex:this.theStudent.sex,
          password: this.theStudent.password,
          yxh:this.theStudent.yxh,
          age:this.theStudent.age
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
    deleteStudent(studentIndex) {
        let URL = `${apiPath}/student/delete`;
  console.log(this.data[this.theStudentIndex])
      axios({
        url: URL,
        method: "get",
        params:{
          studentNum: this.data[this.studentIndex].studentNum
        }
       
      })
        .then(res => {
         this.loadData()
        })
        .catch(err => {
          this.$Notice.warning({ title: `该学生有选课` });
        });
      this.deletemodal = false;
    },
    changeStudent(studentIndex) {
       let URL = `${apiPath}/student/update`;
      console.log(this.data)
      console.log(this.studentIndex)
      axios({
        url: URL,
        method: "get",
        params:{
          studentNum:this.data[this.studentIndex].studentNum,
          studentName:this.theStudent.studentName,
          sex:this.theStudent.sex,
          password: this.theStudent.password,
          yxh:this.theStudent.yxh,
          age:this.theStudent.age
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
      let URL = `${apiPath}/student/consult`;
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
        this.theStudent.studentName = "";
        this.theStudent.studentNum = "";
        this.theStudent.sex = "";
        this.theStudent.age = "";
        this.theStudent.yxh = "";
        this.theStudent.password = "";
        this.theStudent.submit = false;
      }
    },
    changemodal(val, oldVal) {
      if (!val) {
        this.theStudent.studentName = "";
        this.theStudent.studentNum = "";
        this.theStudent.sex = "";
        this.theStudent.age = "";
        this.theStudent.yxh = "";
        this.theStudent.password = "";
        this.theStudent.submit = false;
      }
    },
    theStudent: {
      handler() {
        if (
          this.theStudent.studentName != "" &&
         
          this.theStudent.yxh != "" &&
          this.theStudent.age != "" &&
          this.theStudent.password != "" &&
          this.theStudent.sex != ""
        ) {
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
@import "basic";
</style>
