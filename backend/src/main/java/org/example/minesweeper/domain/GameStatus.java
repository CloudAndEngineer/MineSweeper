package org.example.minesweeper.domain;

public enum GameStatus {
    READY,
    IN_PROGRESS,
    WON,
    LOST;

    public boolean isFinished() {
        return this == WON || this == LOST;
    }

    public boolean isPlayable() {
        return this == READY || this == IN_PROGRESS;
    }
}
