<template>
  <div>
    <!-- <el-upload
      class="upload-demo"
      action="auto"
      :http-request="uploadSectionFile"
      list-type="picture"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
    </el-upload>
    <el-button size="small" type="primary" @click="upload">提交</el-button>-->

    <el-upload
      class="upload-demo"
      
      accept=".jpg, .jpeg, .png, .gif, .bmp, .pdf, .JPG, .JPEG, .PBG, .GIF, .BMP, .PDF"
      :action="fileURL"
      name="pic"
      :on-success="handleAvatarSuccess"
    >
      <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      uploadFile: "",
      fileURL: `${apiPath}/file/uploadss`
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res);
    },
    uploadSectionFile(param) {
      let fileObj = param.file;

      const isLt2M = fileObj.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
        return;
      }
      console.log(fileObj.type);
      if (fileObj.type === "image/jpeg") {
        this.uploadFile = new File([fileObj], new Date().getTime() + ".jpg", {
          type: "image/jpeg"
        });
        console.log(111);
      } else if (fileObj.type === "image/png") {
        this.uploadFile = new File([fileObj], new Date().getTime() + ".png", {
          type: "image/png"
        });
      } else {
        this.$message.error("只能上传jpg/png文件");
        return;
      }
    },
    upload() {
      var param = new FormData(); // FormData 对象
      param.append("file", this.uploadFile); // 文件对象
      param.append("name", "ziguiyu"); // 其他参数
      this.$axios({
        method: "post",
        url: `${apiPath}/file/uploads`,
        data: param
      })
        .then(response => {
          this.$message({
            message: "上传成功",
            type: "success"
          });
        })
        .catch(error => {
          this.$message.error("上传失败,请稍后重试");
        });
    }
  }
};
</script>