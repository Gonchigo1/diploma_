package mn.astvision.starter.dto.auth.response;

import lombok.Builder;
import lombok.Data;
import mn.astvision.starter.dto.auth.AuthenticationCredentials;

/**
 * @author digz6666
 */
@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private AuthenticationCredentials credentials;
}
