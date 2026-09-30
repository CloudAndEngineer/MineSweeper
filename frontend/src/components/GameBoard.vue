<template>
  <div class="game-container">
    <div class="controls">
      <!-- 새 게임 버튼 이벤트 연결 -->
      <button class="new-game-btn" @click="startNewGame">새 게임</button>
      <div v-if="gameStatus" class="status">상태: {{ gameStatus }}</div>
    </div>

    <!-- 보드 영역 -->
    <div v-if="board.length > 0" class="board">
      <div v-for="(row, y) in board" :key="y" class="board-row">
        <Cell
            v-for="(cell, x) in row"
            :key="x"
            :cell="cell"
            :x="x"
            :y="y"
            @click="onCellClick"
            @right-click="onCellFlag"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Cell from './Cell.vue';

// 반응형 상태 정의
const board = ref([]);
const gameId = ref(null);
const gameStatus = ref('IN_PROGRESS');

// 백엔드 API 주소 (환경에 맞게 수정)
const API_BASE_URL = 'http://localhost:8080/api/v1/games';

// 새 게임 시작 함수
const startNewGame = async () => {
  try {
    // 1. 백엔드에 새 게임 생성 요청 (예: 10x10 보드, 지뢰 10개)
    const response = await fetch(API_BASE_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ width: 10, height: 10, mineCount: 10 })
    });

    if (!response.ok) {
      throw new Error('게임 생성 실패');
    }

    const data = await response.json();
    console.log('백엔드 응답 데이터:', data);

    // 2. 백엔드 응답 구조에 맞게 데이터 상태 갱신
    gameId.value = data.id;
    gameStatus.value = data.status || 'IN_PROGRESS';

    const width = data.width;
    const cells = data.cellResponses || [];

    // 2. 1차원 cellResponses 배열을 width 기준으로 2차원 배열로 변환
    const formattedBoard = [];
    for (let i = 0; i < cells.length; i += width) {
      formattedBoard.push(cells.slice(i, i + width));
    }

    board.value = formattedBoard;

  } catch (error) {
    console.error('새 게임 시작 중 오류 발생:', error);
    alert('새 게임을 시작할 수 없습니다. 백엔드 서버 상태를 확인해 주세요.');
  }
};

// 셀 좌클릭 (열기)
const onCellClick = async (x, y) => {
  if (!gameId.value) return;

  try {
    const response = await fetch(`${API_BASE_URL}/${gameId.value}/open`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ x, y })
    });
    const data = await response.json();

    board.value = data.board;
    gameStatus.value = data.status;
  } catch (error) {
    console.error('셀 오픈 실패:', error);
  }
};

// 셀 우클릭 (깃발)
const onCellFlag = async (x, y) => {
  if (!gameId.value) return;

  try {
    const response = await fetch(`${API_BASE_URL}/${gameId.value}/flag`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ x, y })
    });
    const data = await response.json();

    board.value = data.board;
  } catch (error) {
    console.error('깃발 토글 실패:', error);
  }
};

// 마운트 시 첫 게임 자동으로 생성
onMounted(() => {
  startNewGame();
});
</script>

<style scoped>
.game-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.controls {
  display: flex;
  gap: 12px;
  align-items: center;
}

.new-game-btn {
  padding: 8px 16px;
  font-size: 16px;
  font-weight: bold;
  background-color: #2563eb;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.new-game-btn:hover {
  background-color: #1d4ed8;
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