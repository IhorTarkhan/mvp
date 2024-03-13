package org.example.mvp.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.example.mvp.game.impl.basketball.BasketballGameService;
import org.example.mvp.game.impl.handball.HandballGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class GameServiceTest {
  @Mock BasketballGameService basketballGameService;
  @Mock HandballGameService handballGameService;
  @Spy ArrayList<SportGameService> sportGameServices;
  @InjectMocks GameService gameService;

  @BeforeEach
  void setUp() {
    sportGameServices.add(basketballGameService);
    sportGameServices.add(handballGameService);
  }

  @Test
  void process_basketballFile_callBasketballService() {
    when(basketballGameService.getSport()).thenReturn(Sport.BASKETBALL);

    BufferedReader reader = new BufferedReader(new StringReader("BASKETBALL\n"));

    gameService.parse(reader);

    verify(basketballGameService, times(1)).parse(any());
  }

  @Test
  void process_handballFile_callHandballService() {
    when(handballGameService.getSport()).thenReturn(Sport.HANDBALL);

    BufferedReader reader = new BufferedReader(new StringReader("HANDBALL\n"));

    gameService.parse(reader);

    verify(handballGameService, times(1)).parse(any());
  }

  @Test
  void process_invalidFile_throwException() {
    BufferedReader reader = new BufferedReader(new StringReader("ABC\n"));

    assertThrows(InvalidGameFormatException.class, () -> gameService.parse(reader));
  }
}
