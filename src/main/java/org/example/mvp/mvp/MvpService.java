package org.example.mvp.mvp;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.mvp.bean.PlayerScoreResult;
import org.example.mvp.mvp.exception.InvalidGameSetException;
import org.springframework.stereotype.Service;

@Service
public class MvpService {
  public static final int TEAM_WON_ADDITIVE = 10;

  public PlayerScoreResult getMvp(List<PlayerGameResult> playerGameResults) {
    Map<String, PlayerScoreResult> scoresByNickname =
        playerGameResults.stream()
            .collect(
                Collectors.toMap(PlayerGameResult::getNickname, this::getScore, this::sumScore));

    return Collections.max(scoresByNickname.values(), this::compareScore);
  }

  public PlayerScoreResult getScore(PlayerGameResult gameResult) {
    return PlayerScoreResult.builder()
        .nickname(gameResult.getNickname())
        .name(gameResult.getName())
        .score(gameResult.getScore() + (gameResult.isHasTeamWon() ? TEAM_WON_ADDITIVE : 0))
        .build();
  }

  public PlayerScoreResult sumScore(PlayerScoreResult a, PlayerScoreResult b) {
    if (!a.getName().equals(b.getName())) {
      throw new InvalidGameSetException("Player name must be the same");
    }

    return PlayerScoreResult.builder()
        .nickname(a.getNickname())
        .name(a.getName())
        .score(a.getScore() + b.getScore())
        .build();
  }

  public int compareScore(PlayerScoreResult a, PlayerScoreResult b) {
    return a.getScore().compareTo(b.getScore());
  }
}
