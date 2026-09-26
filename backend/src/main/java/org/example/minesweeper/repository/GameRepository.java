package org.example.minesweeper.repository;

import org.example.minesweeper.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {
    private final Map<UUID, Board> store = new ConcurrentHashMap<>();

    public UUID save(Board board) {
        UUID gameId = UUID.randomUUID();
        store.put(gameId, board);
        return gameId;
    }

    public Optional<Board> findById(UUID gameId) {
        return Optional.ofNullable(store.get(gameId));
    }

    public void deleteById(UUID gameId) {
        store.remove(gameId);
    }

    public void clear() {
        store.clear();
    }
}
