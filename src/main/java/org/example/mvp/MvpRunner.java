package org.example.mvp;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mvp.game.GameService;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.mvp.MvpService;
import org.example.mvp.mvp.bean.PlayerScoreResult;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MvpRunner implements ApplicationRunner {
  private final GameService gameService;
  private final MvpService mvpService;

  @Override
  public void run(ApplicationArguments args) {
    String setFolder = "src/main/resources/set/";

    List<String> contents = readFilesContent(setFolder);
    List<PlayerGameResult> playerGamesResults =
        contents.stream().flatMap(content -> gameService.calculate(content).stream()).toList();
    PlayerScoreResult mvpPlayer = mvpService.getMvp(playerGamesResults);

    log.info("MVP player is: " + mvpPlayer);
  }

  private List<String> readFilesContent(String setFolder) {
    try (Stream<Path> paths = Files.walk(Paths.get(setFolder))) {
      return paths.filter(path -> !Files.isDirectory(path)).map(this::readContent).toList();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private String readContent(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
