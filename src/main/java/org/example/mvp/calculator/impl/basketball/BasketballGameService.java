package org.example.mvp.calculator.impl.basketball;

import org.example.mvp.calculator.Sport;
import org.example.mvp.calculator.impl.SportGameServiceTemplate;
import org.example.mvp.calculator.SportGameService;
import org.springframework.stereotype.Service;

@Service
public class BasketballGameService extends SportGameServiceTemplate<BasketballPlayerStatisticBean>
    implements SportGameService {
  @Override
  public Sport getSport() {
    return Sport.BASKETBALL;
  }

  @Override
  protected Class<BasketballPlayerStatisticBean> getBeanClass() {
    return BasketballPlayerStatisticBean.class;
  }

  @Override
  protected Long getPayerRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints() * 2 + bean.getRebounds() + bean.getAssists();
  }

  @Override
  protected Long getTeamRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints();
  }
}
