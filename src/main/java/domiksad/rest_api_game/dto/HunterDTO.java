package domiksad.rest_api_game.dto;

import jakarta.validation.constraints.NotBlank;

public class HunterDTO {
    private Long id;

    @NotBlank(message = "Field name is required and cannot be empty")
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
