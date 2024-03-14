package org.example.mvp.game.impl.handball;

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
class HandballGameServiceTest {
  @Mock SportGameParser<HandballPlayerStatisticBean> sportGameParser;
  @Mock SportGameValidator<HandballPlayerStatisticBean> sportGameValidator;
  @Mock SportGameCalculator<HandballPlayerStatisticBean> sportGameCalculator;
  @InjectMocks
  HandballGameService handballGameService;

  @BeforeEach
  void setUp() {}

  @Test
  void getSport_returnHandball() {
    assertEquals(Sport.HANDBALL, handballGameService.getSport());
  }

  @Test
  void calculate_anyString_callServices() {
    String content = "anyString";
    List<HandballPlayerStatisticBean> game = List.of();

    when(sportGameParser.getPlayersStatistic(HandballPlayerStatisticBean.class, content))
        .thenReturn(game);

    handballGameService.calculate(content);

    verify(sportGameParser, times(1))
        .getPlayersStatistic(HandballPlayerStatisticBean.class, content);
    verify(sportGameValidator, times(1)).validate(eq(game), any());
    verify(sportGameCalculator, times(1)).getWinnerTeam(eq(game), any());
    verify(sportGameCalculator, times(1)).getPlayersGameResult(eq(game), any(), any());
  }

  @Test
  void calculate_correctValidation() {
    String content = "anyString";

    doAnswer(
            invocation -> {
              Consumer<HandballPlayerStatisticBean> consumer = invocation.getArgument(1);
              List<HandballPlayerStatisticBean> argument = invocation.getArgument(0);
              argument.forEach(consumer);
              return null;
            })
            .when(sportGameValidator)
            .validate(anyList(), any());

    List<HandballPlayerStatisticBean> game =
        List.of(
            new HandballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", 10L, 20L));

    when(sportGameParser.getPlayersStatistic(HandballPlayerStatisticBean.class, content))
        .thenReturn(game);

    assertDoesNotThrow(() -> handballGameService.calculate(content));
  }

  @Test
  void calculate_wrongValidation() {
    String content = "anyString";
    List<HandballPlayerStatisticBean> game;
    InvalidGameFormatException exception;
    doAnswer(
            invocation -> {
              Consumer<HandballPlayerStatisticBean> consumer = invocation.getArgument(1);
              List<HandballPlayerStatisticBean> argument = invocation.getArgument(0);
              argument.forEach(consumer);
              return null;
            })
        .when(sportGameValidator)
        .validate(anyList(), any());

    game =
        List.of(
            new HandballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", null, 20L));
    when(sportGameParser.getPlayersStatistic(HandballPlayerStatisticBean.class, content))
        .thenReturn(game);
    exception =
        assertThrowsExactly(
            InvalidGameFormatException.class, () -> handballGameService.calculate(content));
    assertEquals("Player goalsMade must be set", exception.getMessage());

    game =
        List.of(
            new HandballPlayerStatisticBean("player 1", "nick1", 1L, "team 1", 10L, null));
    when(sportGameParser.getPlayersStatistic(HandballPlayerStatisticBean.class, content))
        .thenReturn(game);
    exception =
        assertThrowsExactly(
            InvalidGameFormatException.class, () -> handballGameService.calculate(content));
    assertEquals("Player goalsReceived must be set", exception.getMessage());
  }
}
