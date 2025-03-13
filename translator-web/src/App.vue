<script setup>
import { RouterView } from 'vue-router';
</script>

<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">
        <a class="navbar-brand" href="/">Translator System</a>
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="/documents">Documents</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/translators">Translators</a>
          </li>
        </ul>
      </div>
    </nav>
    <div class="container mt-3">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
