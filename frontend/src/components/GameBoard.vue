<template>
  <div class="game-board-container" v-if="gameStore.board">
    <!-- 게임 상태 및 난이도 선택/새 게임 컨트롤 -->
    <div class="game-header">
      <div class="status">
        상태: <strong>{{ gameStore.statusText }}</strong>
      </div>
      <button class="btn-new-game" @click="gameStore.startNewGame">
        새 게임
      </button>
    </div>

    <!-- 지뢰찾기 격자 보드 -->
    <div
        class="board-grid"
        :style="gridStyle"
        @contextmenu.prevent
    >
      <Cell
          v-for="cell in gameStore.board.cells"
          :key="`${cell.x}-${cell.y}`"
          :cell="cell"
          @click="gameStore.openCell(cell.x, cell.y)"
          @right-click="gameStore.toggleFlag(cell.x, cell.y)"
      />
    </div>
  </div>

  <div v-else class="loading">
    게임을 생성하는 중...
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useGameStore } from '../stores/gameStore';
import Cell from './Cell.vue';

const gameStore = useGameStore();

// 보드 크기에 따른 CSS Grid 동적 속성
const gridStyle = computed(() => {
  if (!gameStore.board) return {};
  return {
    display: 'grid',
    gridTemplateColumns: `repeat(${gameStore.board.width}, 36px)`,
    gridTemplateRows: `repeat(${gameStore.board.height}, 36px)`,
    gap: '2px'
  };
});

// 마운트 시 기본 게임 자동 생성
onMounted(() => {
  if (!gameStore.board) {
    gameStore.startNewGame();
  }
});
</script>

<style scoped>
.game-board-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px;
  background-color: #f4f4f5;
  border-radius: 12px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  width: fit-content;
  margin: 0 auto;
}

.game-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 16px;
}

.status {
  font-size: 16px;
}

.btn-new-game {
  padding: 8px 16px;
  font-weight: bold;
  background-color: #3b82f6;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-new-game:hover {
  background-color: #2563eb;
}

.board-grid {
  background-color: #71717a;
  padding: 4px;
  border-radius: 6px;
}

.loading {
  text-align: center;
  padding: 40px;
  color: #71717a;
}
</style>