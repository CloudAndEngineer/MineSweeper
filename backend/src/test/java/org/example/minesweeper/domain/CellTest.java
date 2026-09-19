package org.example.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CellTest {
    @Test
    @DisplayName("타일을 처음 생성하면 닫힌 상태여야 한다.")
    void initialCellState() {
        Cell cell = new Cell(false); // 새로 생성된 셀은
        assertThat(cell.isOpen()).isFalse(); // 닫힌 상태여야 한다.
    }

    @Test
    @DisplayName("깃발이 없는 닫힌 타일을 좌클릭하면 열려야 한다.")
    void openClosedCell() {
        Cell cell = new Cell(false); // 새로 생성된 닫힌 셀을
        cell.open(); // 열려고 시도하면
        assertThat(cell.isOpen()).isTrue(); // 열린 상태여야 한다.
    }

    @Test
    @DisplayName("깃발이 있는 닫힌 타일을 좌클릭하면 닫힌 상태가 유지되어야 한다.")
    void doNotOpenFlaggedCell() {
        Cell cell = new Cell(false); // 닫힌 셀에서
        cell.toggleFlag(); // 깃발을 꽂고
        cell.open(); // 열려고 시도하면
        assertThat(cell.isOpen()).isFalse(); // 열리지 않아야 한다.
    }

    @Test
    @DisplayName("깃발이 없는 닫힌 타일을 우클릭하면 깃발이 꽂혀야 한다.")
    void markFlagOnNotFlaggedCell() {
        Cell cell = new Cell(false); // 깃발이 없는 타일을 생성한 뒤
        cell.toggleFlag(); // 깃발을 꽂으려고 시도하면
        assertThat(cell.isFlagged()).isTrue(); // 깃발이 꽂혀야 한다.
    }

    @Test
    @DisplayName("깃발이 있는 닫힌 타일을 우클릭하면 깃발이 없어져야 한다.")
    void removeFlagOnFlaggedCell() {
        Cell cell = new Cell(false); // 깃발이 없는 타일을 생성한 뒤
        cell.toggleFlag(); // 깃발을 꽂고
        cell.toggleFlag(); // 깃발을 뽑으려고 시도하면
        assertThat(cell.isFlagged()).isFalse(); // 깃발이 제거되어야 한다.
    }

    @Test
    @DisplayName("열린 타일을 좌클릭하면 아무 변화가 없어야 한다.")
    void doNothingOnOpenCell() {
        Cell cell = new Cell(false); // 닫힌 셀을 생성하고
        cell.open(); // 연 뒤
        cell.open(); // 다시 열려고 시도하면
        assertThat(cell.isOpen()).isTrue(); // 열린 상태가 유지되어야 한다.
    }

    @Test
    @DisplayName("열린 타일을 우클릭하면 깃발이 꽂히지 않아야 한다.")
    void doNotFlagOnOpenCell() {
        Cell cell = new Cell(false); // 닫힌 셀을 생성하고
        cell.open(); // 연 뒤 (이 시점에서 isFlagged == false)
        cell.toggleFlag(); // 깃발을 꽂으려고 시도하면
        assertThat(cell.isFlagged()).isFalse(); // 깃발이 꽂히지 않아야 한다.
    }
}
