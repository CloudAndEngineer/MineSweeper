package org.example.minesweeper.service;

import org.example.minesweeper.dto.BoardResponse;
import org.example.minesweeper.dto.CellActionRequest;
import org.example.minesweeper.dto.CellResponse;
import org.example.minesweeper.dto.GameCreateRequest;
import org.example.minesweeper.exception.GameNotFoundException;
import org.example.minesweeper.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameServiceTest {
    private GameRepository gameRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameRepository = new GameRepository();
        gameService = new GameService(gameRepository);
    }

    @Test
    @DisplayName("새로운 게임을 생성하면 GameRepository에 등록되고 BoardResponse가 반환된다.")
    public void createGame() {
        GameCreateRequest request = new GameCreateRequest(10, 10, 10);

        BoardResponse response = gameService.createGame(request); // Get information about the board you try to create

        assertThat(response.gameId()).isNotNull();
        assertThat(response.width()).isEqualTo(10);
        assertThat(response.height()).isEqualTo(10);
        assertThat(gameRepository.findById(response.gameId())).isPresent();
    }

    @Test
    @DisplayName("특정 위치의 Cell을 좌클릭하면 타일 상태가 업데이트된다.")
    public void openCell() {
        BoardResponse createdGame = gameService.createGame(new GameCreateRequest(10, 10, 10));
        UUID gameId = createdGame.gameId();
        CellActionRequest request = new CellActionRequest(4, 6);

        BoardResponse updatedGame = gameService.openCell(createdGame.gameId(), request);

        assertThat(updatedGame.gameId()).isEqualTo(gameId);

        boolean result = updatedGame.cellResponses().stream()
                .filter(cell -> cell.x() == 4 && cell.y() == 6)
                        .anyMatch(CellResponse::isOpen);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("깃발을 꽂으면 BoardResponse에서 remainingFlags가 한 개 줄어야 한다.")
    public void toggleFlag() {
        BoardResponse createdGame = gameService.createGame(new GameCreateRequest(9, 9, 79));

        BoardResponse updatedGame = gameService.openCell(createdGame.gameId(), new CellActionRequest(0, 0));

        BoardResponse flaggedGame = gameService.toggleFlag(updatedGame.gameId(), new CellActionRequest(4, 4));

        assertThat(flaggedGame.remainingFlags()).isEqualTo(78);
    }

    @Test
    @DisplayName("존재하지 않는 gameId로 요청 시 GameNotFoundException이 발생한다.")
    public void findByNonExistentId() {
        UUID nonExistentId = UUID.randomUUID();

        assertThatThrownBy(() -> gameService.getGame(nonExistentId))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining(nonExistentId.toString());
    }
}
