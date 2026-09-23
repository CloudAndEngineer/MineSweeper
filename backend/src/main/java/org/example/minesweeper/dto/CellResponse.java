package org.example.minesweeper.dto;

import org.example.minesweeper.domain.Cell;

public record CellResponse(
        int x,
        int y,
        boolean isOpen,
        boolean isFlagged,
        boolean isMine,
        int adjacentMineCount
) {
    public static CellResponse from(int x, int y, Cell cell) {
        return new CellResponse(
                x,
                y,
                cell.isOpen(),
                cell.isFlagged(),
                cell.isMine() && cell.isOpen(), // return isMine() only when open
                cell.getAdjacentMineCount()
        );
    }
}
