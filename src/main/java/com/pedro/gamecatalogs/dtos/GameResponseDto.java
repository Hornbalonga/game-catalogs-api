package com.pedro.gamecatalogs.dtos;

import com.pedro.gamecatalogs.model.GameStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Value
public class GameResponseDto {
    Long id;
    String name;
    String platform;
    String genre;
    GameStatus status;
    BigDecimal rating;
    Integer yearPlayed;
    String observation;
    Instant createdAt;


}
