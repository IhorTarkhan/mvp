package org.example.mvp;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.example.mvp.bean.BasketballPlayerStatistic;
import org.example.mvp.bean.PlayerGameResult;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Component
public class Main {
  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    var game = parseBasketballGave(Path.of("src/main/resources/set/game1.csv"));

    var winnerTeam = getWinnerTeam(game);
    var playerGameResults = getPlayersGameResult(game, winnerTeam);

    System.out.println();
  }

  private List<BasketballPlayerStatistic> parseBasketballGave(Path path) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      System.out.println(reader.readLine()); // todo build processing logic on this

      List<BasketballPlayerStatistic> playerStatistics =
          new CsvToBeanBuilder<BasketballPlayerStatistic>(reader)
              .withType(BasketballPlayerStatistic.class)
              .withSeparator(';')
              .build()
              .parse();
      System.out.println(playerStatistics.size());
      return playerStatistics;
    }
  }

  private String getWinnerTeam(List<BasketballPlayerStatistic> game) {
    Map<String, Long> teamsScoreByName =
        game.stream()
            .collect(
                Collectors.toMap(
                    BasketballPlayerStatistic::getTeamName,
                    BasketballPlayerStatistic::getScoredPoints,
                    Long::sum));
    return Collections.max(teamsScoreByName.entrySet(), comparingByValue()).getKey();
  }

  private List<PlayerGameResult> getPlayersGameResult(
      List<BasketballPlayerStatistic> game, String winnerTeam) {
    return game.stream()
        .map(
            stat ->
                new PlayerGameResult(
                    stat.getNickname(),
                    stat.getScoredPoints() * 2 + stat.getRebounds() + stat.getAssists(),
                    winnerTeam.equals(stat.getTeamName())))
        .toList();
  }
}
