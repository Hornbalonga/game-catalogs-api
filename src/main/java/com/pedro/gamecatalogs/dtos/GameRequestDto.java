package com.pedro.gamecatalogs.dtos;

import com.pedro.gamecatalogs.model.GameStatus;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class GameRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3,max = 100)
    String name;

    @NotBlank(message = "Plataforma é obrigatória")
    String platform;

    @NotBlank(message = "Gênero é obrigatório")
    String genre;

    @NotBlank(message = "Status é obrigatório")
    GameStatus status;

    @DecimalMin("0.0") @DecimalMax("10.0")
    BigDecimal rating;

    @Min(1970)  @Max(2026)
    Integer yearPlayed;

    @Size(max = 500)
    String observation;


}
