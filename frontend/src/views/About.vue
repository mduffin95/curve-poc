<template>
  <div class="about">
    <h1>This is an about page</h1>
    <Scatter/>
    <WebSocketDemo/>
  </div>
</template>

<script>
// @ is an alias to /src
// import Dashboard from '@/components/Dashboard.vue'

import Scatter from '@/components/Scatter.vue'
import WebSocketDemo from '@/components/WebSocketDemo.vue'

export default {
  name: 'about',
  components: {
    Scatter, WebSocketDemo
  }
}
</script>
