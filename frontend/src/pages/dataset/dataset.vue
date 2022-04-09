<template>
  <div class="tables">
    <div class="buttons">
      <Button type="success" @click="refresh">刷新</Button>
    </div>
    <div class="tables2">
      <Table border :columns="columns" :data="data">
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="show(index)">修改简介</Button>
        </template>
      </Table>
    </div>

     <Modal v-model="modal1" width="25%" :closable="false" :styles="{top: '100px'}">
      <p slot="header" style="color:#000;text-align:center;font-size:22px">
        <span>设置简介</span>
      </p>
      <div style="margin-bottom:-15px;">
        <Form ref="form" :model="form" :label-width="100">
         
          <FormItem label="项目简介">
            <Input
              v-model="description"
              type="textarea"
              :autosize="{minRows: 3,maxRows: 5}"
              placeholder="请写下对数据集的介绍"
            />
          </FormItem>
        </Form>
      </div>
      <div slot="footer" style="text-align:center">
        <Button
          type="primary"
          size="large"
          @click="modal1=false;change()"
          style="margin-right:1vh;"
        >确定</Button>
        <Button type="primary" size="large" ghost @click="cancel()" style="margin-right:0vh;">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      columns: [
        {
          type: "index",
          width: 100,
          align: "center",
          title: "number"
        },
        {
          title: "模型名",
          key: "name",
          width: 200,
          align: "center"
        },
        {
          title: "描述",
          key: "description",
          align: "center"
        },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      data: [],
      modal1:false,
      description:'',
      nowIndex:'',
      form: {
        newProjectName: "",
        oldProjectName: ""
      },
    };
  },
  mounted() {
    this.init();
    this.loadData();
  },
  methods: {
    refresh(){
       this.init();
    this.loadData();
    },
    init() {
      let URL = `${apiPath}/dataset/get`;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    loadData() {
      let URL = `${apiPath}/alldata`;
      axios({
        url: URL,
        method: "get"
      })
        .then(res => {
          console.log(res);
          this.data = res.data;
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    change(){
     let URL = `${apiPath}/dataset/change`;
     let name = this.data[this.nowIndex].name
     
      axios({
        url: URL,
        method: "get",
        params:{
          name:name,
          description:this.description
        }
      })
        .then(res => {
          console.log(res);
          this.loadData();
        })
        .catch(err => {
          this.$Notice.warning({ title: `出错，提示：${err}` });
        });
    },
    cancel(){
      this.modal1=false;
    },
    show(index){

      this.nowIndex = index;
      this.modal1=true;
      this.description = this.data[index].description

    }
  }
};
</script>
<style lang="scss" >
@import "dataset.scss";
</style>