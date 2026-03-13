package domiksad.rest_api_game.service;

import domiksad.rest_api_game.dto.HunterDTO;
import domiksad.rest_api_game.dto.QuestDTO;
import domiksad.rest_api_game.entity.Hunter;
import domiksad.rest_api_game.repository.HunterRepository;
import domiksad.rest_api_game.repository.QuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HunterService {

    @Autowired
    HunterRepository hunterRepository;

    @Autowired
    QuestRepository questRepository;

    ModelMapper mapper = new ModelMapper();

    public HunterDTO createHunter(HunterDTO hunterDTO) {
        Hunter hunter = mapper.map(hunterDTO, Hunter.class);

        Hunter savedHunter = hunterRepository.save(hunter);

        HunterDTO responseDTO = mapper.map(savedHunter, HunterDTO.class);

        return responseDTO;
    }

    public List<HunterDTO> getAllHunters(){
        return hunterRepository.findAll()
                .stream().map(h -> mapper.map(h, HunterDTO.class))
                .toList();
    }

    public HunterDTO getHunterById(Long id){
        return mapper.map(hunterRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)), HunterDTO.class);
    }

    public void deleteHunterById(Long id){
        hunterRepository.deleteById(id);
    }

    public List<QuestDTO> getHunterAssignedQuestsById(Long id) {
        return hunterRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)).getAssignedQuests()
                .stream().map(q -> mapper.map(q, QuestDTO.class))
                .toList();

    }

    public HunterDTO updateHunter(Long id, HunterDTO hunterDTO) {
        Hunter hunter = hunterRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Hunter newHunter = mapper.map(hunterDTO, Hunter.class);
        newHunter.setId(hunter.getId());

        hunterRepository.save(newHunter);
        return mapper.map(newHunter, HunterDTO.class);
    }
}
