package mn.astvision.starter.repository.oxfordThinkers;

import mn.astvision.starter.model.oxfordThinkers.Topic;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OxfordThinkersCustomRepository {
    List<Topic> list(String role,
                     String search,
                     Boolean using2fa,
                     Boolean emailVerified,
                     Boolean phoneVerified,
                     Boolean active,
                     Boolean deleted,
                     Pageable pageable);
}
