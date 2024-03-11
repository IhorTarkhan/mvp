package org.example.mvp.calculator.impl;

import org.example.mvp.Sport;
import org.example.mvp.bean.HandballPlayerStatistic;
import org.example.mvp.calculator.SportResultCalculator;
import org.springframework.stereotype.Service;

@Service
public class HandballResultCalculator extends AbstractResultCalculator<HandballPlayerStatistic>
    implements SportResultCalculator {
  @Override
  public Sport getSport() {
    return Sport.HANDBALL;
  }

  @Override
  protected Class<HandballPlayerStatistic> getBeanClass() {
    return HandballPlayerStatistic.class;
  }

  @Override
  protected Long getPayerRatingScore(HandballPlayerStatistic bean) {
    return bean.getGoalsMade() * 2 - bean.getGoalsReceived();
  }

  @Override
  protected Long getTeamRatingScore(HandballPlayerStatistic bean) {
    return bean.getGoalsMade();
  }
}
