package org.example.mvp.game.impl.handball;

import org.example.mvp.game.Sport;
import org.example.mvp.game.impl.SportGameServiceTemplate;
import org.example.mvp.game.SportGameService;
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

  @Override
  protected Long getPayerRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade() * 2 - bean.getGoalsReceived();
  }

  @Override
  protected Long getTeamRatingScore(HandballPlayerStatisticBean bean) {
    return bean.getGoalsMade();
  }
}
