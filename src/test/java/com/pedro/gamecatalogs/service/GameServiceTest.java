package com.pedro.gamecatalogs.service;

import com.pedro.gamecatalogs.dtos.GameResponseDto;
import com.pedro.gamecatalogs.dtos.GameRequestDto;
import com.pedro.gamecatalogs.exception.ResourceNotFoundException;
import com.pedro.gamecatalogs.model.Game;
import com.pedro.gamecatalogs.model.GameStatus;
import com.pedro.gamecatalogs.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository repository;

    @InjectMocks
    private GameService service;


    @Test
    void create_ReturnsGameResponseDto() {
        GameRequestDto dto = new GameRequestDto();
        dto.setName("The Witcher 3");
        dto.setPlatform("PC");
        dto.setGenre("RPG");
        dto.setStatus(GameStatus.IN_PROGRESS);
        dto.setRating(BigDecimal.valueOf(9.5));
        dto.setYearPlayed(2025);
        dto.setObservation("Muito bom");

        Game savedGame = new Game();
        savedGame.setId(1L);
        savedGame.setName("The Witcher 3");
        savedGame.setPlatform("PC");
        savedGame.setGenre("RPG");
        savedGame.setStatus(GameStatus.IN_PROGRESS);
        savedGame.setRating(BigDecimal.valueOf(9.5));
        savedGame.setYearPlayed(2025);
        savedGame.setObservation("Muito bom");
        savedGame.setCreatedAt(Instant.now());

        when(repository.save(any(Game.class))).thenReturn(savedGame);

        GameResponseDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1l, result.getId());
        assertEquals("The Witcher 3", result.getName());
        verify(repository, times(1)).save(any(Game.class));

    }

    @Test
    void findAll_RetunsListOfGameResponseDto() {
        Game game = new Game();
        game.setName("The Witcher 3");
        game.setPlatform("PC");
        game.setGenre("RPG");
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setRating(BigDecimal.valueOf(9.5));
        game.setYearPlayed(2025);
        game.setObservation("Muito bom");
        game.setCreatedAt(Instant.now());

        when(repository.findAll()).thenReturn(List.of(game));

        List<GameResponseDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Witcher 3", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_RetunsGameResponseDto_WhenGameExists() {
        Game game = new Game();
        game.setId(1L);
        game.setName("The Witcher 3");
        game.setPlatform("PC");
        game.setGenre("RPG");
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setRating(BigDecimal.valueOf(9.5));
        game.setYearPlayed(2025);
        game.setObservation("Muito bom");
        game.setCreatedAt(Instant.now());

        when(repository.findById(1L)).thenReturn(Optional.of(game));

        GameResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1l, result.getId());
        assertEquals("The Witcher 3", result.getName());
        verify(repository, times(1)).findById(1L);

    }

    @Test
    void findById_ThrowsException_WhenGameDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows
                (ResourceNotFoundException.class,
                () -> service.findById(1L)
        );

        assertEquals("Jogo não encontrado com id 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void update_returnsUpdateGameResponseDto_WhenGameExists() {
        GameRequestDto dto = new GameRequestDto();
        dto.setName("Updated The Witcher 3");
        dto.setPlatform("PC");
        dto.setGenre("RPG");
        dto.setStatus(GameStatus.FINISHED);
        dto.setRating(BigDecimal.valueOf(10.0));
        dto.setYearPlayed(2025);
        dto.setObservation("Zerado");

        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setName("The Witcher 3");
        existingGame.setPlatform("PC");
        existingGame.setGenre("RPG");
        existingGame.setStatus(GameStatus.IN_PROGRESS);
        existingGame.setRating(BigDecimal.valueOf(9.5));
        existingGame.setYearPlayed(2024);
        existingGame.setObservation("Muito bom");
        existingGame.setCreatedAt(Instant.now());

        Game updatedGame = new Game();
        updatedGame.setId(1L);
        updatedGame.setName("Updated The Witcher 3");
        updatedGame.setPlatform("PC");
        updatedGame.setGenre("RPG");
        updatedGame.setStatus(GameStatus.FINISHED);
        updatedGame.setRating(BigDecimal.valueOf(10.0));
        updatedGame.setYearPlayed(2025);
        updatedGame.setObservation("Zerado");
        updatedGame.setCreatedAt(existingGame.getCreatedAt());

        when(repository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(repository.save(any(Game.class))).thenReturn(updatedGame);

        GameResponseDto result = service.update(1L, dto);

        //assertNotNull(result);
        assertEquals("Updated The Witcher 3", result.getName());
        assertEquals(GameStatus.FINISHED, result.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Game.class));


    }

    @Test
    void update_ThrowsException_WhenGameDoesNotExist() {
        GameRequestDto dto = new GameRequestDto();
        dto.setName("Updated Witcher");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(1L, dto)
        );

        assertEquals("Jogo não encontrado com id: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Game.class));
    }

    @Test
    void delete_RemovesGame_WhenGameExists() {
        Game game = new Game();
        game.setId(1L);
        game.setName("The Witcher 3");

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        doNothing().when(repository).delete(game);

        service.delete(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(game);
    }

    @Test
    void delete_ThrowsException_WhenGameDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.delete(1L)
        );

        assertEquals("Jogo não encontrado com id 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).delete(any(Game.class));
    }



}
