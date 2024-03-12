package org.example.mvp.calculator.impl;

import static java.util.Map.Entry.comparingByValue;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.mvp.calculator.Sport;
import org.example.mvp.calculator.bean.PlayerGameResult;
import org.example.mvp.calculator.SportGameService;

public abstract class SportGameServiceTemplate<BEAN extends PlayerStatisticBean> implements SportGameService {
  public List<PlayerGameResult> calculate(Reader reader) {
    var game = getPlayersStatistic(reader);
    var winnerTeam = getWinnerTeam(game);
    return getPlayersGameResult(game, winnerTeam);
  }

  private List<BEAN> getPlayersStatistic(Reader reader) {
    return new CsvToBeanBuilder<BEAN>(reader)
        .withType(getBeanClass())
        .withSeparator(';')
        .build()
        .parse();
  }

  private String getWinnerTeam(List<BEAN> game) {
    Map<String, Long> teamsScoreByName =
        game.stream()
            .collect(
                Collectors.toMap(
                    PlayerStatisticBean::getTeamName, this::getTeamRatingScore, Long::sum));
    return Collections.max(teamsScoreByName.entrySet(), comparingByValue()).getKey();
  }

  private List<PlayerGameResult> getPlayersGameResult(List<BEAN> game, String winnerTeam) {
    return game.stream()
        .map(
            stat ->
                new PlayerGameResult(
                    stat.getNickname(),
                    stat.getPlayerName(),
                    getPayerRatingScore(stat),
                    winnerTeam.equals(stat.getTeamName())))
        .toList();
  }

  public abstract Sport getSport();

  protected abstract Class<BEAN> getBeanClass();

  protected abstract Long getPayerRatingScore(BEAN bean);

  protected abstract Long getTeamRatingScore(BEAN bean);
}
