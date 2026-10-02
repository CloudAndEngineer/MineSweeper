<template>
  <div class="game-container">
    <!-- 난이도 설정 및 컨트롤 영역 -->
    <div class="controls">
      <div class="difficulty-selector">
        <label>난이도: </label>
        <select v-model="gameStore.mode" @change="onModeChange">
          <option value="EASY">Easy (9x9, 지뢰 10)</option>
          <option value="MEDIUM">Medium (16x16, 지뢰 40)</option>
          <option value="HARD">Hard (30x16, 지뢰 99)</option>
          <option value="CUSTOM">사용자 지정</option>
        </select>
      </div>

      <!-- CUSTOM 선택 시 입력창 표시 -->
      <div v-if="gameStore.mode === 'CUSTOM'" class="custom-inputs">
        <input type="number" v-model="gameStore.width" placeholder="너비(가로)" min="5" max="50" />
        <input type="number" v-model="gameStore.height" placeholder="높이(세로)" min="5" max="50" />
        <input type="number" v-model="gameStore.mineCount" placeholder="지뢰 수" min="1" />
      </div>

      <div class="mine-count">💣 남은 지뢰: {{ gameStore.remainingFlags }}</div>
      <button class="new-game-btn" @click="gameStore.startNewGame">새 게임</button>
      <div v-if="gameStore.gameStatus" class="status">상태: {{ gameStore.gameStatus }}</div>
    </div>

    <!-- 보드 영역 -->
    <div v-if="gameStore.board?.length > 0" class="board">
      <div v-for="(row, y) in gameStore.board" :key="y" class="board-row">
        <Cell
            v-for="(cell, x) in row"
            :key="x"
            :cell="cell"
            :x="x"
            :y="y"
            @click="gameStore.openCell"
            @right-click="gameStore.toggleFlag"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useGameStore } from '../stores/gameStore'; // 👈 Store 경로 확인
import Cell from './Cell.vue';

// 1. GameStore 인스턴스 가져오기
const gameStore = useGameStore();

// 난이도 변경 핸들러
const onModeChange = (e) => {
  gameStore.setDifficulty(e.target.value);
};

// 마운트 시 게임 시작
onMounted(() => {
  gameStore.startNewGame();
});
</script>

<style scoped>
.game-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  padding: 20px;
}

.controls {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
  justify-content: center;
}

.custom-inputs input {
  width: 70px;
  padding: 4px 8px;
  margin-right: 4px;
}

.new-game-btn {
  padding: 8px 16px;
  background-color: #2563eb;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
}

.board {
  display: flex;
  flex-direction: column;
  gap: 2px;
  background-color: #71717a;
  padding: 4px;
  border-radius: 6px;
}

.board-row {
  display: flex;
  gap: 2px;
}
</style>