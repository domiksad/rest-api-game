package domiksad.rest_api_game.entity;

import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Quest {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String reward;

    @Enumerated(EnumType.STRING)
    private DangerLevel dangerLevel;

    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus;

    @ManyToMany
    private final List<Hunter> assignedHunters = new ArrayList<>();

    protected Quest(){}
    public Quest(String name, String description, String reward, DangerLevel dangerLevel) {
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
    public List<Hunter> getAssignedHunters() {
        return Collections.unmodifiableList(assignedHunters);
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

    public boolean isHunterAlreadyAssigned(Hunter hunter) {
        return assignedHunters.contains(hunter);
    }
    public void assignHunter(Hunter hunter) {
        assignedHunters.add(hunter);
    }
    public void removeHunter(Hunter hunter) {
        assignedHunters.remove(hunter);
    }

    public void updateStatus(QuestStatus questStatus){
        this.questStatus = questStatus;
    }
}
