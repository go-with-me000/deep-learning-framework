<template>
  <Layout style="height:100vh">
    <Header class="header">
      <img
        src="../../common/images/login/logo-white.png"
        style="width:170px;position:relative;top:10%;left:10%"
      />
    </Header>
    <Content id="content">
      <div id="theBg"></div>
      <div class="login">
        <div class="filter">
          <div class="SysTitle">
            <div id="title">登录/login</div>

            <div class="login-box">
              <Input
                v-model="loginForm.username"
                style="margin-bottom:25px;wdith:100%"
                size="large"
                prefix="md-person"
                placeholder="请输入账号/Campus ID Number"
              />
              <Input
                v-model="loginForm.password"
                type="password"
                style="margin-bottom: 25px"
                prefix="md-lock"
                size="large"
                placeholder="请输入密码/Password"
              />

              <Button
                size="large"
                type="primary"
                long
                @click="handleOnClickLogin()"
                @keyup.enter="handleAddBook"
                ghost
              >登录/login</Button>
            </div>
          </div>
        </div>
      </div>
    </Content>
    <Footer class="footer">
      <p class="message">
        信息服务电话：66133370 技术支持 上海大学信息化工作办公室
        <br />Technical support: IT Office of Shanghai University Tel:66133370
        <br />地址：上海市宝山区上大路99号 邮编：200444 Address: 99 Shangda Rd. Shanghai, China. 200444.
        <br />Copyright © 2012-2020 Shanghai University, All Rights Reserved.
      </p>
    </Footer>
    <Spin size="large" fix v-if="spin"></Spin>
  </Layout>
</template>

<script>
import axios from "axios";
export default {
  name: "login",
  data() {
    return {
      title: "14324",
      loginForm: {
        username: "陈科宇",
        password: "123",
        identity: ""
      },
      identity: {
        isControler: false,
        isCounter: false,
        isVoter: false,
        isDepartmentControler: false
      },
      spin: false
    };
  },
  methods: {
    keyDown(e) {
      //如果是回车则执行登录方法
      if (e.keyCode == 13) {
        this.handleOnClickLogin();
      }
    },
    handleOnClickLogin() {
     
          this.spin = false;

          // this.user();

          localStorage.setItem("userId", this.loginForm.username);
          if (1) {
            this.$router.push({ path: "/select/model" });
          } else {
            this.$router.push({ path: "/control/courses" });
          }
      
    },
    user() {
      this.$axios({
        url: `${apiPath}/student/one`,
        method: "get",
        params: {
          studentNum: this.loginForm.username
        }
      })
        .then(res => {
          console.log(res)
          localStorage.setItem("userName", res.data.studentName);
        })
        .catch(err => {
          // this.$Notice.warning({ title: `err` });
        });
    }
  },
  mounted() {
    if (this.$route.query.hasOwnProperty("redirect")) {
      this.$Notice.warning({ title: "请先登录!" });
    }
    window.addEventListener("keydown", this.keyDown);
  },
  created() {
    // this.keyupEnter();
  },
  destroyed() {
    window.removeEventListener("keydown", this.keyDown, false);
  }
};
</script>

<style scoped lang="scss">
@import "./login.scss";
</style>

<style lang="scss">
.login-box {
  .ivu-input {
    // color: #f8f8f9ab;
  }
}

.ivu-select-selection {
  background-color: rgba(19, 15, 15, 0) !important;
}

.ivu-select-selected-value {
  background-color: transparent;
  // color: #f8f8f9ab;
}

.ivu-select-arrow {
  // color: #f8f8f9ab;
}
</style>
<style lang="scss">
.footer {
  padding-top: 10px;
  position: relative;
  p {
    position: relative;
    top: -10px;
  }
  .ivu-layout-footer {
  }
}
.login-box {
  .ivu-btn-ghost.ivu-btn-primary {
    background-color: #275085;
    border-radius: 8px;
  }
  .ivu-btn-ghost.ivu-btn-primary {
    color: #fff;
  }
  .ivu-input-with-prefix {
    background-color: #fff;
  }
  .ivu-input .ivu-input-large .ivu-input-with-prefix {
    background: #fff;
  }
  .ivu-input {
    background: #fff;
  }
}
.button {
  .ivu-input {
    background-color: #275085;
    color: #fff;
  }
}
#theBg {
  background: url("../../common/images/login/login-bg.jpg") no-repeat;
  background-size: "100%";
  height: calc(100vh - 175px);
  background-size: 100% 100%;

  // position: fixed;
  width: 100%;
}
</style>
