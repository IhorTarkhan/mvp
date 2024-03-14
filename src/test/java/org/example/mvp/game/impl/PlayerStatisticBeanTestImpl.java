package org.example.mvp.game.impl;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatisticBeanTestImpl implements PlayerStatisticBean {
  @CsvBindByPosition(position = 0)
  private String playerName;

  @CsvBindByPosition(position = 1)
  private String nickname;

  @CsvBindByPosition(position = 2)
  private Long number;

  @CsvBindByPosition(position = 3)
  private String teamName;

  @CsvBindByPosition(position = 4)
  private Long teamRatingScore;

  @CsvBindByPosition(position = 5)
  private Long payerRatingScore;
}
