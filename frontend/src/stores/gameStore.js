// gameStore.js
import {createGame} from "../api/gameApi.js";
import {defineStore} from "pinia";

export const useGameStore = defineStore('game', {
    state: () => ({
        board: null,
        loading: false,
        currentDifficulty: 'EASY',
        difficulties: {
            EASY: { width: 9, height: 9, mineCount: 10, label: '쉬움 (9x9, 지뢰 10개)' },
            MEDIUM: { width: 16, height: 16, mineCount: 40, label: '보통 (16x16, 지뢰 40개)' },
            HARD: { width: 30, height: 16, mineCount: 99, label: '어려움 (30x16, 지뢰 99개)' },
        }
    }),
    actions: {
        async startNewGame(difficultyKey = this.currentDifficulty) {
            this.loading = true;
            this.currentDifficulty = difficultyKey;
            const config = this.difficulties[difficultyKey];
            try {
                const response = await createGame(config.width, config.height, config.mineCount);
                this.board = response.data;
            } finally {
                this.loading = false;
            }
        }
    },
    // gameStore.js getters & actions 추가
    getters: {
        // 남은 지뢰 수 계산
        remainingMines: (state) => {
            if (!state.board) return 0;
            const flaggedCount = state.board.cells.filter(cell => cell.isFlagged).length;
            return state.board.mineCount - flaggedCount;
        }
    }
});