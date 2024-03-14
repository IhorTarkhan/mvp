package org.example.mvp.game;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
  private final SportGameServiceFactory sportGameServiceFactory;

  public List<PlayerGameResult> calculate(String content) {
    String[] splitContent = content.split(System.lineSeparator(), 2);

    Sport sport = getSport(splitContent[0]);
    SportGameService sportGameService = sportGameServiceFactory.getSportGameService(sport);

    return sportGameService.calculate(splitContent[1]);
  }

  private Sport getSport(String content) {
    try {
      return Sport.valueOf(content);
    } catch (IllegalArgumentException e) {
      throw new InvalidGameFormatException("Unknown sport: " + content, e);
    }
  }
}
