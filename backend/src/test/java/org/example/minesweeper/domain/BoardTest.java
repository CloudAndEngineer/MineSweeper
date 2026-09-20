package org.example.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {
    Random random = new Random();

    @Test
    @DisplayName("지뢰 개수가 전체 칸 수를 넘어가면 IllegalArgumentException이 발생해야 한다.")
    public void createBoardWithInvalidMineCount() {
        int width = 3;
        int height = 3;
        int mineCount = 9; // 모든 칸이 지뢰로 이루어진 Board 생성을 시도하면 예외가 반환된다.

        assertThatThrownBy(() -> new Board(width, height, mineCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지뢰의 개수");
    }

    @Test
    @DisplayName("Board를 생성할 때 모든 칸이 숫자 또는 지뢰인 Cell로 채워져야 한다.")
    public void everyCellFilled() {
        Board board = new Board(5, 5, 10); // 5 * 5 Board를 생성하고 지뢰 개수를 10으로 설정하고
        board.openCell(random.nextInt(5), random.nextInt(5)); // 가로, 새로 좌표 0 ~ 4 중 하나를 클릭하면

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertThat(board.getCell(j, i)).isNotNull();
            }
        }
    }

    @Test
    @DisplayName("첫 번째로 클릭하는 타일은 지뢰가 아니어야 한다.")
    public void initialCellNotMine() {
        Board board = new Board(5, 5, 10);
        int x = random.nextInt(5);
        int y = random.nextInt(5);
        board.openCell(x, y);

        assertThat(board.getCell(x, y).isMine()).isFalse();
    }

    @Test
    @DisplayName("숫자 타일인 경우 표시된 숫자는 주변 칸의 지뢰 수가 같아야 한다.")
    public void numberIsEqualToAdjacentMines() {
    }
}
