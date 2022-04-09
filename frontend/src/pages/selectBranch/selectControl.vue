
<template>
  <div class="layout">
    <Layout style="height: 100vh;">
      <Header
        :style="{background: 'rgb(31, 105, 243)', height: '70px',boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}"
        class="layout-header-bar"
      >
        <Row justify="space-between">
          <Col span="8">
            <Row>
              <Col span="4">
                <img src="@/common/images/logo.png" style="position:relative;top:3px" />
              </Col>
              <Col span="20" class="titles">
                <div>上海大学一体化选课系统</div>
              </Col>
            </Row>
          </Col>
            <Col span="2" offset="14">
            <Button type="primary" size="large" @click="logout">
              注销
              <Icon type="ios-arrow-forward"></Icon>
            </Button>
          </Col>
        </Row>
      </Header>
      <Layout>
        <Sider :width="180" style="background: #fff;">
          <div class="info">工号：{{id}}</div>
          <div class="info">身份：管理员</div>

          <Menu width="100%" :active-name="fullPath" accordion style="margin-top:10px">
            <MenuItem name="courses" to="/control/courses">
              <Icon type="ios-paper" />
              <span>开课管理</span>
            </MenuItem>
            <MenuItem name="students" to="/control/students">
              <Icon type="md-body" />
              <span>学生管理</span>
            </MenuItem>
            <MenuItem name="teachers" to="/control/teachers">
              <Icon type="ios-body-outline" />
              <span>老师管理</span>
            </MenuItem>
            <MenuItem name="choosecourses" to="/control/choosecourses">
              <Icon type="md-bookmarks" />
              <span>选课管理</span>
            </MenuItem>
           

            <MenuItem name="message" to="/control/message">
              <Icon type="md-person" />
              <span>个人信息</span>
            </MenuItem>
          </Menu>
        </Sider>
        <Content :style="{margin: '15px', background: '#f9fafc'}" class="main;margin-top:-20px;">
          <GeminiScrollbar class="my-scroll-bar">
            <router-view></router-view>
          </GeminiScrollbar>
        </Content>
        <!--页面部分-->
      </Layout>
      <!--右边部分-->
    </Layout>
  </div>
</template>
<script>
export default {
  data() {
    return {
      title: "",
      username: "",
      type: "",
      openname: "",
      identity: "",
      fullPath: "",
      id:"",
    };
  },

  methods: {
    initIdentity() {
      this.fullPath = this.$route.name;
    },
    logout() {
      sessionStorage.clear();
      localStorage.clear();

      this.$router.push("/login");

      localStorage.setItem("logout", "comfirm");
    }
  },

  computed: {},

  mounted() {
    this.id = localStorage.getItem("userId");
    this.initIdentity();
  },

  watch: {
    $route() {
      this.initIdentity();
    }
  }
};
</script>
<style scoped lang="scss">
@import "./select";
</style>

