package org.example.minesweeper.dto;

import jakarta.validation.constraints.*;

public record GameCreateRequest(
        @NotNull(message = "가로 크기는 필수입니다.")
        @Min(value = 1, message = "가로 크기는 최소 1 이상이어야 합니다.")
        int width,

        @NotNull(message = "세로 크기는 필수입니다.")
        @Min(value = 1, message = "세로 크기는 최소 1 이상이어야 합니다.")
        int height,

        @NotNull(message = "지뢰 개수는 필수입니다.")
        @Min(value = 0, message = "지뢰 개수는 0 이상이어야 합니다.")
        int mineCount
) {

    public static GameCreateRequest custom(int width, int height, int mineCount) {
        return new GameCreateRequest(width, height, mineCount);
    }

    // 필요한 경우 난이도별 정적 팩토리 메서드 추가 가능 (선택)
    public static GameCreateRequest easy() {
        return new GameCreateRequest(9, 9, 10);
    }
}
