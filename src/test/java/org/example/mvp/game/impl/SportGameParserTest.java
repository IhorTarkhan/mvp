package org.example.mvp.game.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class SportGameParserTest {
  @InjectMocks SportGameParser<PlayerStatisticBeanTestImpl> sportGameParser;

  @BeforeEach
  void setUp() {}

  @Test
  void getPlayersStatistic_correctFile_readCorrectly() {
    String content =
        """
                player 1;nick1;1;team 1;10;50
                player 2;nick2;2;team 1;20;60
                player 3;nick3;3;team 2;30;70
                player 4;nick4;4;team 2;40;80
                """;
    List<PlayerStatisticBeanTestImpl> expectedResult =
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 40L, 80L));

    List<PlayerStatisticBeanTestImpl> actualResult =
        sportGameParser.getPlayersStatistic(PlayerStatisticBeanTestImpl.class, content);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void getPlayersStatistic_incorrectFile_throwsException() {
    String content = """
                player 1;nick1;abc;team 1;10;50
                """;

    InvalidGameFormatException exception =
        assertThrowsExactly(
            InvalidGameFormatException.class,
            () -> sportGameParser.getPlayersStatistic(PlayerStatisticBeanTestImpl.class, content));
    assertEquals("Can not read file to Beans", exception.getMessage());
  }
}
