package org.example.mvp.game;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SportGameServiceFactory {
  private final List<SportGameService> sportGameServices;
  private Map<Sport, SportGameService> serviceBySport;

  @PostConstruct
  private void init() {
    serviceBySport =
        sportGameServices.stream()
            .collect(Collectors.toMap(SportGameService::getSport, Function.identity()));
  }

  public SportGameService getSportGameService(Sport sport) {
    SportGameService sportGameService = serviceBySport.get(sport);
    if (sportGameService == null) {
      throw new IllegalArgumentException("Unsupported sport type: " + sport);
    }
    return sportGameService;
  }
}
