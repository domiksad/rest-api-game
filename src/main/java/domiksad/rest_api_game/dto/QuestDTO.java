package domiksad.rest_api_game.dto;

import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;

public class QuestDTO {
    private Long id;
    private String name;
    private String description;
    private String reward;

    private DangerLevel dangerLevel;
    private QuestStatus questStatus;

    protected QuestDTO(){}
    public QuestDTO(String name, String description, String reward, DangerLevel dangerLevel) {
        this.id = id;
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
