package org.example.mvp.calculator;

import lombok.RequiredArgsConstructor;
import org.example.mvp.Sport;
import org.example.mvp.bean.PlayerGameResult;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultCalculator {
  private final List<SportResultCalculator> sportResultCalculators;

  public List<PlayerGameResult> calculate(Sport sport, BufferedReader reader) {
    return sportResultCalculators.stream()
        .filter(calculator -> calculator.getSport() == sport)
        .findFirst()
        .orElseThrow() // todo add exception
        .calculate(reader);
  }
}
