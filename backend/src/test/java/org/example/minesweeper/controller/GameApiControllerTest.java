package org.example.minesweeper.controller;

import tools.jackson.databind.ObjectMapper;
import org.example.minesweeper.MineSweeperApplication;
import org.example.minesweeper.domain.GameStatus;
import org.example.minesweeper.dto.BoardResponse;
import org.example.minesweeper.dto.GameCreateRequest;
import org.example.minesweeper.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.UUID;

@WebMvcTest(GameApiController.class)
@ContextConfiguration(classes = MineSweeperApplication.class)
public class GameApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GameService gameService;

    @Test
    @DisplayName("게임 생성 요청 시 201 Created 응답과 BoardResponse를 반환한다.")
    public void createGameSuccess() throws Exception {
        UUID gameId = UUID.randomUUID();
        GameCreateRequest request = GameCreateRequest.easy(); // 9, 9, 10
        BoardResponse response = new BoardResponse(
                gameId,
                9,
                9,
                GameStatus.READY,
                Collections.emptyList()
        );

        given(gameService.createGame(any(GameCreateRequest.class)))
                .willReturn(response);

        mockMvc.perform(post("/api/v1/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(gameId.toString()))
                .andExpect(jsonPath("$.width").value(9))
                .andExpect(jsonPath("$.height").value(9))
                .andExpect(jsonPath("$.status").value("READY"));
    }

    @Test
    @DisplayName("잘못된 데이터로 게임 생성을 요청하면 422 Unprocessable Content를 반환한다.")
    public void createGameValidationFailure() throws Exception {
        GameCreateRequest request = new GameCreateRequest(0, 1, 3); // malformed data: width

        mockMvc.perform(post("/api/v1/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("UNPROCESSABLE_CONTENT"));
    }
}

