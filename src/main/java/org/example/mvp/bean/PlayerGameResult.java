package org.example.mvp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerGameResult {
  private String name;
  private Long score;
  private boolean hasTeamWon;
}
