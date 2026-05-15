package com.pedro.gamecatalogs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.gamecatalogs.dtos.GameRequestDto;
import com.pedro.gamecatalogs.dtos.GameResponseDto;
import com.pedro.gamecatalogs.model.GameStatus;
import com.pedro.gamecatalogs.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private GameService service;

    @Test
    void createGame_ValidData_ReturnsCreated() throws Exception {
        GameRequestDto request = new GameRequestDto();
        request.setName("The Witcher 3");
        request.setPlatform("PC");
        request.setGenre("RPG");
        request.setStatus(GameStatus.IN_PROGRESS);
        request.setRating(BigDecimal.valueOf(9.5));
        request.setYearPlayed(2025);

        GameResponseDto response = GameResponseDto.builder()
                .id(1L)
                .name("The Witcher 3")
                .platform("PC")
                .genre("RPG")
                .status(GameStatus.IN_PROGRESS)
                .rating(BigDecimal.valueOf(9.5))
                .yearPlayed(2025)
                .observation("Muito bom")
                .createdAt(Instant.now())
                .build();

        when(service.create(any(GameRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("The Witcher 3"));
    }

    @Test
    void getAllGames_ReturnsOk() throws Exception {
        GameResponseDto response = GameResponseDto.builder()
        .id(1L)
        .name("The Witcher 3")
        .build();

        when(service.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("The Witcher 3"));
    }

    @Test
    void getGameById_ReturnsOk() throws Exception {
        GameResponseDto response =  GameResponseDto.builder()
        .id(1L)
        .name("The Witcher 3")
        .build();

        when(service.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("The Witcher 3"));
    }

    @Test
    void updateGame_ReturnsOk() throws Exception {
        GameRequestDto request = new GameRequestDto();
        request.setName("Updated Witcher");
        request.setPlatform("PC");
        request.setGenre("RPG");
        request.setStatus(GameStatus.IN_PROGRESS);

        GameResponseDto response =  GameResponseDto.builder()
        .id(1L)
        .name("Updated Witcher")
        .build();

        when(service.update(eq(1L), any(GameRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Witcher"));
    }

    @Test
    void deleteGame_ReturnsNoContent() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isNoContent());
    }
}