<template>
  <div id='scatter'/>
</template>

<script>
import { default as vegaEmbed } from 'vega-embed';
// import changeset from 'vega-dataflow';
import axios from 'axios';
import BarChartSpec from '@/assets/bar.vl.json';
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const vega = require('vega');

export default {
  data () {
    return {
      data: [],
      errors: [],
      connected: false
    }
  },
  methods: {
    embed() {
      vegaEmbed('#scatter', BarChartSpec).then(res => window.view = res.view);
    },
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { name: this.send_message };
        this.stompClient.send("/app/hello", JSON.stringify(msg), {});
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:8088/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, frame => {
        this.connected = true;
        this.stompClient.subscribe("/app/init"), msg => {
          console.log("INIT RECEIVED")
          this.data = JSON.parse(msg.body).values;
          let changeSet = vega.changeset().remove(() => true).insert(this.data);
          window.view.change('table', changeSet).run()
        }
        // console.log(frame);
        this.stompClient.subscribe("/topic/data", tick => {
          // console.log(tick);
          // this.received_messages.push(JSON.parse(tick.body).values);
          let data = JSON.parse(tick.body).values;
          console.log(this.data);
          let maturities = []
          for (var key in data) {
            maturities.push(data[key].maturityDate)
          }
          let changeSet = vega.changeset().remove(x => {
            maturities.includes(x.maturityDate)
          }).insert(data);
          window.view.change('table', changeSet).run()
        });
      }, error => {
        console.log(error);
        this.connected = false;
      });
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    }
  },
  mounted() {
    this.embed()
    this.connect()
  }
}


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
