package domiksad.rest_api_game.dto;

import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestDTO {
    private Long id;

    @NotBlank(message = "Field name is required and cannot be empty")
    private String name;

    @NotBlank(message = "Field description is required and cannot be empty")
    private String description;

    @NotBlank(message = "Field reward is required and cannot be empty")
    private String reward;

    @NotNull(message = "Field dangerLevel is required")
    private DangerLevel dangerLevel;

    @NotNull(message = "Field questStatus is required")
    private QuestStatus questStatus;

    protected QuestDTO(){}
    public QuestDTO(String name, String description, String reward, DangerLevel dangerLevel) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.dangerLevel = dangerLevel;
        this.questStatus = QuestStatus.CREATED;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getReward() {
        return reward;
    }
    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }
    public QuestStatus getQuestStatus() {
        return questStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setReward(String reward) {
        this.reward = reward;
    }
    public void setDangerLevel(DangerLevel dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
    public void setQuestStatus(QuestStatus questStatus) {
        this.questStatus = questStatus;
    }
}
