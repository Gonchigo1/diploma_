package mn.astvision.starter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ContextLoadTest {

	@Test
	void contextLoads() {
		log.info("Test context loaded");
	}
}
