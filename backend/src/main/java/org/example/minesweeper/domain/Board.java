package org.example.minesweeper.domain;
import java.util.*;

public class Board {
    private final int width;
    private final int height;
    private final int numberOfMine;
    private boolean isInitialized = false;
    private final Cell[][] cells;

    Random random = new Random();

    public Board(int width, int height, int numberOfMine) {
        validateBoardSizeAndMineCount(width, height, numberOfMine);
        this.width = width;
        this.height = height;
        this.numberOfMine = numberOfMine;

        this.cells = new Cell[height][width];
    }

    private void validateBoardSizeAndMineCount(int width, int height, int numberOfMine) {
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("가로, 세로의 길이는 1 이상이어야 합니다.");
        }

        if(numberOfMine < 1 || numberOfMine >= width * height) {
            throw new IllegalArgumentException("지뢰의 개수는 1 이상, 전체 셀 개수(" + width * height + ") 미만이어야 합니다.");
        }
    }

    public Cell getCell(int x, int y)  {
        return this.cells[y][x];
    }

    public void openCell(int x, int y) {
        if(!isInitialized) {
            initCells(x, y);
            isInitialized = true;
        }

        if(!cells[y][x].isOpen()) {
            cells[y][x].open();
        }
    }

    private void initCells(int x, int y) {  // The coordinate of the first clicked cell (Shouldn't have the mine)
        Set<Integer> minePositions = generateMinePositionExcluding(x, y);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(minePositions.contains(j * width + i)) {
                    cells[j][i] = new Cell(true);
                }
                else {
                    cells[j][i] = new Cell(false);
                }
            }
        }
    }

    private Set<Integer> generateMinePositionExcluding(int x, int y) {
        Set<Integer> minePositions = new HashSet<>();

        int i = 0;

        while(i < numberOfMine) {
            int newValue = random.nextInt(width * height);

            if(minePositions.add(newValue)) { // if added successfully
                if(minePositions.contains(y * width + x)) { // if the newValue indicates the first-clicked cell
                    minePositions.remove(newValue);
                }
                else {
                    i += 1;
                }
            }
        }

        return minePositions;
    }
}
