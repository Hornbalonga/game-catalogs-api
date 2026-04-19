package com.pedro.gamecatalogs.service;

import com.pedro.gamecatalogs.dtos.GameRequestDto;
import com.pedro.gamecatalogs.dtos.GameResponseDto;
import com.pedro.gamecatalogs.exception.ResourceNotFoundException;
import com.pedro.gamecatalogs.model.Game;
import com.pedro.gamecatalogs.repository.GameRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    
    private final GameRepository repository;
    
    public GameResponseDto create(GameRequestDto dto) {
        
        Game game = new Game();
        game.setName(dto.getName());
        game.setPlatform(dto.getPlatform());
        game.setGenre(dto.getGenre());
        game.setStatus(dto.getStatus());
        game.setRating(dto.getRating());
        game.setYearPlayed(dto.getYearPlayed());
        game.setObservation(dto.getObservation());
        
        Game saved = repository.save(game);
        return mapToResponse(saved);
        
    }


    public List<GameResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Game findEntityId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com id " + id));
    }


    public GameResponseDto findById(Long id) {
        Game game =findEntityId(id);

        return mapToResponse(game);
    }

    public GameResponseDto update(Long id, GameRequestDto dto) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com id: " + id));

        game.setName(dto.getName());
        game.setPlatform(dto.getPlatform());
        game.setGenre(dto.getGenre());
        game.setStatus(dto.getStatus());
        game.setRating(dto.getRating());
        game.setYearPlayed(dto.getYearPlayed());
        game.setObservation(dto.getObservation());

        Game updated = repository.save(game);
        return mapToResponse(updated);
    }

    public void delete(Long id) {
        Game game = findEntityId(id);
        repository.delete(game);
    }


    private GameResponseDto mapToResponse(Game game) {
        return GameResponseDto.builder()
                .id(game.getId())
                .name(game.getName())
                .platform(game.getPlatform())
                .genre(game.getGenre())
                .status(game.getStatus())
                .rating(game.getRating())
                .yearPlayed(game.getYearPlayed())
                .observation(game.getObservation())
                .createdAt(game.getCreatedAt())
                .build();
    }

}
