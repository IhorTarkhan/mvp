package org.example.mvp;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import org.example.mvp.bean.BasketballGame;
import org.example.mvp.bean.HandballGame;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Main {
  @SneakyThrows
  @EventListener(ApplicationReadyEvent.class)
  public void afterStart() {
    Path path1 = Path.of("src/main/resources/set/game1.csv");
    try (BufferedReader reader = Files.newBufferedReader(path1)) {
      System.out.println(reader.readLine());

      List<BasketballGame> games =
          new CsvToBeanBuilder<BasketballGame>(reader)
              .withType(BasketballGame.class)
              .withSeparator(';')
              .build()
              .parse();
      System.out.println(games.size());
    }

    Path path2 = Path.of("src/main/resources/set/game2.csv");
    try (BufferedReader reader = Files.newBufferedReader(path2)) {
      System.out.println(reader.readLine());

      List<HandballGame> games =
          new CsvToBeanBuilder<HandballGame>(reader)
              .withType(HandballGame.class)
              .withSeparator(';')
              .build()
              .parse();
      System.out.println(games.size());
    }
  }
}
