package org.example.mvp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.mvp.game.GameService;
import org.example.mvp.mvp.MvpService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Main {
  private final GameService gameService;
  private final MvpService mvpService;

  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    String setFolder = "src/main/resources/set/";

    try (Stream<Path> paths = Files.walk(Paths.get(setFolder))) {
      var playerGamesResults =
          paths
              .filter(path -> !Files.isDirectory(path))
              .flatMap(path -> gameService.parse(path).stream())
              .toList();
      var mvpPlayer = mvpService.getMvp(playerGamesResults);
      System.out.println(mvpPlayer);
    } catch (IOException e) {
      throw new RuntimeException(e); // todo
    }
  }
}
