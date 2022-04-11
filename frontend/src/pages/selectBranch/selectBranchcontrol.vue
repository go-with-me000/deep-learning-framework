
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
                <div>机器学习算法平台</div>
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
          <div class="info">user：{{studentNum}}</div>
          <!-- <div class="info">身份：学生</div> -->

          <Menu width="100%" :active-name="fullPath" accordion style="margin-top:10px">
            <MenuItem name="model" to="/select/model">
              <Icon type="ios-paper" />
              <span>模型库</span>
            </MenuItem>
            <MenuItem name="dataset" to="/select/dataset">
              <Icon type="md-bookmarks" />
              <span>数据集</span>
            </MenuItem>
           

            <!-- <MenuItem name="message" to="/select/test">
              <Icon type="md-person" />
              <span>个人信息</span>
            </MenuItem> -->
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
      studentNum:"",
      userName:"",
    };
  },

  methods: {
    
    logout() {
      sessionStorage.clear();
      localStorage.clear();

      this.$router.push("/login");

      localStorage.setItem("logout", "comfirm");
    }
  },

  computed: {},

  mounted() {
    this.studentNum = localStorage.getItem("userId");

    this.userName = localStorage.getItem("userName");
  
  },

  watch: {
   
  }
};
</script>
<style scoped lang="scss">
@import "./select";
</style>

