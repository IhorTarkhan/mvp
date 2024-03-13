package org.example.mvp.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final List<SportGameService> sportGameServices;

  public List<PlayerGameResult> parse(Path path) {
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      return parse(reader);
    } catch (IOException e) {
      throw new InvalidGameFormatException("Can not read game file", e);
    }
  }

  public List<PlayerGameResult> parse(BufferedReader reader) { // todo private
    String sportTypeText = getSportTypeText(reader);
    Sport sportType = getSportType(sportTypeText);
    return parse(sportType, reader);
  }

  private static String getSportTypeText(BufferedReader reader) {
    try {
      return reader.readLine();
    } catch (IOException e) {
      throw new InvalidGameFormatException("Can not read sport type from file", e);
    }
  }

  private static Sport getSportType(String sportTypeText) {
    try {
      return Sport.valueOf(sportTypeText);
    } catch (IllegalArgumentException e) {
      throw new InvalidGameFormatException("Can not read kind of sport: " + sportTypeText, e);
    }
  }

  private List<PlayerGameResult> parse(Sport sport, Reader reader) {
    return sportGameServices.stream()
        .filter(calculator -> calculator.getSport() == sport)
        .findFirst()
        .orElseThrow(() -> new InvalidGameFormatException("Unsupported sport type: " + sport))
        .parse(reader);
  }
}
