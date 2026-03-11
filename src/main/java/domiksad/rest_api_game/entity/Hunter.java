package domiksad.rest_api_game.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Hunter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany
    private final List<Quest> assignedQuests = new ArrayList<>();

    protected Hunter(){}
    public Hunter(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Quest> getAssignedQuests() { return Collections.unmodifiableList(assignedQuests); }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void assignQuest(Quest quest) {
        if (!assignedQuests.contains(quest)) {
            assignedQuests.add(quest);
        }
    }
    public void removeQuest(Quest quest) {
        assignedQuests.remove(quest);
    }
}
