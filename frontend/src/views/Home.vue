<template>
  <div class="home">
    <Scatter/>
  </div>
</template>

<script>
// @ is an alias to /src
// import Dashboard from '@/components/Dashboard.vue'
// import WebSocketDemo from '@/components/WebSocketDemo.vue'
import Scatter from '@/components/Scatter.vue'

export default {
  name: 'home',
  components: {
    Scatter
  }
}
</script>
