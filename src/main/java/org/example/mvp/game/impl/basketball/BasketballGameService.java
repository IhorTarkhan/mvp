package org.example.mvp.game.impl.basketball;

import org.example.mvp.game.Sport;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.example.mvp.game.impl.SportGameServiceTemplate;
import org.example.mvp.game.SportGameService;
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

  protected void validateBean(BasketballPlayerStatisticBean bean) {
    super.validateBean(bean);
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

  @Override
  protected Long getPayerRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints() * 2 + bean.getRebounds() + bean.getAssists();
  }

  @Override
  protected Long getTeamRatingScore(BasketballPlayerStatisticBean bean) {
    return bean.getScoredPoints();
  }
}
