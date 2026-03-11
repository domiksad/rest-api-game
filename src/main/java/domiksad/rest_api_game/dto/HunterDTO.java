package domiksad.rest_api_game.dto;

public class HunterDTO {
    private Long id;
    private String name;

    public HunterDTO(){}
    public HunterDTO(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
