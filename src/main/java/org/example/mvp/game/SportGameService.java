package org.example.mvp.game;

import org.example.mvp.game.bean.PlayerGameResult;

import java.io.Reader;
import java.util.List;

public interface SportGameService {
  Sport getSport();

  List<PlayerGameResult> calculate(Reader reader);
}
