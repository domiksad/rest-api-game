package domiksad.restapigame.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record HunterRequestDto(
    @NotBlank(message = "Field name is required and cannot be empty") String name) {}
