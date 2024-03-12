package org.example.mvp.game;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.mvp.game.bean.PlayerGameResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
  private final List<SportGameService> sportGameServices;

  @SneakyThrows
  public List<PlayerGameResult> process(BufferedReader reader) {
    Sport sportType = Sport.valueOf(reader.readLine());
    return calculate(sportType, reader);
  }

    private List<PlayerGameResult> calculate(Sport sport, BufferedReader reader) {
    return sportGameServices.stream()
        .filter(calculator -> calculator.getSport() == sport)
        .findFirst()
        .orElseThrow() // todo add exception
        .calculate(reader);
  }
}
