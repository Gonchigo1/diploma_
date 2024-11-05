package mn.astvision.starter.auth;

import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.AuthApi;
import mn.astvision.starter.api.request.LoginRequest;
import mn.astvision.starter.api.response.AuthResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@Slf4j
@SpringBootTest
public class AuthApiTest {

    @Autowired
    private AuthApi authApi;

    @Test
    public void testAuth() {
        ResponseEntity<?> responseEntity = authApi.login(LoginRequest.builder()
                .username("dev@astvision.mn")
                .password("dev123")
                .build());

        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getClass(), AuthResponse.class);

        AuthResponse authResponse = (AuthResponse) responseEntity.getBody();

        responseEntity = authApi.checkToken(authResponse.getToken());
//        log.info("responseEntity: " + responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
