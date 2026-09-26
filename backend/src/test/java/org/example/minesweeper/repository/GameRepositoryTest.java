package org.example.minesweeper.repository;

import org.example.minesweeper.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

public class GameRepositoryTest {
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        this.gameRepository = new GameRepository();
    }

    @Test
    @DisplayName("Board를 저장하면 고유 UUID가 발급된다.")
    public void saveBoard() {
        Board board = new Board(5, 5, 5);

        UUID gameId = gameRepository.save(board);

        assertThat(gameId).isNotNull();
    }

    @Test
    @DisplayName("발급된 UUID로 Board를 조회할 수 있다.")
    public void findById() {
        Board board = new Board(5, 5, 5);

        UUID gameId = gameRepository.save(board);

        Optional<Board> foundBoard = gameRepository.findById(gameId);

        assertThat(foundBoard).isPresent();
        assertThat(foundBoard.get()).isEqualTo(board);
    }

    @Test
    @DisplayName("발급되지 않은 UUID로 Board를 조회하면 빈 Optional을 반환한다.")
    public void findByNonExistentId() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<Board> foundBoard = gameRepository.findById(nonExistentId);

        assertThat(foundBoard).isEmpty();
    }
}
