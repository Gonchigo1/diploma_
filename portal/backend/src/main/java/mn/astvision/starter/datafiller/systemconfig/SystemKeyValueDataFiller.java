package mn.astvision.starter.datafiller.systemconfig;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.systemconfig.SystemKey;
import mn.astvision.starter.model.systemconfig.SystemKeyValue;
import mn.astvision.starter.repository.systemconfig.SystemKeyValueRepository;
import org.springframework.stereotype.Component;

/**
 * @author digz6666
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemKeyValueDataFiller {

    private final SystemKeyValueRepository systemKeyValueRepository;

    @PostConstruct
    private void fill() {
        for (String key : SystemKey.values())
            if (!systemKeyValueRepository.existsById(key))
                systemKeyValueRepository.save(new SystemKeyValue(key));
    }
}
