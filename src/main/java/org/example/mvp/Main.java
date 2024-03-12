package org.example.mvp;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.GameService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Main {
  private final GameService gameService;

  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    var playerGameResults = parseBasketballGave(Path.of("src/test/resources/set/basketball/invalid_game_format_5.csv"));
    System.out.println();
  }

  private List<PlayerGameResult> parseBasketballGave(Path path) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      return gameService.process(reader);
    }
  }
}
