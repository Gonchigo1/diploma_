package mn.astvision.starter.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.auth.UserRepository;
import mn.astvision.starter.util.ListUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author digz6666
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public String getEmailById(String id) {
        if (ObjectUtils.isEmpty(id))
            return null;

        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.map(User::getEmail).orElse(null);
    }

    public User getByEmail(String email) {
        if (ObjectUtils.isEmpty(email))
            return null;

        return userRepository.findByEmailAndDeletedFalse(email.toLowerCase());
    }

    public List<String> findByEmail(String email) {
        List<String> userIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(email))
            userIds = ListUtils.getIds(userRepository.findAllByEmail(email));
        return userIds;
    }
}
