package org.example.minesweeper.exception;

import java.util.UUID;

public class GameNotFoundException extends IllegalArgumentException {
    public GameNotFoundException(UUID gameId) {
        super("게임을 찾을 수 없습니다. (gameId: " + gameId + ")");
    }
}