<template>
  <div
      class="cell"
      :class="{ revealed: cell.isRevealed, flagged: cell.isFlagged }"
      @click="handleClick"
      @contextmenu.prevent="handleRightClick"
  >
    <!-- 셀 내부 표시 (깃발, 지뢰, 숫자 등) -->
    <span v-if="cell.isFlagged">🚩</span>
    <span v-else-if="cell.isRevealed && cell.isMine">💣</span>
    <span v-else-if="cell.isRevealed && cell.adjacentMines > 0">
      {{ cell.adjacentMines }}
    </span>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import Cell from './Cell.vue'

const props = defineProps({
  cell: {
    type: Object,
    required: true
  }
});

// API 연동 예시 함수
const onCellClick = (x, y) => {
  // 백엔드 Cell Open API 호출
}

const onCellFlag = (x, y) => {
  // 백엔드 Flag Toggle API 호출
}

defineEmits(['click', 'right-click']);

// 셀 상태에 따른 표시 텍스트/이모지
const cellContent = computed(() => {
  if (props.cell.isFlagged) return '🚩';
  if (!props.cell.isOpen) return '';
  if (props.cell.isMine) return '💣';
  return props.cell.nearMineCount > 0 ? props.cell.nearMineCount : '';
});

// 셀 상태에 따른 CSS 클래스
const cellClass = computed(() => ({
    'open': props.cell.isOpen,
    'mine': props.cell.isOpen && props.cell.isMine,
    'flagged': props.cell.isFlagged,
    [`number-${props.cell.nearMineCount}`]: props.cell.isOpen && !props.cell.isMine && props.cell.nearMineCount > 0
}));
</script>

<style scoped>
.cell {
  width: 36px;
  height: 36px;
  background-color: #e4e4e7;
  border: 1px solid #a1a1aa;
  font-size: 18px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0;
  border-radius: 4px;
  user-select: none;
  -webkit-user-drag: none;
}

.cell:hover:not(.open) {
  background-color: #d4d4d8;
}

.cell.open {
  background-color: #ffffff;
  border-color: #e4e4e7;
  cursor: default;
}

.cell.mine {
  background-color: #ef4444;
}

.cell.number-1 { color: blue; }
.cell.number-2 { color: green; }
.cell.number-3 { color: red; }
.cell.number-4 { color: purple; }
.cell.number-5 { color: orange; }
.cell.number-6 { color: cyan; }
.cell.number-7 { color: darkblue; }
.cell.number-8 { color: black; }
</style>