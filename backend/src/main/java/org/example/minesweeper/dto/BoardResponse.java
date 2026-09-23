package org.example.minesweeper.dto;

import org.example.minesweeper.domain.*;

import java.util.*;

public record BoardResponse (
        UUID gameId,
        int width,
        int height,
        GameStatus status,
        List<CellResponse> cells
) {
    public static BoardResponse of(UUID gameId, Board board) {
        List<CellResponse> cellResponses = new ArrayList<>();

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                cellResponses.add(CellResponse.from(x, y, board.getCell(x, y)));
            }
        }

        return new BoardResponse(
                gameId,
                board.getWidth(),
                board.getHeight(),
                board.getStatus(),
                cellResponses
        );
    }
}
