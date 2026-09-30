<template>
  <div id="app">
    <header class="app-header">
      <h1 class="title">💣 Minesweeper</h1>
      <p class="subtitle">Spring Boot 3.4 & Vue 3 로 제작된 지뢰찾기</p>
    </header>

    <main class="app-content">
      <GameBoard />
    </main>
  </div>
</template>

<script setup>
import GameBoard from './components/GameBoard.vue';
</script>

<style>
/* 전역 스타일 리셋 및 기본 레이아웃 */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, system-ui, Roboto, sans-serif;
  background-color: #e4e4e7;
  color: #18181b;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

#app-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 32px 16px;
}

.app-header {
  text-align: center;
}

.app-header .title {
  font-size: 32px;
  font-weight: 800;
  color: #27272a;
}

.app-header .subtitle {
  font-size: 14px;
  color: #71717a;
  margin-top: 4px;
}

.app-content {
  width: 100%;
}
</style>