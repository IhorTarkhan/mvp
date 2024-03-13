package org.example.mvp.game.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayerGameResult {
  private String nickname;
  private String name;
  private Long number;
  private Long score;
  private boolean hasTeamWon;
}
