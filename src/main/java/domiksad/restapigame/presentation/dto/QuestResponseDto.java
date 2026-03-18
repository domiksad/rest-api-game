package domiksad.restapigame.presentation.dto;

import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.QuestStatus;

public record QuestResponseDto(
    Long id,
    String name,
    String description,
    String reward,
    DangerLevel dangerLevel,
    QuestStatus questStatus) {}
