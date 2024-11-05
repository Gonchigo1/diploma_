package mn.astvision.starter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class BaseController {

    @Autowired
    protected LocalizationUtil localizationUtil;

    @Autowired
    protected ObjectMapper objectMapper;

    public ResponseEntity<?> badRequest() {
        return badRequestMessage(localizationUtil.buildMessage("error.BAD_REQUEST"));
    }

    public ResponseEntity<?> badRequestMessage(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    public ResponseEntity<?> errorPermission() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(localizationUtil.buildMessage("error.FORBIDDEN"));
    }

    public ResponseEntity<?> errorNotFound() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(localizationUtil.buildMessage("data.notFound"));
    }

    public ResponseEntity<?> errorDataExists() {
        return ResponseEntity.badRequest().body(localizationUtil.buildMessage("data.exists"));
    }

    public ResponseEntity<?> errorDatabase() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(localizationUtil.buildMessage("error.database"));
    }

    public ResponseEntity<?> serverError() {
        return serverErrorMessage(localizationUtil.buildMessage("error.INTERNAL_SERVER_ERROR"));
    }

    public ResponseEntity<?> serverErrorMessage(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
