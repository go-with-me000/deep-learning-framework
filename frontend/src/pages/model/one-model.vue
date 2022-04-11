<template>
  <div class="tabs">
    <div class="title">模型：{{data.model_name}}</div>
    <div class="basic">
      <Tag type="dot" color="primary" size="large" style="margin-right:15px">
        <span class="basic-item">神经网络：</span>
        {{data.network}}
      </Tag>

      <Tag type="dot" color="success" size="large" style="margin-right:15px;">
        <span class="basic-item">数据集：</span>
        {{data.dataset_name}}
      </Tag>
      <Tag type="dot" color="warning" size="large">
        <span class="basic-item">准确率：</span>
        {{data.accuracy}}
      </Tag>
    </div>
    <div class="desc">
      <div class="desc-title">
        <Icon type="ios-bookmark-outline" />
        <span style="margin-left:8px;">模型概述</span>
      </div>
      <div class="description">{{data.description}}</div>
    </div>
    <div class="img">
      <img :src="data.pic" style="width: 250px;height:250px;margin-right:80px;margin-top:20px;" />
    </div>

    <div class="train">
      <div class="desc-title">
        <Icon type="logo-buffer" />
        <span style="margin-left:8px;">模型训练</span>
      </div>
      <Row>
        <Col span="6">
          <div class="train-input">
            <Input v-model="epoch" placeholder="输入轮数" style="width: 200px" />
          </div>
          <div class="train-bottom">
            <Button type="primary" ghost size="large" long @click="train();getnumber()">训练</Button>
          </div>
          <div class="train-bottom">
            <Button type="warning" ghost size="large" long @click="test()">测试</Button>
          </div>
        </Col>
        <Col span="18" offset="0">
          <div id="main" style="width: 980px;height:400px;"></div>
        </Col>
      </Row>
    </div>
    <div class="test">
      <div class="desc-title">
        <Icon type="ios-bonfire-outline" />
        <span style="margin-left:8px;">模型测试</span>
      </div>
      <Row>
        <Col span="10">
          <div class="upload">
            <el-upload
              class="upload-demo"
              accept=".jpg, .jpeg, .png, .gif, .bmp, .pdf, .JPG, .JPEG, .PBG, .GIF, .BMP, .PDF"
              :action="fileURL"
              :on-remove="handleRemove"
              list-type="picture"
              name="pic"
              :on-success="handleAvatarSuccess"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </div>
          <div class="test-res" v-if="already_val">测试结果：{{result}}</div>
        </Col>
        <Col span="14" offset="2">
          <!-- <div class="imgs">
            <img src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar" />
          </div>-->
        </Col>
      </Row>
    </div>

    <Modal v-model="modal1" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>训练完成</span>
      </p>
      <div style="margin-bottom:-15px;">
        <div style="text-align:center">
          <p>最佳轮次：{{train_res.best_epoch+1}}</p>
          <p style="font-weight:bold;margin-top:10px;">accuracy：{{train_res.best_acc}}</p>
        </div>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" ghost @click="cancel()" style="margin-right:0vh;">确定</Button>
      </div>
    </Modal>
    <Modal v-model="modal2" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>模型测试</span>
      </p>
      <div style="margin-bottom:-15px;">
        <div style="text-align:center">
          <p>accuracy</p>
          <p style="font-weight:bold;margin-top:10px;">{{test_acc}}</p>
        </div>
      </div>
      <div slot="footer" style="text-align:center">
        <Button type="primary" size="large" ghost @click="cancel2()" style="margin-right:0vh;">确定</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  data() {
    return {
      already_val: false,
      result: "",
      test_acc: "",
      modal1: false,
      modal2: false,
      model_id: "",
      data: {},
      myChart: "",
      option: "",
      interval: "",
      epoch: 1,
      nowEpoch: 0,
      train_res: 0,
      fileURL: `${apiPath}/file/uploadss`,
      pic_name: "",
      pic_pre_path: "/static/images/avatar/"
    };
  },
  mounted() {
    this.loadData();
    this.init();
  },
  methods: {
    //新增时图片上传调用，uploadobj:页面传入的文件对象，limitfilessize:限定文件的大小
    handleAvatarSuccess(res, file) {
      this.pic_name = res;
      this.vals();
    },
    handleRemove(file, fileList) {
      // console.log(file, fileList);
      this.already_val = false;
    },
    vals() {
      let URL = `${apiPath}/test`;
      let neural=0
      // console.log(this.pic_name, this.data.model_name, this.data.dataset_name);
      if (this.data.network == "ResNet18") {
        neural = 1;
      } else {
        neural = 2;
      }
      axios({
        url: URL,
        method: "get",
        params: {
          pic: this.pic_name,
          model_name: this.data.model_name + ".mdl",
          dataset_name: this.data.dataset_name,
          network: neural
        }
      })
        .then(res => {
          // console.log(res);
          this.result = res.data.output;
          this.already_val = true;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    cancel() {
      this.modal1 = false;
    },
    cancel2() {
      this.modal2 = false;
    },
    test() {
      let URL = `${apiPath}/train`;
      let neural=0
      if (this.data.network == "ResNet18") {
        neural = 1;
      } else {
        neural = 2;
      }
      axios({
        url: URL,
        method: "get",
        params: {
          isTrain: 0,
          isFirst: 0,
          epoch: 0,
          network: neural,
          model_name: this.data.model_name + ".mdl",
          dataset_name: this.data.dataset_name
        }
      })
        .then(res => {
          // console.log(res);
          res.data.test_acc = 100 * res.data.test_acc;
          res.data.test_acc = parseInt(res.data.test_acc);
          this.test_acc = res.data.test_acc;
          this.modal2 = true;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    train() {
      // console.log(111);
      // this.option.series[0].data = [85, 87, 91, 95, 82, 80, 74, 64];
      // this.myChart.setOption(this.option);
      this.model_id = localStorage.getItem("model_id");
      let neural = 0;
      // console.log(this.data.model_name);
      if (this.data.network == "ResNet18") {
        neural = 1;
      } else {
        neural = 2;
      }
      let URL = `${apiPath}/train`;
      axios({
        url: URL,
        method: "get",
        params: {
          isTrain: 1,
          isFirst: 1,
          network: neural,
          epoch: this.epoch,
          model_name: this.data.model_name + ".mdl",
          dataset_name: this.data.dataset_name
        }
      })
        .then(res => {
          // console.log(res);

          res.data.best_acc = 100 * res.data.best_acc;
          res.data.best_acc = parseInt(res.data.best_acc);
          this.train_res = res.data;
          this.update_res();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    update_res() {
      let model_id = localStorage.getItem("model_id");
      // console.log(model_id, this.train_res);
      let URL = `${apiPath}/update_res`;
      axios({
        url: URL,
        method: "get",
        params: {
          model_id: model_id,
          train_res: this.train_res.best_acc
        }
      })
        .then(res => {
          // console.log(res);
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    init() {
      this.option = {
        xAxis: {
          data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
          name: "epoch"
        },
        yAxis: {
          min: 60,
          max: 100,
          name: "accuracy"
        },
        series: [
          {
            data: [],
            type: "line",
            areaStyle: {}
          }
        ]
      };
      this.myChart = echarts.init(document.getElementById("main"));

      this.myChart.setOption(this.option);
    },
    getnumber() {
      var that = this;
      this.interval = setInterval(function() {
        let URL = `${apiPath}/model/getnumbers`;
        axios({
          url: URL,
          method: "get"
        })
          .then(res => {
            // console.log(res);
            let len = res.data.key.length;
            that.nowEpoch = len;
            that.option.series[0].data = res.data.key;
            that.myChart.setOption(that.option);
            console.log(len)
            console.log(that.epoch)
            if (len == that.epoch) {
              // console.log("finished");
              that.modal1 = true;
              clearInterval(that.interval);
            }
          })
          .catch(err => {
            this.$Notice.warning({ title: `出错，提示：${err}` });
          });
      }, 10000);
    },
    loadData() {
      this.model_id = localStorage.getItem("model_id");
      let URL = `${apiPath}/model/one`;
      axios({
        url: URL,
        method: "get",
        params: {
          model_id: this.model_id
        }
      })
        .then(res => {
          // console.log(res);
          res.data.model_name = res.data.model_name.replace(".mdl", "");
          res.data.pic = this.pic_pre_path + res.data.pic;
          this.data = res.data;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    }
  },
  watch: {
    nowEpoch(newVal, oldVal) {
     
    }
  }
};
</script>

<style lang="scss" >
@import "model.scss";
</style>