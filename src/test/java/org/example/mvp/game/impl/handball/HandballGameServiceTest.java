package org.example.mvp.game.impl.handball;

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
class HandballGameServiceTest {
  @InjectMocks HandballGameService handballGameService;

  @BeforeEach
  void setUp() {}

  @Test
  void getSport_returnHandball() {
    assertEquals(Sport.HANDBALL, handballGameService.getSport());
  }

  @Test
  void calculate_positive_worksCorrectly() throws IOException {
    var path = Path.of("src/test/resources/set/handball/correct_game_1.csv");
    List<PlayerGameResult> actualResult;
    List<PlayerGameResult> expectedResult =
        List.of(
//            new PlayerGameResult("nick1", "player 1", 4L, -20L, true),
//            new PlayerGameResult("nick2", "player 2", 8L, 10L, true),
//            new PlayerGameResult("nick3", "player 3", 15L, 0L, true),
//            new PlayerGameResult("nick4", "player 4", 16L, -23L, false),
//            new PlayerGameResult("nick5", "player 5", 23L, -1L, false),
//            new PlayerGameResult("nick6", "player 6", 42L, -9L, false)
        );

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      actualResult = handballGameService.calculate("reader");
    }

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void calculate_invalidFormat_throwException() throws IOException {
    Path path;

    path = Path.of("src/test/resources/set/handball/invalid_game_format_1.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(InvalidGameFormatException.class, () -> handballGameService.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/handball/invalid_game_format_2.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(InvalidGameFormatException.class, () -> handballGameService.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/handball/invalid_game_format_3.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(InvalidGameFormatException.class, () -> handballGameService.calculate("reader"));
    }

    path = Path.of("src/test/resources/set/handball/invalid_game_format_4.csv");
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      reader.readLine();
      assertThrows(InvalidGameFormatException.class, () -> handballGameService.calculate("reader"));
    }
  }
}
