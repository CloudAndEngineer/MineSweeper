<template>
  <div
      class="cell"
      :class="cellClass"
      @click="handleClick"
      @contextmenu.prevent="handleRightClick"
  >
    {{ cellContent }}
  </div>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  cell: {
    type: Object,
    required: true
  },
  x: {
    type: Number,
    required: true
  },
  y: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['click', 'right-click']);

// 좌클릭 이벤트 발생
const handleClick = () => {
  console.log("1. Cell.vue 클릭됨")
  emit('click', props.x, props.y);
};

// 우클릭 이벤트 발생
const handleRightClick = () => {
  emit('right-click', props.x, props.y);
};

// 셀 상태에 따른 표시 텍스트/이모지
const cellContent = computed(() => {
  if (props.cell.isFlagged) return '🚩';
  if (!props.cell.isOpen) return '';
  if (props.cell.isMine) return '💣';
  return props.cell.adjacentMineCount > 0 ? props.cell.adjacentMineCount : '';
});

// 셀 상태에 따른 CSS 클래스
const cellClass = computed(() => ({
  'open': props.cell.isOpen,
  'mine': props.cell.isOpen && props.cell.isMine,
  'flagged': props.cell.isFlagged,
  [`number-${props.cell.adjacentMineCount}`]: props.cell.isOpen && !props.cell.isMine && props.cell.adjacentMineCount > 0
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

.cell.number-1 { color: #2563eb; }
.cell.number-2 { color: #16a34a; }
.cell.number-3 { color: #dc2626; }
.cell.number-4 { color: #9333ea; }
.cell.number-5 { color: #ea580c; }
.cell.number-6 { color: #0891b2; }
.cell.number-7 { color: #1e3a8a; }
.cell.number-8 { color: #18181b; }
</style>