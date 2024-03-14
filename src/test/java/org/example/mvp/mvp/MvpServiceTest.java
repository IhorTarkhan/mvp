package org.example.mvp.mvp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.mvp.bean.PlayerScoreResult;
import org.example.mvp.mvp.exception.InvalidGameSetException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class MvpServiceTest {
  @InjectMocks MvpService mvpService;

  @Test
  void getMvp_correct_workCorrectly() {
    List<PlayerGameResult> playerGameResults =
        List.of(
            new PlayerGameResult("nick1", "player 1", 1L, 10L, true),
            new PlayerGameResult("nick1", "player 1", 1L, 10L, false),
            new PlayerGameResult("nick2", "player 2", 2L, 20L, true),
            new PlayerGameResult("nick2", "player 2", 2L, 10L, false));

    PlayerScoreResult actualResult = mvpService.getMvp(playerGameResults);

    assertEquals(
        PlayerScoreResult.builder().nickname("nick2").name("player 2").score(40L).build(),
        actualResult);
  }

  @Test
  void getMvp_playerHowLoseWithoutTeamWonBonus_workCorrectly() {
    List<PlayerGameResult> playerGameResults =
        List.of(
            new PlayerGameResult("nick1", "player 1", 1L, 3L, true),
            new PlayerGameResult("nick1", "player 1", 1L, 3L, true),
            new PlayerGameResult("nick2", "player 2", 2L, 10L, false),
            new PlayerGameResult("nick2", "player 2", 2L, 10L, false));

    PlayerScoreResult actualResult = mvpService.getMvp(playerGameResults);

    assertEquals(
        PlayerScoreResult.builder().nickname("nick1").name("player 1").score(26L).build(),
        actualResult);
  }

  @Test
  void getMvp_playerHowLoseWithoutTeamWonBonus_throwsException() {
    List<PlayerGameResult> playerGameResults =
        List.of(
            new PlayerGameResult("nick1", "player 1", 1L, 3L, true),
            new PlayerGameResult("nick1", "player 11", 1L, 3L, true),
            new PlayerGameResult("nick2", "player 2", 2L, 10L, false),
            new PlayerGameResult("nick2", "player 2", 2L, 10L, false));

    InvalidGameSetException exception =
        assertThrowsExactly(
            InvalidGameSetException.class, () -> mvpService.getMvp(playerGameResults));
    assertEquals("Player name must be the same", exception.getMessage());
  }
}
