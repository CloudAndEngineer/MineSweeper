package org.example.minesweeper.domain;
import java.util.*;
import java.util.function.Predicate;

public class Board {
    private GameStatus status = GameStatus.READY;
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

        if(numberOfMine < 0 || numberOfMine >= width * height) {
            throw new IllegalArgumentException("지뢰의 개수는 0 이상, 전체 셀 개수(" + width * height + ") 미만이어야 합니다.");
        }
    }

    public Cell getCell(int x, int y)  {
        return this.cells[y][x];
    }

    public List<Cell> getAllCells() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList(); // Java 16+ (Java 8~15는 .collect(Collectors.toList()))
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void openCell(int x, int y) {

        if(status.isFinished()) {
            return;
        }

        if(!isInitialized) {
            initCells(x, y);
            isInitialized = true;
        }

        if(!cells[y][x].isOpen()) {
            cells[y][x].open();

            if(cells[y][x].isMine()) {
                status = GameStatus.LOST;
                return;
            }

            if(cells[y][x].getAdjacentMineCount() == 0) {
                recursiveOpen(x, y);
            }

            long isMine = getAllCells().stream().filter(Cell::isMine).count();
            long isClosed = getAllCells().stream().filter(Predicate.not(Cell::isOpen)).count();

            if(isMine == isClosed) {
                status = GameStatus.WON;
            }
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

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[j][i].setMineCount(countAdjacentMines(i, j));
            }
        }

        status = GameStatus.IN_PROGRESS;
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

    // 주변 지뢰 개수 계산 예시
    private int countAdjacentMines(int x, int y) {
        // 8방향 오프셋 (상, 하, 좌, 우, 상좌, 상우, 하좌, 하우)
        int[] DX = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] DY = {-1, -1, -1, 0, 0, 1, 1, 1};

        int mineCount = 0;

        for (int i = 0; i < 8; i++) {
            int nx = x + DX[i];
            int ny = y + DY[i];

            // 1. 보드 범위를 벗어나지 않는지 검사
            if (isValidPosition(nx, ny)) {
                // 2. 안전함이 보장된 상태에서 접근
                if (cells[ny][nx].isMine()) {
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    private void recursiveOpen(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isValidPosition(i, j)) {
                    cells[j][i].open();
                }
            }
        }
    }
}
