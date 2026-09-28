package org.example.minesweeper.controller;

import jakarta.validation.Valid;
import org.example.minesweeper.dto.BoardResponse;
import org.example.minesweeper.dto.CellActionRequest;
import org.example.minesweeper.dto.GameCreateRequest;
import org.example.minesweeper.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/games")
public class GameApiController {
    private final GameService gameService;

    public GameApiController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<BoardResponse> createGame(@Valid @RequestBody GameCreateRequest request) {
        BoardResponse response = gameService.createGame(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } // @Valid @RequestBody: request의 데이터가 유효하지 않으면 MethodArgumentNotValidException이 발생하고, 자동으로 400 응답을 반환한다.

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getGame(@PathVariable UUID id) {
        BoardResponse response = gameService.getGame(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<BoardResponse> openCell(
            @PathVariable UUID id,
            @Valid @RequestBody CellActionRequest request
    ) {
        BoardResponse response = gameService.openCell(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/flag")
    public ResponseEntity<BoardResponse> toggleFlag(
            @PathVariable UUID id,
            @Valid @RequestBody CellActionRequest request
    ) {
        BoardResponse response = gameService.toggleFlag(id, request);
        return ResponseEntity.ok(response);
    }
}