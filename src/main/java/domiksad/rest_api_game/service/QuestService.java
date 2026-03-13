package domiksad.rest_api_game.service;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.dto.QuestDTO;
import domiksad.rest_api_game.entity.Hunter;
import domiksad.rest_api_game.entity.Quest;
import domiksad.rest_api_game.enums.DangerLevel;
import domiksad.rest_api_game.enums.QuestStatus;
import domiksad.rest_api_game.repository.HunterRepository;
import domiksad.rest_api_game.repository.QuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuestService {

    @Autowired
    HunterRepository hunterRepository;

    @Autowired
    QuestRepository questRepository;

    ModelMapper mapper = new ModelMapper();

    public QuestDTO createQuest(QuestDTO questDTO){
        Quest quest = mapper.map(questDTO, Quest.class);

        Quest savedQuest = questRepository.save(quest);

        QuestDTO responseDTO = mapper.map(savedQuest, QuestDTO.class);

        return responseDTO;
    }

//    public List<QuestDTO> getAllQuests(){
//        return questRepository.findAll()
//                .stream().map(q -> mapper.map(q, QuestDTO.class))
//                .toList();
//    }

    public QuestDTO getQuestById(Long id){
        return mapper.map(questRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)), QuestDTO.class);
    }

    public void deleteQuestById(Long id){
        questRepository.deleteById(id);
    }

    public List<HunterDTO> getQuestAssignedHuntersById(Long id) {
        return questRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)).getAssignedHunters()
                .stream().map(h -> mapper.map(h, HunterDTO.class))
                .toList();
    }

    public void assignQuestToHunter(Long questId, Long hunterId) {
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Hunter hunter = hunterRepository.findById(hunterId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(quest.isHunterAlreadyAssigned(hunter) || hunter.isQuestAlreadyAssigned(quest)) throw new ResponseStatusException(HttpStatus.CONFLICT);

        quest.assignHunter(hunter);
        hunter.assignQuest(quest);

        questRepository.save(quest);
        hunterRepository.save(hunter);
    }

    public QuestDTO completeTaskById(Long id) {
        Quest quest = questRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        quest.updateStatus(QuestStatus.COMPLETED);

        questRepository.save(quest);

        return mapper.map(quest, QuestDTO.class);
    }

    public QuestDTO updateQuest(Long id, QuestDTO questDTO) {
        Quest quest = questRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Quest newQuest = mapper.map(questDTO, Quest.class);
        newQuest.setId(quest.getId());

        questRepository.save(newQuest);
        return mapper.map(newQuest, QuestDTO.class);
    }

    // for more params change to jpa specifications
    public List<QuestDTO> getAllQuestsWithOptionalParams(QuestStatus questStatus, DangerLevel dangerLevel) {
        List<Quest> quests;
        if(questStatus != null && dangerLevel != null){
            quests = questRepository.findAllByQuestStatusAndDangerLevel(questStatus, dangerLevel);
        } else if (questStatus != null) {
            quests = questRepository.findAllByQuestStatus(questStatus);
        } else if (dangerLevel != null) {
            quests = questRepository.findAllByDangerLevel(dangerLevel);
        } else {
            quests = questRepository.findAll();
        }

        return quests.stream()
                .map(q -> mapper.map(q, QuestDTO.class))
                .toList();
    }
}
