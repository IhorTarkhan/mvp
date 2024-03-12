package org.example.mvp.calculator;

import org.example.mvp.calculator.bean.PlayerGameResult;

import java.io.Reader;
import java.util.List;

public interface SportGameService {
  Sport getSport();

  List<PlayerGameResult> calculate(Reader reader);
}
