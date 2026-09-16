package org.example.minesweeper.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CellTest {
    @Test
    @DisplayName("타일을 처음 생성하면 닫힌 상태여야 한다.")
    void initialCellState() {
        Cell cell = new Cell();
        assertThat(cell.isOpen()).isFalse();
    }
}
