package domiksad.restapigame.presentation.dto;

import domiksad.restapigame.domain.quest.DangerLevel;
import domiksad.restapigame.domain.quest.QuestStatus;
import java.util.UUID;

public record QuestResponseDto(
    UUID id,
    String name,
    String description,
    String reward,
    DangerLevel dangerLevel,
    QuestStatus questStatus) {}
