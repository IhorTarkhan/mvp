package org.example.mvp.game.impl.basketball;

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
public class BasketballGameService implements SportGameService {
  private final SportGameParser<BasketballPlayerStatisticBean> sportGameParser;
  private final SportGameValidator<BasketballPlayerStatisticBean> sportGameValidator;
  private final SportGameCalculator<BasketballPlayerStatisticBean> sportGameCalculator;

  @Override
  public Sport getSport() {
    return Sport.BASKETBALL;
  }

  @Override
  public List<PlayerGameResult> calculate(String content) {
    var game = sportGameParser.getPlayersStatistic(BasketballPlayerStatisticBean.class, content);
    sportGameValidator.validate(game, this::validateBean);
    var winnerTeam = sportGameCalculator.getWinnerTeam(game, this::getTeamRatingScore);
    return sportGameCalculator.getPlayersGameResult(game, winnerTeam, this::getPayerRatingScore);
  }

  private void validateBean(BasketballPlayerStatisticBean bean) {
    if (bean.getScoredPoints() == null) {
      throw new InvalidGameFormatException("Player scoredPoints must be set");
    }
    if (bean.getRebounds() == null) {
      throw new InvalidGameFormatException("Player rebounds must be set");
    }
    if (bean.getAssists() == null) {
      throw new InvalidGameFormatException("Player assists must be set");
    }
  }

  private Long getTeamRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints();
  }

  private Long getPayerRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints() * 2 + bean.getRebounds() + bean.getAssists();
  }
}
