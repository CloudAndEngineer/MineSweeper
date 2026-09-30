import axios from 'axios';

// 환경 변수에서 Base URL을 로드하며, 설정이 없으면 기본 백엔드 주소를 사용합니다.
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1/games';

// Axios 인스턴스 생성
const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
    timeout: 5000, // 5초 타임아웃
});

// Response Interceptor: 공통 에러 핸들링
api.interceptors.response.use(
    (response) => response,
    (error) => {
        // 백엔드 GlobalExceptionHandler에서 전달한 ErrorResponse 메시지 파싱
        const errorMessage = error.response?.data?.message || '서버와의 통신에 실패했습니다.';
        console.error('[API Error]:', errorMessage);

        // 예외 메시지를 상위(Store)로 전달
        return Promise.reject(new Error(errorMessage));
    }
);

/**
 * 1. 새 게임 생성 요청
 * @param {number} width - 가로 크기
 * @param {number} height - 세로 크기
 * @param {number} mineCount - 지뢰 개수
 */
export const createGame = (width = 9, height = 9, mineCount = 10) => {
    return api.post('', { width, height, mineCount });
};

/**
 * 2. 게임 보드 조회 요청
 * @param {string} gameId - 게임 UUID
 */
export const getGame = (gameId) => {
    return api.get(`/${gameId}`);
};

/**
 * 3. 셀 열기 요청
 * @param {string} gameId - 게임 UUID
 * @param {number} x - X 좌표
 * @param {number} y - Y 좌표
 */
export const openCell = (gameId, x, y) => {
    return api.post(`/${gameId}/open`, { x, y });
};

/**
 * 4. 깃발 토글 요청
 * @param {string} gameId - 게임 UUID
 * @param {number} x - X 좌표
 * @param {number} y - Y 좌표
 */
export const toggleFlag = (gameId, x, y) => {
    return api.post(`/${gameId}/flag`, { x, y });
};

export default api;