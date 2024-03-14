package org.example.mvp.game.impl;

import static java.util.Map.Entry.comparingByValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

@Service
public class SportGameCalculator<Bean extends PlayerStatisticBean> {
  public String getWinnerTeam(List<Bean> game, Function<Bean, Long> getTeamRatingScore) {
    Map<String, Long> teamsScoreByName =
        game.stream()
            .collect(
                Collectors.toMap(PlayerStatisticBean::getTeamName, getTeamRatingScore, Long::sum));

    Long[] scores = teamsScoreByName.values().toArray(Long[]::new);
    if (scores[0].equals(scores[1])) {
      throw new InvalidGameFormatException("Game must have a winner team");
    }

    return Collections.max(teamsScoreByName.entrySet(), comparingByValue()).getKey();
  }

  public List<PlayerGameResult> getPlayersGameResult(
      List<Bean> game, String winnerTeam, Function<Bean, Long> getPayerRatingScore) {
    return game.stream()
        .map(
            stat ->
                PlayerGameResult.builder()
                    .nickname(stat.getNickname())
                    .name(stat.getPlayerName())
                    .number(stat.getNumber())
                    .score(getPayerRatingScore.apply(stat))
                    .hasTeamWon(winnerTeam.equals(stat.getTeamName()))
                    .build())
        .toList();
  }
}
