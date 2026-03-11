package domiksad.rest_api_game.service;

import domiksad.rest_api_game.dto.HunterDTO;
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

        if(hunter.getName() == null || hunter.getName().isBlank())
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Field name is required and cannot be empty"
            );

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
}
