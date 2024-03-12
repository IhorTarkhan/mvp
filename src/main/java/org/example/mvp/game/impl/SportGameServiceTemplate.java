package org.example.mvp.game.impl;

import static java.util.Map.Entry.comparingByValue;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.mvp.game.Sport;
import org.example.mvp.game.SportGameService;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;

public abstract class SportGameServiceTemplate<BEAN extends PlayerStatisticBean>
    implements SportGameService {
  public List<PlayerGameResult> calculate(Reader reader) {
    var game = getPlayersStatistic(reader);
    validate(game);
    var winnerTeam = getWinnerTeam(game);
    return getPlayersGameResult(game, winnerTeam);
  }

  private List<BEAN> getPlayersStatistic(Reader reader) {
    try {
      return new CsvToBeanBuilder<BEAN>(reader)
          .withType(getBeanClass())
          .withSeparator(';')
          .build()
          .parse();
    } catch (RuntimeException e) {
      throw new InvalidGameFormatException(e);
    }
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

  protected void validate(List<BEAN> beans) {
    beans.forEach(
        bean -> {
          if (bean.getPlayerName() == null
              || bean.getNickname() == null
              || bean.getTeamName() == null) {
            throw new InvalidGameFormatException();
          }
        });
  }

  protected abstract Long getPayerRatingScore(BEAN bean);

  protected abstract Long getTeamRatingScore(BEAN bean);
}
