package domiksad.restapigame.application.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
  @Bean
  public ModelMapper modelMapper(){
    ModelMapper mapper = new ModelMapper();
    mapper.registerModule(new RecordModule());
    return mapper;
  }
}
