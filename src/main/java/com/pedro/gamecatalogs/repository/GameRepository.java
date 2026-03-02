package com.pedro.gamecatalogs.repository;

import com.pedro.gamecatalogs.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public class GameRepository  extends JpaRepository<Game, Integer> {
}
