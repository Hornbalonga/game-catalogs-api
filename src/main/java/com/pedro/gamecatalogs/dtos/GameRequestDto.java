package com.pedro.gamecatalogs.dtos;

import com.pedro.gamecatalogs.model.GameStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3,max = 100)
    private String name;

    @NotBlank(message = "Plataforma é obrigatória")
    private String platform;

    @NotBlank(message = "Gênero é obrigatório")
    private String genre;

    @NotNull(message = "Status é obrigatório")
    private GameStatus status;

    @DecimalMin("0.0") @DecimalMax("10.0")
    private BigDecimal rating;

    @Min(1970)  @Max(2026)
    private Integer yearPlayed;

    @Size(max = 500)
    private String observation;


}
