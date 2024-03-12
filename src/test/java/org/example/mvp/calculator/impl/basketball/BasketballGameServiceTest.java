package org.example.mvp.calculator.impl.basketball;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.example.mvp.calculator.Sport;
import org.example.mvp.calculator.bean.PlayerGameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;


@MockitoSettings
class BasketballGameServiceTest {
  @InjectMocks private BasketballGameService basketballResultCalculator;

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
            new PlayerGameResult("nick1", "player 1", 29L, false),
            new PlayerGameResult("nick2", "player 2", 10L, false),
            new PlayerGameResult("nick3", "player 3", 44L, false),
            new PlayerGameResult("nick4", "player 4", 40L, true),
            new PlayerGameResult("nick5", "player 5", 22L, true),
            new PlayerGameResult("nick6", "player 6", 26L, true));

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      actualResult = basketballResultCalculator.calculate(reader);
    }

    assertEquals(expectedResult, actualResult);
  }
}
