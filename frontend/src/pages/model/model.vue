<template>
  <div>
    <div class="buttons">
      <Button type="success" size="large" @click="refresh">刷新</Button>
      <Button type="primary" size="large" @click="modal1=true">增加模型</Button>
    </div>
    <Row style="margin-top:40px" type="flex" justify="center" class="code-row-bg">
      <Col span="18">
        <List item-layout="vertical">
          <ListItem v-for="item in data" :key="item.title" class="lists">
            <div v-on:click="enter(item)">
              <div class="title">
                <span>{{item.model_name}}</span>
              </div>
              <div class="neural">{{item.description}}</div>
              <Row class="code-row-bg small-item">
                <Col span="6">神经网络：{{item.network}}</Col>
                <Col span="6" offset="0">数据集：{{item.dataset_name}}</Col>
                <Col span="6" offset="0 ">准确率：{{item.accuracy}}</Col>
                <Col span="6" offset="0 ">
                  <Button type="warning"  @click.stop="modal2=true;form1=item">修改模型</Button>
                  <Button type="error"  @click.stop="deleteM(item.model_id);">删除模型</Button>
                </Col>
              </Row>
            </div>
            <template slot="extra">
              <img :src="item.pic" style="width: 150px;margin-right:20px" />
            </template>
          </ListItem>
        </List>
      </Col>
    </Row>

    <Modal v-model="modal1" width="25" :styles="{top: '100px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>增加模型</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="form1" :model="form1" :label-width="100">
          <FormItem label="模型名称">
            <Input v-model="form1.model_name" />
          </FormItem>
          <FormItem label="数据集">
            <Select v-model="form1.dataset_name">
              <Option v-for="item in dataset" :value="item.name" :key="item.id">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="神经网络">
            <Select v-model="form1.network">
              <Option value="ResNet18">ResNet18</Option>
              <Option value="Lenet5">Lenet5</Option>
              <Option value="RNN">RNN</Option>
            </Select>
          </FormItem>
          <FormItem label="图片">
            <el-upload
              class="upload-demo"
              accept=".jpg, .jpeg, .png, .gif, .bmp, .pdf, .JPG, .JPEG, .PBG, .GIF, .BMP, .PDF"
              :action="fileURL"
              name="pic"
              :on-success="handleAvatarSuccess"
            >
              <el-button size="small" type="primary" long>点击上传</el-button>
            </el-upload>
          </FormItem>
          <FormItem label="简介">
            <Input
              v-model="form1.description"
              type="textarea"
              :autosize="{minRows: 4,maxRows: 5}"
              placeholder="请写入简介"
            ></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" @click="addModal()" style="margin-right:1vh;">增加</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="modal1 = false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>
    <Modal v-model="modal2" width="25" :styles="{top: '100px'}" class="smallModal">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>修改模型</span>
      </p>
      <div style="text-align:center;margin-bottom:-15px;">
        <Form ref="form1" :model="form1" :label-width="100">
          <FormItem label="模型名称">
            <Input v-model="form1.model_name" />
          </FormItem>
          <FormItem label="数据集">
            <Select v-model="form1.dataset_name">
              <Option v-for="item in dataset" :value="item.name" :key="item.id">{{ item.name }}</Option>
            </Select>
          </FormItem>
          <FormItem label="神经网络">
            <Select v-model="form1.network">
              <Option value="ResNet18">ResNet18</Option>
              <Option value="Lenet5">Lenet5</Option>
              <Option value="RNN">RNN</Option>
            </Select>
          </FormItem>
          <FormItem label="图片">
            <el-upload
              class="upload-demo"
              accept=".jpg, .jpeg, .png, .gif, .bmp, .pdf, .JPG, .JPEG, .PBG, .GIF, .BMP, .PDF"
              :action="fileURL"
              name="pic"
              :on-success="handleAvatarSuccess2"
            >
              <el-button size="small" type="primary" long>点击上传</el-button>
            </el-upload>
          </FormItem>
          <FormItem label="简介">
            <Input
              v-model="form1.description"
              type="textarea"
              :autosize="{minRows: 4,maxRows: 5}"
              placeholder="请写入简介"
            ></Input>
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" @click="changeModal()" style="margin-right:1vh;">修改</Button>
        <Button
          type="primary"
          size="large"
          ghost
          @click="modal2 = false"
          style="margin-right:0vh;"
        >取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      chaM:'',
      data: [],
      modal1: false,
      modal2: false,
      form1: {},
      fileURL: `${apiPath}/file/uploadss2`,

      pic_path: "",
      dataset: [],
      pic_pre_path: "/static/images/avatar/"
    };
  },
  mounted() {
    this.loadData();
    this.getDataset();
  },
  methods: {
    getDataset() {
      let URL = `${apiPath}/alldata`;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          this.dataset = res.data;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    handleAvatarSuccess(res, file) {
      this.pic_path = res;
      // console.log(res);
    },
    handleAvatarSuccess2(res, file) {
      this.pic_path = res;
      // console.log(res);
    },
    refresh() {
      //  this.init();
      this.loadData();
    },
    deleteM(model_id){
         let URL = `${apiPath}/model/delete`;
      axios({
        url: URL,
        method: "get",
        params: {
          model_id:model_id
        }
      })
        .then(res => {
          // console.log(res);
          
          this.$Notice.success({ title: `删除成功` });
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    addModal() {
      let URL = `${apiPath}/model/add`;
      axios({
        url: URL,
        method: "get",
        params: {
          user_id: 1,
          model_name: this.form1.model_name+'.mdl',
          dataset_name: this.form1.dataset_name,
          pic: this.pic_path,
          network: this.form1.network,
          description: this.form1.description
        }
      })
        .then(res => {
          // console.log(res);
          this.modal1 = false;
          this.$Notice.success({ title: `增加成功` });
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    changeModal() {
      let URL = `${apiPath}/model/update`;
      console.log(this.form1)
      axios({
        url: URL,
        method: "get",
        params: {
          model_id: this.form1.model_id,
          model_name:this.form1.model_name+'.mdl',
          description:this.form1.description,
          pic:this.pic_path
        }
      })
        .then(res => {
          console.log(res);
          this.modal2 = false;
          this.$Notice.success({ title: `修改成功` });
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    enter(item) {
      let id = item.model_id;
      localStorage.setItem("model_id", id);
      this.$router.push({
        path: `onemodel`
      });
    },
    loadData() {
      let URL = `${apiPath}/allmodal`;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          // console.log(res);

          let len = res.data.length;
          for (let i = 0; i < len; i++) {
            // this.data[i].pic = this.pic_pre_path+this.data[i].pic;
             res.data[i].model_name=res.data[i].model_name.replace(".mdl","")
            res.data[i].pic_name = res.data[i].pic
            res.data[i].pic = this.pic_pre_path + res.data[i].pic;
          }
          this.data = res.data;
        })
        .catch(err => {
          console.log(err);
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  watch: {
    modal1(val, oldVal) {
      if (!val) {
        this.form1.model_name = "";
        this.form1.dataset_name = "";
        this.form1.pic = "";
        this.form1.description = "";
        this.form1.network = "";
      }
    },
    modal2(val, oldVal) {
      if (!val) {
        this.form1.model_name = "";
        this.form1.dataset_name = "";
        this.form1.pic = "";
        this.form1.description = "";
        this.form1.network = "";
      }else{
        this.pic_path = this.form1.pic_name
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@import "model.scss";
</style>