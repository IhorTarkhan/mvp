package org.example.mvp.game.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.StringReader;
import java.util.List;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.springframework.stereotype.Service;

@Service
public class SportGameParser<Bean extends PlayerStatisticBean> {
  public static final char CSV_SEPARATOR = ';';

  public List<Bean> getPlayersStatistic(Class<Bean> beanClass, String content) {
    try {
      return new CsvToBeanBuilder<Bean>(new StringReader(content))
          .withType(beanClass)
          .withSeparator(CSV_SEPARATOR)
          .build()
          .parse();
    } catch (RuntimeException e) {
      throw new InvalidGameFormatException("Can not read file to Beans", e);
    }
  }
}
