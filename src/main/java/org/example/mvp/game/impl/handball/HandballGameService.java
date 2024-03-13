package org.example.mvp.game.impl.handball;

import org.example.mvp.game.Sport;
import org.example.mvp.game.SportGameService;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.example.mvp.game.impl.SportGameServiceTemplate;
import org.springframework.stereotype.Service;

@Service
public class HandballGameService extends SportGameServiceTemplate<HandballPlayerStatisticBean>
    implements SportGameService {
  @Override
  public Sport getSport() {
    return Sport.HANDBALL;
  }

  @Override
  protected Class<HandballPlayerStatisticBean> getBeanClass() {
    return HandballPlayerStatisticBean.class;
  }

  protected void validateBean(HandballPlayerStatisticBean bean) {
    super.validateBean(bean);
    if (bean.getGoalsMade() == null) {
      throw new InvalidGameFormatException("Player goalsMade must be set");
    }
    if (bean.getGoalsReceived() == null) {
      throw new InvalidGameFormatException("Player goalsReceived must be set");
    }
  }

  @Override
  protected Long getPayerRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade() * 2 - bean.getGoalsReceived();
  }

  @Override
  protected Long getTeamRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade();
  }
}
