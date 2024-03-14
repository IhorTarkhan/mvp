package org.example.mvp.game.impl;

import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MockitoSettings
class SportGameCalculatorTest {
  @InjectMocks SportGameCalculator<PlayerStatisticBeanTestImpl> sportGameCalculator;

  @BeforeEach
  void setUp() {}

  @Test
  void getWinnerTeam_correctGame_getResult() {
    List<PlayerStatisticBeanTestImpl> game =
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 40L, 80L));

    assertEquals(
        "team 2",
        sportGameCalculator.getWinnerTeam(game, PlayerStatisticBeanTestImpl::getTeamRatingScore));
  }

  @Test
  void getWinnerTeam_draw_throwsException() {
    List<PlayerStatisticBeanTestImpl> game =
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 10L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 10L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 10L, 80L));

    InvalidGameFormatException exception =
        assertThrowsExactly(
            InvalidGameFormatException.class,
            () ->
                sportGameCalculator.getWinnerTeam(
                    game, PlayerStatisticBeanTestImpl::getTeamRatingScore));
    assertEquals("Game must have a winner team", exception.getMessage());
  }

  @Test
  void getPlayersGameResult_correctGame_getResult() {
      List<PlayerStatisticBeanTestImpl> game =
              List.of(
                      new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
                      new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 10L, 60L),
                      new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 10L, 70L),
                      new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 10L, 80L));

        List<PlayerGameResult> expectedResult =
                List.of(
                        new PlayerGameResult("nick1", "player 1", 1L, 50L, false),
                        new PlayerGameResult("nick2", "player 2", 2L, 60L, false),
                        new PlayerGameResult("nick3", "player 3", 3L, 70L, true),
                        new PlayerGameResult("nick4", "player 4", 4L, 80L, true));
      assertEquals(expectedResult, sportGameCalculator.getPlayersGameResult(game, "team 2", PlayerStatisticBeanTestImpl::getPayerRatingScore));

  }
}
