package mn.astvision.starter.datafiller.systemconfig;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.systemconfig.SystemCron;
import mn.astvision.starter.model.systemconfig.enums.SystemCronType;
import mn.astvision.starter.repository.systemconfig.SystemCronRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author digz6666
 */
@Component
@RequiredArgsConstructor
public class SystemCronDataFiller {

    private final SystemCronRepository systemCronRepository;

    @PostConstruct
    private void fill() {
        for (SystemCronType type : SystemCronType.values())
            if (!systemCronRepository.existsById(type))
                systemCronRepository.save(new SystemCron(type, false, null));
    }
}
