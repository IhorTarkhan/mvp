package org.example.mvp.game.impl;

import static java.util.Map.Entry.comparingByValue;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import org.example.mvp.game.Sport;
import org.example.mvp.game.SportGameService;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;

public abstract class SportGameServiceTemplate<BEAN extends PlayerStatisticBean>
    implements SportGameService {
  public static final char CSV_SEPARATOR = ';';

  public List<PlayerGameResult> parse(Reader reader) {
    var game = getPlayersStatistic(reader);
    validate(game);
    var winnerTeam = getWinnerTeam(game);
    return getPlayersGameResult(game, winnerTeam);
  }

  private List<BEAN> getPlayersStatistic(Reader reader) {
    try {
      return new CsvToBeanBuilder<BEAN>(reader)
          .withType(getBeanClass())
          .withSeparator(CSV_SEPARATOR)
          .build()
          .parse();
    } catch (RuntimeException e) {
      throw new InvalidGameFormatException("Can not read file to Beans", e);
    }
  }

  private void validate(List<BEAN> beans) {
    beans.forEach(this::validateBean);

    Set<String> uniqueNickname =
        beans.stream().map(PlayerStatisticBean::getNickname).collect(Collectors.toSet());
    Set<Long> uniqueNumber =
        beans.stream().map(PlayerStatisticBean::getNumber).collect(Collectors.toSet());
    Set<String> uniqueTeamName =
        beans.stream().map(PlayerStatisticBean::getTeamName).collect(Collectors.toSet());

    if (uniqueNickname.size() != beans.size()) {
      throw new InvalidGameFormatException("Nicknames must be unique");
    }
    if (uniqueNumber.size() != beans.size()) {
      throw new InvalidGameFormatException("Numbers must be unique");
    }
    if (uniqueTeamName.size() != 2) {
      throw new InvalidGameFormatException("Must be two teams in the game");
    }
  }

  private String getWinnerTeam(List<BEAN> game) {
    Map<String, Long> teamsScoreByName =
        game.stream()
            .collect(
                Collectors.toMap(
                    PlayerStatisticBean::getTeamName, this::getTeamRatingScore, Long::sum));

    Long[] scores = teamsScoreByName.values().toArray(Long[]::new);
    if (scores[0].equals(scores[1])) {
      throw new InvalidGameFormatException("Game must have a winner team");
    }

    return Collections.max(teamsScoreByName.entrySet(), comparingByValue()).getKey();
  }

  private List<PlayerGameResult> getPlayersGameResult(List<BEAN> game, String winnerTeam) {
    return game.stream()
        .map(
            stat ->
                PlayerGameResult.builder()
                    .nickname(stat.getNickname())
                    .name(stat.getPlayerName())
                    .number(stat.getNumber())
                    .score(getPayerRatingScore(stat))
                    .hasTeamWon(winnerTeam.equals(stat.getTeamName()))
                    .build())
        .toList();
  }

  public abstract Sport getSport();

  protected abstract Class<BEAN> getBeanClass();

  protected void validateBean(BEAN bean) {
    if (bean.getNickname() == null) {
      throw new InvalidGameFormatException("Player nickname must be set");
    }
    if (bean.getPlayerName() == null) {
      throw new InvalidGameFormatException("Player name must be set");
    }
    if (bean.getNumber() == null) {
      throw new InvalidGameFormatException("Player number must be set");
    }
    if (bean.getTeamName() == null) {
      throw new InvalidGameFormatException("Team name must be set");
    }
  }

  protected abstract Long getPayerRatingScore(BEAN bean);

  protected abstract Long getTeamRatingScore(BEAN bean);
}
