
<template>
  <div>
    <tcontrol></tcontrol>
  </div>
</template>
<script>
import axios from 'axios'
import tcontrol from './tcontrol.vue'
export default {
  components: { tcontrol },
  data() {
    return {
      title: '',
      username: '',
      projectName: ''
    }
  },
  methods: {
  },
  mounted() {
    this.username = localStorage.getItem('username')
    this.title = localStorage.getItem('projectName')
   
  }
}
</script>
<style scoped>
</style>
