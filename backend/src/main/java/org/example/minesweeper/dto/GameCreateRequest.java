package org.example.minesweeper.dto;

public record GameCreateRequest(int width, int height, int numberOfMine) {
}
