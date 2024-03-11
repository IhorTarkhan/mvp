package org.example.mvp.bean;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class HandballGame {
  @CsvBindByPosition(position = 0)
  private String playerName;

  @CsvBindByPosition(position = 1)
  private String nickname;

  @CsvBindByPosition(position = 2)
  private Long number;

  @CsvBindByPosition(position = 3)
  private String teamName;

  @CsvBindByPosition(position = 4)
  private Long goalsMade;

  @CsvBindByPosition(position = 5)
  private Long goalsReceived;
}
