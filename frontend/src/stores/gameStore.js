import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useGameStore = defineStore('game', () => {
    // 반응형 상태
    const board = ref([]);
    const gameId = ref(null);
    const gameStatus = ref('IN_PROGRESS');
    const remainingFlags = ref(0);

    // 난이도 설정 (기본값: EASY)
    const mode = ref('EASY'); // 'EASY' | 'MEDIUM' | 'HARD' | 'CUSTOM'
    const width = ref(9);
    const height = ref(9);
    const mineCount = ref(10);

    const API_BASE_URL = 'http://localhost:8080/api/v1/games';

    // 난이도 변경 핸들러
    const setDifficulty = (selectedMode) => {
        mode.value = selectedMode;
        if (selectedMode === 'EASY') {
            width.value = 9;
            height.value = 9;
            mineCount.value = 10;
        } else if (selectedMode === 'MEDIUM') {
            width.value = 16;
            height.value = 16;
            mineCount.value = 40;
        } else if (selectedMode === 'HARD') {
            width.value = 30;
            height.value = 16;
            mineCount.value = 99;
        }
    };

    // 응답 데이터로 보드 상태 업데이트
    const updateBoard = (data) => {
        if (!data) return;

        gameId.value = data.gameId || data.id;
        gameStatus.value = data.status;

        if (data.remainingFlags !== undefined) {
            remainingFlags.value = data.remainingFlags
        }

        const w = data.width || width.value;
        const cells = data.cellResponses || [];

        if (w && cells.length > 0) {
            const formattedBoard = [];
            for (let i = 0; i < cells.length; i += w) {
                formattedBoard.push(cells.slice(i, i + w));
            }
            board.value = [...formattedBoard]; // 렌더링 강제를 위한 참조 갱신
        }
    };

    // 새 게임 시작
    const startNewGame = async () => {
        try {
            const response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                // 선택된 가로, 세로, 지뢰 개수를 백엔드 DTO 필드명에 맞게 전달
                body: JSON.stringify({
                    width: Number(width.value),
                    height: Number(height.value),
                    mineCount: Number(mineCount.value)
                })
            });

            if (!response.ok) throw new Error('게임 생성 실패');

            const data = await response.json();
            updateBoard(data);
        } catch (error) {
            console.error('새 게임 시작 에러:', error);
        }
    };

    // 셀 오픈
    const openCell = async (x, y) => {
        if (!gameId.value || gameStatus.value === 'ENDED' || gameStatus.value === 'WON') return;

        try {
            const response = await fetch(`${API_BASE_URL}/${gameId.value}/open`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ x, y })
            });

            if (!response.ok) throw new Error('셀 오픈 실패');

            const data = await response.json();
            updateBoard(data);
        } catch (error) {
            console.error('셀 오픈 에러:', error);
        }
    };

    // 깃발 토글
    const toggleFlag = async (x, y) => {
        if (!gameId.value || gameStatus.value === 'ENDED' || gameStatus.value === 'WON') return;

        try {
            const response = await fetch(`${API_BASE_URL}/${gameId.value}/flag`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ x, y })
            });

            if (!response.ok) throw new Error('깃발 토글 실패');

            const data = await response.json();
            updateBoard(data);
        } catch (error) {
            console.error('깃발 토글 에러:', error);
        }
    };

    return {
        board,
        gameId,
        gameStatus,
        remainingFlags,
        mode,
        width,
        height,
        mineCount,
        setDifficulty,
        startNewGame,
        openCell,
        toggleFlag
    };
});