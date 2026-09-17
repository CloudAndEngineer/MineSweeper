package org.example.minesweeper.domain;

public class Cell {
    private int numberOfMines;
    private boolean isOpen = false;
    private boolean flag = false;
    private boolean mine;

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isFlagged() {
        return this.flag;
    }

    public boolean isMine() {
        return this.mine;
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
}
