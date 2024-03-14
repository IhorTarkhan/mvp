package org.example.mvp.game.impl.handball;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.mvp.game.Sport;
import org.example.mvp.game.SportGameService;
import org.example.mvp.game.bean.PlayerGameResult;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.example.mvp.game.impl.SportGameCalculator;
import org.example.mvp.game.impl.SportGameParser;
import org.example.mvp.game.impl.SportGameValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandballGameService implements SportGameService {
  private final SportGameParser<HandballPlayerStatisticBean> sportGameParser;
  private final SportGameValidator<HandballPlayerStatisticBean> sportGameValidator;
  private final SportGameCalculator<HandballPlayerStatisticBean> sportGameCalculator;

  @Override
  public Sport getSport() {
    return Sport.HANDBALL;
  }

  @Override
  public List<PlayerGameResult> calculate(String content) {
    var game = sportGameParser.getPlayersStatistic(HandballPlayerStatisticBean.class, content);
    sportGameValidator.validate(game, this::validateBean);
    var winnerTeam = sportGameCalculator.getWinnerTeam(game, this::getTeamRatingScore);
    return sportGameCalculator.getPlayersGameResult(game, winnerTeam, this::getPayerRatingScore);
  }

  private void validateBean(HandballPlayerStatisticBean bean) {
    if (bean.getGoalsMade() == null) {
      throw new InvalidGameFormatException("Player goalsMade must be set");
    }
    if (bean.getGoalsReceived() == null) {
      throw new InvalidGameFormatException("Player goalsReceived must be set");
    }
  }

  private Long getTeamRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade();
  }

  private Long getPayerRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade() * 2 - bean.getGoalsReceived();
  }
}
