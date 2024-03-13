package org.example.mvp.mvp.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerScoreResult {
  private String nickname;
  private String name;
  private Long score;
}
