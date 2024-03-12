package org.example.mvp.calculator.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerGameResult {
  private String nickname;
  private String name;
  private Long score;
  private boolean hasTeamWon;
}