package org.example.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {
    @Test
    @DisplayName("지뢰 개수가 전체 칸 수 이상이면 IllegalArgumentException이 발생해야 한다.")
    public void createBoardWithInvalidMineCount() {
        int width = 3;
        int height = 3;
        int mineCount = 9; // 모든 칸이 지뢰로 이루어진 Board 생성을 시도하면 예외가 반환된다.

        assertThatThrownBy(() -> new Board(width, height, mineCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("지뢰의 개수");
    }

    @Test
    @DisplayName("Board의 가로, 세로 길이가 1 미만이면 IllegalArgumentException이 발생해야 한다.")
    public void createBoardWithInvalidLength() {
        int width = 0;
        int height = 1;
        int mineCount = 0;

        assertThatThrownBy(() -> new Board(width, height, mineCount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가로, 세로");
    }

    @Test
    @DisplayName("Board를 생성하고 첫 번째 클릭을 마친 이후에 모든 칸이 숫자 또는 지뢰인 Cell로 채워져야 한다.")
    public void everyCellFilled() {
        Board board = new Board(5, 5, 10);
        board.openCell(4, 2);

        assertThat(board.getAllCells())
                .allSatisfy(cell -> assertThat(cell).isNotNull());
    }

    @Test
    @DisplayName("첫 번째로 클릭하는 타일은 지뢰가 아니어야 한다.")
    public void initialCellNotMine() {
        Board board = new Board(5, 5, 10);
        board.openCell(3, 3); // 처음 openCell을 호출하면 Board가 초기화된다.

        assertThat(board.getCell(3, 3).isMine()).isFalse(); // 지뢰가 아니어야 한다.
    }

    @Test
    @DisplayName("Board를 생성할 때 자정한 지뢰 개수와 실제 지뢰 개수가 같아야 한다.")
    public void numberOfMinesEqualToActualMines() {
        int numberOfMine = 10;
        Board board = new Board(5, 5, numberOfMine);
        board.openCell(1, 2);

        assertThat(board.getAllCells())
                .filteredOn(Cell::isMine)
                .hasSize(numberOfMine);
    }

    @Test
    @DisplayName("숫자 타일인 경우 표시된 숫자는 0~8이어야 한다.")
    public void numberIsEqualToAdjacentMines() {
        Board board = new Board(5, 5, 10);
        board.openCell(3, 4);

        assertThat(board.getAllCells())
                .allSatisfy(cell -> assertThat(cell.getAdjacentMineCount()).isBetween(0, 8));
    }

    @Test
    @DisplayName("주변 지뢰 개수가 0인 타일을 클릭하면 연쇄적으로 주변 타일이 모두 열린다")
    void recursiveOpen() {
        // Given: 특정 좌표에만 지뢰가 설치된 Board 준비 (또는 첫 클릭으로 0이 확정되는 고정 상황)
        Board board = new Board(3, 3, 0); // 지뢰 0개 설정

        // When: (1, 1) 중심 타일 클릭
        board.openCell(1, 1);

        // Then: 3x3 전체 Cell(9개)이 모두 isOpen() == true 상태여야 함

        assertThat(board.getAllCells())
                .hasSize(9)
                .allSatisfy(cell -> assertThat(cell.isOpen()).isTrue());
    }
}
