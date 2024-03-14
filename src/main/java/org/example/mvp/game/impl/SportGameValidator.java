package org.example.mvp.game.impl;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

@Service
public class SportGameValidator<Bean extends PlayerStatisticBean> {
  public void validate(List<Bean> beans, Consumer<Bean> typeSpecificValidation) {
    beans.forEach(bean -> validateBean(bean, typeSpecificValidation));

    Set<String> uniqueNickname =
        beans.stream().map(PlayerStatisticBean::getNickname).collect(Collectors.toSet());
    Set<Long> uniqueNumber =
        beans.stream().map(PlayerStatisticBean::getNumber).collect(Collectors.toSet());
    Set<String> uniqueTeamName =
        beans.stream().map(PlayerStatisticBean::getTeamName).collect(Collectors.toSet());

    if (uniqueNickname.size() != beans.size()) {
      throw new InvalidGameFormatException("Nicknames must be unique");
    }
    if (uniqueNumber.size() != beans.size()) {
      throw new InvalidGameFormatException("Numbers must be unique");
    }
    if (uniqueTeamName.size() != 2) {
      throw new InvalidGameFormatException("Must be two teams in the game");
    }
  }

  private void validateBean(Bean bean, Consumer<Bean> typeSpecificValidation) {
    if (bean.getPlayerName() == null) {
      throw new InvalidGameFormatException("Player name must be set");
    }
    if (bean.getNickname() == null) {
      throw new InvalidGameFormatException("Player nickname must be set");
    }
    if (bean.getNumber() == null) {
      throw new InvalidGameFormatException("Player number must be set");
    }
    if (bean.getTeamName() == null) {
      throw new InvalidGameFormatException("Team name must be set");
    }
    typeSpecificValidation.accept(bean);
  }
}
