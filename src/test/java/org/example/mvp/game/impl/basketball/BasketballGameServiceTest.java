package org.example.mvp.game.impl.basketball;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.function.Consumer;
import org.example.mvp.game.Sport;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.example.mvp.game.impl.SportGameCalculator;
import org.example.mvp.game.impl.SportGameParser;
import org.example.mvp.game.impl.SportGameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class BasketballGameServiceTest {
  @Mock SportGameParser<BasketballPlayerStatisticBean> sportGameParser;
  @Mock SportGameValidator<BasketballPlayerStatisticBean> sportGameValidator;
  @Mock SportGameCalculator<BasketballPlayerStatisticBean> sportGameCalculator;
  @InjectMocks BasketballGameService basketballGameService;

  @BeforeEach
  void setUp() {}

  @Test
  void getSport_returnBasketball() {
    assertEquals(Sport.BASKETBALL, basketballGameService.getSport());
  }

  @Test
  void calculate_anyString_callServices() {
    String content = "anyString";
    List<BasketballPlayerStatisticBean> game = List.of();

    when(sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content))
        .thenReturn(game);

    basketballGameService.calculate(content);

    verify(sportGameParser, times(1))
        .getPlayersStatistic(BasketballPlayerStatisticBean.class, content);
    verify(sportGameValidator, times(1)).validate(eq(game), any());
    verify(sportGameCalculator, times(1)).getWinnerTeam(eq(game), any());
    verify(sportGameCalculator, times(1)).getPlayersGameResult(eq(game), any(), any());
  }

  @Test
  void calculate_correctValidation() {
    String content = "anyString";

    doAnswer(
            invocation -> {
              Consumer<BasketballPlayerStatisticBean> consumer = invocation.getArgument(1);
              List<BasketballPlayerStatisticBean> argument = invocation.getArgument(0);
              argument.forEach(consumer);
              return null;
            })
            .when(sportGameValidator)
            .validate(anyList(), any());

    List<BasketballPlayerStatisticBean> game =
        List.of(
            new BasketballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", 10L, 20L, 30L));

    when(sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content))
        .thenReturn(game);

    assertDoesNotThrow(() -> basketballGameService.calculate(content));
  }

  @Test
  void calculate_wrongValidation() {
    String content = "anyString";
    List<BasketballPlayerStatisticBean> game;
    InvalidGameFormatException exception;
    doAnswer(
            invocation -> {
              Consumer<BasketballPlayerStatisticBean> consumer = invocation.getArgument(1);
              List<BasketballPlayerStatisticBean> argument = invocation.getArgument(0);
              argument.forEach(consumer);
              return null;
            })
        .when(sportGameValidator)
        .validate(anyList(), any());

    game =
        List.of(
            new BasketballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", null, 20L, 30L));
    when(sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content))
        .thenReturn(game);
    exception =
        assertThrowsExactly(
            InvalidGameFormatException.class, () -> basketballGameService.calculate(content));
    assertEquals("Player scoredPoints must be set", exception.getMessage());

    game =
        List.of(
            new BasketballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", 10L, null, 30L));
    when(sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content))
        .thenReturn(game);
    exception =
        assertThrowsExactly(
            InvalidGameFormatException.class, () -> basketballGameService.calculate(content));
    assertEquals("Player rebounds must be set", exception.getMessage());

    game =
        List.of(
            new BasketballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", 10L, 20L, null));
    when(sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content))
        .thenReturn(game);
    exception =
        assertThrowsExactly(
            InvalidGameFormatException.class, () -> basketballGameService.calculate(content));
    assertEquals("Player assists must be set", exception.getMessage());
  }
}
