package domiksad.restapigame.presentation.dto;

import domiksad.restapigame.domain.quest.DangerLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestRequestDto(
    @NotBlank(message = "Field name is required and cannot be empty") String name,
    @NotBlank(message = "Field description is required and cannot be empty") String description,
    @NotBlank(message = "Field reward is required and cannot be empty") String reward,
    @NotNull(message = "Field dangerLevel is required") DangerLevel dangerLevel) {}
