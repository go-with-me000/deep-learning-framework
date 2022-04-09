<template>
  <div class="wrapper">
    <Row type="flex" justify="center" class="code-row-bg">
      <Col span="16">
        <div class="content">
          <Form
            ref="person_info"
            :model="person_info"
            :rules="rulesx"
            :label-width="120"
            class="formEx"
            style="width: 500px"
            size="large"
          >
            <FormItem label="姓名">
              <Input v-model="person_info.studentName" placeholder="请输入您的名字" disabled></Input>
            </FormItem>
            <FormItem label="学号">
              <Input v-model="person_info.studentNum" placeholder="请输入您的学号" disabled></Input>
            </FormItem>

            <FormItem label="学院">
              <Input v-model="person_info.academy" placeholder="请输入您的学院" disabled></Input>
            </FormItem>
            <FormItem label="年龄" prop="age">
              <Input v-model="person_info.age" placeholder="请输入您的年龄" :disabled="disable_impl"></Input>
            </FormItem>
            <FormItem label="性别" prop="sex">
              <RadioGroup v-model="person_info.sex">
                <Radio label="男" :disabled="disable_impl">男</Radio>
                <Radio label="女" :disabled="disable_impl">女</Radio>
              </RadioGroup>
            </FormItem>
          </Form>

          <div class="autos">
            <Button
              type="primary"
              size="large"
              v-if="disable_impl"
              style="margin-left: 150px;text-align:center"
              ghost
              @click="Change()"
            >修改信息</Button>
            <Button
              size="large"
              style="margin-left: 100px"
              v-if="!disable_impl"
              @click="cancel('person_info')"
            >取消</Button>
            <Button
              type="success"
              size="large"
              style="margin-left: 20px"
              :loading="loading"
              v-if="!disable_impl"
              ghost
              @click="updatePersonInfo('person_info')"
            >保存</Button>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      disable_impl: true,
      loading: false,
      loading2: false,
      loading3: false,
      theIndex: "",
      studentNum:"",
      person_info: {
        name: "",
        age: "",
        id: "",
        academy: "",
        sex: ""
      },
      person_temp: {},

      rulesx: {
        name: [
          {
            required: true,
            message: "姓名不能为空",
            trigger: "blur"
          }
        ],
        id: [
          {
            required: true,
            message: "学号不能为空",
            trigger: "blur"
          }
        ],
        sex: [
          {
            required: true,
            message: "性别不能为空",
            trigger: "blur"
          }
        ],
        age: [
          {
            required: true,
            message: "年龄不能为空",
            trigger: "blur"
          }
        ],
        academy: [
          {
            required: true,
            message: "学院不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    updatePersonInfo(person_info) {
      this.$refs[person_info].validate(valid => {
        if (valid) {
          let URL = `${apiPath}/studentupdate`;

          this.loading = true;
       
          axios({
            url: URL,
            method: "get",
            params: {
              studentNum: this.studentNum,
              age: this.person_info.age,
              sex: this.person_info.sex
            }
          })
            .then(res => {
             
              this.disable_impl = !this.disable_impl;

              this.loading = false;
            })
            .catch(err => {
              // this.$Message.warning({ content: `出错，提示：${err}` });
              this.disable_impl = !this.disable_impl;
              this.loading = false;
            });
        } else {
          this.$Message.warning("个人信息填写不正确");
        }
      });
    },
    cancel(name) {
      // this.$refs[name].resetFields();
      this.person_info = this.person_temp;
      this.disable_impl = !this.disable_impl;
    },
    save() {
      localStorage.setItem("name", person_info.name);
    },

    Change() {
      this.disable_impl = !this.disable_impl;
      this.person_temp = this.person_info;
    },

    loadData() {
      let studentNum = localStorage.getItem("userId");
      let URL = `${apiPath}/studentmessage`;
      axios({
        url: URL,
        method: "get",
        params: {
          studentNum: studentNum
        }
      })
        .then(res => {
          console.log(res);
          this.person_info.studentName = res.data.studentName;
          this.person_info.studentNum = res.data.studentNum;
          this.person_info.age = res.data.age;
          this.person_info.academy = res.data.academyName;
          this.person_info.sex = res.data.sex;
        })
        .catch(err => {
          // this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  mounted() {
    this.studentNum = localStorage.getItem("userId");
    this.loadData();
  }
};
</script>

<style lang="scss" scoped>
@import "message";
</style>
<style>
.ivu-btn-primary {
  color: #fff;

  border-color: rgba(216, 218, 218, 0.4);
}
</style>
