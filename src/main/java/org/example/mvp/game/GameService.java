package org.example.mvp.game;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
  private final List<SportGameService> sportGameServices;

  @SneakyThrows
  public List<PlayerGameResult> process(BufferedReader reader) {
    Sport sportType;
    try{
        sportType = Sport.valueOf(reader.readLine());
    } catch (IllegalArgumentException e) {
        throw new InvalidGameFormatException(e);
    }
    return calculate(sportType, reader);
  }

    private List<PlayerGameResult> calculate(Sport sport, Reader reader) {
    return sportGameServices.stream()
        .filter(calculator -> calculator.getSport() == sport)
        .findFirst()
        .orElseThrow(InvalidGameFormatException::new)
        .calculate(reader);
  }
}
