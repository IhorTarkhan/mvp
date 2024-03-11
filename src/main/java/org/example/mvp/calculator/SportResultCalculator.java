package org.example.mvp.calculator;

import java.io.Reader;
import java.util.List;

import org.example.mvp.Sport;
import org.example.mvp.bean.PlayerGameResult;

public interface SportResultCalculator {
  Sport getSport();

  List<PlayerGameResult> calculate(Reader reader);
}
