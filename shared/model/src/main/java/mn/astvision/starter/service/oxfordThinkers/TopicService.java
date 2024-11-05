    package mn.astvision.starter.service.oxfordThinkers;

    import lombok.RequiredArgsConstructor;
    import mn.astvision.starter.dao.oxfordThinkers.OxfordThinkersDao;
    import mn.astvision.starter.model.oxfordThinkers.Topic;
    import mn.astvision.starter.repository.lesson.LessonRepository;
    import mn.astvision.starter.repository.oxfordThinkers.TopicRepository;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class TopicService {

        private final TopicRepository topicRepository;
        private final OxfordThinkersDao oxfordThinkersDao;
        private final LessonRepository lessonRepository;


        public Topic save(Topic oxfordItem) {
            return topicRepository.save(oxfordItem);
        }
        public List<Topic> findAll() {
            return topicRepository.findAll();
        }

        public Optional<Topic> findById(String id) {
            return topicRepository.findById(id);
        }
        public void deleteById(String id) {
            topicRepository.deleteById(id);
        }
        public Iterable<Topic> list(
                String bookId,
                String name,
                PageRequest request
        ) {
            Iterable<Topic> listData = oxfordThinkersDao.list(
                    bookId,
                    name,
                    request
            );
            for (Topic topic : listData) {
                fillRelatedData(topic);
            }

            return listData;
        }

        public void fillRelatedData(Topic topic) {
            topic.setCountLessons(lessonRepository.countByTopicIdAndDeletedFalse(topic.getId()));
            topic.setLessons(lessonRepository.findByTopicIdAndDeletedFalse(topic.getId()));
        }
    }


