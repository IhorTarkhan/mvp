package org.example.mvp.game.impl.basketball;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.example.mvp.game.Sport;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class BasketballGameServiceTest {
  @InjectMocks BasketballGameService basketballResultCalculator;

  @BeforeEach
  void setUp() {}

  @Test
  void getSport_returnBasketball() {
    assertEquals(Sport.BASKETBALL, basketballResultCalculator.getSport());
  }

  @Test
  void calculate_positive_worksCorrectly() throws IOException {
    var path = Path.of("src/test/resources/set/basketball/correct_game_1.csv");
    List<PlayerGameResult> actualResult;
    List<PlayerGameResult> expectedResult =
        List.of(
//            new PlayerGameResult("nick1", "player 1", 4L, 29L, false),
//            new PlayerGameResult("nick2", "player 2", 8L, 10L, false),
//            new PlayerGameResult("nick3", "player 3", 15L, 44L, false),
//            new PlayerGameResult("nick4", "player 4", 16L, 40L, true),
//            new PlayerGameResult("nick5", "player 5", 23L, 22L, true),
//            new PlayerGameResult("nick6", "player 6", 42L, 26L, true)
        );

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      actualResult = basketballResultCalculator.calculate("reader");
    }

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void calculate_invalidFormat_throwException() throws IOException {
    Path path;

    path = Path.of("src/test/resources/set/basketball/invalid_game_format_1.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(
          InvalidGameFormatException.class, () -> basketballResultCalculator.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/basketball/invalid_game_format_2.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(
          InvalidGameFormatException.class, () -> basketballResultCalculator.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/basketball/invalid_game_format_3.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(
          InvalidGameFormatException.class, () -> basketballResultCalculator.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/basketball/invalid_game_format_4.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(
          InvalidGameFormatException.class, () -> basketballResultCalculator.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/basketball/invalid_game_format_5.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(
          InvalidGameFormatException.class, () -> basketballResultCalculator.calculate("reader"));
    }
  }
}
