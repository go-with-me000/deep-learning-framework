<template>
<!--  <Layout style="height:100vh">-->
<!--    <Header class="header">-->
<!--      <img-->
<!--        src="../../common/images/login/logo-white.png"-->
<!--        style="width:170px;position:relative;top:10%;left:10%"-->
<!--      />-->
<!--    </Header>-->
<!--      背景-->
<!--      <div id="theBg"></div>-->
<!--      登录框-->
  <div id="theBack">
<!--  <div id="theBack"></div>-->
      <div id="login-box">
        <h1 style="color:#b7eb8f">Login</h1>
        <div class="form">
          <div class="item">
            <i></i>
            <Input
              v-model="loginForm.username"
              prefix="md-person"
              placeholder="username"
            />
          </div>
          <div class="item">
            <i></i>
            <Input
              v-model="loginForm.password"
              type="password"
              prefix="md-lock"
              placeholder="password"
            />
          </div>
          <Button
            size="large"
            type="primary"
            class="btnStyle"
            @click="handleOnClickLogin()"
            @keyup.enter="handleAddBook"
            ghost
          >login</Button>
        </div>
      </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "login",
  data() {
    return {
      title: "14324",
      loginForm: {
        username: "",
        password: "",
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
    donothing() {

    },
    handleOnClickLogin() {
      // this.$axios({
      //   url: `${apiPath}/login`,
      //   method: "get",
      //   params: {
      //     account: this.loginForm.username,
      //     password: this.loginForm.password
      //   },
      //   timeout: 10000
      // })
      //   .then(res => {
      //     this.spin = false;
      //
      //     this.user();
      //
      //     localStorage.setItem("userId", this.loginForm.username);
      //     if (res.data.power == 0) {
      //       this.$router.push({ path: "/select/selection" });
      //     } else {
      //       this.$router.push({ path: "/control/courses" });
      //     }
      //   })
      //   .catch(err => {
      //     this.spin = false;
      //     this.$Notice.warning({ title: `账号密码出错` });
      //   });
      this.$axios({
        url: `${apiPath}/login`,
        method: "post",
        data: {
          username: this.loginForm.username,
          password: this.loginForm.password
        },
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(res => {
        this.spin = false;
        localStorage.setItem("userId", this.loginForm.username);

        if (res.data === "请检查账号或密码是否正确") {
          throw res.data;
        }

        if (res.data === "ROLE_USER") {
          this.user();
          this.$router.push({ path: "/select/selection" });
        } else {
          this.admin();
          this.$router.push({ path: "/control/courses" });
        }

      })
      .catch(err => {
        this.spin = false;
        this.$Notice.warning({ title: `账号密码出错` });
      })
    },
    user() {
      this.$axios({
        url: `${apiPath}/student/info`,
        method: "get",
        params: {
          xh: this.loginForm.username
        }
      })
        .then(res => {
          console.log(res)
          localStorage.setItem("userName", res.data.sname);
        })
        .catch(err => {
          this.$Notice.warning({ title: `err` });
        });
    },
    admin() {
      // 获取管理员个人信息
      this.$axios({
        url: `${apiPath}/ o`,
        method: "get",
        params: {
          gh: this.loginForm.username
        }
      }).then(res => {
        console.log('tname------', res.data.tname);
        localStorage.setItem("userName", res.data.tname);
      }).catch(err => {
        this.$Notice.warning({ title: `err` });
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
  #login-box {
    width: 30%;
    height: auto;
    border: 1px solid #000000;
    margin: 15% auto 0;
    text-align: center;
    background: #00000020;
    padding: 20px 50px;
  }
  .form {
    margin-top: 40px;
  }
  .item {
    width: 70%;
    border: 0;
    padding: 5px 10px;
    align-items: center;
    background: #ffffff00;
    color: #fff;
    margin-left: auto;
    margin-right: auto;
    margin-top: 15px;
  }
  .btnStyle {
    margin: 40px;
    width: 300px;
    height: 45px;
  }

  #theBack{
    background: url("../../common/images/login/background.jpg") no-repeat;
    background-size: 100% 100%; // 宽 高
  }

</style>
