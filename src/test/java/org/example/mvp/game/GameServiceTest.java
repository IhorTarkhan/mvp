package org.example.mvp.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.example.mvp.game.exception.InvalidGameFormatException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class GameServiceTest {
  @Mock SportGameServiceFactory sportGameServiceFactory;
  @InjectMocks GameService gameService;

  @Test
  void process_basketballFile_callBasketballInFactory() {
    when(sportGameServiceFactory.getSportGameService(Sport.BASKETBALL)).thenReturn(mock(SportGameService.class));

    gameService.calculate("BASKETBALL\n");

    verify(sportGameServiceFactory, times(1)).getSportGameService(Sport.BASKETBALL);
  }

  @Test
  void process_handballFile_callHandballInFactory() {
    when(sportGameServiceFactory.getSportGameService(Sport.HANDBALL)).thenReturn(mock(SportGameService.class));

    gameService.calculate("HANDBALL\n");

    verify(sportGameServiceFactory, times(1)).getSportGameService(Sport.HANDBALL);
  }

  @Test
  void process_invalidFile_throwException() {
    InvalidGameFormatException exception = assertThrowsExactly(InvalidGameFormatException.class, () -> gameService.calculate("ABC\n"));
    assertEquals("Unknown sport: ABC", exception.getMessage());
  }
}
