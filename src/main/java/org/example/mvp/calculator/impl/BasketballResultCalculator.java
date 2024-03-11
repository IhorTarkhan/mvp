package org.example.mvp.calculator.impl;

import org.example.mvp.Sport;
import org.example.mvp.bean.BasketballPlayerStatistic;
import org.example.mvp.calculator.SportResultCalculator;
import org.springframework.stereotype.Service;

@Service
public class BasketballResultCalculator extends AbstractResultCalculator<BasketballPlayerStatistic>
    implements SportResultCalculator {
  @Override
  public Sport getSport() {
    return Sport.BASKETBALL;
  }

  @Override
  protected Class<BasketballPlayerStatistic> getBeanClass() {
    return BasketballPlayerStatistic.class;
  }

  @Override
  protected Long getPayerRatingScore(BasketballPlayerStatistic bean) {
    return bean.getScoredPoints() * 2 + bean.getRebounds() + bean.getAssists();
  }

  @Override
  protected Long getTeamRatingScore(BasketballPlayerStatistic bean) {
    return bean.getScoredPoints();
  }
}
