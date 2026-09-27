package org.example.minesweeper.service;

import org.example.minesweeper.domain.Board;
import org.example.minesweeper.dto.BoardResponse;
import org.example.minesweeper.dto.CellActionRequest;
import org.example.minesweeper.dto.GameCreateRequest;
import org.example.minesweeper.exception.GameNotFoundException;
import org.example.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public BoardResponse createGame(GameCreateRequest request) {
        Board board = new Board(request.width(), request.height(), request.mineCount());
        UUID gameId = this.gameRepository.save(board);

        return BoardResponse.of(gameId, board);
    }

    public BoardResponse getGame(UUID gameId) {
        return BoardResponse.of(gameId, findBoardOrThrow(gameId));
    }

    public BoardResponse openCell(UUID gameId, CellActionRequest request) {
        Board board = findBoardOrThrow(gameId);

        board.openCell(request.x(), request.y()); // gameId에 해당하는 Board의 상태를 업데이트

        return BoardResponse.of(gameId, board);
    }

    public BoardResponse toggleFlag(UUID gameId, CellActionRequest request) {
        Board board = findBoardOrThrow(gameId);

        board.toggleFlag(request.x(), request.y());

        return BoardResponse.of(gameId, board);
    }

    private Board findBoardOrThrow(UUID gameId) { // Convert Optional<Board> to Board
        return this.gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }
}
