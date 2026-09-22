package org.example.minesweeper.domain;

public class Cell {
    private int adjacentMineCount = 0;
    private boolean isOpen = false;
    private boolean isMineCountSet = false;
    private boolean flag = false;
    private final boolean mine;

    public Cell(boolean Mine) {
        this.mine = Mine;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isFlagged() {
        return this.flag;
    }

    public boolean isMine() {
        return this.mine;
    }

    public int getAdjacentMineCount() {
        return this.adjacentMineCount;
    }

    public void open() {
        if(!flag) {
            isOpen = true;
        }
    }

    public void toggleFlag() {
        if(!isOpen) {
            flag = !flag;
        }
    }

    public void setMineCount(int adjacentMineCount) {
        if(!isMineCountSet) {
            this.adjacentMineCount = adjacentMineCount;
            isMineCountSet = true;
        }
    }
}
