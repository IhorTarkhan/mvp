package org.example.mvp.game.impl.basketball;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.mvp.game.impl.PlayerStatisticBean;

@Data
@AllArgsConstructor
public class BasketballPlayerStatisticBean implements PlayerStatisticBean {
  @CsvBindByPosition(position = 0)
  private String playerName;

  @CsvBindByPosition(position = 1)
  private String nickname;

  @CsvBindByPosition(position = 2)
  private Long number;

  @CsvBindByPosition(position = 3)
  private String teamName;

  @CsvBindByPosition(position = 4)
  private Long scoredPoints;

  @CsvBindByPosition(position = 5)
  private Long rebounds;

  @CsvBindByPosition(position = 6)
  private Long assists;
}
