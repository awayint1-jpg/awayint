<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'smartDoc',
  data() {
    return {
      url: 'http://doc.smart.vip/smart-vue'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>