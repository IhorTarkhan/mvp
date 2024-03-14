package org.example.mvp.game.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.example.mvp.game.exception.InvalidGameFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class SportGameValidatorTest {
  @InjectMocks SportGameValidator<PlayerStatisticBeanTestImpl> sportGameValidator;

  @BeforeEach
  void setUp() {}

  void testImplBeanValidator(PlayerStatisticBeanTestImpl bean) {
    if (bean.getTeamRatingScore() == null) {
      throw new InvalidGameFormatException("TeamRatingScore must be set");
    }
    if (bean.getPayerRatingScore() == null) {
      throw new InvalidGameFormatException("PlayerRatingScore must be set");
    }
  }

  @Test
  void validate_correctBeans_doesNotThrow() {
    List<PlayerStatisticBeanTestImpl> beans =
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 40L, 80L));

    assertDoesNotThrow(() -> sportGameValidator.validate(beans, this::testImplBeanValidator));
  }

  @Test
  void validate_nullFields_throwsException() {
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl(null, "nick1", 1L, "team 1", 10L, 50L)),
        "Player name must be set");
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl("player 1", null, 1L, "team 1", 10L, 50L)),
        "Player nickname must be set");
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl("player 1", "nick1", null, "team 1", 10L, 50L)),
        "Player number must be set");
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, null, 10L, 50L)),
        "Team name must be set");
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", null, 50L)),
        "TeamRatingScore must be set");
    assertThrowsValidationInvalidGameFormatException(
        List.of(new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, null)),
        "PlayerRatingScore must be set");
  }

  @Test
  void validate_failConstrain_throwsException() {
    assertThrowsValidationInvalidGameFormatException(
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick1", 2L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 40L, 80L)),
        "Nicknames must be unique");
    assertThrowsValidationInvalidGameFormatException(
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 1L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 2", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 2", 40L, 80L)),
        "Numbers must be unique");
    assertThrowsValidationInvalidGameFormatException(
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 1", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 1", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 1", 40L, 80L)),
        "Must be two teams in the game");
    assertThrowsValidationInvalidGameFormatException(
        List.of(
            new PlayerStatisticBeanTestImpl("player 1", "nick1", 1L, "team 1", 10L, 50L),
            new PlayerStatisticBeanTestImpl("player 2", "nick2", 2L, "team 2", 20L, 60L),
            new PlayerStatisticBeanTestImpl("player 3", "nick3", 3L, "team 3", 30L, 70L),
            new PlayerStatisticBeanTestImpl("player 4", "nick4", 4L, "team 4", 40L, 80L)),
        "Must be two teams in the game");
  }

  void assertThrowsValidationInvalidGameFormatException(
          List<PlayerStatisticBeanTestImpl> beans, String expectedMessage) {
    InvalidGameFormatException exception =
            assertThrowsExactly(
                    InvalidGameFormatException.class,
                    () -> sportGameValidator.validate(beans, this::testImplBeanValidator));
    assertEquals(expectedMessage, exception.getMessage());
  }
}
